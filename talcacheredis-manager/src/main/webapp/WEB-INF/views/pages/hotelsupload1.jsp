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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>

<head>
    <title>Home</title>
    <style type="text/css">
        /* .hero-unit {
            position: fixed;
            width: 60%;
            left: 20%;
            top: 10%;

        } */
    </style>
</head>
<body>


<form id="hoteluploadform1" name="hoteluploadform" action="" method="post"
      enctype="multipart/form-data">
    <table align="center" width="45%" cellspacing="0" cellpadding="5" border="0" class="formTable">
        <tr>
            <td><label class="">Select file : </label></td>
            <td><input type="file" name="file" size="50"/></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="button" value="Upload to Database" name="btnmhotelUpload" id="btnmhotelUpload1"/></td>
        </tr>
        <break>
            <break>

                <tr>
                    <td colspan="2">
                        <div id="info" style="color: green;"></div>
                    </td>
                </tr>

    </table>
</form>
<script src="${root}/resources/js/jquery-1.9.1.js"></script>
<script src="${root}/resources/js/hotelUpload.js"></script>
<script src="${root}/resources/js/jquery-1.11.2.min.js"></script>
</body>
</html>
