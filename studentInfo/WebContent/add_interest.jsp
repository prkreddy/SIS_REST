<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Interest</title>
</head>
<body>

<h2 align="middle">STUDENT INFORMATION SYSTEM</h2>
	<br>
	<h2>Add Interest</h2>
	
	<br>
	<s:form action="addInterest">
		<s:textfield key="name" label="name" />
		<s:textarea key="details" name="details" rows="5" cols="50"/>
		   	
		<s:submit label="Add"/>
	</s:form>
	<br>
<a href="adminhome.jsp"> BACK </a>
	

</body>
</html>