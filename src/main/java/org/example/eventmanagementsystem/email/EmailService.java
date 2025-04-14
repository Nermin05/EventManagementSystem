package org.example.eventmanagementsystem.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(String sendTo, String verificationEmail) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setTo(sendTo);
            mimeMessageHelper.setSubject("Verify your account");
            mimeMessageHelper.setText("Your verification code:" + verificationEmail);
            javaMailSender.send(mimeMailMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
