package com.example.deposit_system.mapper;

import com.example.deposit_system.entity.Consignee;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 12:41
 */

@Mapper
public interface ConsigneeMapper {
    public Consignee findbycid(Integer cid);
    public List<Consignee> findC(Integer uid);
    public List<Consignee> findall();
    public Consignee find(Consignee consignee);
    public int addConsignee(Consignee consignee);
    public int modeifyConsignee(Consignee consignee);
    public int deleteConsignee(Integer cid);
    //分页查询
    public PageInfo<Consignee> findallPage(Integer uid,Integer pageNum, Integer pageSize);
}
