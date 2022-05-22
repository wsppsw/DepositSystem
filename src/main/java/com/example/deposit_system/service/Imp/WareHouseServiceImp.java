package com.example.deposit_system.service.Imp;

import com.example.deposit_system.entity.WareHouse;
import com.example.deposit_system.mapper.WareHouseMapper;
import com.example.deposit_system.service.WareHouseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 21:48
 */

@Service
public class WareHouseServiceImp implements WareHouseService {

    @Autowired
    private WareHouseMapper wareHouseMapper;

    @Override
    public List<WareHouse> findall() {
        return wareHouseMapper.findall();
    }

    @Override
    public PageInfo<WareHouse> findALL(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WareHouse> list = wareHouseMapper.findall();
        PageInfo<WareHouse> pageInfo = new PageInfo<WareHouse>(list);
        return pageInfo;
    }

    @Override
    public List<WareHouse> findOne(String city) {
        return wareHouseMapper.findOne(city);
    }

    @Override
    public WareHouse findbyid(Integer hid) {
        return wareHouseMapper.findbyid(hid);
    }

    @Override
    public int addWare(WareHouse wareHouse) {
        return wareHouseMapper.addWare(wareHouse);
    }

    @Override
    public int modWare(WareHouse wareHouse) {
        return wareHouseMapper.modWare(wareHouse);
    }

    @Override
    public int delWare(Integer hid) {
        return wareHouseMapper.delWare(hid);
    }
}
