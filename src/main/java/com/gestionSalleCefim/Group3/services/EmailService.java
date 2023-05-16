package com.gestionSalleCefim.Group3.services;

import com.gestionSalleCefim.Group3.entities.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    /*@Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;*/
    public JavaMailSender emailSender;
    /*public EmailService(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }*/

    @ResponseBody
    public String sendRegisterMail(User user, String randomCode) throws MessagingException {

        String toAddress = user.getEmail();
        String fromAddress = "fake@gmail.com";
        String subject = "Veuillez confirmer votre adresse email";
        String content = "[[name]],<br>"
                + "Veuillez utiliser le code suivant pour confirmer votre adresse email: [[randomCode]].<br>"
                + "Merci,<br>"
                + "la Team Quiode.";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        content = content.replace("[[randomCode]]", randomCode);

        helper.setText(content, true);

        emailSender.send(message);

        return "Email sent";
    }

    @ResponseBody
    public String sendForgotPasswordMail(User user, String resetToken) throws MessagingException {

        String link = "http://localhost:4200" + "/reset?token=" +resetToken;
        String toAddress = user.getEmail();
        String fromAddress = "fake@gmail.com";
        String subject = "Réinitialisation du mot de passe";
        String content = "[[name]],<br>"
                + "Veuillez utiliser le lien suivant pour modifier votre mot de passe: <a href='[[link]]'>Lien de réinitialisation du mot de passe</a>"
                + "Merci,<br>"
                + "la Team Quiode.";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        content = content.replace("[[link]]", link);

        helper.setText(content, true);

        emailSender.send(message);

        return "Email sent";
    }
}