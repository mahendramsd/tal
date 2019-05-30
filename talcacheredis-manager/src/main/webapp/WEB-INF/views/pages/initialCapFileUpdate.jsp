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
    <title>Initial Update</title>

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
<div class="se-pre-con"></div>
<div class="wrapper">
    <div class="row">
        <div class="col-md-12">
            <header>
                <h3>Upload Availability To The Postgresql Database From Given
                    Cap File.</h3>
            </header>
            <hr>
            <c:if test="${pageType == 1}">
                <c:choose>
                    <c:when test="${isSave}">
                        <h5 class="alert alert-success text-center">Successfully
                            updated!</h5>
                    </c:when>
                    <c:otherwise>
                        <h5 class="alert alert-danger text-center">Update failed!</h5>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <form id="initialUploadForm" action="${root}/upload/uploadCapFile" enctype="multipart/form-data"
                  method="post">
                <table align="left" width="50%" cellspacing="0" cellpadding="5"
                       border="0" class="formTable">
                    <tr>
                        <td><label class="">Add Cap File : </label></td>
                        <td><input type="file" name="file" style="width:100%;"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input class="btn btn-success" type="submit"
                                   value="Upload Data" id="btnInitialUpload"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                </table>


                <c:if test="${pageType == 1}">
                    <table align="" width="30%" cellspacing="0" cellpadding="5"
                           border="0" class="formDataTable">
                        <tr>
                            <th>Main Hotels</th>
                            <td>${mainHotesCount}</td>
                        </tr>
                        <tr>
                            <th>Processing Time (Seconds)</th>
                            <td>${processingTime}</td>
                        </tr>
                    </table>
                </c:if>

            </form>

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

    $('#btnInitialUpload').click(function () {
        $(".se-pre-con").show();
    });


    //    function uploadFile() {
    //        $.ajax({
    //            url: "upload/uploadCapFile",
    //            type: "POST",
    //            data: new FormData($("#initialUploadForm")[0]),
    //            enctype: 'multipart/form-data',
    //            processData: false,
    //            contentType: false,
    //            cache: false,
    //            success: function () {
    //                alert("Success");
    //            },
    //            error: function () {
    //                alert("Fail");
    //            }
    //        });
    //   }
</script>
</html>

