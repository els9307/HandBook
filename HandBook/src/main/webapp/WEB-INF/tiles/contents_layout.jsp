<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="ko">
<head>
<tiles:insertAttribute name="header" />
</head>
<body>
		<!--CONTAINER-->
		<tiles:insertAttribute name="body" />
		<!--CONTAINER-->
<tiles:insertAttribute name="footer"/>
</body>
</html>