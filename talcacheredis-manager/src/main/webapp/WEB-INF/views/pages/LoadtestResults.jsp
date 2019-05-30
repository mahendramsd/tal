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
    <link rel="stylesheet" type="text/css"
          href="${root}/resources/css/jquery-ui.css">
    <script>
        $(function () {
            $("#fromDate1").datepicker({
                dateFormat: 'dd/mm/y'
            });

        });


        $(function () {
            $("#toDate1").datepicker({
                dateFormat: 'dd/mm/y'
            });
        });

        $(function () {
            $("#fromDate2").datepicker({
                dateFormat: 'dd/mm/y'
            });

        });


        $(function () {
            $("#toDate2").datepicker({
                dateFormat: 'dd/mm/y'
            });
        });

    </script>

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
            <div class="col-md-8">
                <header>
                    <h3>Load Test On Requests</h3>
                </header>
                <!-- <div class="clear">&nbsp;</div> -->
                <hr>
                <div class="clear">&nbsp;</div>
                <form action="${root}/loadtest/loadtestdata" method="POST">


                    <div class="row">
                        <div class="col-md-5">Hotel Code</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                                id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="txtHotelCode"
                                   name="hotelCode"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">Hotel Requests Count</div>
                        <div class="col-md-4">
                            <input autocomplete="off" style="width: 125%" class="numbersOnly"
                                   type="number" placeholder="All" name="hotelCount"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-6"></div>
                        <div class="col-md-4">
                            <button id="btnSubmit" class="btn btn-success" type="submit">Generate</button>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                </form>
                <br>
                <form action="${root}/loadtest/loadResourttestdata" method="POST">


                    <div class="row">
                        <div class="col-md-5">Resort Code</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                                id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="resortcode"
                                   name="resortcode"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">Resort Requests Count</div>
                        <div class="col-md-4">
                            <input autocomplete="off" style="width: 125%" class="numbersOnly"
                                   type="number" placeholder="All" name="resortCount"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-6"></div>
                        <div class="col-md-4">
                            <button id="btnSubmit" class="btn btn-success" type="submit">Generate</button>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                </form>
                <br>
                <form action="${root}/loadtest/dateloadtestdata" method="POST">


                    <div class="row">
                        <div class="col-md-5">Hotel Code</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                                id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="txtHotelCode"
                                   name="datehotelCode"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="row">
                        <div class="col-md-5">From Date</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                                id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input type="date"
                                   id="fromDate1" name="fromDate1"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="row">
                        <div class="col-md-5">To Date</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                                id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input type="date"
                                   id="toDate1" name="toDate1"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>


                    <div class="row">
                        <div class="col-md-6">Hotel Requests Count</div>
                        <div class="col-md-4">
                            <input autocomplete="off" style="width: 125%" class="numbersOnly"
                                   type="number" placeholder="All" name="datehotelCount"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-6"></div>
                        <div class="col-md-4">
                            <button id="btnSubmit" class="btn btn-success" type="submit">Generate</button>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                </form>

                <br>
                <form action="${root}/loadtest/dateResrtloadtestdata" method="POST">


                    <div class="row">
                        <div class="col-md-5">Resort Code</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                                id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="txtHotelCode"
                                   name="dateresortcode"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="row">
                        <div class="col-md-5">From Date</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                                id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input type="date"
                                   id="fromDate2" name="fromDate2"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="row">
                        <div class="col-md-5">To Date</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                                id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input type="date"
                                   id="toDate1" name="toDate2"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>


                    <div class="row">
                        <div class="col-md-6">Resort Requests Count</div>
                        <div class="col-md-4">
                            <input autocomplete="off" style="width: 125%" class="numbersOnly"
                                   type="number" placeholder="All" name="dateResortCount"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-6"></div>
                        <div class="col-md-4">
                            <button id="btnSubmit" class="btn btn-success" type="submit">Generate</button>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                </form>
            </div>
            <div class="col-md-12">
                <h2>Load Test On Requests Results</h2>
                <table width="100%" class="table table-striped">
                    <thead>
                    <tr>
                        <th>Request No:</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Time Spent (milliseconds)</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${testDataHoldList}" var="testData">
                        <tr>

                            <td>${testData.reqno}</td>
                            <td>${testData.starttime}</td>
                            <td>${testData.endTime}</td>
                            <td>${testData.timespent}</td>
                            <td>${testData.status}</td>

                                <%-- <td>${productss.adultPrice}</td> --%>

                                <%-- <td>${contact.email}</td>
                                <td>${contact.phone}</td> --%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
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

    /* $(function() {
        $("#txtHotelCode").prop('disabled', true);
    }); */
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

