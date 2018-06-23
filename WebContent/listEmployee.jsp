<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employees Details</title>
</head>
<body>
	<h3>Employees Details of Microsoft</h3>
	<table border=1>
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Profile</th>
				<th colspan=2>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${employees}" var="employee">
				<tr>
					<td><c:out value="${employee.rowKey}" /></td>
					<td><c:out value="${employee.partitionKey}" /></td>
					<td><c:out value="${employee.email}" /></td>
					<td><img src="${employee.profileURL}" alt="${employee.rowKey}"
						height="50" width="50"></td>
					<td><a
						href="EmployeeController?action=delete&firstName=<c:out value="${employee.rowKey}"/>&lastName=<c:out value="${employee.partitionKey}"/>">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>
		<a href="EmployeeController?action=insert">Add Employee</a>
	</p>
</body>
</html>