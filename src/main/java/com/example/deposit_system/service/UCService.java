package com.example.deposit_system.service;

import com.example.deposit_system.entity.UC;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 13:49
 */

public interface UCService {
    public UC findbyuid(Integer uid);
    public int addUC(UC uc);
    public int deleteUC(Integer cid);
    public List<UC> findall(Integer uid);
}
