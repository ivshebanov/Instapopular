package ru.instapopular.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.instapopular.model.Roles;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.UsrRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singleton;

@Controller
public class RegistrationController {

    private final UsrRepository usrRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public RegistrationController(UsrRepository usrRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.usrRepository = usrRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
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
        Set<ConstraintViolation<Usr>> validates = validator.validate(usr);
        if (validates.size() > 0) {
            for (ConstraintViolation<Usr> validate : validates) {
                model.put("message", "Вы ввели невалидные данные " + validate.getMessage() + ": " + validate.getInvalidValue());
            }
            return "login";
        }

        Usr usrFromDb = usrRepository.findByUsrname(usr.getUsrname());
        if (usrFromDb != null) {
            model.put("message", "Пользователь существует!");
            return "login";
        }
        usr.setActive(true);
        usr.setPassword(passwordEncoder.encode(usr.getPassword()));
        usr.setInstPassword(usr.getInstPassword());
        usr.setDoNotUnsubscribe(0);
        usr.setRole(singleton(Roles.USER));
        usrRepository.save(usr);
        model.put("message", "Пользователь зарегистрирован!");
        return "login";
    }
}
