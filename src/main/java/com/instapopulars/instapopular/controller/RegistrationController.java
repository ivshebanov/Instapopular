package com.instapopulars.instapopular.controller;

import com.instapopulars.instapopular.model.Role;
import com.instapopulars.instapopular.model.User;
import com.instapopulars.instapopular.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

@Controller
public class RegistrationController {

    private UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setDoNotUnsubscribe(0);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setMyPhotos(new LinkedList<>());
        userRepository.save(user);
        return "redirect:/login";
    }
}
