package com.example.deposit_system.mapper;

import com.example.deposit_system.entity.UC;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 13:45
 */

@Mapper
public interface UCMapper {
    public UC findbyuid(Integer uid);
    public int addUC(UC uc);
    public int deleteUC(Integer cid);
    public List<UC> findall(Integer uid);
}
