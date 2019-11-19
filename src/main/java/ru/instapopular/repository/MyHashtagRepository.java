package ru.instapopular.repository;

import ru.instapopular.model.MyHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyHashtagRepository extends JpaRepository<MyHashtag, Integer> {

}
