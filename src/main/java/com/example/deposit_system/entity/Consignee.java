package com.example.deposit_system.entity;

import lombok.Data;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 15:24
 */

@Data
public class Consignee {
    private Integer cid;//收货人编码
    private String cname;//收货人姓名
    private String cphone;//收货人联系方式
    private String caddress;//收货人地址
    private String ccity;//收货人所在城市
}
