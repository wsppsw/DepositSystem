package com.example.deposit_system.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 15:27
 */
@Data
public class Order {
    private String oid;//订单流水编码
    private Integer uid;//用户
    private Integer wid;//员工
    private Integer cid;//收货人
    private Integer hid;//仓库
    private Date starttime;//订单开始时间
    private Date endtime;//订单结束时间
    private Integer status;//订单的完成状态
    private Integer audit;//订单的审核状态
    private String items;//寄存的物品
    private String items_img;//寄存的物品图片描述
    private String otype;//订单的服务类型
    private String cmessage;//订单的备注

    private String caddress; //收货人地址
    private String cphone;//收货人电话
    private String cname;//收货人姓名
    private String ccity;//收货人所在城市
}
