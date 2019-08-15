<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Synergy</title>
<c:url var="bootstrapCss" value="/css/bootstrap.min.css" />
<c:url var="siteCss" value="/css/site.css" />
<c:url var="jQueryJs" value="/js/jquery.min.js" />
<c:url var="jqValidateJs" value="/js/jquery.validate.min.js" />
<c:url var="jqvAddMethJs" value="/js/additional-methods.min.js" />
<c:url var="jqTimeagoJs" value="/js/jquery.timeago.js" />
<c:url var="popperJs" value="/js/popper.min.js" />
<c:url var="bootstrapJs" value="/js/bootstrap.min.js" />
<link href="http://fonts.googleapis.com/css?family=Droid+Serif"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${siteCss}">

</head>

<body>
	<header class="header">
		<input
			class="menu-btn" type="checkbox" id="menu-btn" /> 
			<label class="menu-icon" for="menu-btn"><span class="navicon"></span></label>
		<ul class="menu">
			<li><a href="/capstone/login">LOGIN</a></li>
			<li><a href="/capstone/users/new">SIGNUP</a></li>
			<li><a href="/capstone/users/${username}">DASHBOARD</a></li>
			<li><a href="/capstone/logout">LOGOUT</a></li>
		</ul>
	</header>