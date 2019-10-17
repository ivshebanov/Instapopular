package ru.instapopular.controller;

import ru.instapopular.model.Role;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.UsrRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Map;

import static java.util.Collections.singleton;

@Controller
public class RegistrationController {

    private UsrRepository usrRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UsrRepository usrRepository, PasswordEncoder passwordEncoder) {
        this.usrRepository = usrRepository;
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
    public String addUser(Usr usr, Map<String, Object> model) {
        Usr usrFromDb = usrRepository.findByUsrname(usr.getUsrname());
        if (usrFromDb != null) {
            model.put("message", "Пользователь существует!");
            return "login";
        }
        usr.setActive(true);
        usr.setPassword(passwordEncoder.encode(usr.getPassword()));
        usr.setInstPassword(usr.getInstPassword());
        usr.setDoNotUnsubscribe(0);
        usr.setMyPhotos(new ArrayList<>());
        usr.setRoles(singleton(Role.USER));
        usrRepository.save(usr);
        return "redirect:/login";
    }
}
