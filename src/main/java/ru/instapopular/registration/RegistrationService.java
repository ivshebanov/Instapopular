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
import static ru.instapopular.Constant.RegistrationServiceConstant.INVALID_DATA;
import static ru.instapopular.Constant.RegistrationServiceConstant.USER_EXIST;
import static ru.instapopular.Constant.RegistrationServiceConstant.USER_IS_REGISTERED;
import static ru.instapopular.Constant.RegistrationServiceConstant.USER_NOT_REGISTERED;

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

    String createNewUsr(Usr usr) {
        try {
            String validMassage = validate(usr);
            if (validMassage != null) return validMassage;

            Usr usrFromDb = usrRepository.findByUsrname(usr.getUsrname());
            if (usrFromDb != null) return USER_EXIST;

            usr.setActive(true);
            usr.setPassword(passwordEncoder.encode(usr.getPassword()));
            usr.setInstPassword(usr.getInstPassword());
            usr.setDoNotUnsubscribe(0);
            usr.setRole(singleton(Roles.USER));
            usrRepository.save(usr);
            return USER_IS_REGISTERED;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return USER_NOT_REGISTERED;
        }
    }

    private String validate(Usr usr) {
        try {
            Set<ConstraintViolation<Usr>> validates = validator.validate(usr);
            if (validates.size() > 0) {
                StringBuilder massage = new StringBuilder().append(INVALID_DATA);
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
}
