package org.example.eventmanagementsystem;

import lombok.RequiredArgsConstructor;
import org.example.eventmanagementsystem.jwt.JwtService;
import org.example.eventmanagementsystem.model.User;
import org.example.eventmanagementsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class EventManagementSystemApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public static void main(String[] args) {
        SpringApplication.run(EventManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        User user = userRepository.findByUsername("Nermin").get();
//        String token = jwtService.issueToken(user);
//        System.out.println(token);
//        System.out.println(jwtService.verifyToken(token));
//        user.setIssueKey(token);
//        userRepository.save(user);
    }
}
