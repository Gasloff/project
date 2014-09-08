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
		function(){$("#studyTopic").css("display","initial");
			$("#studyId").css("display","none");
			$("#studyList").css("display","none");
			$("#histList").css("display","none");
			$("#showCard").css("display","none");
		}
	);  
});

$(document).ready(function(){
	$("#loadStudy").click(function(){
		$.get("/web/app/listStudy/",
			function(data,status){
				$("#savedStudyTable").append(jsonStudy(data));
			}
		);
		$("#studyId").css("display","initial");
		$("#studyList").css("display","initial");
		$("#studyTopic").css("display","none");
		$("#histList").css("display","none");
		$("#showCard").css("display","none");
	});	  
});

function jsonStudy(data) {
	var arr = data;
	var result = "";
	for (i = 0; i < arr.length; i++) { 
		result = result + "<tr><td class='tableData'>" + 
			arr[i].id + "</td><td class='tableData'>" + 			
			arr[i].topic + "</td><td class='tableData'>" + 
			arr[i].done + "</td><td class='tableData'>" + 
			arr[i].remaining + "</td><td class='tableData'>" + 
			arr[i].date + "</td></tr>";
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
		result = result + "<tr><td class='tableData'>" + 
			arr[i].id + "</td><td class='tableData'>" + 
			arr[i].answ + "</td><td class='tableData'>" + 
			arr[i].corr + "</td><td class='tableData'>" + 
			arr[i].histDate + "</td><td class='tableData'>" + 
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