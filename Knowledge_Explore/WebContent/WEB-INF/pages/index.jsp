<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test spring mvc</title>
<script type="text/javascript" src="jquery-1.11.1.min.js"></script>
</head>
<body>
			<div style="width:300px;height:200px;">
				<form name="myform" action="/seed/test" method="get">
					<input id="name" name="name" value="" maxlength=10>
					<input type="button" value="提交" onClick="fact()">
				</form>
				<div id="test"></div>
			</div>
			<script type="text/javascript">
	
				function fact(){
					var nn=$('#name').val();
					$.ajax({
						url:"/seed/result",
						data:{},
						type:"POST",
						dataType:'json',
						success:function(result,status){
							alert(result);
							$('#test').val(result);
						},
						error:function(er,status){
							alert(er);
						}
					})
					
				}
			</script>
</body>
</html>