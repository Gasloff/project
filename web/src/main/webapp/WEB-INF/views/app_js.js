var topicList;

//Request for User name
$(document).ready(function(){
	$.get("/web/app/user/",
	function(data,status){
		$("#authUser").html(data);		
	});
});

$(document).ready(function(){
	$(".button").hover(
		function(){$(this).css({"background-color":"#52A3CC","cursor":"pointer"});},
		function(){$(this).css({"background-color":"#66CCFF","cursor":"default"});}
	);  
});

//New study button
$(document).ready(function(){
	$("#newStudy").click(
		function(){$("#studyTopic").css("display","initial");
			$("#topicListDiv").css("display","initial");
			$("#studyId").css("display","none");
			$("#studyList").css("display","none");
			$("#histList").css("display","none");
			$("#showCard").css("display","none");
			$.get("/web/app/listTopic/",
			function(data,status){
				$("#topicList").html("");
				$("#topicList").append(jsonTopic(data));
			});
		}
	); 
});

function jsonTopic(data) {
	topicList = data;
	var result = "";
	for (i = 0; i < topicList.length; i++) { 
		result = result + "<li class='topicElement'>" + 
			topicList[i].topic + "</li>";
	}
	return result;
}

//Load study button
$(document).ready(function(){
	$("#loadStudy").click(function(){
		$.get("/web/app/listStudy/",
			function(data,status){
				$("#savedStudyTable").html(
					"<tr>" +
						"<th class='tableHeader'>ID</th>" +
						"<th class='tableHeader'>Topic</th>" +
						"<th class='tableHeader'>Cards done</th>" +
						"<th class='tableHeader'>Cards remaining</th>" +
						"<th class='tableHeader'>Date</th>" +
					"</tr>"
				);
				$("#savedStudyTable").append(jsonStudy(data));
			}
		);
		$("#studyId").css("display","initial");
		$("#studyList").css("display","initial");
		$("#studyTopic").css("display","none");
		$("#topicListDiv").css("display","none");
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

//History button
$(document).ready(function(){
	$("#history").click(function(){
		$.get("/web/app/listHist/",
			function(data,status){
				$("#histTable").html(
					"<tr>" +
						"<th class='tableHeader'>ID</th>" +
						"<th class='tableHeader'>Cards answered</th>" +
						"<th class='tableHeader'>Correct answers</th>" +
						"<th class='tableHeader'>Date</th>" +
						"<th class='tableHeader'>Topic</th>" +
					"</tr>"
				);
				$("#histTable").append(jsonHist(data));
			}
		);
		$("#histList").css("display","initial");
		$("#studyTopic").css("display","none");
		$("#topicListDiv").css("display","none");
		$("#studyId").css("display","none");
		$("#studyList").css("display","none");
		$("#showCard").css("display","none");		
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

function newStudy() {
	var valid;
	if ($("#topic").val() == "all") {
		valid = true;
	} else {
		for (i = 0; i < topicList.length; i++) {	
			if ($("#topic").val() == topicList[i].topic) {
				valid = true;
			}
		}
	}
	if (valid) {
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
		$("#showCard").css("display","initial");
		$("#studyTopic").css("display","none");
		$("#topicListDiv").css("display","none");
	} else {
		$("#topicFormText").html("There is no such topic, please try again:");		
	}
}

//Loads study by ID
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

//Process answer
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

//Request for next card
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

//Save study button
$(document).ready(function(){
	$("#saveStudy").click(function(){
		$.get("/web/app/saveStudy/",
		function(data,status){
			$("#comment").html(data);
		});
	});  
});