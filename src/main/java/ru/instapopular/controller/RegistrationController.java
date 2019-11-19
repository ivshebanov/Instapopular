package ru.instapopular.controller;

import ru.instapopular.model.Role;
import ru.instapopular.model.User;
import ru.instapopular.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Map;

import static java.util.Collections.singleton;

@Controller
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String greeting() {
        return "main";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/registration")
    public String registration() {
        return "login";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "Пользователь существует!");
            return "login";
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setInstPassword(passwordEncoder.encode(user.getInstPassword()));
        user.setDoNotUnsubscribe(0);
        user.setMyPhotos(new ArrayList<>());
        user.setRoles(singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
