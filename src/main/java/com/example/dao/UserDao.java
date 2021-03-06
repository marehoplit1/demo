package com.example.dao;

import com.example.model.City;
import com.example.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mgudelj
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {

    User getUserByUsername(String username);
}
