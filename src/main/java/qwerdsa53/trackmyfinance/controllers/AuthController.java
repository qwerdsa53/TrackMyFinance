package qwerdsa53.trackmyfinance.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import qwerdsa53.trackmyfinance.security.BlacklistService;
import qwerdsa53.trackmyfinance.security.JwtResponse;
import qwerdsa53.trackmyfinance.security.JwtTokenProvider;
import qwerdsa53.trackmyfinance.user.LoginRequest;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistService blacklistService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            log.info("Authenticated user: {}", authentication.getName());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);

            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token));
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            long remainingTime = jwtTokenProvider.getRemainingTimeFromToken(token);

            if (remainingTime > 0) {
                blacklistService.addToBlacklist(token, remainingTime);
                return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has already expired");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
    }

}