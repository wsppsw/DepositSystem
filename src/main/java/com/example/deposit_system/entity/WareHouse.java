package com.example.deposit_system.entity;

import lombok.Data;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 15:21
 */
@Data
public class WareHouse {
    private Integer hid;//仓库编号
    private String address;//仓库地址
    private String city;//仓库所在城市
    private String coordinate;//仓库所在坐标
    private Double hsize;//仓库大小
}
