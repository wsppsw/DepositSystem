$(function(){

/*
    $("#umap").attr("style","display:none;");//隐藏div
    var map = new BMap.Map("umap");
    var address = document.getElementById("address").value;
    console.log(address+"-----");
     var zuobiao =  document.getElementById("zuobiao").value;
     var cccc =  document.getElementById("scity").value;

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
    var licontent="<b>所在位置</b><br>";
    licontent+="<span><strong>具体位置：</strong>"+address+"</span><br>";
    var opts = {
        width : 200,
        height: 80,
    };
    var  infoWindow = new BMap.InfoWindow(licontent, opts);
    marker.openInfoWindow(infoWindow);
    marker.addEventListener('click',function(){
        marker.openInfoWindow(infoWindow);
    });*/
    var n = 0;


    $("#place").click(function(){
        if(n===0){
            n++;
            $("#umap").attr("style","display:block;");//显示div
            var map = new BMap.Map("umap");
            var address = document.getElementById("address").value;
            console.log(address+"-----");
            var zuobiao =  document.getElementById("zuobiao").value;
            var cccc =  document.getElementById("scity").value;

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
            var licontent="<b>所在位置</b><br>";
            licontent+="<span><strong>具体位置：</strong>"+address+"</span><br>";
            var opts = {
                width : 200,
                height: 80,
            };
            var  infoWindow = new BMap.InfoWindow(licontent, opts);
            marker.openInfoWindow(infoWindow);
            marker.addEventListener('click',function(){
                marker.openInfoWindow(infoWindow);
            });
        }else{
            n=0;
            $("#umap").attr("style","display:none;");//隐藏div
        }
        //path();
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