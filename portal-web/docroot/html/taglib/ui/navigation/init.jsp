<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.theme.NavItem" %>

<%
String bulletStyle = StringUtil.toLowerCase(((String)request.getAttribute("liferay-ui:navigation:bulletStyle")));
String headerType = (String)request.getAttribute("liferay-ui:navigation:headerType");
String navigationString = (String)request.getAttribute("liferay-ui:navigation:navigationString");
boolean preview = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:navigation:preview"));
NavItem rootNavItem = (NavItem)request.getAttribute("liferay-ui:navigation:rootNavItem");
%>