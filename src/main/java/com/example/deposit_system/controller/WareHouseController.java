package com.example.deposit_system.controller;

import com.example.deposit_system.entity.Consignee;
import com.example.deposit_system.entity.User;
import com.example.deposit_system.entity.WareHouse;
import com.example.deposit_system.service.Imp.WareHouseServiceImp;
import com.github.pagehelper.PageInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 欧皇表哥
 * @create 2022/5/4 13:47
 */

@Controller
public class WareHouseController {

    @Autowired
    private WareHouseServiceImp wareHouseServiceImp;

    //根据hid获取仓库
    @RequestMapping( "/getwarehousebyid")
    @ResponseBody
    public String togetwarehousebyid(HttpServletRequest request) throws JSONException {
        String id = request.getParameter("hid");
        Integer hid = Integer.parseInt(id);
        WareHouse wareHouse = wareHouseServiceImp.findbyid(hid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address_",wareHouse.getAddress());
        jsonObject.put("scity",wareHouse.getCity());
        jsonObject.put("coor",wareHouse.getCoordinate());
       // System.out.println(jsonObject);
        return jsonObject.toString();
    }

    //后台获取所有仓库
    @RequestMapping( "/getallwarehouse")
    @ResponseBody
    public PageInfo<WareHouse> togetallwarehouse(HttpServletRequest request) throws JSONException {
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<WareHouse> pageInfo = wareHouseServiceImp.findALL(pageNum,pageSize);
        return pageInfo;
    }

    //后台添加仓库
    @RequestMapping( "/addwarehouse")
    @ResponseBody
    public String toaddwarehouse(HttpServletRequest request) throws JSONException {
       String city = request.getParameter("city");
       String address = request.getParameter("address");
       String coordinate = request.getParameter("coordinate");
       Double hszie = Double.valueOf(request.getParameter("hsize"));
       WareHouse wareHouse = new WareHouse();
       wareHouse.setHsize(hszie);
       wareHouse.setCity(city);
       wareHouse.setAddress(address);
       wareHouse.setCoordinate(coordinate);
       wareHouseServiceImp.addWare(wareHouse);
       return "true";
    }

    //后台修改仓库
    @RequestMapping( "/modifywarehouse")
    @ResponseBody
    public PageInfo<WareHouse> tomodifywarehouse(HttpServletRequest request) throws JSONException {
        Integer hid = Integer.valueOf(request.getParameter("hid"));
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String coordinate = request.getParameter("coordinate");
        Double hszie = Double.valueOf(request.getParameter("hsize"));
        WareHouse wareHouse = new WareHouse();
        wareHouse.setHid(hid);
        wareHouse.setHsize(hszie);
        wareHouse.setCity(city);
        wareHouse.setAddress(address);
        wareHouse.setCoordinate(coordinate);
        wareHouseServiceImp.modWare(wareHouse);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<WareHouse> pageInfo = wareHouseServiceImp.findALL(pageNum,pageSize);
        return pageInfo;
    }

    //后台删除仓库
    @RequestMapping( "/delwarehouse")
    @ResponseBody
    public PageInfo<WareHouse> todelwarehouse(HttpServletRequest request) throws JSONException {
        Integer hid = Integer.valueOf(request.getParameter("hid"));
        wareHouseServiceImp.delWare(hid);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<WareHouse> pageInfo = wareHouseServiceImp.findALL(pageNum,pageSize);
        return pageInfo;
    }
}
