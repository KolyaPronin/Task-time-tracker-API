package com.kolya.TaskTimeTracker.auth.persistence;

import com.kolya.TaskTimeTracker.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByUsername(String username);
    void insert(User user);
}
