package ru.instapopular.repository;

import ru.instapopular.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.instapopular.model.MyGroup;
import ru.instapopular.model.Usr;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    List<Like> findAllByUsr(Usr usr);
}
