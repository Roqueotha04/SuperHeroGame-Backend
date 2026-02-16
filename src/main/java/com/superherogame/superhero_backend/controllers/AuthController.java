package com.superherogame.superhero_backend.controllers;

import com.superherogame.superhero_backend.dto.EmailRequest;
import com.superherogame.superhero_backend.dto.PasswordUpdateRequest;
import com.superherogame.superhero_backend.dto.ResetPasswordDTO;
import com.superherogame.superhero_backend.dto.UserAuthResponse;
import com.superherogame.superhero_backend.dto.auth.UserLoginDTO;
import com.superherogame.superhero_backend.dto.auth.UserRegisterDTO;
import com.superherogame.superhero_backend.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserAuthResponse> signUp(@RequestBody UserRegisterDTO userRegisterDto){
        return ResponseEntity.ok(authService.register(userRegisterDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserAuthResponse> signIn(@RequestBody UserLoginDTO userLoginDTO){
        return ResponseEntity.ok(authService.login(userLoginDTO));
    }

    @GetMapping("/confirmEmail/{token}")
    public ResponseEntity<Map<String, String>> confirmEmail(@PathVariable String token){
        authService.confirmUser(token);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Mail modificado con exito");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/requestConfirmationEmail")
    public ResponseEntity<Map<String, String>> resendConfirmationEmail(@RequestBody EmailRequest email){
        authService.resendConfirmationEmail(email.email());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Mail enviado correctamente");
        return ResponseEntity.ok(response);
    }

    @PostMapping ("/recoverPassword")
    public ResponseEntity<Map<String, String>> sendForgetPasswordEmail(@RequestBody EmailRequest email){
        authService.sendForgetPasswordEmail(email.email());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Mail enviado correctamente");
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/resetPassword/{token}")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, @PathVariable String token){
        authService.resetPassword(resetPasswordDTO.newPassword(), resetPasswordDTO.confirmPassword(), token);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contraseña modificada con exito");
        return ResponseEntity.ok(response);
    }
}
