<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<link href="${pageContext.servletContext.contextPath}/resources/app_css.css" rel="stylesheet"/>

</head>
<body>
	<header id="appHeader">
		Card Study		
	</header>
	
	
	
	<main id="main">
		<p><a href="/web/app">Log In</a></p>
		<p><a href="/web/create">Create new User account</a></p>
	</main>
	
	<footer>
		<p></p>		
	</footer>	
</body>
</html>
