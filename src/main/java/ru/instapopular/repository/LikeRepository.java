package ru.instapopular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.instapopular.model.Like;
import ru.instapopular.model.Usr;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    List<Like> findAllByUsr(Usr usr);

    Like findLikeByUsrAndGuys(Usr usr, String guys);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Like SET active = false WHERE usr = :usr AND guys = :guys")
    void deactivateGuys(Usr usr, String guys);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Like SET active = true WHERE usr = :usr AND guys = :guys")
    void activateGuys(Usr usr, String guys);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Like SET countLike = :countLike, active = true WHERE usr = :usr AND guys = :guys")
    void updateCountLikeAndActiveByUsrAndGuys(Usr usr, String guys, Integer countLike);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("SELECT guys FROM Like WHERE usr = :usr AND active = :active")
    List<String> findGuysByUsrAndActive(Usr usr, boolean active);
}
