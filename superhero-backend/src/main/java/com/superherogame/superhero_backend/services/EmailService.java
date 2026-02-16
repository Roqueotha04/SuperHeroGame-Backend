package com.superherogame.superhero_backend.services;

public interface EmailService {
    public void sendEmail(String toUser, String subject,  String message);
    public void sendConfirmationEmail(String toUser, String link);
    public void sendForgetPasswordEmail(String toUser, String link);
}
