package ru.instapopular.repository;

import ru.instapopular.model.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrRepository extends JpaRepository<Usr, Integer> {

    Usr findByUsrname(String usrname);
}