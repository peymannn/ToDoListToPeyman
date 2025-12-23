package com.peyman.todo.controller;

import com.peyman.todo.model.User;
import com.peyman.todo.repository.UserRepository;
import com.peyman.todo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");
        if (userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.badRequest().body(Map.of("error","username_taken"));
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        userRepository.save(u);
        return ResponseEntity.ok(Map.of("status","ok"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body){
        String username = body.get("username");
        String password = body.get("password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
