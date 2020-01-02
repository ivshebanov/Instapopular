package ru.instapopular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.instapopular.model.Client;
import ru.instapopular.model.Usr;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findClientByUsrAndClientName(Usr usr, String clientName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("SELECT clientName FROM Client WHERE usr = :usr AND active = :active")
    List<String> findClientNameByUsrAndActive(Usr usr, boolean active);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("SELECT clientName FROM Client WHERE usr = :usr")
    List<String> findClientNameByUsr(Usr usr);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Client SET frequency = :frequency WHERE usr = :usr AND clientName = :clientName")
    void updateFrequencyByUsrAndClientName(Usr usr, String clientName, Integer frequency);
}
