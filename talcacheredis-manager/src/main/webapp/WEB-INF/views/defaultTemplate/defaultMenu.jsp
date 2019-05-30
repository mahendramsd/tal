<%--
   Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
   PROPRIETARY AND COPYRIGHT NOTICE.

   This software product contains information which is proprietary to
   and considered a trade secret The Data management Group Ltd .
   It is expressly agreed that it shall not be reproduced in whole or part,
   disclosed, divulged or otherwise made available to any third party directly
   or indirectly.  Reproduction of this product for any purpose is prohibited
   without written authorisation from the The Data Management Group Ltd
   All Rights Reserved.

   E-Mail andyj@datam.co.uk
   URL : www.datam.co.uk
   Created By : Mahendra Sri Dayarathna

 --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>

    <style>
        .nav-sidebar {
            width: 100%;
            padding: 23px 2px;
        }

        .nav-sidebar a {
            color: #333;
            -webkit-transition: all 0.08s linear;
            -moz-transition: all 0.08s linear;
            -o-transition: all 0.08s linear;
            transition: all 0.08s linear;
        }

        .nav-sidebar .active a {
            cursor: default;
            background-color: #0b56a8;
            color: #fff;
        }

        .nav-sidebar .active a:hover {
            background-color: #E50000;
        }

        .nav-sidebar .text-overflow a, .nav-sidebar .text-overflow .media-body {
            white-space: nowrap;
            overflow: hidden;
            -o-text-overflow: ellipsis;
            text-overflow: ellipsis;
        }
    </style>

</head>
<body>
<nav class="nav-sidebar">
    <ul class="nav tabs">
        <li class="active"><a href="${root}/">Services</a></li>
        <li class=""><a href="${root}/loadInitialUpdate">
            Upload Initial Data </a></li>
        <li class=""><a href="${root}/getXml">Download
            Notification</a></li>
        <li class=""><a href="${root}/getAvailability">Hotel
            Availability Enquiry</a></li>
        <li class=""><a href="${root}/getUpdates">
            Automate Updated Availability</a></li>
        <li class=""><a href="${root}/getMainHotelUpdate">
            Automate Updated Main Hotels</a></li>
        <li class=""><a href="${root}/getAgentMarkup">
            Automate Updated AgentMarkup</a></li>
    </ul>
</nav>
</body>
</html>