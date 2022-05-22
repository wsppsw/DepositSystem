package com.example.deposit_system.controller;

import com.example.deposit_system.entity.Citys;
import com.example.deposit_system.entity.User;
import com.example.deposit_system.entity.WareHouse;
import com.example.deposit_system.entity.Worker;
import com.example.deposit_system.service.Imp.CitysSerivceImp;
import com.example.deposit_system.service.Imp.UserServiceImp;
import com.example.deposit_system.service.Imp.WareHouseServiceImp;
import com.example.deposit_system.service.Imp.WorkerServiceImp;
import com.example.deposit_system.service.PhoneService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

/**
 * @author 欧皇表哥
 * @create 2021/11/22 14:40
 */
@Controller
public class UserController {

    //用户逻辑判断
    @Autowired
    private UserServiceImp userServiceImp;

    //短信服务
    @Autowired
    private PhoneService phoneService;

    @Autowired
    private CitysSerivceImp citysSerivceImp;

    @Autowired
    private WareHouseServiceImp wareHouseServiceImp;

    @Autowired
    private WorkerServiceImp workerServiceImp;

    //全局变量验证码
    private String v_code="";

    //全局城市与省份
    private String global_city;
    private String global_province;

    //登录界面
    @RequestMapping("/login")
    public String tologin(){
        return "vue_login";
    }

    //图片验证登录
    @RequestMapping("/imgverify")
    public String toimgberify(){
        return "imgverify";
    }

    //图片验证注册
    @RequestMapping("/imgverifyregist")
    public String toimgberifyregist(){
        return "imgverify_regist";
    }

    //注册界面
    @RequestMapping("/regist")
    public String toregist(){
        return "vue_regist";
    }

    //找回密码
    @RequestMapping("/findPwd")
    public String tofindPwd(){
        return "main/vue_findPwd";
    }

    //验证身份
    @RequestMapping("/verifyuser")
    public String toverifyuser(){
        return "main/vue_findU";
    }

    //选择方式
    @RequestMapping("/selectway")
    public String toselectway(){
        return "main/vue_selectway";
    }

    //验证密保
    @RequestMapping("/verifyquestion")
    public String toverifyquestion(){
        return "main/vue_verifyquestion";
    }

    //验证手机
    @RequestMapping("/verifyphone")
    public String toverifyphone(){
        return "main/vue_verifyphone";
    }

    //找回并确认密码
    @RequestMapping("/newPwd")
    public String tonewPwd(){
        return "main/vue_newPwd";
    }

    //用户主界面
    @RequestMapping("/main")
    public String tomain(){
        return "main/vue_main";
    }

    //介绍页面
    @RequestMapping("/index")
    public String toindex(){
        return "main/vue_index";
    }

    //预约页面
    @RequestMapping("/subscribe")
    public String tosubscribe(){
        return "main/vue_subscribe";
    }

    //审核页面
    @RequestMapping("/check")
    public String tocheck(){
        return "main/vue_check";
    }

    //已完成页面
    @RequestMapping("/finish")
    public String tofinish(){
        return "main/vue_finish";
    }

    //未完成页面
    @RequestMapping("/unfinish")
    public String tounfinish(){
        return "main/vue_unfinish";
    }

    //未通过页面
    @RequestMapping("/modifyOrder")
    public String tomodifyOrder(){
        return "main/vue_modifyOrder";
    }

    //修改用户页面
    @RequestMapping("/modifyUser")
    public String tomodifyUser(){
        return "main/vue_modifyUser";
    }

    //修改密码页面
    @RequestMapping("/modifyPwd")
    public String tomodifyPwd(){
        return "main/vue_modifyPwd";
    }

    //收货地址页面
    @RequestMapping("/consignee")
    public String toconsignee(){
        return "main/vue_consignee";
    }

    @RequestMapping("/illness")
    public String toillness(){
        return "main/vue_illness";
    }

    //城市地位
    @RequestMapping("/city")
    @ResponseBody
    public String toCity() throws JSONException {
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
        global_city = jsonObject.get("city").toString();
        global_province = jsonObject.getString("province").toString();
        return jsonObject.get("city").toString();
    }

