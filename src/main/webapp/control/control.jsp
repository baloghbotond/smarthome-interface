<%@page import="com.project.smarthome.helpers.StatusHelper"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Control</title>
</head>
<body>

	<h1 align="center"><b><u>Control</u></b></h1>
	
	<br/>
	
	<table align="center" width="400" height="100">
		<tr>
			<td>Living room</td>
			<td>
				<form action="livingroom">
					<input type="submit" value="ON/OFF">
				</form>
			</td>
			<td><b><%=StatusHelper.displayLightStatus("livingroom")%></b></td>
			<td>Temperature: <%= StatusHelper.displayTemperature("livingroom") %></td>
		</tr>
		
		<tr>
			<td>Kitchen</td>
			<td>
				<form action="kitchen">
					<input type="submit" value="ON/OFF">
				</form>
			</td>
			<td><b><%=StatusHelper.displayLightStatus("kitchen")%></b></td>
			<td>Temperature: <%= StatusHelper.displayTemperature("kitchen") %></td>
		</tr>
		
		<tr>
			<td>Outside</td>
			<td>
				<form action="outside">
					<input type="submit" value="ON/OFF">
				</form>
			</td>
			<td><b><%=StatusHelper.displayLightStatus("outside")%></b></td>
			<td>Temperature: <%= StatusHelper.displayTemperature("outside") %></td>
		</tr>
	</table>
	
	<br/>
	
	<table align="center" width="200" height="100">
		<tr>
			<td>ALL OFF</td>
			<td>
				<form action="everywhere">
					<input type="submit" value="OFF">
				</form>
			</td>
		</tr>
	</table>
	
	<%@ include file="control-footer.html" %>
</body>
</html>