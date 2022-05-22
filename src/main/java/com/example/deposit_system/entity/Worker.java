package com.example.deposit_system.entity;

import lombok.Data;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 15:17
 */

@Data
public class Worker {
    private Integer wid;//员工编码
    private String wname;//员工姓名
    private String wpwd;//员工密码
    private String wphone;//员工手机号
    private String wtype;//员工类型
    private String whead_img;//员工头像
}
