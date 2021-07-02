package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mgudelj
 */
@Service
public class UserServiceImpl implements UserService {

    UserDao userDao ;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    @Override
    public User getByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }
}
