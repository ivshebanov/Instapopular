package ru.instapopular.registration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.instapopular.groups.GroupsService;
import ru.instapopular.model.Roles;
import ru.instapopular.model.Usr;
import ru.instapopular.repository.UsrRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static java.util.Collections.singleton;

@Service
public class RegistrationService {

    private static final Logger logger = LogManager.getLogger(GroupsService.class);

    private final UsrRepository usrRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public RegistrationService(UsrRepository usrRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.usrRepository = usrRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    String validate(Usr usr) {
        try {
            Set<ConstraintViolation<Usr>> validates = validator.validate(usr);
            if (validates.size() > 0) {
                StringBuilder massage = new StringBuilder().append("Вы ввели невалидные данные, ");
                for (ConstraintViolation<Usr> validate : validates) {
                    massage.append(validate.getMessage()).append(" ").append(validate.getInvalidValue()).append("/");
                }
                return massage.toString();
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    Usr findByUsrname(Usr usr) {
        try {
            return usrRepository.findByUsrname(usr.getUsrname());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    boolean createNewUsr(Usr usr) {
        try {
            usr.setActive(true);
            usr.setPassword(passwordEncoder.encode(usr.getPassword()));
            usr.setInstPassword(usr.getInstPassword());
            usr.setDoNotUnsubscribe(0);
            usr.setRole(singleton(Roles.USER));
            usrRepository.save(usr);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
