<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<div class="text-center active-routes">
	<h3>
		<spring:message code="messages" />
	</h3>
</div>
<spring:url value="/styles/messages.css" var="messages" />
<link href="${messages}" rel="stylesheet" />
<script src="${messages}"></script>

<security:authorize access="hasAnyRole('DRIVER', 'PASSENGER', 'ADMIN')">
	<center>
		<jstl:forEach items="${threads}" var="thread">

			<a href="thread/message/view.do?threadId=${thread.id}">
				${thread.route.origin} <i class="fas fa-arrow-right"></i> ${thread.route.destination}</a>
			<span class="fa-stack"> <i class="fa fa-comment fa-stack-2x"
				style="color: red"></i> <strong
				class="fa-stack-1x fa-stack-text fa-inverse">${thread.newMessages}</strong>
			</span>


			<br>
			<spring:message code="thread.participants" /><br>
			<span class="badge badge-pill badge-primary">
			${thread.participantA.name} ${thread.participantA.surname}
			</span>
			<br>
			<div class="container2" style="padding-top:5px">
			<span class="badge badge-pill badge-primary">
			${thread.participantB.name} ${thread.participantB.surname}
			</span>
			</div>
			<br>
			<i class="fa fa-calendar" aria-hidden="true"></i>
			<fmt:formatDate value="${thread.lastMessage.issueDate}" type = "both" 
         dateStyle = "medium" timeStyle = "short" />
			<br>
			<br>
		</jstl:forEach>

	</center>
</security:authorize>