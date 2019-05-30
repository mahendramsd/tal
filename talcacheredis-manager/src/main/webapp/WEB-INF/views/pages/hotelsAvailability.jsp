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
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel Update</title>

    <style>
        .no-js #loader {
            display: none;
        }

        .js #loader {
            display: block;
            position: absolute;
            left: 100px;
            top: 0;
        }

        .se-pre-con {
            position: fixed;
            left: 0px;
            top: 0px;
            width: 100%;
            height: 100%;
            z-index: 9999;
            background: url(${root}/resources/images/Preloader_3.gif) center no-repeat #fff;
            opacity: .8;
        }
    </style>
<body>
<!-- Paste this code after body tag -->
<div class="se-pre-con"></div>
<!-- Ends -->

<div class="wrapper">
    <div class="row">
        <div class="col-md-12">
            <header>
                <h3>Update Availability To The Postgresql Database From PICK
                    Database.</h3>
            </header>
            <hr>

            <c:if test="${isProcess}">
                <div id="div_initial_notify">
                    <h5 class="alert alert-success text-center">Started
                        Availability Automate Process</h5>
                    <div class="progressLoading">
                        <img src="${root}/resources/images/loading.gif">
                    </div>
                </div>
            </c:if>
            <div id="div_submit_notify">
                <h5 class="alert alert-success text-center" id="info"></h5>
                <div class="progressLoading">
                    <img src="${root}/resources/images/loading.gif">
                </div>
            </div>
            <table align="center" width="45%" cellspacing="0" cellpadding="5"
                   border="0" class="formTable1">
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="button"
                               value="Start Availability Automate Process "
                               id="btnStartAvailability"/></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><input type="button"
                               value="Stop Availability Automate Process "
                               id="btnStoptAvailability"/></td>
                </tr>
            </table>
        </div>
        <div class="col-md-5"></div>
    </div>
</div>
<input type="hidden" id="isProcess" value="${isProcess}"/>
</body>
<script src="${root}/resources/js/hotelAvailability.js"></script>
<script type="text/javascript">

    $(function () {
        var isProcess = $("#isProcess").val();

        if (isProcess == "true") {
            $("#btnStartAvailability").attr("disabled", "disabled");
        } else {
            $("#btnStoptAvailability").attr("disabled", "disabled");
        }


    });
    $(window).load(function () {
        // Animate loader off screen
        $(".se-pre-con").fadeOut("slow");
    });
</script>
</html>

