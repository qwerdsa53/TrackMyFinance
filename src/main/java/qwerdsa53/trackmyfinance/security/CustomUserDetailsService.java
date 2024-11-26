package qwerdsa53.trackmyfinance.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import qwerdsa53.trackmyfinance.user.User;
import qwerdsa53.trackmyfinance.user.UserRepo;


import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user by username: {}", username);

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found for username: {}", username);
                    return new UsernameNotFoundException("User not found");
                });

        log.debug("User found: {}", user);

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
