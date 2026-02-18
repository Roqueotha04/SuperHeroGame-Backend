package com.superherogame.superhero_backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${BREVOTOKEN}")
    private String apiKey;

    @Value("${email.sender}")
    private String emailSender;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BREVO_URL = "https://api.brevo.com/v3/smtp/email";

    @Override
    @Async
    public void sendEmail(String toUser, String subject, String htmlContent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        // Estructura según la API de Brevo
        Map<String, Object> body = new HashMap<>();
        body.put("sender", Map.of("name", "SuperHero Game", "email", emailSender));
        body.put("to", List.of(Map.of("email", toUser)));
        body.put("subject", subject);
        body.put("htmlContent", htmlContent);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(BREVO_URL, entity, String.class);
            logger.info("📧 Email enviado con éxito a: {}", toUser);
        } catch (Exception e) {
            logger.error("❌ Error al enviar email a {}: {}", toUser, e.getMessage());
        }
    }

    @Override
    @Async
    public void sendConfirmationEmail(String toUser, String link) {
        String message = buildConfirmationEmail(link);
        sendEmail(toUser, "SuperheroGame - Confirmation Email", message);
    }

    @Override
    @Async
    public void sendForgetPasswordEmail(String toUser, String link) {
        String message = buildPasswordResetEmail(link);
        sendEmail(toUser, "SuperheroGame - Forget Password", message);
    }

    private String buildConfirmationEmail(String link) {
        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Confirmación de Email</title>
    </head>
    <body style="margin:0; padding:0; font-family: Arial, sans-serif; background-color:#f4f6f8;">

        <div style="display:none; max-height:0px; overflow:hidden;">
            Confirmá tu cuenta y empezá tu aventura en SuperHeroGame 🦸‍♂️
        </div>

        <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:20px 0;">
            <tr>
                <td align="center">

                    <table width="600" cellpadding="0" cellspacing="0" 
                           style="background-color:#ffffff; padding:20px; border-radius:8px;">

                        <tr>
                            <td align="center" style="color:#1976d2; font-size:22px; font-weight:bold; padding-bottom:10px;">
                                ¡Qué bueno tenerte por aquí!
                            </td>
                        </tr>

                        <tr>
                            <td style="color:#333333; font-size:16px; padding-bottom:15px;">
                                Gracias por registrarte en <strong>SuperHeroGame</strong>.<br>
                                Solo falta un paso para comenzar tu aventura 🦸‍♂️🦸‍♂️.
                            </td>
                        </tr>

                        <tr>
                            <td align="center" style="padding-bottom:20px;">
                                <a href="%s"
                                   style="background-color:#1976d2;
                                          color:#ffffff;
                                          padding:12px 24px;
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
                            <td style="color:#777777; font-size:13px; padding-bottom:20px;">
                                Este enlace expirará en unos minutos.<br>
                                Si no solicitaste esta cuenta, simplemente ignora este mensaje.
                            </td>
                        </tr>

                        <tr>
                            <td align="center" style="color:#999999; font-size:12px; padding-top:10px; border-top:1px solid #eeeeee;">
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

    private String buildPasswordResetEmail(String link) {
        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Restablecer Contraseña</title>
    </head>
    <body style="margin:0; padding:0; font-family: Arial, sans-serif; background-color:#f4f6f8;">

        <div style="display:none; max-height:0px; overflow:hidden;">
            Restablecé tu contraseña en SuperHeroGame 🦸‍♂️
        </div>

        <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:20px 0;">
            <tr>
                <td align="center">

                    <table width="600" cellpadding="0" cellspacing="0" 
                           style="background-color:#ffffff; padding:20px; border-radius:8px;">

                        <tr>
                            <td align="center" style="color:#1976d2; font-size:22px; font-weight:bold; padding-bottom:10px;">
                                Restablecé tu contraseña
                            </td>
                        </tr>

                        <tr>
                            <td style="color:#333333; font-size:16px; padding-bottom:15px;">
                                Recibimos una solicitud para restablecer tu contraseña de <strong>SuperHeroGame</strong>.
                            </td>
                        </tr>

                        <tr>
                            <td align="center" style="padding-bottom:20px;">
                                <a href="%s"
                                   style="background-color:#1976d2;
                                          color:#ffffff;
                                          padding:12px 24px;
                                          text-decoration:none;
                                          border-radius:6px;
                                          font-size:16px;
                                          font-weight:bold;
                                          display:inline-block;">
                                   Restablecer contraseña
                                </a>
                            </td>
                        </tr>

                        <tr>
                            <td style="color:#777777; font-size:13px; padding-bottom:20px;">
                                Si no solicitaste este cambio, simplemente ignora este mensaje.<br>
                                El enlace expirará en 30 minutos.
                            </td>
                        </tr>

                        <tr>
                            <td align="center" style="color:#999999; font-size:12px; padding-top:10px; border-top:1px solid #eeeeee;">
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
