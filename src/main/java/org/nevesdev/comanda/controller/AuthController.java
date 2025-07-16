package org.nevesdev.comanda.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.nevesdev.comanda.dto.user.UserLogin;
import org.nevesdev.comanda.dto.user.UserRegister;
import org.nevesdev.comanda.model.user.User;
import org.nevesdev.comanda.service.security.AuthService;
import org.nevesdev.comanda.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        Boolean res = !tokenService.validateToken(token).equals("");
        if(!res) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("register")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<User> registerUser(@RequestBody @Valid UserRegister userRegister) {
        return ResponseEntity.status(201).body(authService.createUser(userRegister));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(userLogin.username() , userLogin.password());
        Authentication auth = this.authenticationManager.authenticate(login);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        Map<String, String> response = new HashMap<>();
        response.put("Token", token);
        response.put("userRole", ((User) auth.getPrincipal()).getRole().toString());
        return ResponseEntity.status(200).body(response);
    }
}
