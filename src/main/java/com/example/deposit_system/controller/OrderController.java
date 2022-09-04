package com.example.deposit_system.controller;

import com.example.deposit_system.entity.*;
import com.example.deposit_system.mapper.WorkerMapper;
import com.example.deposit_system.service.Imp.*;
import com.example.deposit_system.utils.RedisUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 21:57
 */

@Controller
public class OrderController {

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private WorkerServiceImp workerServiceImp;

    @Autowired
    private OrderServiceImp orderServiceImp;

    @Autowired
    private ConsigneeServiceImp consigneeServiceImp;

    @Autowired
    private UCServiceImp ucServiceImp;

    @Autowired
    private RedisUtil redisUtil;

    private String imgsrc="";//统计图片

    //上传物品图片
    @RequestMapping("/fileupitems")
    @ResponseBody
    public String fileupitems(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        // replaceAll 用来替换windows中的\\ 为 /
        MultipartFile multipartFile1 = multipartFile;
       // System.out.println("名称："+multipartFile.getOriginalFilename()+"-类型："+multipartFile.getContentType());
        String[] filename =multipartFile.getOriginalFilename().split("\\.");
      //  System.out.println("截取类型"+filename[1]);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
      //  System.out.println("更名后"+uuid);

        imgsrc+=uuid+"."+filename[1]+",";
        //System.out.println(imgsrc);
        // 文件存储位置，文件的目录要存在才行，可以先创建文件目录，然后进行存储
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\items_img\\" +uuid+"."+filename[1];
        String filePath1 = System.getProperty("user.dir")+"\\target\\classes\\static\\items_img\\" +uuid+"."+filename[1];

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 文件存储
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File result = new File(filePath1.replaceAll("\\\\", "/"));//需要复制到的路径，以及图片的新命名+格式
        FileInputStream input = new FileInputStream(filePath.replaceAll("\\\\", "/"));//需要复制的原图的路径+图片名+ .png(这是该图片的格式)
        FileOutputStream out = new FileOutputStream(result);
        byte[] buffer = new byte[100];//一个容量，相当于打水的桶，可以自定义大小
        int hasRead = 0;
        while ((hasRead = input.read(buffer)) > 0) {
            out.write(buffer, 0, hasRead);//0：表示每次从0开始
        }
        // System.out.println(result.getAbsolutePath());
        input.close();//关闭
        out.close();

        //System.out.println(file.getAbsolutePath().replaceAll("\\\\", "/"));
        return file.getAbsolutePath().replaceAll("\\\\", "/");
        //return FileUploadUtil.upload(multipartFile).replaceAll("\\\\", "/");

    }

    //添加订单
    @RequestMapping("/addOrder")
    @ResponseBody
    public String toaddOrder(HttpServletRequest request) throws Exception {
        if(imgsrc.equals("")){
            Thread.currentThread().sleep(1000);
        }
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);
        String cid = request.getParameter("cid");
        String hid = request.getParameter("hid");
        String type = request.getParameter("otype");
        String cmessage = request.getParameter("cmessage");
        String items = request.getParameter("items");

        List<Worker> list = workerServiceImp.findall("服务");
        Collections.shuffle(list);
        Worker worker = new Worker();
        for (Worker w:list){
            worker = w;
        }
        Integer uid = user.getUid();
        Integer ccid = Integer.valueOf(cid);
        Integer hhid = Integer.valueOf(hid);
        Integer wid = worker.getWid();
        Integer otype = Integer.valueOf(type);
        String oid = UUID.randomUUID().toString().replaceAll("-","");
        Date date=new Date();
        SimpleDateFormat dateFormat =new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        dateFormat.format(date);

        Order order = new Order();
        order.setOid(oid);
        order.setUid(uid);
        order.setCid(ccid);
        order.setWid(wid);
        order.setHid(hhid);
        order.setStarttime(date);
        order.setStatus(0);
        order.setAudit(0);
        order.setItems(items);
        order.setItems_img(imgsrc);
        if(otype==1){
            order.setOtype("上门取件");
        }else{
            order.setOtype("自行取件");
        }
        order.setCmessage(cmessage);

