package com.example.deposit_system.service;

import com.example.deposit_system.entity.User;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 15:46
 */

public interface UserService {
    public User findbyName(String name);
    public User findbyPhone(String phone);
    public User findbyuid(Integer uid);
    public List<User> findall();
    public int AddUser(User user);
    public int updateUser(User user);
}
