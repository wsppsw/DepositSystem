package com.example.deposit_system.service;

import com.example.deposit_system.entity.Order;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 21:37
 */

public interface OrderSerivce {
    public List<Order> findallOrder(Integer uid);
    public List<Order> findAll();
    public PageInfo<Order> findallpage( Integer pageNum, Integer pageSize);
    public int addOrder(Order order);
    public int modifyordr(Order order);
    public int deleteOrder(String oid);
    //分页查询
    public PageInfo<Order> findallPage(Integer uid, Integer pageNum, Integer pageSize);
    public List<Order> findorderbywid(Integer wid);
    public PageInfo<Order> findpagebywid(Integer wid, Integer pageNum, Integer pageSize);
}
