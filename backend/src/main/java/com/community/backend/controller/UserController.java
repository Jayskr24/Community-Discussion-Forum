package com.community.backend.controller;

import com.community.backend.model.User;
import com.community.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        User user = userRepository.findByEmail(loginData.get("email"));
        if (user == null || !user.getPassword().equals(loginData.get("password"))) {
            Map<String, String> err = new HashMap<>();
            err.put("message", "Galt credentials!");
            return ResponseEntity.status(401).body(err);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            Map<String, String> err = new HashMap<>();
            err.put("message", "Email already exists.");
            return ResponseEntity.status(400).body(err);
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    // Dynamic user profile updates endpoint (For Profile Pictures)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String id, @RequestBody User profileUpdate) {
        return userRepository.findById(id).map(user -> {
            user.setProfilePicUrl(profileUpdate.getProfilePicUrl());
            user.setUsername(profileUpdate.getUsername());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }
}