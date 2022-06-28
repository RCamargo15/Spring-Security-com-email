package com.rcamargo15.cursospringsecurity.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender javaMailSender;
    private final static Logger LOG = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void sendEmail(String to, String email) {
        try{
            MimeMessage mm = javaMailSender.createMimeMessage();
            MimeMessageHelper mmh = new MimeMessageHelper(mm, "UTF-8");

            mmh.setText(email, true);
            mmh.setTo(to);
            mmh.setSubject("Confirm your e-mail");
            mmh.setFrom("rafael.camargo384@gmail.com");

            javaMailSender.send(mm);
        } catch(MessagingException ex){
            LOG.info("Failed to send e-mail: {}", ex.getMessage());
            throw new IllegalStateException("Failed to send e-mail");
        }

    }
}
