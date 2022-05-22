package com.example.deposit_system.controller;

import com.example.deposit_system.entity.Citys;
import com.example.deposit_system.entity.Consignee;
import com.example.deposit_system.entity.UC;
import com.example.deposit_system.entity.User;
import com.example.deposit_system.service.Imp.CitysSerivceImp;
import com.example.deposit_system.service.Imp.ConsigneeServiceImp;
import com.example.deposit_system.service.Imp.UCServiceImp;
import com.example.deposit_system.service.Imp.UserServiceImp;
import com.github.pagehelper.PageInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import java.io.IOException;
import java.util.List;

/**
 * @author 欧皇表哥
 * @create 2022/4/10 13:13
 */

@Controller
public class ConnsigneeController {

    @Autowired
    private UCServiceImp ucServiceImp;

    @Autowired
    private ConsigneeServiceImp consigneeServiceImp;

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private CitysSerivceImp citysSerivceImp;

    //查看收货地址
    @RequestMapping( "/look")
    @ResponseBody
    public PageInfo<Consignee> tolook(HttpServletRequest request) throws JSONException {
        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Consignee> pageInfo = consigneeServiceImp.findallPage(user.getUid(),pageNum,pageSize);
        return pageInfo;
    }

    //修改收货地址
    @RequestMapping( "/modifyconsignee")
    @ResponseBody
    public PageInfo<Consignee> tomodifyconsignee(HttpServletRequest request) throws JSONException {
        String name = (String) request.getSession().getAttribute("username");
        Integer cid = Integer.valueOf(request.getParameter("cid"));
        String cname = request.getParameter("cname");
        String cphone = request.getParameter("cphone");
        String caddress = request.getParameter("caddress");
        String ccity = request.getParameter("ccity");
        Integer uid = userServiceImp.findbyName(name).getUid();
        Consignee c = new Consignee();
        c = consigneeServiceImp.findbycid(cid);
        c.setCname(cname);
        c.setCphone(cphone);
        c.setCaddress(caddress);
        c.setCcity(ccity);
        consigneeServiceImp.modeifyConsignee(c);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Consignee> pageInfo = consigneeServiceImp.findallPage(uid,pageNum,pageSize);
        /*
        List<Consignee> list= consigneeServiceImp.findC(uid);
        JSONArray jsonArray = new JSONArray();
        if(list.size()!=0){
            for(Consignee consignee: list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cid",consignee.getCid());
                jsonObject.put("cname",consignee.getCname());
                jsonObject.put("cphone",consignee.getCphone());
                jsonObject.put("caddress",consignee.getCaddress());
                jsonObject.put("ccity",consignee.getCcity());
                jsonArray.put(jsonObject);
            }
        }*/
        return pageInfo;
    }

    //删除收货地址
    @RequestMapping( "/deleteconsignee")
    @ResponseBody
    public PageInfo<Consignee> todeleteconsignee(HttpServletRequest request) throws JSONException {
        String name = (String) request.getSession().getAttribute("username");
        Integer cid = Integer.valueOf(request.getParameter("cid"));
        Integer uid = userServiceImp.findbyName(name).getUid();
        ucServiceImp.deleteUC(cid);
        consigneeServiceImp.deleteConsignee(cid);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Consignee> pageInfo = consigneeServiceImp.findallPage(uid,pageNum,pageSize);
        /*
        List<Consignee> list= consigneeServiceImp.findC(uid);
        JSONArray jsonArray = new JSONArray();
        if(list.size()!=0){
            for(Consignee consignee: list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cid",consignee.getCid());
                jsonObject.put("cname",consignee.getCname());
                jsonObject.put("cphone",consignee.getCphone());
                jsonObject.put("caddress",consignee.getCaddress());
                jsonObject.put("ccity",consignee.getCcity());
                jsonArray.put(jsonObject);
            }
        }*/
        return pageInfo;
    }

    //添加收货地址
    @RequestMapping( "/addconsignee")
    @ResponseBody
    public PageInfo<Consignee> toaddconsignee(HttpServletRequest request) throws JSONException {
        String name = (String) request.getSession().getAttribute("username");
        Integer uid = userServiceImp.findbyName(name).getUid();
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
        u.setUid(uid);
        u.setCid(consigneeServiceImp.find(cc).getCid());
        ucServiceImp.addUC(u);
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =Integer.valueOf(request.getParameter("pageSize"));
        PageInfo<Consignee> pageInfo = consigneeServiceImp.findallPage(uid,pageNum,pageSize);
        /*List<Consignee> list= consigneeServiceImp.findC(uid);
        JSONArray jsonArray = new JSONArray();
        if(list.size()!=0){
            for(Consignee consignee: list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cid",consignee.getCid());
                jsonObject.put("cname",consignee.getCname());
                jsonObject.put("cphone",consignee.getCphone());
                jsonObject.put("caddress",consignee.getCaddress());
                jsonObject.put("ccity",consignee.getCcity());
                jsonArray.put(jsonObject);
            }
        }*/
        return pageInfo;
    }

