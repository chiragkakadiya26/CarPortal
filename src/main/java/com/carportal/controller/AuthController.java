package com.carportal.controller;

import com.carportal.enums.Roles;
import com.carportal.exception.ExistingUserNameAndEmailException;
import com.carportal.exception.WeakPasswordException;
import com.carportal.model.RefreshToken;
import com.carportal.model.User;
import com.carportal.payload.AdminDto;
import com.carportal.payload.RegisterDto;
import com.carportal.repository.UserRepository;
import com.carportal.request.JwtRequest;
import com.carportal.request.JwtResponse;
import com.carportal.filter.JwtService;
import com.carportal.request.RefreshTokenRequest;
import com.carportal.security.CustomUserDetails;
import com.carportal.service.RefreshTokenService;
import com.carportal.service.UserService;
import com.carportal.utils.PasswordValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest jwtRequest){

        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtService.generateJwtToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .refreshToken(refreshToken.getRefreshToken())
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/registeradmin")
    public ResponseEntity<AdminDto> registerAdmin(@RequestBody AdminDto adminDto){

        AdminDto saveAdmin = this.userService.registerAdmin(adminDto);
        return new ResponseEntity<>(saveAdmin, HttpStatus.CREATED);
    }

    @PostMapping("singup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto, BindingResult result){

        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new ExistingUserNameAndEmailException("User with the provided username already exists");
        }  else if (userRepository.findByEmail(registerDto.getEmail()) != null) {
            throw new ExistingUserNameAndEmailException("User with the provided email already exists");
        }else if (!PasswordValidator.matcher(registerDto.getPassword()).matches()) {
            throw new WeakPasswordException("Password Must Contains Minimum Eight Characters," +
                    " At Least One Uppercase Letter, One Lowercase Letter And One Number!");
        }else if(result.hasErrors()){
            Map<String, String> validationErrors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                validationErrors.put(fieldName, message);
            });
            return ResponseEntity.badRequest().body(validationErrors);
        }else {
            log.info("Registration of new user");
            User newUser = User.builder().name(registerDto.getName()).username(registerDto.getUsername())
                    .email(registerDto.getEmail()).password(this.passwordEncoder.encode(registerDto.getPassword()))
                    .mobileNo(registerDto.getMobileNo()).birthDate(registerDto.getBirthDate()).role(Roles.USER).active(true).build();

            User registerUser = this.userRepository.save(newUser);

            RegisterDto responseDto = this.modelMapper.map(registerUser, RegisterDto.class);

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        }
    }

    @PostMapping("/refresh")
    public JwtResponse refreshJwtToken(@RequestBody RefreshTokenRequest request){

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();
        UserDetails userDetails = new CustomUserDetails(user);

        String token = this.jwtService.generateJwtToken(userDetails);

        return JwtResponse
                .builder()
                .refreshToken(refreshToken.getRefreshToken())
                .jwtToken(token)
                .username(user.getEmail())
                .build();
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }
}
