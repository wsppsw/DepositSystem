package com.example.deposit_system.service.Imp;

import com.example.deposit_system.entity.UC;
import com.example.deposit_system.mapper.UCMapper;
import com.example.deposit_system.service.UCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 13:50
 */

@Service
public class UCServiceImp implements UCService {

    @Autowired
    private UCMapper ucMapper;

    @Override
    public UC findbyuid(Integer uid) {
        return ucMapper.findbyuid(uid);
    }

    @Override
    public int addUC(UC uc) {
        return ucMapper.addUC(uc);
    }

    @Override
    public int deleteUC(Integer cid) {
        return ucMapper.deleteUC(cid);
    }

    @Override
    public List<UC> findall(Integer uid) {
        return ucMapper.findall(uid);
    }
}
