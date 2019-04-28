package com.instapopulars.instapopular.repository;

import com.instapopulars.instapopular.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}