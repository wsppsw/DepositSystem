package com.example.deposit_system.service;

import com.example.deposit_system.entity.Citys;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/16 14:32
 */

public interface CitysService {
    public List<Citys> findCity1();
    public List<Citys> findCity2();
    public List<Citys> findCity3();
    public int addcity1(Citys citys);
    public int addcity2(Citys citys);
    public int addcity3(Citys citys);
}
