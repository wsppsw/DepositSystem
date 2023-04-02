package com.example.deposit_system;

import com.example.deposit_system.entity.*;
import com.example.deposit_system.service.Imp.*;
import com.example.deposit_system.service.PhoneService;
import com.example.deposit_system.utils.RedisUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DepositSystemApplicationTests {

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private ConsigneeServiceImp consigneeServiceImp;

    @Autowired
    private WorkerServiceImp workerServiceImp;

    @Autowired
    private OrderServiceImp orderServiceImp;

    @Autowired
    private CitysSerivceImp citysSerivceImp;

    @Autowired
    private WareHouseServiceImp wareHouseServiceImp;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void getRedis(){
        redisUtil.set("pp","123");
        if(redisUtil.hasKey("pp")){
            System.out.println(redisUtil.get("pp"));
        }else {
            System.out.println("无redis缓存");
            redisUtil.set("pp","888");
            System.out.println(redisUtil.get("pp"));
        }
        /*PageInfo<Consignee> pageInfo = consigneeServiceImp.findallPage(1,1,10);
        redisUtil.set("page",pageInfo);
        System.out.println(redisUtil.get("page"));*/
    }

    @Test
    void gets(){

//        List<Worker> list = workerServiceImp.findall("服务");
//        System.out.println(list);
       // List<Order> list = orderServiceImp.findallOrder(1);


    }

    @Test
    void getconsignee() throws Exception {
       /* List<Consignee> list = consigneeServiceImp.findC(1);

        System.out.println(consigneeServiceImp.findallPage(1,1,5));

        */

       // phoneService.sendPhone("15027707570","1234");

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
        str = str.replace("},[{",",children:[{");
        str = str.replace("}],{","}]},{");
       // str = str.replace("}],[{",",children:[{");
        str = str.replace("}]]","}]}]}");
        System.out.println(str);
/*
        Citys citys1 = list1.get(0);
        Citys citys2 = list2.get(0);
        Citys citys3 = list3.get(0);
        System.out.println(citys1);
        System.out.println(citys2);
        System.out.println(citys3);
        JSONArray array1 = new JSONArray();
        JSONArray array2 = new JSONArray();
        JSONArray array3 = new JSONArray();
        JSONObject object1 = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        object1.put("value",citys1.getValue());
        object1.put("label",citys1.getLabel());
        object2.put("value",citys2.getValue());
        object2.put("label",citys2.getLabel());
        object3.put("value",citys3.getValue());
        object3.put("label",citys3.getLabel());
        array1.put(object1);
        array2.put(object2);
        array3.put(object3);
        array1.put(array2);
        array1.put(array3);
        String str = array1.toString();
        str = str.replace("},[{",",children:[{");
        str = str.replace("}],[{",",children:[{");
        str = str.replace("}]]","}]}]}]");
        System.out.println(str);*/
    }

    @Test
    void contextLoads() {
        User u = userServiceImp.findbyPhone("15027707570");
        if(u == null){
            System.out.println("111");
        }else {
            System.out.println("222");
        }
    }

    @Test
    void getccitys(){
        List<Citys> list1 = citysSerivceImp.findCity1();
        List<Citys> list2 = citysSerivceImp.findCity2();
        List<Citys> list3 = citysSerivceImp.findCity3();
        //System.out.println(wareHouseServiceImp.findOne("昌平"));
        System.out.println(orderServiceImp.findbyoid("370f3b03c849468ebec7364acef40311"));
    }


    @Test
    void getcity() throws IOException, JSONException {


        //String url = "http://www.mca.gov.cn/article/sj/xzqh/2019/201901-06/201904301706.html";
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
                Citys citys = new Citys();
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
                citys.setCode(regionCode);
                citys.setLabel(regionArea);
                citys.setValue(regionArea);
                count++;
                if(count>=3&&count<=3213){
                   // System.out.println(count);
                    if(regionCode.substring(2).equals("0000")){
                        array1.put(jsonObject);
                      //  citysSerivceImp.addcity1(citys);
                    }
                    else if(regionCode.substring(4).equals("00")||regionCode.substring(0,4).equals("1101")||regionCode.substring(0,4).equals("1201")||regionCode.substring(0,4).equals("3101")||regionCode.substring(0,4).equals("5001")){
                        array2.put(jsonObject);
                     //   citysSerivceImp.addcity2(citys);
                    }else {
                        array3.put(jsonObject);
                   //     citysSerivceImp.addcity3(citys);
                       // System.out.println(citys.getCode());
                    }
                    //System.out.println(regionCode+"---"+regionArea.trim());
                }

                }
            }




        //System.out.println("1"+array1);
       // System.out.println("2"+array2);
       // System.out.println(array3);

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
        //System.out.println(array4);
        String str = array4.toString();
        str = str.replace("},[{",",\"children\":[{");
        str = str.replace("}],[{",",children:[{");
        str = str.replace("}]]","}]}]}]");
        System.out.println(str);
