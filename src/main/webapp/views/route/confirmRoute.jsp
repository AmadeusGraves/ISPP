<%--
 * display.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="route.price"  var="msgPrice"/>
<spring:message code="route.distance"  var="msgDistance"/>
<spring:message code="route.confirm"  var="confirm"/>
<spring:message code="route.cancel"  var="cancel"/>
<spring:message code="route.confirm" var="msgConfirm" />



<center>
	<div class="col-sm-10 text-center" style="padding-top: 20px;">
		<h2>Do you want to confirm this route?</h2>
	</div>
</center>

<center>
	<div class="col-sm-6 text-center" style="padding-top: 20px;">
		<h4><jstl:out value="${msgPrice}" />:</h4>
		<h3><span class="badge badge-success"><jstl:out value="${route.pricePerPassenger}" /> &euro;</span></h3>
		<br />
	</div>
</center>
<center>
	<div class="col-sm-6 text-center" style="padding-top: 20px;">
		<h4><jstl:out value="${msgDistance}" />:</h4>
		<h3><span class="badge badge-primary"><jstl:out value="${route.distance}" /></span></h3>
		<br />
	</div>
</center>
<center>
	<div class="col-sm-6 text-center" style="padding-top: 20px;">
		<a href="route/list.do" class="btn btn-success"><jstl:out value="${confirm}" /></a>

		<spring:url var="deleteUrl" value="route/driver/delete.do">
			<spring:param name="routeId" value="${route.id}" />
		</spring:url>
		<a href="${deleteUrl}" class="btn btn-danger" onclick="return confirm('${msgConfirm}')"><jstl:out
				value="${cancel}" /></a>
	</div>
</center>





