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
			
			<td>
				<b>O</b>
			</td>
			
		</tr>
		
		<tr>
			<td>Kitchen</td>
			<td>
				<form action="kitchen">
					<input type="submit" value="ON/OFF">
				</form>
			</td>
			<td><b>O</b></td>
		</tr>
		
		<tr>
			<td>Outside</td>
			<td>
				<form action="outside">
					<input type="submit" value="ON/OFF">
				</form>
			</td>
			<td><b>O</b></td>
		</tr>
	</table>
	
	<br/>
	
	<table align="center" width="200" height="100">
		<tr>
			<td>ALL OFF</td>
			<td><button type="button">OFF</button></td>
		</tr>
	</table>
	
</body>
</html>