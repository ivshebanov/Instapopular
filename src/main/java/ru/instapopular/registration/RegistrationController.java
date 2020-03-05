package ru.instapopular.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.MyGroupRepository;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;
    private final MyGroupRepository myGroupRepository;

    public RegistrationController(RegistrationService registrationService,
                                  MyGroupRepository myGroupRepository) {

        this.registrationService = registrationService;
        this.myGroupRepository = myGroupRepository;
    }

    @GetMapping("/")
    public String greeting(@AuthenticationPrincipal Usr usr,
                           Model model) {
        Map<Object, Object> data = new HashMap<>();

        if (usr != null) {
            data.put("profile", usr);
            data.put("messages", myGroupRepository.findAllByUsr(usr));
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", true);
        return "index";
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
    public ResponseEntity addUser(Usr usr, Map<String, Object> model) {
        String resultMassage = registrationService.createNewUsr(usr);
        model.put("message", resultMassage);
        return ResponseEntity.ok().build();
    }
}
