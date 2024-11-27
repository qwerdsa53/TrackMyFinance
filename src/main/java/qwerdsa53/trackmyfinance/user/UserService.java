package qwerdsa53.trackmyfinance.user;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Async
    public void registerUser(UserRegistrationDto userDto) {
        System.out.println("Executing in thread: " + Thread.currentThread().getName());
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole("USER");
        user.setEnabled(true);

        try {
            userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists");
        }
    }

}