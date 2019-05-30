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
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel Details</title>

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
                <h1 align="center">Automate Process</h1>
                <br> <br> <br><br><br>
                <img src="${root}/resources/images/under-construction.png" alt="under construction">
            </header>
            <!-- <div class="clear">&nbsp;</div> -->
            <hr>
            <div class="clear">&nbsp;</div>

</body>
<!-- <div class="clear">&nbsp;</div>
				<div class="clear">&nbsp;</div> -->
</div>
<div class="col-md-5"></div>
</div>

</div>

</body>

<script type="text/javascript">
    $(window).load(function () {
        // Animate loader off screen
        $(".se-pre-con").fadeOut("slow");
    });

    $('#btnSubmit').click(function () {
        $(".se-pre-con").show();
    });

    jQuery('.numbersOnly').keyup(function () {
        this.value = this.value.replace(/[^0-9\.]/g, '');
    });
    $(function () {
        $("#txtHotelCode").prop('disabled', true);
    });

    function anableHotel() {
        if ($('#che_hotelCode').is(":checked")) {
            $("#txtHotelCode").prop('disabled', true);
            $("#txtHotelCode").val("");
        } else {
            $("#txtHotelCode").prop('disabled', false);
        }
    }
</script>
</html>

