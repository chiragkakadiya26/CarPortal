package com.carportal.utils;

import com.carportal.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

//    private User user;

    public void mailSender(String to, String subject) throws MessagingException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("chiragkakadiya56@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
    }

    public void sendPasswordResetVerificationEmail(User user, String url) throws MessagingException, UnsupportedEncodingException {
        if (user == null) {
            // Handle the case where user is null, log an error, or throw an exception
            log.error("User object is null in sendPasswordResetVerificationEmail");
            return;
        }

        String subject = "Password Reset Request Verification";
        String mailContent = "<p> Hi, "+ user.getName()+ ", </p>"+
                "<p><b>You recently requested to reset your password,</b>"+ " " +
                "Please, follow the link below to complete the action.</p>"+
                "<a href=\"" +url+ "\">Reset password</a>"+
                "<p> Car Rental Service Portal";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("chiragkakadiya56@gmail.com");
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(mimeMessage);

        log.info("Mail send to : " + user.getEmail() + " " +"Successfully send message..");
    }

}
