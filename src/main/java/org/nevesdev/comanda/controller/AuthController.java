package org.nevesdev.comanda.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.nevesdev.comanda.dto.user.UserLogin;
import org.nevesdev.comanda.dto.user.UserRegister;
import org.nevesdev.comanda.dto.user.UserResponse;
import org.nevesdev.comanda.model.user.User;
import org.nevesdev.comanda.service.security.AuthService;
import org.nevesdev.comanda.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "valid")
    public ResponseEntity<?> tokenIsValid(@RequestParam String token) {
        return ResponseEntity.ok(tokenService.validateToken(token));
    }

    @PostMapping("register")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRegister userRegister) {
        return ResponseEntity.status(201).body(authService.createUser(userRegister));
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String >> login(@RequestBody UserLogin userLogin) {
        try {
            Map<String, String> response = new HashMap<>();
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(userLogin.username() , userLogin.password());
            Authentication auth = this.authenticationManager.authenticate(login);
            String token = tokenService.generateToken((User) auth.getPrincipal());
            response.put("token", token);
            response.put("barId", ((User) auth.getPrincipal()).getBar().getId().toString());
            response.put("userRole", ((User) auth.getPrincipal()).getRole().toString());
            return ResponseEntity.status(200).body(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(400).body(Map.of("message", "Usuário ou senha incorretos"));
        }
    }
}
