<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.ryan.oa.utils.JdbcUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OA Processor Index</title>
</head>
<body>
<%
	String outMsg = null;
    if(JdbcUtils.DBConnection() != null){
	outMsg = "Successfully";
	}
%>
<h1><%=outMsg %></h1>
<h2><a href="test">test</a></h2>
</body>
</html>
