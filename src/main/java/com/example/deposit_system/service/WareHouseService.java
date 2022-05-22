package com.example.deposit_system.service;

import com.example.deposit_system.entity.Order;
import com.example.deposit_system.entity.WareHouse;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 21:47
 */

public interface WareHouseService {
    public List<WareHouse> findall();
    public PageInfo<WareHouse> findALL(Integer pageNum, Integer pageSize);
    public List<WareHouse> findOne(String city);
    public WareHouse findbyid(Integer hid);
    public int addWare(WareHouse wareHouse);
    public int modWare(WareHouse wareHouse);
    public int delWare(Integer hid);
}
