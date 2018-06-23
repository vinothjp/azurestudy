<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Employee</title>
</head>
<body>


	<form method="POST" action='EmployeeController' name="frmAddUser" enctype="multipart/form-data">
		<table>
			<tr>
				<td>First Name :</td>
				<td><input type="text" name="firstName" /></td>
			</tr>
			<tr>
				<td>Last Name :</td>
				<td><input type="text" name="lastName" /></td>
			</tr>
			<tr>
				<td>Email :</td>
				<td><input type="text" name="email" /></td>
			</tr>
			<tr>
				<td>Photo :</td>
				<td><input type="file" name="empPhoto" accept="image/*" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form>
</body>
</html>