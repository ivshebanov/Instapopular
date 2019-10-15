package ru.instapopular.repository;

import ru.instapopular.model.MyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPhotoRepository extends JpaRepository<MyPhoto, Integer> {

}
