package com.example.deposit_system.service.Imp;

import com.example.deposit_system.entity.User;
import com.example.deposit_system.mapper.UserMapper;
import com.example.deposit_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 15:46
 */

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findbyName(String name) {
        return userMapper.findbyName(name);
    }

    @Override
    public User findbyPhone(String phone) {
        return userMapper.findbyPhone(phone);
    }

    @Override
    public User findbyuid(Integer uid) {
        return userMapper.findbyuid(uid);
    }

    @Override
    public List<User> findall() {
        return userMapper.findall();
    }

    @Override
    public int AddUser(User user) {
        return userMapper.AddUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }
}
