<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Поезда</title>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Itim&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Raleway+Dots&display=swap" rel="stylesheet">
<link href="route.css" type="text/css" rel="stylesheet" >
</head>
<body>

<!-- container starts here -->
 <div class="container">
	<div class="body-wrapper" id="bd">
	<!-- Body-wrapper wraps this div -->
		<div class="center-wrapper">
		<!--center-wrapper wraps this cards-> left-side -->
			<div class="left-side">
			<!-- left-side starts here -->
				<div class="text">
				<!-- Text starts  here-->
					<ul style = "list-style-type:none"> 
						<li><form method="post" action="AllServlet"><input type="submit" class="a" value="Все маршруты"></form></li>
						<!-- <li><a class="add" href="route.jsp" id="a">Информация о маршруте</a></li>-->
						<li><a class="edit" href="routes.jsp" id="a">Информация о маршрутах</a></li>
					</ul>
				</div>	
				<!-- Text finishes  here-->
			</div>
			<!-- left-side finishes here -->
		</div>
		<!--center-wrapper finishes here-->
		<div class="center-wrapper">
		<!--center-wrapper wraps this card-> article -->	
			<div class = "article">
		     <h2 class="article-title">Самара</h2>
			 <h3 class="article-subtitle">Расписание электричек</h3>
				<form method="post" action="RouteServlet" name="Form">
					
					<div class="forma-button">
					<input type="text" placeholder="Введите номер маршрута" name="num" class="place">
					<label for="button"><input type="submit" id="button" value="Получить данные"></label>
							
					</div>
				</form>								
			</div>	
		</div>	
		<!--center-wrapper finishes here-->
		<div class="center-wrapper">
		<!--center-wrapper wraps this cards-> right-side -->
			
		</div>
		<!--center-wrapper finishes here-->
	</div>
	<!-- body-wrapper finishes  here-->
	<div class="footer">
	<!-- footer starts here -->
		
		<!-- Footer-year starts here -->
		<div class="footer-year">
			<h2>2021</h2>
		</div>
		<!-- Footer-year finishes here -->
		
		<!-- Footer-theme starts here -->
		<div class="footer-theme">
			<!-- footer-theme-img starts here -->
			<div class="footer-theme-img">
			
				<img src="./img/sun_2.png" id="theme" onclick="change();">
			
			</div>
			<!-- footer-theme-img finishes here -->
		</div>
		<!-- Footer-theme finishes here -->
		
	</div>
	<!-- footer finishes here -->
</div>
<!-- container finishes here -->
<script>

function change(){

	var bg1=document.body.style.backgroundColor;
	document.body.style.transition = "all linear 1s";
	var elem =document.getElementById("bd");
	document.body.backgroundImage
	if(document.body.style.backgroundImage = "url('./img/fon_2.jpg')"){
		document.body.style.backgroundImage = "url('./img/fon.jpg')";
		document.body.style.backgroundRepeat = "no-repeat"; 
	}
	
	
	
	};

		
		
			
	</script>
			
</body>
</html>