$(function(){

	var map = new BMap.Map("cmap");  
   // var address = "北京市朝阳区北辰世纪中心B座";
    var address = document.getElementById("address").value;
    console.log(address+"-----");
    var zuobiao =  document.getElementById("zuobiao").value;
    var cccc =  document.getElementById("scity").value;
   // var zuobiao = "116.394532,40.005334";
   // var cccc ="北京市";
    console.log(zuobiao+"-----");
    console.log(cccc+"-----");
    var coor=zuobiao.split(",");
    let left1=coor[0];
    let right2=coor[1];
    console.log(left1+"--"+right2);
    var point = new BMap.Point(left1,right2);
    map.centerAndZoom(point, 12);  
    map.enableScrollWheelZoom(); 
 
    var myIcon = new BMap.Icon("./img_head/ico/z.ico",new BMap.Size(30,30),{
        anchor: new BMap.Size(10,10)    
    });
 
    var marker=new BMap.Marker(point,{icon: myIcon});  
    map.addOverlay(marker);  


     map.addOverlay(marker);  
    var licontent="<b>仓库详情</b><br>";
        licontent+="<span><strong>地址：</strong>"+address+"</span><br>";        
    var opts = { 
        width : 200,
        height: 80,
    };         
    var  infoWindow = new BMap.InfoWindow(licontent, opts);  
    marker.openInfoWindow(infoWindow);  
    marker.addEventListener('click',function(){
        marker.openInfoWindow(infoWindow);
    });  
 	
    $("#path").click(function(){
    	path();
    })


    $("#shownewmap").click(function(){

        var map = new BMap.Map("cmap");
        // var address = "河北省邯郸市武安市活水乡七步沟村";
        var newaddress = document.getElementById("address").value;
        console.log(newaddress+"-----");
        var newzuobiao =  document.getElementById("zuobiao").value;
        var newcccc =  document.getElementById("scity").value;
        //  var zuobiao = "113.881628,36.938712";
        //   var cccc ="武安市";
        console.log(newzuobiao+"-----");
        console.log(newcccc+"-----");
        var coor=newzuobiao.split(",");
        let newleft1=coor[0];
        let newright2=coor[1];
        console.log(newleft1+"--"+newright2);
        left1 = newleft1;
        right2 = newright2;
        cccc = newcccc;
        var point = new BMap.Point(newleft1,newright2);
        map.centerAndZoom(point, 12);
        map.enableScrollWheelZoom();

        var myIcon = new BMap.Icon("./img_head/ico/z.ico",new BMap.Size(30,30),{
            anchor: new BMap.Size(10,10)
        });

        var marker=new BMap.Marker(point,{icon: myIcon});
        map.addOverlay(marker);


        map.addOverlay(marker);
        var licontent="<b>仓库地址</b><br>";
        licontent+="<span><strong>地址：</strong>"+newaddress+"</span><br>";
        var opts = {
            width : 200,
            height: 80,
        };
        var  infoWindow = new BMap.InfoWindow(licontent, opts);
        marker.openInfoWindow(infoWindow);
        marker.addEventListener('click',function(){
            marker.openInfoWindow(infoWindow);


        });
    })

    function path(){
    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            //map.panTo(r.point);//地图中心点移到当前位置
            var latCurrent = r.point.lat;
            var lngCurrent = r.point.lng;
           // alert('我的位置：'+ latCurrent + ',' + lngCurrent);
           // 36.60930793,114.48269393
           // 114.530934,37.988984
           // 114.490265,38.016287
            //114.28346,38.094277
            //113.881628,36.938712 七步沟 113.881628,36.938712
           // $('#maphtml').load("http://api.map.baidu.com/direction?origin="+latCurrent+","+lngCurrent+"&destination="+right2+","+left1+"&mode=driving&region="+cccc+"&output=html");
            window.open("http://api.map.baidu.com/direction?origin="+latCurrent+","+lngCurrent+"&destination="+right2+","+left1+"&mode=driving&region="+cccc+"&output=html");
            //location.href="http://api.map.baidu.com/direction?origin="+latCurrent+","+lngCurrent+"&destination=36.938712,113.881628&mode=driving&region=石家庄&output=html";
            // $("#maphtml").attr("style","display:block;");//显示div
        }
        else {
            alert('failed'+this.getStatus());
        }        
    },{enableHighAccuracy: true})
 
    }
})