/*
        String[] data2 = array4.toString().split("},\\[\\{");
        StringBuffer result = new StringBuffer();
        for(int i=0;i<data2.length;i++){
            if(i>0){
                data2[i]=",children:[{"+data2[i];
            }
            result.append(data2[i]);
            continue;
        }
        String str = result.toString().replace("}],","}]},");
        System.out.println(str.replace("]]","]}"));*/
    }

    @Test
    void getscity() throws JSONException {
        String urls="http://ip.ws.126.net/ipquery?ie=utf-8";
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(urls);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"GBK"));
            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] data = result.toString().split("=");

        JSONObject jsonObject = new JSONObject(data[3]);


        System.out.println(jsonObject);
        System.out.println(jsonObject.get("city"));

    }

    @Test
    void getillness() throws JSONException {
        String urls="https://c.m.163.com/ug/api/wuhan/app/data/list-total";
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(urls);
            URLConnection conn;
            conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* total{confirm 累积确诊
           input 境外输入
           heal 累积治愈
           dead 累积死亡
           }
           extData{
           noSymptom 无症状感染者
           incrNoSymptom 今日增加无症状感染者
           }
           现确诊= 累积确诊数-累积治愈-累积死亡
           storeConfirm 今日累积现确诊
         */
        JSONObject jsonObject = new JSONObject(result.toString());
        //此字段是累积的疫情
        JSONObject object = jsonObject.getJSONObject("data").getJSONObject("chinaTotal");
        //此字段是累积的疫情
        JSONObject allillness = object.getJSONObject("total");
        //此字段是今日的情况
        JSONObject today = object.getJSONObject("today");
        //System.out.println(allillness);
       // System.out.println(today);
        System.out.println(jsonObject.getJSONObject("data").get("lastUpdateTime"));
       // System.out.println(jsonObject.getJSONObject("data").getJSONArray("areaTree").getJSONObject(2).getJSONArray("children"));
        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("areaTree").getJSONObject(2).getJSONArray("children");
        for(int i=0;i<34;i++){
            //System.out.println(array.getJSONObject(i).getJSONArray("children").length());
          /*  int m = array.getJSONObject(i).getJSONArray("children").length();
            for(int j=0;j<m;j++){

                if(array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("id").equals("110114")){
                    System.out.println(array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("today"));
                    JSONObject o = (JSONObject) array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("today");
                    System.out.println(o.get("confirm"));
                    System.out.println(array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("total"));
                    System.out.println(array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("lastUpdateTime"));
                    break;
                }
                continue;

            }
            continue; */
            System.out.println(array.getJSONObject(i).get("name")+"-"+array.getJSONObject(i).get("lastUpdateTime")+"-"+array.getJSONObject(i).get("today")+"-"+array.getJSONObject(i).get("total"));
            //System.out.println(array.getJSONObject(i).getJSONArray("children").getJSONObject(0));
       }
       // System.out.println(jsonObject.getJSONObject("data").getJSONArray("areaTree").getJSONObject(2).getJSONArray("children").getJSONObject(33).getJSONArray("children").getJSONObject(0));
    }

    @Test
    void code(){
       /* List<Citys> citys = citysSerivceImp.findCity2();
        String code = null;
        for(Citys c:citys){
            if (c.getValue().equals("昌平区")){
                code = c.getCode();
                break;
            }
        }*/
       /* List<WareHouse> list = wareHouseServiceImp.findOne("昌平区");
        System.out.println(list);*/
      /*  String ss = "15027707570";
        Boolean res = ss.matches("[0-9]+");
        if(res){
            System.out.println("纯数字");
        }else {
            System.out.println("不是的");
        }*/

    }

    @Test
    public void toamdminorder(){
        PageInfo<Worker> pageInfo = workerServiceImp.findalltype("服务",1,2);
        System.out.println(pageInfo);
        System.out.println(pageInfo.getList().get(1));
    }
}
