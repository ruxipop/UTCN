package com.bianca.dao;

import com.bianca.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertUser(User user) {
        Object[] sqlArgsUsers = {user.getUsername(), user.getPassword(), 1};
        Object[] sqlArgsRoles = {user.getUsername(), "USER"};
        String sqlUsers = "INSERT INTO users(username, password, enabled) VALUES (?, ?, ?)";
        String sqlRoles = "INSERT INTO authorities(username, authority) VALUES (?, ?)";
        jdbcTemplate.update(sqlUsers, sqlArgsUsers);
        jdbcTemplate.update(sqlRoles, sqlArgsRoles);
    }
}
