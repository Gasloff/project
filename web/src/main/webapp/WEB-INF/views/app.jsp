<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
	<link href="${pageContext.servletContext.contextPath}/resources/app_css.css" rel="stylesheet"/>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.servletContext.contextPath}/resources/app_js.js"></script>
</head>

<body>
	<header id="appHeader">
		<img src="${pageContext.servletContext.contextPath}/resources/header.png">
	</header>
	
	<div id="appUser">
		<span><a id="appLogout" href="<c:url value="/j_spring_security_logout"/>">Log out</a></span>
		<span class="welcome" id="authUser">Unknown user</span>
		<span class="welcome">Welcome,</span>
	</div>
	
	<div id="appMenu">
		<ul id="menulist">
			<li class="menu button" id="newStudy">New study</li>
			<li class="menu button" id="loadStudy">Load study</li>
			<li class="menu button" id="history">History</li>			
		</ul>		
	</div>
	
	<div id="topField">
		
		<div id="studyTopic">
			<form name="topicForm" id="topicForm" action="javascript:void(null);" onsubmit="newStudy()">
				<span id="topicFormText">Please enter new study topic ("all" for all cards in database):</span>
				<input id="topic" type="text" name="topic" value =""/>
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
		<div id="topicListDiv">		
			<p>List of available topics:</p>
			<ul id='topicList'></ul>
		</div>
		
		<div id="studyList">
			<p>List of saved studies:</p>
			<table id="savedStudyTable">
			</table>
		</div>
		
		<div id="showCard">
			<div id="word">Word:</div>
			<div id="translation">
				<form name="answerForm" id="answerForm" action="javascript:void(null);" onsubmit="answ()">
					Translation: <input id="answer" type="text" name="answer" value =""/>
					<input type="submit" value="SEND">
				</form>
			</div>
			<div id="comment"><br></div>
			<div>
				<p id="next" class="button">Next card</p>
			</div>
			<div>
				<p id="saveStudy" class="button">Save study</p>
			</div>
		</div>
		
		<div id="histList">
			<p>Study history for current user:</p>
			<table id="histTable">
			</table>
		</div>
	</main>

</body>
</html>
