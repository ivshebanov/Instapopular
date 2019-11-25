package ru.instapopular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.instapopular.model.MyGroup;
import ru.instapopular.model.Usr;

import java.util.List;

public interface MyGroupRepository extends JpaRepository<MyGroup, Integer> {

    List<MyGroup> findAllByUsr(Usr usr);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE MyGroup SET active = false WHERE usr = :usr AND myGroup = :groupName")
    void deactivateMyGroup(Usr usr, String groupName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE MyGroup SET active = true WHERE usr = :usr AND myGroup = :groupName")
    void activateMyGroup(Usr usr, String groupName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("SELECT myGroup FROM MyGroup WHERE usr = :usr AND active = :active")
    List<String> findMyGroupByUsrAndActive(Usr usr, boolean active);
}
