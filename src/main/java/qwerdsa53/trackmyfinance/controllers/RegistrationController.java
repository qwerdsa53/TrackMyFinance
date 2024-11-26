package qwerdsa53.trackmyfinance.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import qwerdsa53.trackmyfinance.user.UserRegistrationDto;
import qwerdsa53.trackmyfinance.user.UserRepo;
import qwerdsa53.trackmyfinance.user.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
