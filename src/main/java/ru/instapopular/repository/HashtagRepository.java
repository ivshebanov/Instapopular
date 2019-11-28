package ru.instapopular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.instapopular.model.Hashtag;
import ru.instapopular.model.Usr;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

    Hashtag findHashtagByUsrAndHashtag(Usr usr, String hashtag);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Hashtag SET active = false WHERE usr = :usr AND hashtag = :hashtag")
    void deactivateHashtag(Usr usr, String hashtag);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Hashtag SET active = true WHERE usr = :usr AND hashtag = :hashtag")
    void activateHashtag(Usr usr, String hashtag);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("SELECT hashtag FROM Hashtag WHERE usr = :usr AND active = :active")
    List<String> findHashtagsByUsrAndActive(Usr usr, boolean active);
}
