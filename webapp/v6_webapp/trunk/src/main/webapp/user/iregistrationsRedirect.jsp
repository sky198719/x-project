<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    window.location.href = "${path}/#!/static/html/user/register.html?path=user&uuid=${uuid}";
</script>