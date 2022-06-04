package com.example.deposit_system.service.Imp;

import com.example.deposit_system.entity.Consignee;
import com.example.deposit_system.entity.Order;
import com.example.deposit_system.mapper.OrderMapper;
import com.example.deposit_system.service.OrderSerivce;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 21:39
 */

@Service
public class OrderServiceImp implements OrderSerivce {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> findallOrder(Integer uid) {
        return orderMapper.findallOrder(uid);
    }

    @Override
    public List<Order> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public Order findbyoid(String oid) {
        return orderMapper.findbyoid(oid);
    }

    @Override
    public PageInfo<Order> findallpage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> list = orderMapper.findAll();
        PageInfo<Order> pageInfo =new PageInfo<Order>(list);
        return pageInfo;
    }

    @Override
    public int addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    @Override
    public int modifyordr(Order order) {
        return orderMapper.modifyordr(order);
    }

    @Override
    public int deleteOrder(String oid) {
        return orderMapper.deleteOrder(oid);
    }

    @Override
    public PageInfo<Order> findallPage(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> list = orderMapper.findallOrder(uid);
        PageInfo<Order> pageInfo =new PageInfo<Order>(list);
        return pageInfo;
    }

    @Override
    public List<Order> findorderbywid(Integer wid) {
        return orderMapper.findorderbywid(wid);
    }

    @Override
    public PageInfo<Order> findpagebywid(Integer wid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> list = orderMapper.findorderbywid(wid);
        PageInfo<Order> pageInfo =new PageInfo<Order>(list);
        return pageInfo;
    }
}
