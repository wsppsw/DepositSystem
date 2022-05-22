package com.example.deposit_system.mapper;

import com.example.deposit_system.entity.User;
import com.example.deposit_system.entity.Worker;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 21:51
 */

@Mapper
public interface WorkerMapper {
    public List<Worker> findall(String wtype);
    public PageInfo<Worker> findalltype(String wtype,Integer pageNum, Integer pageSize);
    public Worker findbyName(String name);
    public Worker findbyPhone(String phone);
    public int addWorker(Worker worker);
    public int modifyWorker(Worker worker);
    public int deleteWorker(Integer wid);

}
