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

 --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Output</title>
</head>
<body>
<div class="row">
    <div class="col-md-1"></div>
    <div class="col-md-7" style="border: 2px solid; border-color: #4a4a4a">
        <div class="row">
            <c:choose>
                <c:when test="${isSuccess}">
                    <h2 style="color: green;text-align: center;">${message}</h2>
                </c:when>
                <c:otherwise>
                    <h2 style="color: red;text-align: center;">${message}</h2>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="row" style="text-align: center;background-color: #9bbae9;"><label>Processing time : ${totalTime}
            Seconds &nbsp;&nbsp;&nbsp; Uploading Time : ${uploadSeconds} Milliseconds </label></div>
        <hr>
        <div class="clear">&nbsp;</div>

        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-3"><label>Total Hotels : </label></div>
            <div class="col-md-4" style="background-color: #9bbae9;"><label
                    style="font-size: 15px;">${mainHotelCount}</label></div>
            <%-- <div class="col-md-3"><label>Upload Hotels : </label></div>
            <div class="col-md-2" style="background-color: #9bbae9;"><label style="font-size: 15px;">${uploadHotelsCount}</label></div> --%>
            <div class="col-md-3"></div>
        </div>
        <div class="clear">&nbsp;</div>


    </div>
    <div class="col-md-4"></div>

</div>

</body>
</html>