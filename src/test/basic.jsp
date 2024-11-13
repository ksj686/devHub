<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<h1>JSP, JSTL, EL</h1>
	
	<c:forEach var="i" begin="1" end="3">
		${list[i] }
	</c:forEach>
	
	<c:forEach var="item" items="${list }" varStatus="idx">
		<div>${idx.count }. ${item }</div>
	</c:forEach>
	
	<h3>${ fn:length(list) }</h3>
	<h3>${ fn:replace("A,B,C,D,E", ",", "-") }</h3>
	
	<c:forEach var="item" items="${map }" varStatus="idx">
		<div>${idx.count }. ${item.key }: ${item.value }</div>
	</c:forEach>
	
	<br/>
	<ul>
		<li>${user.idx }</li>
		<li>${user.userId }</li>
		<li>${user["userId"] }</li>
	</ul>
	
	<fmt:setLocale value="en_US" />
	<!-- fmt:setLocale value="ko_KR" / -->
	<fmt:formatNumber value="500000" type="currency" /><br>
	<fmt:formatNumber value="500000" pattern="###,###,###.00" /><br>
	
	<jsp:useBean id="today" class="java.util.Date" />
	<div>${today }</div>
	<fmt:formatDate value="${today }" type="date" />
	<fmt:formatDate value="${today }" type="time" />
	<fmt:formatDate value="${today }" pattern="yyyy-MM-dd hh:mm:ss" />
		
</body>
</html>