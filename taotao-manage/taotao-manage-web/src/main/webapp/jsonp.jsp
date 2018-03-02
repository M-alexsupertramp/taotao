<%@page import="org.apache.commons.lang3.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
	//获取回调函数名称
	String callback=request.getParameter("callback");
	//判断是否为null
	if(StringUtils.isNoneEmpty(callback)){
		out.print(callback+"({\"num\":123})");
	}else{
		out.print("{\"num\":123}");
	}
%>