    //不带分页的获取收货地址
    @RequestMapping( "/getc")
    @ResponseBody
    public String togetc(HttpServletRequest request) throws JSONException {
        String name = (String) request.getSession().getAttribute("username");
        Integer uid = userServiceImp.findbyName(name).getUid();
        List<Consignee> list = consigneeServiceImp.findC(uid);
        JSONArray jsonArray = new JSONArray();
        if(list.size()!=0){
            for(Consignee consignee: list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cid",consignee.getCid());
                jsonObject.put("cname",consignee.getCname());
                jsonObject.put("cphone",consignee.getCphone());
                jsonObject.put("caddress",consignee.getCaddress());
                jsonObject.put("ccity",consignee.getCcity());
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray.toString();
    }

    //获取全国行政城市数据（含港澳台）
    @RequestMapping( "/getCitys")
    @ResponseBody
    public String togetCitys() throws JSONException {
        List<Citys> list1 = citysSerivceImp.findCity1();
        List<Citys> list2 = citysSerivceImp.findCity2();
        List<Citys> list3 = citysSerivceImp.findCity3();
        JSONArray jsonArray = new JSONArray();
        if(list1.size()!=0){
            for(Citys c1:list1){
                JSONObject object1 = new JSONObject();
                object1.put("value",c1.getValue());
                object1.put("label",c1.getLabel());
                jsonArray.put(object1);
                JSONArray array2 =  new JSONArray();
                String num1 = c1.getCode().substring(0,2);
                for (Citys c2:list2){
                    String num2 = c2.getCode().substring(0,4);
                    JSONArray array3 = new JSONArray();
                    if(c2.getCode().substring(0,2).equals(num1)){
                        JSONObject object2 = new JSONObject();
                        object2.put("value",c2.getValue());
                        object2.put("label",c2.getLabel());
                        array2.put(object2);
                    }else {
                        continue;
                    }
                    for (Citys c3:list3){
                        if(c3.getCode().substring(0,4).equals(num2)){
                            JSONObject object3 = new JSONObject();
                            object3.put("value",c3.getValue());
                            object3.put("label",c3.getLabel());
                            array3.put(object3);
                        }
                        continue;
                    }
                    if(array3.length()!=0){
                        array2.put(array2.length(),array3);
                    }
                }
                if(array2.length()!=0){
                    jsonArray.put(jsonArray.length(),array2);
                }
            }

        }
        //System.out.println(jsonArray);
        String str = jsonArray.toString();

        str = str.replace("},[{",",\"children\":[{");
        str = str.replace("}],{","}]},{");
        // str = str.replace("}],[{",",children:[{");
        str = str.replace("}]]","}]}]}");


       // System.out.println("获取！");
        return str;
    }

    //爬取行政城市
    @RequestMapping( "/getCityData")
    @ResponseBody
    public String togetCityData() throws IOException, JSONException{
       String urls = "http://www.mca.gov.cn/article/sj/xzqh/2020/20201201.html";
        int count = 0;
        Document doc = Jsoup.connect(urls)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0 Win64 x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xmlq=0.9,image/webp,image/apng,*/*q=0.8,application/signed-exchangev=b3")
                .maxBodySize(0)
                .timeout(100000)
                .get();
        Elements trs = doc.select("tr");
        JSONArray array1 = new JSONArray();//一级行政
        JSONArray array2 = new JSONArray();//二级行政
        JSONArray array3 = new JSONArray();//三级行政
        for (Element tr : trs ) {
            Elements tds = tr.select("td");
            if (tds.size() > 3) {
                String regionCode = tds.get(1).text();//行政编码
                String regionArea = tds.get(2).text();//行政区域
                if(regionArea.equals("西沙区")){
                    regionCode = "460301";
                }
                if(regionArea.equals("南沙区")){
                    regionCode = "460302";
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value",regionArea);
                jsonObject.put("label",regionArea);
                jsonObject.put("code",regionCode);
                count++;
                if(count>=3&&count<=3213){
                    if(regionCode.substring(2).equals("0000")){
                        array1.put(jsonObject);
                    }
                    else if(regionCode.substring(4).equals("00")||regionCode.substring(0,4).equals("1101")||regionCode.substring(0,4).equals("1201")||regionCode.substring(0,4).equals("3101")||regionCode.substring(0,4).equals("5001")){
                        array2.put(jsonObject);
                    }else {
                        array3.put(jsonObject);
                    }
                    //System.out.println(regionCode+"---"+regionArea.trim());
                }

            }
        }
        JSONArray array4 = new JSONArray();
        for (int i=0;i<array1.length();i++) {
            array4.put(array1.getJSONObject(i));
            JSONArray array5 = new JSONArray();
            String num =array1.getJSONObject(i).get("code").toString().substring(0,2);
            for(int j=0;j<array2.length();j++){
                JSONArray array6 = new JSONArray();
                String num2 =array2.getJSONObject(j).get("code").toString().substring(0,4);
                if(array2.getJSONObject(j).get("code").toString().substring(0,2).equals(num)){
                    array5.put(array2.getJSONObject(j));
                }else {
                    continue;
                }
                for(int k=0;k<array3.length();k++){
                    if(array3.getJSONObject(k).get("code").toString().substring(0,4).equals(num2)){
                        array6.put(array3.getJSONObject(k));
                    }
                    continue;
                }
                if(array6.length()!=0){
                    array5.put(array5.length(),array6);
                }
            }
            if(array5.length()!=0){
                array4.put(array4.length(),array5);
            }
        }

        String[] data2 = array4.toString().split("},\\[\\{");
        StringBuffer result = new StringBuffer();
        for(int i=0;i<data2.length;i++){
            if(i>0){
                data2[i]=",children: [{"+data2[i];
            }
            result.append(data2[i]);
            continue;
        }

        String str = result.toString().replace("}],","}]},");
        str = str.replace("]]","]}");
        System.out.println(str);

        return str;
    }
}
