package com.example.deposit_system.controller;

import com.example.deposit_system.entity.Order;
import com.example.deposit_system.entity.User;
import com.example.deposit_system.entity.Worker;
import com.example.deposit_system.service.Imp.OrderServiceImp;
import com.example.deposit_system.service.Imp.WorkerServiceImp;
import com.github.pagehelper.PageInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * @author 欧皇表哥
 * @create 2022/5/4 17:12
 */

@Controller
public class WorkerController {

    @Autowired
    private WorkerServiceImp workerServiceImp;

    @Autowired
    private OrderServiceImp orderServiceImp;

    //后台登录界面
    @RequestMapping("/manlogin")
    public String tomanlogin(){
        return "vue_management";
    }

    //超级管理员界面
    @RequestMapping("/management/admin")
    public String tomanadmin(){
        return "man/vue_man_admin";
    }

    //后台订单界面
    @RequestMapping("/management/order")
    public String tomanorder(){
        return "man/vue_man_order";
    }

    //管理员工界面
    @RequestMapping("/management/worker")
    public String tomanworker(){
        return "man/vue_man_worker";
    }

    //管理仓库界面
    @RequestMapping("/management/warehouse")
    public String tomanwarehouse(){
        return "man/vue_man_warehouse";
    }

    //添加仓库界面
    @RequestMapping("/management/addwarehouse")
    public String tomanaddwarehouse(){
        return "man/vue_man_addwarehouse";
    }

    //员工界面
    @RequestMapping("/management/serviceworker")
    public String tomanserviceworker(){
        return "man/vue_man_serivceworker";
    }

    //员工修改信息页面
    @RequestMapping("/management/modifyuser")
    public String tomanmodifyuser(){
        return "man/vue_man_modifyUser";
    }

    //员工修改密码页面
    @RequestMapping("/management/modifypwd")
    public String tomanmodifypwd(){
        return "man/vue_man_modifyPwd";
    }

    //查看已登录信息
    @RequestMapping( "/AlreadyWorkerLogin")
    @ResponseBody
    public String toAlreadyWorkerLogin(HttpServletRequest request) throws JSONException {
        JSONObject object = new JSONObject();
        String name = (String) request.getSession().getAttribute("admin");
        Worker worker= workerServiceImp.findbyName(name);
        object.put("username",worker.getWname());
        object.put("userphone",worker.getWphone());
        object.put("userhead",worker.getWhead_img());

        // System.out.println(object);
        return object.toString();
    }

    //判断管理员后台登录
    @RequestMapping("/loginmanagement")
    @ResponseBody
    public String tologinmanagement(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Worker worker = new Worker();
        boolean result=username.matches("[0-9]+");
        if(result){
            worker = workerServiceImp.findbyPhone(username);
        }else {
            worker = workerServiceImp.findbyName(username);
        }

        if(worker==null){
            return "用户或密码不存在!";
        }else if(!worker.getWpwd().equals(password)){
            return "用户或密码不存在!";
        }else {
            request.getSession().setAttribute("admin",worker.getWname());
            return worker.getWtype();
        }
    }

    //判断后台登录的身份
    @RequestMapping("/checkadmin")
    @ResponseBody
    public String tocheckadmin(HttpServletRequest request){
        String name = (String) request.getSession().getAttribute("admin");
        Worker worker = workerServiceImp.findbyName(name);
        return worker.getWtype();
    }

    //修改密码
    @RequestMapping( "/modifyworkerpassword")
    @ResponseBody
    public String tomodifyworkerpassword(HttpServletRequest request) throws JSONException {
        String name = (String) request.getSession().getAttribute("admin");
        String oldpwd = request.getParameter("oldpwd");
        String newpwd1= request.getParameter("newpwd1");
        String newpwd2= request.getParameter("newpwd2");
        name = (String) request.getSession().getAttribute("admin");
        Worker worker = workerServiceImp.findbyName(name);

        if(!worker.getWpwd().equals(oldpwd)){
            return "旧密码不正确!";
        }else if(oldpwd.equals(newpwd1)){
            return "新旧密码不能一致!";
        }else if(!newpwd1.equals(newpwd2)){
            return "两次密码不一致!";
        }else {
            worker.setWpwd(newpwd1);
            workerServiceImp.modifyWorker(worker);
            return "true";
        }
    }

    //查看员工
    @RequestMapping("/showworkertype")
    @ResponseBody
    public PageInfo<Worker> toshowworkertype(HttpServletRequest request){
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Worker> pageInfo = workerServiceImp.findalltype("服务",pageNum,pageSize);
        return pageInfo;
    }

    //修改员工信息
    @RequestMapping("/modifyworkertype")
    @ResponseBody
    public PageInfo<Worker> tomodifyworkertype(HttpServletRequest request){
        String wid = request.getParameter("wid");
        String wname = request.getParameter("wname");
        String wpwd = request.getParameter("wpwd");
        String wtype = request.getParameter("wtype");
        String wphone = request.getParameter("wphone");
        String whead = request.getParameter("whead_img");
        Integer id = Integer.valueOf(wid);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        Worker worker = new Worker();
        worker.setWid(id);
        worker.setWname(wname);
        worker.setWphone(wphone);
        worker.setWpwd(wpwd);
        worker.setWtype(wtype);
        worker.setWhead_img(whead);
        workerServiceImp.modifyWorker(worker);
        PageInfo<Worker> pageInfo = workerServiceImp.findalltype("服务",pageNum,pageSize);
        return pageInfo;
    }

    //删除员工
    @RequestMapping("/delworkertype")
    @ResponseBody
    public PageInfo<Worker> todelworkertype(HttpServletRequest request){
        String id = request.getParameter("wid");
        Integer wid = Integer.valueOf(id);
        List<Order> list = orderServiceImp.findAll();
        if(list.size()!=0){
            for(Order o:list){
                if(o.getWid()==wid){
                    Order order = o;
                    order.setWid(1);
                    orderServiceImp.modifyordr(order);
                }
                continue;
            }
        }
        workerServiceImp.deleteWorker(wid);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Worker> pageInfo = workerServiceImp.findalltype("服务",pageNum,pageSize);
        return pageInfo;
    }

    //添加员工
    @RequestMapping("/addworkertype")
    @ResponseBody
    public PageInfo<Worker> toaddworkertype(HttpServletRequest request){
        String wname = request.getParameter("wname");
        String wphone = request.getParameter("wphone");
        Worker worker = new Worker();
        worker.setWpwd("123456");
        worker.setWtype("服务");
        worker.setWname(wname);
        worker.setWphone(wphone);
        int x= new Random().nextInt(12)+1;
        worker.setWhead_img("img_head/"+x+".png");
        workerServiceImp.addWorker(worker);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Worker> pageInfo = workerServiceImp.findalltype("服务",pageNum,pageSize);
        return pageInfo;
    }


}