    //今日天气预报
    @RequestMapping("/weather")
    @ResponseBody
    public String toWeather(HttpServletRequest request) throws JSONException {
        String city = null;
        city =request.getParameter("city");
        if(city.equals("")){
            city="张家口";
        }
        String urls = "http://wthrcdn.etouch.cn/weather_mini?city="+city;
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(urls);
            URLConnection conn = url.openConnection();
            InputStream stream = null;
            stream = new GZIPInputStream(conn.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject(result.toString());
        String[] arr = new String[3];
        JSONObject object1 =object.getJSONObject("data");
        JSONObject yes = object1.getJSONObject("yesterday");
        JSONArray array = object1.getJSONArray("forecast");
        arr[0]=object1.getString("city");
        arr[1]=object1.getString("ganmao");
        arr[2]=object1.getString("wendu")+"℃";
        JSONObject jsonObject2 = array.getJSONObject(0);
        String type=jsonObject2.getString("type").toString();
        System.out.println("城市"+city+","+type+"--"+arr[2]);
        JSONObject obj = new JSONObject();
        obj.put("wendu",arr[2]);
        obj.put("type",type);
        return obj.toString();
    }

    //用户名登录
    @RequestMapping("/loginusername")
    @ResponseBody
    public String tologin(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user= userServiceImp.findbyName(username);
        if(user==null){
            return "用户或密码不存在!";
        }else if(!user.getPwd().equals(password)){
            return "用户或密码不存在!";
        }else {
            request.getSession().setAttribute("username",user.getUname());
            return "true";
        }
    }

    //手机号登录
    @RequestMapping("/loginuserphone")
    @ResponseBody
    public String tologin1(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        User user = new User();
        user = userServiceImp.findbyPhone(phone);
        if(user==null){
            return "用户不存在,请先注册!";
        }else if(!code.equals(v_code)){
            return "验证码不正确!";
        }else {
            request.getSession().setAttribute("username",user.getUname());
            return "true";
        }
    }

    //发送手机验证码登录
    @RequestMapping("/loginsendphone")
    @ResponseBody
    public String tosendphone(HttpServletRequest request) throws Exception {
        String phone = request.getParameter("phone");
        Boolean flag = false;
        List<User> list = userServiceImp.findall();
        for(User u:list){
            if(u.getPhone().equals(phone)){
                flag = true;
                break;
            }
        }
        if(flag){
            String code="";
            Random r = new Random();
            for(int i=0;i<4;i++){
                code+=r.nextInt(10);
            }
            v_code=code;
            // phoneService.sendPhone(phone,v_code);
            return v_code;
        }else {
            return "用户不存在,请先注册!";
        }

    }

    //发送手机验证码
    @RequestMapping("/sendphone")
    @ResponseBody
    public String send(HttpServletRequest request) throws Exception {
        String phone = request.getParameter("phone");
        System.out.println(phone);
        String code="";
        Random r = new Random();
        for(int i=0;i<4;i++){
            code+=r.nextInt(10);
        }
        v_code=code;
      //   phoneService.sendPhone(phone,v_code);

        return v_code;
    }

    //注册时发送验证码同时验证手机是否存在
    @RequestMapping("/sendphoneAndverifyphone")
    @ResponseBody
    public String sendAndverifyphone(HttpServletRequest request) throws Exception {
        String phone = request.getParameter("phone");
        List<User> list = userServiceImp.findall();
        Boolean flag = false;
        for(User u:list){
            if(u.getPhone().equals(phone)){
                flag = true;
            }
        }
        if(flag){
            return "手机号已被注册";
        }else {
            String code="";
            Random r = new Random();
            for(int i=0;i<4;i++){
                code+=r.nextInt(10);
            }
            v_code=code;
          //   phoneService.sendPhone(phone,v_code);
            return v_code;
        }

    }

    //注册用户
    @RequestMapping("/registUser")
    @ResponseBody
    public String adduser(HttpServletRequest request){
        String name = request.getParameter("username");
        String passwd1 = request.getParameter("pwd1");
        String passwd2 = request.getParameter("pwd2");
        String security1 = request.getParameter("security1");
        String answer1 = request.getParameter("answer1");
        String security2 = request.getParameter("security2");
        String answer2 = request.getParameter("answer2");
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        List<User> list = userServiceImp.findall();
        Boolean flag=false;//判断用户名是否已存在
        Boolean flagp=false;//判断手机号是否已存在
        for(User u:list){
            if(u.getUname().equals(name)){
                flag=true;
                break;
            }
            if(u.getPhone().equals(phone)){
                flagp=true;
                break;
            }
        }
        if(flag){
            return "用户名已存在!";
        }else if(!passwd1.equals(passwd2)){
            return "两次密码不一致!";
        }else if(flagp){
            return "预留手机号码存在!";
        }else if(!code.equals(v_code)){
            return "验证码不正确!";
        }else {
            User user = new User();
            user.setUname(name);
            user.setPwd(passwd1);
            user.setSecurity1(security1);
            user.setAnswer1(answer1);
            user.setSecurity2(security2);
            user.setAnswer2(answer2);
            user.setPhone(phone);
            int x= new Random().nextInt(12)+1;
            user.setImg_head("img_head/"+x+".png");
            userServiceImp.AddUser(user);
            return "true";
        }
    }

    //找回密码验证用户名
    @RequestMapping("/findUser")
    @ResponseBody
    public String tofinduser(HttpServletRequest request) {
        String username = request.getParameter("username");
        User user = new User();
        user= userServiceImp.findbyName(username);
        if(user==null){
            return "用户不存在!";
        }else {
            request.getSession().setAttribute("findname",user.getUname());
            return "true";
        }
    }

    //找回密码验证密保问题
    @RequestMapping("/findUserbyQ")
    @ResponseBody
    public String tofindbyquestion(HttpServletRequest request) {
        String findname = (String) request.getSession().getAttribute("findname");
        String security1 = request.getParameter("security1");
        String answer1 = request.getParameter("answer1");
        String security2 = request.getParameter("security2");
        String answer2 = request.getParameter("answer2");
        System.out.println(findname+"--"+security1+"--"+answer1+"--"+security2+"--"+answer2);
        User user = new User();
        user= userServiceImp.findbyName(findname);
        if(user.getSecurity1().equals(security1)&&user.getAnswer1().equals(answer1)&&user.getSecurity2().equals(security2)&&user.getAnswer2().equals(answer2)){
            return "true";
        }else {
            return "密保验证不正确！";
        }
    }

    //验证是否有用户找回密码
    @RequestMapping("/checkfindname")
    @ResponseBody
    public String tocheckfindname(HttpServletRequest request){
        String name = (String) request.getSession().getAttribute("findname");
        if(name == null){
            return "false";
        }else {
            return name;
        }
    }

    //查看找回用户的手机号码
    @RequestMapping("/finduserphone")
    @ResponseBody
    public String tofinduserphone(HttpServletRequest request){
        String findname = (String) request.getSession().getAttribute("findname");
        User user = userServiceImp.findbyName(findname);
        return user.getPhone();
    }

    //找回密码验证手机密保
    @RequestMapping("/findUserbyP")
    @ResponseBody
    public String tofindbyphone(HttpServletRequest request){
        String code = request.getParameter("code");
        if(code.equals(v_code)){
            return "true";
        }else {
            return "验证码不正确！";
        }
    }

    //找回用户修改新密码
    @RequestMapping("/getnewpwd")
    @ResponseBody
    public String togetnewpwd(HttpServletRequest request)throws IOException{
        String findname = (String) request.getSession().getAttribute("findname");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");
        User user = userServiceImp.findbyName(findname);
        if(!pwd1.equals(pwd2)){
            return "两次密码不一致！";
        }else {
            User u = new User();
            u.setUid(user.getUid());
            u.setPwd(pwd1);
            u.setSecurity1(user.getSecurity1());
            u.setAnswer1(user.getAnswer1());
            u.setSecurity2(user.getSecurity2());
            u.setAnswer2(user.getAnswer2());
            u.setPhone(user.getPhone());
            u.setImg_head(user.getImg_head());
            userServiceImp.updateUser(u);
            request.getSession().removeAttribute("findname");
            return "true";
        }
    }

    //判断是否有用户登录
    @RequestMapping("/verifyTologin")
    @ResponseBody
    public String toVerify(HttpServletRequest request){
        String name = (String) request.getSession().getAttribute("username");
        if(name == null){
            return "false";
        }else {
            return "true";
        }
    }

    //销毁登录信息
    @RequestMapping("/verifyexit")
    @ResponseBody
    public String toVerifyexit(HttpServletRequest request){
        request.getSession().invalidate();
        return "登录信息已销毁";
    }

    //查看已登录信息
    @RequestMapping( "/Loginhead")
    @ResponseBody
    public String toLoginhead(HttpServletRequest request) throws JSONException {
        String name = (String) request.getSession().getAttribute("username");
        if(name == null){
          return "img_head/2.png";
        }else {
            return userServiceImp.findbyName(name).getImg_head();
        }

    }

    //查看已登录信息
    @RequestMapping( "/AlreadyLogin")
    @ResponseBody
    public String toAlreadyLogin(HttpServletRequest request) throws JSONException {
        JSONObject object = new JSONObject();

        String name = (String) request.getSession().getAttribute("username");
        User user = userServiceImp.findbyName(name);
        object.put("username",user.getUname());
        object.put("userphone",user.getPhone());
        object.put("userhead",user.getImg_head());

       // System.out.println(object);
        return object.toString();
    }

    //上传头像及修改
    @RequestMapping("/fileup")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        // replaceAll 用来替换windows中的\\ 为 /
        MultipartFile multipartFile1 = multipartFile;
        System.out.println("名称："+multipartFile.getOriginalFilename()+"-类型："+multipartFile.getContentType());
        String[] filename =multipartFile.getOriginalFilename().split("\\.");
        System.out.println("截取类型"+filename[1]);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println("更名后"+uuid);
        String imgsrc=uuid+"."+filename[1];
        System.out.println(imgsrc);

        User user = userServiceImp.findbyName((String) request.getSession().getAttribute("username"));
        user.setImg_head("img_head/"+imgsrc);
        userServiceImp.updateUser(user);

        // 文件存储位置，文件的目录要存在才行，可以先创建文件目录，然后进行存储
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img_head\\" +uuid+"."+filename[1];
        String filePath1 = System.getProperty("user.dir")+"\\target\\classes\\static\\img_head\\" +uuid+"."+filename[1];

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

    //上传员工头像及修改
    @RequestMapping("/fileupworker")
    @ResponseBody
    public String uploadworker(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        // replaceAll 用来替换windows中的\\ 为 /
        MultipartFile multipartFile1 = multipartFile;
        System.out.println("名称："+multipartFile.getOriginalFilename()+"-类型："+multipartFile.getContentType());
        String[] filename =multipartFile.getOriginalFilename().split("\\.");
        System.out.println("截取类型"+filename[1]);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println("更名后"+uuid);
        String imgsrc=uuid+"."+filename[1];
        System.out.println(imgsrc);

        Worker worker = workerServiceImp.findbyName((String) request.getSession().getAttribute("admin"));
        worker.setWhead_img("img_head/"+imgsrc);
        workerServiceImp.modifyWorker(worker);

        // 文件存储位置，文件的目录要存在才行，可以先创建文件目录，然后进行存储
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img_head\\" +uuid+"."+filename[1];
        String filePath1 = System.getProperty("user.dir")+"\\target\\classes\\static\\img_head\\" +uuid+"."+filename[1];

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

    //发送手机验证码修改信息
    @RequestMapping("/Msphone")
    @ResponseBody
    public String toMsphone(HttpServletRequest request) throws Exception {
        String phone = request.getParameter("phone");

        String code="";
        Random r = new Random();
        for(int i=0;i<4;i++){
            code+=r.nextInt(10);
        }
        v_code=code;
        System.out.println(code);
        // phoneService.sendPhone(phone,v_code);
        return v_code;
    }

    //修改信息
    @RequestMapping( "/modifyphone")
    @ResponseBody
    public String tomodifyhead(HttpServletRequest request) throws JSONException {
            String name = (String) request.getSession().getAttribute("username");
            User user = userServiceImp.findbyName(name);
            String code = request.getParameter("code");
            String newphone = request.getParameter("newphone");
            if(!code.equals(v_code)){
                return "验证码不正确!";
            }else {
                user.setPhone(newphone);
                userServiceImp.updateUser(user);
                return "true";
            }
    }

    //修改员工信息
    @RequestMapping( "/modifyworkerphone")
    @ResponseBody
    public String tomodifyworkerhead(HttpServletRequest request) throws JSONException {
            String name = (String) request.getSession().getAttribute("admin");
            Worker worker = workerServiceImp.findbyName(name);
            String code = request.getParameter("code");
            String newphone = request.getParameter("newphone");
            if(!code.equals(v_code)){
                return "验证码不正确!";
            }else {
                worker.setWphone(newphone);
                workerServiceImp.modifyWorker(worker);
                return "true";
            }
    }

    //修改密码
    @RequestMapping( "/modifypassword")
    @ResponseBody
    public String tomodifypassword(HttpServletRequest request) throws JSONException {
        String name = (String) request.getSession().getAttribute("username");
        String oldpwd = request.getParameter("oldpwd");
        String newpwd1= request.getParameter("newpwd1");
        String newpwd2= request.getParameter("newpwd2");
        User user = userServiceImp.findbyName(name);
        if(!user.getPwd().equals(oldpwd)){
            return "旧密码不正确!";
        }else if(oldpwd.equals(newpwd1)){
            return "新旧密码不能一致!";
        }else if(!newpwd1.equals(newpwd2)){
            return "两次密码不一致!";
        }else {
            user.setPwd(newpwd1);
            userServiceImp.updateUser(user);
            return "true";
        }
    }

    //获取2级行政城市
    @RequestMapping( "/twocity")
    @ResponseBody
    public String totwocity(HttpServletRequest request) throws JSONException {
        List<Citys> list = citysSerivceImp.findCity2();
        JSONArray array = new JSONArray();
        for(Citys c:list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value",c.getValue());
            jsonObject.put("label",c.getLabel());
            array.put(jsonObject);
        }
        System.out.println("获取2级城市");
        return array.toString();
    }

    //城市转换行政编码
    @RequestMapping( "/changecode")
    @ResponseBody
    public String tochangecode(HttpServletRequest request) throws JSONException {
        String city = request.getParameter("city");
        List<Citys> list = citysSerivceImp.findCity2();
        String code = null;
        for(Citys c:list){
           if(c.getValue().equals(city)){
              code = c.getCode();
              break;
           }
        }
       // System.out.println(code);
        return code;
    }

    //获取全国疫情数据
    @RequestMapping( "/quanguo")
    @ResponseBody
    public String toquanguo(HttpServletRequest request) throws JSONException {
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
        JSONObject results = new JSONObject();
        results.put("allconfirm",allillness.get("confirm"));
        results.put("allheal",allillness.get("heal"));
        results.put("alldead",allillness.get("dead"));
        results.put("todayconfirm",today.get("confirm"));
        results.put("todayheal",today.get("heal"));
        results.put("todaydead",today.get("dead"));
        results.put("time",jsonObject.getJSONObject("data").get("lastUpdateTime"));
        return results.toString();
    }

    //获取具体地区疫情数据
    @RequestMapping( "/diqu")
    @ResponseBody
    public String todiqu(HttpServletRequest request) throws JSONException {
        String code = request.getParameter("code");
        Boolean flag = true;
        if(code==""){
            List<Citys> citys = citysSerivceImp.findCity2();
            for(Citys c:citys){
                if (c.getValue().equals(global_city)){
                    code = c.getCode();
                    break;
                }
            }
        }

        if(code==""){
            List<Citys> citys = citysSerivceImp.findCity1();
            for(Citys c:citys){
                if (c.getValue().equals(global_city)){
                    code = c.getCode();
                    flag = false;
                    break;
                }
            }
        }


        System.out.println(code);
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
        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("areaTree").getJSONObject(2).getJSONArray("children");
        JSONObject results = new JSONObject();

        if(flag){
            for(int i=0;i<34;i++){
                //System.out.println(array.getJSONObject(i).getJSONArray("children").length());
                int m = array.getJSONObject(i).getJSONArray("children").length();
                for(int j=0;j<m;j++){
                    if(array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("id").equals(code)){
                        //System.out.println(array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("today"));
                        JSONObject total = (JSONObject) array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("total");
                        JSONObject today = (JSONObject) array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("today");
                        String todayconfirm = today.getString("confirm");
                        String todayheal = today.getString("heal");
                        String todaydead = today.getString("dead");
                        if(todayconfirm.equals("null")){
                            todayconfirm = "0";
                        }
                        if(todayheal.equals("null")){
                            todayheal = "0";
                        }
                        if(todaydead.equals("null")){
                            todaydead = "0";
                        }
                        results.put("totalconfirm",total.get("confirm"));
                        results.put("totalheal",total.get("heal"));
                        results.put("totaldead",total.get("dead"));
                        results.put("todayconfirm",todayconfirm);
                        results.put("todayheal",todayheal);
                        results.put("todaydead",todaydead);
                        results.put("time",array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("lastUpdateTime"));
                        // System.out.println(array.getJSONObject(i).getJSONArray("children").getJSONObject(j).get("total"));
                        break;
                    }
                    continue;
                }
                continue;
            }
        }else {
            for(int i=0;i<34;i++){
               if(array.getJSONObject(i).get("id").equals(code)){
                   JSONObject total = (JSONObject) array.getJSONObject(i).get("total");
                   JSONObject today = (JSONObject) array.getJSONObject(i).get("today");
                   results.put("totalconfirm",total.get("confirm"));
                   results.put("totalheal",total.get("heal"));
                   results.put("totaldead",total.get("dead"));
                   results.put("todayconfirm",today.get("confirm"));
                   results.put("todayheal",today.get("heal"));
                   results.put("todaydead",today.get("dead"));
                   results.put("time",array.getJSONObject(i).get("lastUpdateTime"));
                   break;
               }
               continue;
            }
        }

        System.out.println("地区疫情"+results);
        return results.toString();
    }

    //获取相关地区的第一个仓库
    @RequestMapping( "/placeOnewarehouse")
    @ResponseBody
    public String togetplaceOnewarehouse(HttpServletRequest request) throws JSONException {
        List<WareHouse> list = wareHouseServiceImp.findOne(global_city);
        JSONObject jsonObject = new JSONObject();
        if(list.size()!=0){
            for(WareHouse w:list){
                jsonObject.put("hhid",w.getHid());
                jsonObject.put("address_",w.getAddress());
                jsonObject.put("scity",w.getCity());
                jsonObject.put("coor",w.getCoordinate());
                break;
            }
        }
      //  System.out.println(jsonObject);
        return jsonObject.toString();
    }

    //获取相关地区的全部仓库
    @RequestMapping( "/placewarehouse")
    @ResponseBody
    public String togetplacewarehouse(HttpServletRequest request) throws JSONException {
        List<WareHouse> list = wareHouseServiceImp.findOne(global_city);
        JSONArray array = new JSONArray();
        if(list.size()!=0){
            for(WareHouse w:list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("hid",w.getHid());
                jsonObject.put("address",w.getAddress());
                jsonObject.put("city",w.getCity());
                jsonObject.put("coordinate",w.getCoordinate());
                jsonObject.put("hsize",w.getHsize());
                array.put(jsonObject);
            }
        }
       // System.out.println(array);
        return array.toString();
    }

}
