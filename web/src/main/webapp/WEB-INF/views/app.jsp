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
		Card Study		
	</header>
	
	<div id="appUser">
		<span><a id="appLogout" href="<c:url value="/j_spring_security_logout"/>">Log out</a></span>
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
		<div id="studyList">
			<p>List of saved studies:</p>
			<table id="savedStudyTable">
				<tr>
					<th class="tableHeader">ID</th>
					<th class="tableHeader">Topic</th>
					<th class="tableHeader">Cards done</th>
					<th class="tableHeader">Cards remaining</th>
					<th class="tableHeader">Date</th>
				</tr>
			</table>
		</div>
		
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
			<p>Study history for current user</p>
			<table id="histTable">
				<tr>
					<th class="tableHeader">ID</th>
					<th class="tableHeader">Cards answered</th>
					<th class="tableHeader">Correct answers</th>
					<th class="tableHeader">Date</th>
					<th class="tableHeader">Topic</th>
				</tr>
			</table>
		</div>
	</main>
	
	<footer>
		<p></p>		
	</footer>

</body>
</html>
