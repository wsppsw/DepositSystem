package com.example.deposit_system.mapper;

import com.example.deposit_system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 15:37
 */
@Mapper
public interface UserMapper {
    public User findbyName(String name);
    public User findbyPhone(String phone);
    public User findbyuid(Integer uid);
    public List<User> findall();
    public int AddUser(User user);
    public int updateUser(User user);
}
