package ru.instapopular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.instapopular.model.MyGroup;
import ru.instapopular.model.Usr;

import java.util.List;

public interface MyGroupRepository extends JpaRepository<MyGroup, Integer> {

    List<MyGroup> findAllByUsr(Usr usr);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE MyGroup SET active = false WHERE usr = :usr AND myGroup = :my_group")
    void deactivateMyGroup(@Param("usr") Usr usr, @Param("my_group") String groupName);
}
