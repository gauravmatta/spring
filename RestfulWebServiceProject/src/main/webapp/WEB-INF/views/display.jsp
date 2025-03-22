<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

Result is : <%= request.getAttribute("result") %>
<br/>
Result is : ${reqResult}

<form action="add">
<input type="text" name="t1"><br>
<input type="text" name="t2"><br>
<input type="submit"><br>
</form>