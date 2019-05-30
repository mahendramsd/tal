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
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>

    <link rel="stylesheet" type="text/css" href="${root}/resources/css/mainLayout.css">
    <link rel="stylesheet" type="text/css" href="${root}/resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${root}/resources/css/daterangepicker.css.css">
    <link rel="stylesheet" type="text/css" href="${root}/resources/css/daterangepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${root}/resources/css/style.css"/>


    <script src="${root}/resources/js/jquery-2.1.1.min.js"></script>
    <script src="${root}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script src="${root}/resources/js/moment.min.js"></script>
    <script src="${root}/resources/js/daterangepicker.js"></script>
    <script src="${root}/resources/js/jquery-ui-1.10.4.custom.js"></script>
    <script src="${root}/resources/js/modernizr.js"></script>

</head>
<body>
<section class="col-md-2 menu sidebar">
    <tiles:insertAttribute name="menu"/>
</section>
<section class="col-md-10 containerDiv">
    <div class="header">
        <tiles:insertAttribute name="header"/>
    </div>
    <div class="row content">
        <tiles:insertAttribute name="body"/>
    </div>
</section>
<footer class="footer col-md-12">
    <tiles:insertAttribute name="footer"/>
</footer>
<div id="dvLoadingOverlay"></div>
<div id="dvLoading"></div>
</body>
</html>