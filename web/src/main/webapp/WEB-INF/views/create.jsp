<!DOCTYPE html>
<html>
<head>
	<link href="${pageContext.servletContext.contextPath}/resources/app_css.css" rel="stylesheet"/>
</head>
<body>
	<header id="appHeader">
		Card Study		
	</header>
	
	<main id="main">
		<form action="/web/adduser" method="POST">
			<p>Please enter new account registration data</p>
			Username: <input type="text" name="username" /><br>
			Password: <input type="password" name="password" /><br>
			<input type="submit" value="Create" />
		</form>
		<span><a class="appIndex" href="/web/">Back</a></span>
	</main>
</body>
</html>
