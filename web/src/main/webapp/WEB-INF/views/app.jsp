<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<link href="${pageContext.servletContext.contextPath}/resources/app_css.css" rel="stylesheet"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<script>
$(document).ready(function(){
	$.get("/web/app/user/",
	function(data,status){
		$("#authUser").html(data);		
	});
});

$(document).ready(function(){
	$("#newStudy").hover(
		function(){$(this).css({"background-color":"#52A3CC","cursor":"pointer"});},
		function(){$(this).css({"background-color":"#66CCFF","cursor":"default"});}
	);  
});
$(document).ready(function(){
	$("#loadStudy").hover(
		function(){$(this).css({"background-color":"#52A3CC","cursor":"pointer"});},
		function(){$(this).css({"background-color":"#66CCFF","cursor":"default"});}
	);  
});
$(document).ready(function(){
	$("#history").hover(
		function(){$(this).css({"background-color":"#52A3CC","cursor":"pointer"});},
		function(){$(this).css({"background-color":"#66CCFF","cursor":"default"});}
	);  
});

$(document).ready(function(){
	$("#newStudy").click(
		function(){$("#studyTopic").css("visibility","visible");}
	);  
});

function call() {
	var msg   = $("#topicForm").serialize();
	$.ajax({
		type: "POST",
		url: "/web/app/topic/",
		data: msg,
		success: function(data) {
			$("main").html(data);
			},
		error:  function(xhr, str){
			alert('Error: ' + xhr.responseCode);
		}
	});
}

</script>

</head>
<body>
	<header id="appHeader">
		Card Study		
	</header>
	
	<div id="appUser">
		<span><a id="appLogout" href="<c:url value="/j_spring_security_logout"/>">Logout</a></span>
		<span class="welcome" id="authUser">Unknown user</span>
		<span class="welcome">Welcome,</span>
	</div>
	
	<div id="appMenu">
		<ul id="menulist">
			<li class="menu" id="newStudy">New study</li>
			<li class="menu" id="loadStudy">Load study</li>
			<li class="menu" id="history">History</li>			
		</ul>		
	</div>
	
	<div id="studyTopic">
		<form name="topicForm" id="topicForm" action="javascript:void(null);" onsubmit="call()">
			Please enter new study topic: <input id="topic" type="text" name="topic" value =""/>
			<input type="submit" value="Send">
		</form>
	</div>
	
	<main id="main">
		
	</main>
	
	<footer>
		<p></p>
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		</form>
	</footer>

</body>
</html>
