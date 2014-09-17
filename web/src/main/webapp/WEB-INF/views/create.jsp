<!DOCTYPE html>
<html>
<head>
	<link href="${pageContext.servletContext.contextPath}/resources/app_css.css" rel="stylesheet"/>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
		function create() {
			var msg   = $("#createForm").serialize();
			$.ajax({
				type: "POST",
				url: "/web/adduser/",
				data: msg,
				success: function(data) {
					ifexist(data);},
				error:  function(xhr, str){
					alert('Error: ' + xhr.responseCode);
				}
			});
		}
		
		function ifexist(data) {
			var obj = data;
			if (obj.exists == false) {
				$("#newCreated").html("User '" + obj.username + "' was successfully created");
				$("#created").css("display","block");
				$("#mainCreate").css("display","none");
			} else if (obj.exists == true) {
				$("#createText").html("User '" + obj.username + "' already exists, please enter new username");
			}
		}
	</script>
</head>
<body>
	<header id="appHeader">
		<img src="${pageContext.servletContext.contextPath}/resources/header.png">		
	</header>
	
	<main id="mainCreate">
		<form id="createForm" action="javascript:void(null);" onsubmit="create()">
			<p id="createText">Please enter new account registration data</p>
			Username: <input type="text" name="username" /><br>
			Password: <input type="password" name="password" /><br>
			<input type="submit" value="Create" />
		</form>
		<span><a class="appIndex" href="/web/">Back</a></span>
	</main>
	
	<div id="created">
		<p id="newCreated"></p>
		<span><a class="appIndex" id="logIn" href="/web/app">Log In</a></span>		
	</div>
</body>
</html>
