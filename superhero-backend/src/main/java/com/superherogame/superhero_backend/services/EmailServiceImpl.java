package com.superherogame.superhero_backend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Value("${email.sender}")
    private String emailSender;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendEmail(String toUser, String subject, String message) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(emailSender);
            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendConfirmationEmail(String toUser, String link){
        String message = buildConfirmationEmail(link);
        sendEmail(toUser, "SuperheroGame - Confirmation Email", message);
    }

    private String buildConfirmationEmail(String link) {
        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Confirmación de Email</title>
    </head>
    <body style="margin:0; padding:0; background-color:#f4f6f8; font-family: Arial, sans-serif;">
    
        <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:40px 0;">
            <tr>
                <td align="center">
    
                    <table width="600" cellpadding="0" cellspacing="0" 
                           style="background-color:#ffffff; padding:40px; border-radius:8px;">
                        
                        <tr>
                            <td align="center" style="color:#1976d2; font-size:22px; font-weight:bold; padding-bottom:15px;">
                                ¡Qué bueno tenerte por aquí!
                            </td>
                        </tr>

                        <tr>
                            <td style="color:#333333; font-size:16px; padding-bottom:25px;">
                                Gracias por registrarte en <strong>SuperHeroGame</strong>.<br><br>
                                Solo falta un paso para comenzar tu aventura 🦸‍♂️🦸‍♂️.
                            </td>
                        </tr>

                        <tr>
                            <td align="center" style="padding-bottom:30px;">
                                <a href="%s"
                                   style="background-color:#1976d2;
                                          color:#ffffff;
                                          padding:14px 28px;
                                          text-decoration:none;
                                          border-radius:6px;
                                          font-size:16px;
                                          font-weight:bold;
                                          display:inline-block;">
                                   Confirmar mi email
                                </a>
                            </td>
                        </tr>

                        <tr>
                            <td style="color:#777777; font-size:13px; padding-bottom:30px;">
                                Este enlace expirará en unos minutos.<br>
                                Si no solicitaste esta cuenta, simplemente ignora este mensaje.
                            </td>
                        </tr>

                        <tr>
                            <td align="center" style="color:#999999; font-size:12px; padding-top:20px; border-top:1px solid #eeeeee;">
                                © SuperHeroGame — All Rights Reserved
                            </td>
                        </tr>

                    </table>

                </td>
            </tr>
        </table>

    </body>
    </html>
    """.formatted(link);
    }

}
