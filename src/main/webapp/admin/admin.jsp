<%@page import="com.project.smarthome.helpers.StatusHelper"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
</head>
<body>

	<h1 align="center"><b><u>Admin</u></b></h1>
	
	<h3 align="center">Database</h3>
	
	<hr>
	
	<h3 align="center">Microcontrollers</h3>
	
	<table align="center" width="800" height="100">
		<tr>
			<td><h4>Living room</h4></td>
			<td><%= StatusHelper.displayMcuStatus("livingroom") %></td>
			<td>
				<form action="sleep_livingroom">
					<input type="submit" value="ON/OFF">
				</form>
			</td>
			<td>
				<form action="ts_livingroom">
					Sample time: <input type="text" name="newTsValue">
					<input type="submit" value="Submit">
				</form>
			</td>
			<td>Sample time: <%= StatusHelper.displayMcuTs("livingroom") %> min.</td>
		</tr>
		
		<tr>
			<td><h4>Kitchen</h4></td>
			<td><%= StatusHelper.displayMcuStatus("kitchen") %></td>
			<td>
				<form action="sleep_kitchen">
					<input type="submit" value="ON/OFF">
				</form>
			</td>
			<td>
				<form action="ts_kitchen">
					Sample time: <input type="text" name="newTsValue">
					<input type="submit" value="Submit">
				</form>
			</td>
			<td>Sample time: <%= StatusHelper.displayMcuTs("kitchen") %> min.</td>
		</tr>
		
		<tr>
			<td><h4>Outside</h4></td>
			<td><%= StatusHelper.displayMcuStatus("outside") %></td>
			<td>
				<form action="sleep_outside">
					<input type="submit" value="ON/OFF">
				</form>
			</td>
			<td>
				<form action="ts_outside">
					Sample time: <input type="text" name="newTsValue">
					<input type="submit" value="Submit">
				</form>
			</td>
			<td>Sample time: <%= StatusHelper.displayMcuTs("outside") %> min.</td>
		</tr>
	</table>
	
	<%@ include file="admin-footer.html" %>
	
</body>
</html>