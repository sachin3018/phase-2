<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book And Fly</title>
</head>
<body >
<br>
<a href=HomePage.jsp style="color:black;text-decoration:none ;font-size:35px;font-weight:bold;">FlyAway</a>
<br><br>

<%
	@SuppressWarnings("unchecked")
	HashMap<String,String> user=(HashMap<String,String>)session.getAttribute("user");
	if(user==null){
		response.sendRedirect("UserPage.jsp");
	}else{
%>
<%
	@SuppressWarnings("unchecked")
	HashMap<String,String> userd = (HashMap<String,String>)session.getAttribute("user");
%>

<h1>User Details</h1>

<center>
<table border="1">
<caption>User Details</caption>
<tr>
	<th>Name</th>
	<th>Email</th>
	<th>Phone No</th>
	<th>Adhar Number</th>
</tr>
<tr>
<td><%=userd.get("name")%></td>
<td><%=userd.get("email")%></td>
<td><%=userd.get("phno")%></td>
<td><%=userd.get("adno")%></td>
</tr>
</table>
</center>
<center>
<h2>Passenger Details</h2>
<div style="border:2px solid black;width:25%;border-radius:20px;padding:20px" align="center">
<form action=Succesfull.jsp method="post" >


    <table >
    <%
		for(int i = 0; i < Integer.parseInt(userd.get("passenger"));i++){
	%>
    <tr>
    <td><label for=email>Name</label><br></td>
    <td><input type="text" name=name id=name /></td>
    </tr>
    <tr>
    <td><label for=pass>Phone Number</label><br></td>
    <td><input type="number" name=phone id=phone /></td>
    </tr>
    <%
		}}
	%>
    <tr>
    <td><a href=Succesfull.jsp><input type="submit" value="Pay Now"/></a></td>
    </tr>
    </table>

</form>
</div>
</center>
</body>
</html>