package com.example.deposit_system.entity;

import lombok.Data;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 15:12
 */
@Data
//用户
public class User {
    private Integer uid;//用户编号
    private String uname;//用户姓名
    private String pwd;//用户密码
    private String security1;//密保问题1
    private String answer1;//答案1
    private String security2;//密保问题2
    private String answer2;//答案2
    private String phone;//手机号
    private String img_head;//头像
}
