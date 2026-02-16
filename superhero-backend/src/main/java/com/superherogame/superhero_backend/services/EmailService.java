package com.superherogame.superhero_backend.services;

public interface EmailService {
    public void sendEmail(String toUser, String subject,  String message);
}
