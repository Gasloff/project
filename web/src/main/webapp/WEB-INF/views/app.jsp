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
	$(".menu").hover(
		function(){$(this).css({"background-color":"#52A3CC","cursor":"pointer"});},
		function(){$(this).css({"background-color":"#66CCFF","cursor":"default"});}
	);  
});

$(document).ready(function(){
	$("#newStudy").click(
		function(){$("#studyTopic").css("display","initial");}
	);  
});

$(document).ready(function(){
	$("#loadStudy").click(function(){
		$.get("/web/app/listStudy/",
			function(data,status){
				$("#studyList").html(jsonStudy(data));
			}
		);
		$("#studyId").css("display","initial");
		$("#studyList").css("display","initial");
	});	  
});

function jsonStudy(data) {
	var arr = data;
	var result = "";
	for (i = 0; i < arr.length; i++) { 
		result = result + arr[i].id + ": " + arr[i].topic + "<br>";
	}
	return result;
}

$(document).ready(function(){
	$("#history").click(function(){
		$.get("/web/app/listHist/",
			function(data,status){
				$("#histTable").append(jsonHist(data));
			}
		);
		$("#histList").css("display","initial");
	});	  
});

function jsonHist(data) {
	var arr = data;
	var result = "";
	for (i = 0; i < arr.length; i++) { 
		result = result + "<tr><td class='histData'>" + 
			arr[i].id + "</td><td class='histData'>" + 
			arr[i].answ + "</td><td class='histData'>" + 
			arr[i].corr + "</td><td class='histData'>" + 
			arr[i].histDate + "</td><td class='histData'>" + 
			arr[i].topic + "</td></tr>";
	}
	return result;
}

function call() {
	var msg   = $("#topicForm").serialize();
	$.ajax({
		type: "POST",
		url: "/web/app/topic/",
		data: msg,
		success: function(data) {
			$("#word").html("Word: " + data);
			},
		error:  function(xhr, str){
			alert('Error: ' + xhr.responseCode);
		}
	});
	$("#studyTopic").css("display","none");
	$("#showCard").css("display","initial");
}

function studyById() {
	var msg   = $("#studyForm").serialize();
	$.ajax({
		type: "POST",
		url: "/web/app/loadStudy/",
		data: msg,
		success: function(data) {
			$("#word").html("Word: " + data);
			},
		error:  function(xhr, str){
			alert('Error: ' + xhr.responseCode);
		}
	});
	$("#studyId").css("display","none");
	$("#studyList").css("display","none");
	$("#showCard").css("display","initial");
}

function answ() {
	var msg   = $("#answerForm").serialize();
	$.ajax({
		type: "POST",
		url: "/web/app/answer/",
		data: msg,
		success: function(data) {
			$("#comment").html(data);
			},
		error:  function(xhr, str){
			alert('Error: ' + xhr.responseCode);
		}
	});
}

$(document).ready(function(){
	$("#next").hover(
		function(){$(this).css({"background-color":"#52A3CC","cursor":"pointer"});},
		function(){$(this).css({"background-color":"#66CCFF","cursor":"default"});}
	);  
});

$(document).ready(function(){
	$("#next").click(function(){
		$.get("/web/app/next/",
		function(data,status){
			$("#word").html("Word: " + data);
			$("#answer").val("");
			$("#comment").html("<br>");
		});
	});  
});

$(document).ready(function(){
	$("#saveStudy").hover(
		function(){$(this).css({"background-color":"#52A3CC","cursor":"pointer"});},
		function(){$(this).css({"background-color":"#66CCFF","cursor":"default"});}
	);  
});

$(document).ready(function(){
	$("#saveStudy").click(function(){
		$.get("/web/app/saveStudy/",
		function(data,status){
			$("#comment").html(data);
		});
	});  
});

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
	
	<div id="topField">
		
		<div id="studyTopic">
			<form name="topicForm" id="topicForm" action="javascript:void(null);" onsubmit="call()">
				Please enter new study topic: <input id="topic" type="text" name="topic" value =""/>
				<input type="submit" value="OK">
			</form>
		</div>
	
		<div id="studyId">
			<form name="studyForm" id="studyForm" action="javascript:void(null);" onsubmit="studyById()">
				Please enter saved study ID: <input id="studyIdInput" type="text" name="studyId" value =""/>
				<input type="submit" value="OK">
			</form>
		</div>
		
	</div>
	
	<main id="main">
		<div id="studyList"></div>
		
		<div id="showCard">
			<div id="word">Word:</div>
			<div id="transl">
				<form name="answerForm" id="answerForm" action="javascript:void(null);" onsubmit="answ()">
					Translation: <input id="answer" type="text" name="answer" value =""/>
					<input type="submit" value="SEND">
				</form>
			</div>
			<div id="comment"><br></div>
			<div>
				<p id="next">Next card</p>
			</div>
			<div>
				<p id="saveStudy">Save study</p>
			</div>
		</div>
		
		<div id="histList">
			<table id="histTable">
				<tr>
					<th class="histHeader">ID</th>
					<th class="histHeader">Cards answered</th>
					<th class="histHeader">Correct answers</th>
					<th class="histHeader">Date</th>
					<th class="histHeader">Topic</th>
				</tr>
			</table>
		</div>
	</main>
	
	<footer>
		<p></p>		
	</footer>

</body>
</html>
