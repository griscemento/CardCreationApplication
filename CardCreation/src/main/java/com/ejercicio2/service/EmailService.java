package com.ejercicio2.service;

import com.ejercicio2.dto.EmailRequest;
import com.ejercicio2.model.Card;
import com.ejercicio2.model.User;
import com.ejercicio2.security.SensitiveDataEncryptionUtil;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    public String subject = "National Bank customer information";

    public String body = "Hello, ${name} ${lastName}!\n\n"
            + "Thanks for operating with us. "
            + "Below are your card's CCV and PAN: "
            + "PAN: ${pan}  -  CCV: ${ccv}"
            + "Best regards,\n"
            + "National Bank";

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void setEmailUserData(User user, Card card) throws Exception {
        SecretKey secretKey = SensitiveDataEncryptionUtil.generateKey();

        String encryptedPersonalAccountNumber = SensitiveDataEncryptionUtil.encrypt(String.valueOf(card.getPersonalAccountNumber()), secretKey);
        String encryptedCCV = SensitiveDataEncryptionUtil.encrypt(String.valueOf(card.getCcv()), secretKey);

        Map<String, Object> userAndCardData = new HashMap<>();
        userAndCardData.put("name", user.getName());
        userAndCardData.put("lastName", user.getLastName());
        userAndCardData.put("pan", encryptedPersonalAccountNumber);
        userAndCardData.put("ccv", encryptedCCV);
    }

    public void sendEmail(EmailRequest emailRequest) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        setEmailUserData(emailRequest.getUser(), emailRequest.getCard());
        message.setTo(emailRequest.getTo());
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
