package ru.instapopular.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.instapopular.repository.UsrRepository;

@Service
public class UsrService implements UserDetailsService {

    private final UsrRepository usrRepository;

    public UsrService(UsrRepository usrRepository) {
        this.usrRepository = usrRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usrname) throws UsernameNotFoundException {
        return usrRepository.findByUsrname(usrname);
    }
}
