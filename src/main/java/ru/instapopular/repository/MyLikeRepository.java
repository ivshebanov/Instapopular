package ru.instapopular.repository;

import ru.instapopular.model.MyLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyLikeRepository extends JpaRepository<MyLike, Integer> {

}