        orderServiceImp.addOrder(order);
        System.out.println("订单号"+order.getOid()+"上传图片"+imgsrc);
        imgsrc = "";

        return "true";
    }

    //添加订单(不带cid)
    @RequestMapping("/addOrdernocid")
    @ResponseBody
    public String toaddOrdernocid(HttpServletRequest request) throws Exception {
        if(imgsrc.equals("")){
            Thread.currentThread().sleep(1000);
        }
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);
        String cname = request.getParameter("cname");
        String cphone = request.getParameter("cphone");
        String caddress = request.getParameter("caddress");
        String ccity = request.getParameter("ccity");
        Consignee cc = new Consignee();
        cc.setCname(cname);
        cc.setCphone(cphone);
        cc.setCaddress(caddress);
        cc.setCcity(ccity);
        consigneeServiceImp.addConsignee(cc);
        UC u = new UC();
        u.setUid(user.getUid());
        Integer cccid = consigneeServiceImp.find(cc).getCid();
        u.setCid(cccid);
        ucServiceImp.addUC(u);
        String hid = request.getParameter("hid");
        String type = request.getParameter("otype");
        String cmessage = request.getParameter("cmessage");
        String items = request.getParameter("items");
        List<Worker> list = workerServiceImp.findall("服务");
        Collections.shuffle(list);
        Worker worker = new Worker();
        for (Worker w:list){
            worker = w;
        }
        Integer uid = user.getUid();
        Integer hhid = Integer.valueOf(hid);
        Integer wid = worker.getWid();
        Integer otype = Integer.valueOf(type);
        String oid = UUID.randomUUID().toString().replaceAll("-","");
        Date date=new Date();
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.format(date);

        Order order = new Order();
        order.setOid(oid);
        order.setUid(uid);
        order.setCid(cccid);
        order.setWid(wid);
        order.setHid(hhid);
        order.setStarttime(date);
        order.setStatus(0);
        order.setAudit(0);
        order.setItems(items);
        order.setItems_img(imgsrc);
        if(otype==1){
            order.setOtype("上门取件");
        }else{
            order.setOtype("自行取件");
        }
        order.setCmessage(cmessage);

        orderServiceImp.addOrder(order);

        imgsrc = "";
        System.out.println("订单号"+order.getOid()+"上传图片"+imgsrc);
        return "true";
    }

    //加入待审核订单
    @RequestMapping("/checkOrder")
    @ResponseBody
    public String tocheckOrder(HttpServletRequest request) throws Exception {
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);

        List<Order> list = orderServiceImp.findallOrder(user.getUid());
        JSONArray array = new JSONArray();
        if(list.size()!=0){
            for(Order o:list){
                if(o.getAudit()==0){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid",o.getOid());
                    jsonObject.put("cid",o.getCid());
                    jsonObject.put("cname",o.getCname());
                    jsonObject.put("cphone",o.getCphone());
                    jsonObject.put("caddress",o.getCaddress());
                    jsonObject.put("otype",o.getOtype());
                    jsonObject.put("items",o.getItems());
                    array.put(jsonObject);
                }
            }
        }
        return array.toString();

    }

    //取消待审核订单
    @RequestMapping("/CancecheckOrder")
    @ResponseBody
    public String toCancecheckOrder(HttpServletRequest request) throws Exception {
        String oid = request.getParameter("oid");
        orderServiceImp.deleteOrder(oid);
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);

        List<Order> list = orderServiceImp.findallOrder(user.getUid());
        JSONArray array = new JSONArray();
        if(list.size()!=0){
            for(Order o:list){
                if(o.getAudit()==0){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid",o.getOid());
                    jsonObject.put("cid",o.getCid());
                    jsonObject.put("cname",o.getCname());
                    jsonObject.put("cphone",o.getCphone());
                    jsonObject.put("caddress",o.getCaddress());
                    jsonObject.put("otype",o.getOtype());
                    jsonObject.put("items",o.getItems());
                    array.put(jsonObject);
                }
            }
        }

        return array.toString();
    }

    //查看已完成订单
    @RequestMapping("/finishOrder")
    @ResponseBody
    public String tofinishOrder(HttpServletRequest request) throws Exception {

        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);

        List<Order> list = orderServiceImp.findallOrder(user.getUid());
        JSONArray array = new JSONArray();
        if(list.size()!=0){
            for(Order o:list){
                if(o.getStatus()==1){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid",o.getOid());
                    jsonObject.put("cid",o.getCid());
                    jsonObject.put("cname",o.getCname());
                    jsonObject.put("cphone",o.getCphone());
                    jsonObject.put("caddress",o.getCaddress());
                    jsonObject.put("otype",o.getOtype());
                    jsonObject.put("items",o.getItems());
                    array.put(jsonObject);
                }
            }
        }

        return array.toString();
    }

    //删除已完成订单
    @RequestMapping("/delfinishOrder")
    @ResponseBody
    public String todelfinishOrder(HttpServletRequest request) throws Exception {
        String oid = request.getParameter("oid");
        orderServiceImp.deleteOrder(oid);
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);
        List<Order> list = orderServiceImp.findallOrder(user.getUid());
        JSONArray array = new JSONArray();
        if(list.size()!=0){
            for(Order o:list){
                if(o.getStatus()==1){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid",o.getOid());
                    jsonObject.put("cid",o.getCid());
                    jsonObject.put("cname",o.getCname());
                    jsonObject.put("cphone",o.getCphone());
                    jsonObject.put("caddress",o.getCaddress());
                    jsonObject.put("otype",o.getOtype());
                    jsonObject.put("items",o.getItems());
                    array.put(jsonObject);
                }
            }
        }

        return array.toString();
    }

    //查看未完成订单
    @RequestMapping("/unfinishOrder")
    @ResponseBody
    public String tounfinishOrder(HttpServletRequest request) throws Exception {
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);
        List<Order> list = orderServiceImp.findallOrder(user.getUid());
        JSONArray array = new JSONArray();
        if(list.size()!=0){
            for(Order o:list){
                if(o.getStatus()==0&&o.getAudit()==2){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid",o.getOid());
                    jsonObject.put("cid",o.getCid());
                    jsonObject.put("cname",o.getCname());
                    jsonObject.put("cphone",o.getCphone());
                    jsonObject.put("caddress",o.getCaddress());
                    jsonObject.put("otype",o.getOtype());
                    jsonObject.put("items",o.getItems());
                    jsonObject.put("hid",o.getHid());
                    array.put(jsonObject);
                }
            }
        }

        return array.toString();
    }

    //查看未通过审核订单
    @RequestMapping("/modifyunOrder")
    @ResponseBody
    public String tomodifyunOrder(HttpServletRequest request) throws Exception {
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);
        List<Order> list = orderServiceImp.findallOrder(user.getUid());
        JSONArray array = new JSONArray();
        if(list.size()!=0){
            for(Order o:list){
                if(o.getAudit()==1){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid",o.getOid());
                    jsonObject.put("cid",o.getCid());
                    jsonObject.put("cname",o.getCname());
                    jsonObject.put("cphone",o.getCphone());
                    jsonObject.put("ccity",o.getCcity());
                    jsonObject.put("caddress",o.getCaddress());
                    jsonObject.put("otype",o.getOtype());
                    jsonObject.put("items",o.getItems());
                    jsonObject.put("cmessage",o.getCmessage());
                    array.put(jsonObject);
                }
            }
        }

        return array.toString();
    }

    //修改未通过的订单
    @RequestMapping("/modifyAuditOrder")
    @ResponseBody
    public String tomodifyAuditOrder(HttpServletRequest request) throws Exception {
        if(imgsrc.equals("")){
            Thread.currentThread().sleep(2000);
        }

        String oid = request.getParameter("oid");
        Order order = orderServiceImp.findbyoid(oid);
        order.setItems_img(imgsrc);
        order.setAudit(0);
        orderServiceImp.modifyordr(order);
        imgsrc="";
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);
        List<Order> list = orderServiceImp.findallOrder(user.getUid());
        JSONArray array = new JSONArray();
        if(list.size()!=0){
            for(Order o:list){
                if(o.getAudit()==1){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid",o.getOid());
                    jsonObject.put("cid",o.getCid());
                    jsonObject.put("cname",o.getCname());
                    jsonObject.put("cphone",o.getCphone());
                    jsonObject.put("ccity",o.getCcity());
                    jsonObject.put("caddress",o.getCaddress());
                    jsonObject.put("otype",o.getOtype());
                    jsonObject.put("items",o.getItems());
                    jsonObject.put("cmessage",o.getCmessage());
                    array.put(jsonObject);
                }
            }
        }
        return array.toString();
    }

    //查看所有订单
    @RequestMapping("/showallOrder")
    @ResponseBody
    public PageInfo<Order> toshowallorder(HttpServletRequest request){
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Order> pageInfo = orderServiceImp.findallpage(pageNum,pageSize);
        return pageInfo;
    }

    //查看所属员工的订单
    @RequestMapping("/showsingleOrder")
    @ResponseBody
    public PageInfo<Order> toshowsingleorder(HttpServletRequest request){
        String name = (String) request.getSession().getAttribute("admin");
        Worker worker = workerServiceImp.findbyName(name);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Order> pageInfo = orderServiceImp.findpagebywid(worker.getWid(),pageNum,pageSize);
        return pageInfo;
    }

    //通过uid和cid获取值
    @RequestMapping("/getuidandcid")
    @ResponseBody
    public String togetuidandcid(HttpServletRequest request) throws JSONException {
       Integer uid = Integer.valueOf(request.getParameter("uid"));
       Integer cid = Integer.valueOf(request.getParameter("cid"));
       JSONObject jsonObject = new JSONObject();
       User user = userServiceImp.findbyuid(uid);
       Consignee consignee = consigneeServiceImp.findbycid(cid);
       jsonObject.put("uname",user.getUname());
       jsonObject.put("uphone",user.getPhone());
       jsonObject.put("cname",consignee.getCname());
       jsonObject.put("cphone",consignee.getCphone());
       jsonObject.put("caddress",consignee.getCaddress());
       return jsonObject.toString();
    }

    //后台审核订单的状态
    @RequestMapping("/modorder")
    @ResponseBody
    public PageInfo<Order> tomodorder(HttpServletRequest request) throws JSONException, ParseException {
        String oid = request.getParameter("oid");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        Integer cid = Integer.valueOf(request.getParameter("cid"));
        Integer hid = Integer.valueOf(request.getParameter("wid"));
        Integer wid = Integer.valueOf(request.getParameter("hid"));
        //String starttime = request.getParameter("starttime");
        String items = request.getParameter("items");
        String items_img = request.getParameter("items_img");
        String otype = request.getParameter("otype");
        String cmessgae = request.getParameter("cmessage");
        Integer status = Integer.valueOf(request.getParameter("status"));
        Integer audit = Integer.valueOf(request.getParameter("audit"));

        Order order = new Order();
        order.setOid(oid);
        order.setUid(uid);
        order.setCid(cid);
        order.setHid(hid);
        order.setWid(wid);
        //order.setStarttime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(starttime));
        order.setStatus(status);
        order.setAudit(audit);
        order.setCmessage(cmessgae);
        order.setItems(items);
        order.setItems_img(items_img);
        order.setOtype(otype);
        if(status==1){
            Date date=new Date();
            SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.format(date);
            order.setEndtime(date);
        }
        orderServiceImp.modifyordr(order);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Order> pageInfo = orderServiceImp.findallpage(pageNum,pageSize);
        return pageInfo;
    }
}
