package ru.instapopular.registration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.instapopular.model.Usr;

import java.util.Map;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
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
        String resultMassage = registrationService.createNewUsr(usr);
        model.put("message", resultMassage);
        return "login";
    }
}
