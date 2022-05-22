package com.example.deposit_system.service.Imp;

import com.example.deposit_system.entity.Citys;
import com.example.deposit_system.mapper.CitysMapper;
import com.example.deposit_system.service.CitysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/16 14:33
 */

@Service
public class CitysSerivceImp implements CitysService {

    @Autowired
    private CitysMapper citysMapper;

    @Override
    public List<Citys> findCity1() {
        return citysMapper.findCity1();
    }

    @Override
    public List<Citys> findCity2() {
        return citysMapper.findCity2();
    }

    @Override
    public List<Citys> findCity3() {
        return citysMapper.findCity3();
    }

    @Override
    public int addcity1(Citys citys) {
        return citysMapper.addcity1(citys);
    }

    @Override
    public int addcity2(Citys citys) {
        return citysMapper.addcity2(citys);
    }

    @Override
    public int addcity3(Citys citys) {
        return citysMapper.addcity3(citys);
    }
}
