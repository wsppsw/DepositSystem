package com.example.deposit_system.service.Imp;

import com.example.deposit_system.entity.Consignee;
import com.example.deposit_system.entity.UC;
import com.example.deposit_system.mapper.ConsigneeMapper;
import com.example.deposit_system.service.ConsigneeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 13:07
 */

@Service
public class ConsigneeServiceImp implements ConsigneeService {

    @Autowired
    private ConsigneeMapper consigneeMapper;


    @Override
    public Consignee findbycid(Integer cid) {
        return consigneeMapper.findbycid(cid);
    }

    @Override
    public List<Consignee> findC(Integer uid) {
        return consigneeMapper.findC(uid);
    }

    @Override
    public List<Consignee> findall() {
        return consigneeMapper.findall();
    }

    @Override
    public Consignee find(Consignee consignee) {
        return consigneeMapper.find(consignee);
    }

    @Override
    public int addConsignee(Consignee consignee) {
        return consigneeMapper.addConsignee(consignee);
    }

    @Override
    public int modeifyConsignee(Consignee consignee) {
        return consigneeMapper.modeifyConsignee(consignee);
    }

    @Override
    public int deleteConsignee(Integer cid) {
        return consigneeMapper.deleteConsignee(cid);
    }

    @Override
    public PageInfo<Consignee> findallPage(Integer uid,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Consignee> list = consigneeMapper.findC(uid);
        PageInfo<Consignee> pageInfo =new PageInfo<Consignee>(list);
        return pageInfo;
    }
}
