package ru.instapopular.repository;

import ru.instapopular.model.MyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyGroupRepository extends JpaRepository<MyGroup, Integer> {

}
