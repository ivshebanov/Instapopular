package ru.instapopular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.instapopular.model.Photo;
import ru.instapopular.model.Usr;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    Photo findPhotoByUsrAndPhoto(Usr usr, String photo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Photo SET active = false WHERE usr = :usr AND photo = :photo")
    void deactivatePhoto(Usr usr, String photo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Photo SET active = true WHERE usr = :usr AND photo = :photo")
    void activatePhoto(Usr usr, String photo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("SELECT photo FROM Photo WHERE usr = :usr AND active = :active")
    List<String> findPhotosByUsrAndActive(Usr usr, boolean active);
}
