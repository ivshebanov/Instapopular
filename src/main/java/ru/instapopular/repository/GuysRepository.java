package ru.instapopular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.instapopular.model.Guys;
import ru.instapopular.model.Usr;

import java.util.List;

public interface GuysRepository extends JpaRepository<Guys, Integer> {

    List<Guys> findAllByUsr(Usr usr);

    Guys findLikeByUsrAndGuyName(Usr usr, String guys);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Guys SET active = false WHERE usr = :usr AND guyName = :guyName")
    void deactivateGuys(Usr usr, String guyName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Guys SET active = true WHERE usr = :usr AND guyName = :guyName")
    void activateGuys(Usr usr, String guyName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Guys SET countLike = :countLike, active = true WHERE usr = :usr AND guyName = :guyName")
    void updateCountLikeAndActiveByUsrAndGuyName(Usr usr, String guyName, Integer countLike);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("SELECT guyName FROM Guys WHERE usr = :usr AND active = :active")
    List<String> findGuysByUsrAndActive(Usr usr, boolean active);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("SELECT guyName FROM Guys WHERE usr = :usr")
    List<String> findGuyNameByUsr(Usr usr);
}
