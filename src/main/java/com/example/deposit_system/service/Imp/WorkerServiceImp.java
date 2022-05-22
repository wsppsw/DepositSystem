package com.example.deposit_system.service.Imp;

import com.example.deposit_system.entity.Worker;
import com.example.deposit_system.mapper.WorkerMapper;
import com.example.deposit_system.service.WorkerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 22:52
 */

@Service
public class WorkerServiceImp implements WorkerService {

    @Autowired
    private WorkerMapper workerMapper;

    @Override
    public List<Worker> findall(String wtype) {
        return workerMapper.findall(wtype);
    }

    @Override
    public PageInfo<Worker> findalltype(String wtype, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Worker> list = workerMapper.findall(wtype);
        PageInfo<Worker> pageInfo = new PageInfo<Worker>(list);
        return pageInfo;
    }

    @Override
    public Worker findbyName(String name) {
        return workerMapper.findbyName(name);
    }

    @Override
    public Worker findbyPhone(String phone) {
        return workerMapper.findbyPhone(phone);
    }

    @Override
    public int addWorker(Worker worker) {
        return workerMapper.addWorker(worker);
    }

    @Override
    public int modifyWorker(Worker worker) {
        return workerMapper.modifyWorker(worker);
    }

    @Override
    public int deleteWorker(Integer wid) {
        return workerMapper.deleteWorker(wid);
    }
}
