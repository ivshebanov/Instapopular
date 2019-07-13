package com.instapopulars.instapopular.repository;

import com.instapopulars.instapopular.model.MyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPhotoRepository extends JpaRepository<MyPhoto, Integer> {

}
