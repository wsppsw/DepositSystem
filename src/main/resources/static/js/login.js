$(function(){
	$("#phone").hide();
	$("#u").click(function(){
		$("#u").css("background-color","#409EFF");
		$("#p").css("background-color","#E6A23C");
		$("#phone").hide();
		$("#user").show();
	})

	$("#p").click(function(){
		$("#p").css("background-color","#409EFF");
		$("#u").css("background-color","#E6A23C");
		$("#phone").show();
		$("#user").hide();
	})
})