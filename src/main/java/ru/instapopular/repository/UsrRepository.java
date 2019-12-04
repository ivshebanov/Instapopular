package ru.instapopular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.instapopular.model.Usr;

public interface UsrRepository extends JpaRepository<Usr, Integer> {

    Usr findByUsrname(String usrname);
}