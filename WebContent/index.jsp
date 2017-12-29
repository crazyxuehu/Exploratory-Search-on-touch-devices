<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test spring mvc</title>
<script type="text/javascript" src="jquery-1.11.1.min.js"></script>
<style type="text/css">
.content input{
padding:5px 10px;
width:200px;
}
ul.list{
list-style:none;
padding:0px;
margin:0px;
overflow:hidden;
}
ul.list li{
border:1px solid #EEE;
width:180px;
padding:5px 10px;
margin:0px;
text-overflow:ellipsis; //溢出时变为省略
overflow:hidden;
}
.lilight{
background-color:#fafafa;
}
</style>
</head>
<body>
			<div style="width:300px;height:200px;">
				<form name="myform" action="/seed/test" method="get">
					<input id="email" name="email" value="" maxlength=20>
					<input type="button" value="submit" onClick="vis()">
					<ul class="list"></ul>
				</form>
				<div id="test"></div>
			</div>
			<script type="text/javascript">
		function check(mail){
				//声明所有的电子邮件变量
				//生成一个个li，并加入到ul中
				$("ul.list").empty();
				for(var i=0;i<mail.length;i++){
				var liElement=$('<li class="autoli"><span class="ex">'+mail[i]+'</span></li>")');
				liElement.appendTo("ul.list");
				}
				//首先让list隐藏起来
				$("ul.list").hide();
				$("#email").keyup(function(event){
				//键入的内容不是上下箭头和回车
				//if(event.keyCode!=38&&event.keyCode!=40&&event.keyCode!=13){
				//如果输入的值不是空或者不以空格开头
				if($.trim($(this).val())!=""&& $.trim($(this).val()).match(/^@/)==null){
				$("ul.list").show();
				//如果当前有已经高亮的下拉选项卡，那么将其移除
				if($("ul.list li:visible").hasClass("lilight")){
				$("ul.list li").removeClass("lilight");
				}
				//如果还存在下拉选项卡，那么将其高亮
				if($("ul.list li:visible")){
				$("ul.list li:visible:eq(0)").addClass("lilight");
				}
				}else{
				//否则不进行显示
				$("ul.list").hide();
				$("ul.list li").removeClass("lilight");
				}
				//输入的内容还没有包括@符号
				/* if($.trim($(this).val()).match(/.*@/)==null){
				$(".list li .ex").text($(this).val());
				}else{
				//输入的符号已经包含了@
				var str = $(this).val();
				var strs = str.split("@");
				$(".list li .ex").text(strs[0]);
				if($(this).val().length>=strs[0].length+1){
				tail=str.substr(strs[0].length+1);
				$(".list li .tail").each(function(){
				//如果数组中的元素是以文本中的后缀开头，那么就显示，否则不显示
				if(!($(this).text().match(tail)!=null&&$(this).text().indexOf(tail)==0)){
				//隐藏其他的li
				$(this).parent().hide();
				}else{
				//显示所在的li
				$(this).parent().show();
				}
				});
				}
				} */
				//}
				//按了回车时，将当前选中的元素写入到文本框中
				/* if(event.keyCode==13){
				$("#email").val($("ul.list li.lilight:visible").text());
				$("ul.list").hide();
				}
				}); */
				//监听上下方向键
			/* 	$("#email").keydown(function(event){
				//下方向键按下了
				if(event.keyCode==40){
				if($("ul.list li").is(".lilight")){
				if($("ul.list li.lilight").nextAll().is("li:visible")){
				$("ul.list li.lilight").removeClass("lilight").next("li").addClass("lilight");
				}
				}
				} */
				//下方向键按下了
			/* 	if(event.keyCode==38){
				if($("ul.list li").is(".lilight")){
				if($("ul.list li.lilight").prevAll().is("li:visible")){
				$("ul.list li.lilight").removeClass("lilight").prev("li").addClass("lilight");
				}
				}
				} */
				});
				//当鼠标点击某个下拉项时，选中该项，下拉列表隐藏
				$("ul.list li").click(function(){
				$("#email").val($(this).text());
				$("ul.list").hide();
				});
				//当鼠标划过某个下拉项时，选中该项，下拉列表隐藏
				$("ul.list li").hover(function(){
				$("ul.list li").removeClass("lilight");
				$(this).addClass("lilight");
				});
				//当鼠标点击其他位置，下拉列表隐藏
				$(document).click(function(){
				$("ul.list").hide();
				}); 
				};
			 $('#email').bind('input propertychange',function(){
				 var query=$(this).val();
				 console.log(query);
				 $.ajax({
					url:"http://localhost:8080/seed/test",
					data:{query:query},
					type:"POST",
					dataType:'json',
					success:function(result,status){
						check(result);
					},
					error:function(error,status){
						console.log(status);
					}
				 });
			 });
				function fact(){
					/* var nn=$('#name').val();*/
					var list=new Array(); 
					list.push("total recall (1990 film)");
					//list.push("sally forrest");
					//list.push("sally forrest");
					var head="category:dutch roman catholics";
					//var head="virginia o'brien";
					//var tail="virginia o'brien";
					/* var head='bryan';
					var tail='palimos ng pag-ibig (tv series)'; */
					$.ajax({
						url:"/Knowledge_Explore/test",
						data:{query:"for"},
						type:"POST",
						dataType:'json',
						contentType:'application/json',
						success:function(result,status){
							console.log(result);
							
							/* for(var i=0;i<result.length;i++){
								//console.log(result[i]['name']);
							} */
						},
						error:function(er,status){
							console.log(er);
							console.log(status);
							alert(status);
						}
					});
					
				}
				function vis(){
					console.log(123);
					//var target=new Array();
					/* for(var i=0;i<list.length;i++){
						target.push(list[i]['name']);
					}
					var tail="virginia o'brien"; */
					var head='forrest gump';
					var tail='tom hanks';
					$.ajax({
						url:"/Knowledge_Explore/getExPath",
						data:{head:head,tail:tail},
						type:"GET",
						dataType:'json',
						contentType:'application/json',
						success:function(result,status){
							//console.log(result);
							/*var data=result['vertexList'];
							var link=result['edgeList'];
							console.log(data);
							console.log(link);
							for(var i=0;i<result.length;i++){
							}*/
							console.log(result);
						},
						error:function(er,status){
							alert(status);
						}
					});
				}
			</script>
</body>
</html>