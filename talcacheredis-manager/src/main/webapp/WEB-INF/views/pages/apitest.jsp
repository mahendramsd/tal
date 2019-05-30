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
   Created By : Thilan Jayasinghe
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
            $("#fromdate").datepicker({
                dateFormat: 'dd/M/yy'
            });

        });

        $(function () {
            $("#todate").datepicker({
                dateFormat: 'dd/M/yy'
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
                    <h3>Hotel Availability Enquiry</h3>
                </header>
                <!-- <div class="clear">&nbsp;</div> -->
                <hr>
                <div class="clear">&nbsp;</div>


                <form action="${root}/enquery/requestAvailability"
                      method="POST">
                    <!-- String countrycode,String resourtcode, String fromdate, String todate -->

                    <div class="row">
                        <div class="col-md-5">Country Code</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="countrycode"
                                   name="countrycode"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <div class="row">
                        <div class="col-md-5">City Code</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="resourtcode"
                                   name="resourtcode"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <B>OR</B>
                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-5">Hotel Name</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="hotelname"
                                   name="hotelname"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <B>Number of Rooms Required</B>
                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-5">Single Room</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <!-- <input autocomplete="off" type="text" id="singleroom"
                                name="singleroom" /> -->
                            <select class="col-md-4" id="singleroom"
                                    style="font-family: Arial; font-size: 10pt; padding-left:5px;" size="1"
                                    name="singleroom">
                                <option selected="" value="">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-5">Double/Twin Room</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <!-- <input autocomplete="off" type="text" id="doubleroom"
                                name="doubleroom" /> -->
                            <select class="col-md-4" style="font-family: Arial; font-size: 10pt; padding-left:5px;"
                                    size="1" id="doubleroom" name="doubleroom">
                                <option value="">0</option>
                                <option selected="" value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-5">Triple Room</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <!-- <input autocomplete="off" type="text" id="tripleroom"
                                name="tripleroom" /> -->
                            <select class="col-md-4" style="font-family: Arial; font-size: 10pt; padding-left:5px;"
                                    size="1" id="tripleroom" name="tripleroom">
                                <option selected="" value="">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        </div>
                        <div class="col-md-2"></div>
                    </div>

                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-5">Extra bed for child?:</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <!-- <input autocomplete="off" type="text" id="extrabed"
                                name="extrabed" /> -->
                            <select class="col-md-4" style="font-family: Arial; font-size: 10pt; padding-left:5px;"
                                    size="1" id="extrabed" name="extrabed">
                                <option selected="" value="">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                        </div>
                        <div class="col-md-2"></div>
                    </div>


                    <B>Please select Check-In & Check-Out Dates</B>
                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-5">From Date</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <!-- <input type="date" id="fromdate" name="fromdate" /> -->
                            <input type="text" id="fromdate" name="fromdate"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <div class="clear">&nbsp;</div>
                    <div class="row">
                        <div class="col-md-5">To Date</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <!-- <input type="date" id="todate" name="todate" /> -->
                            <input type="text" id="todate" name="todate"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <div class="row">
                        <div class="col-md-5">Agent Code</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="agentcode"
                                   name="agentcode"/>
                        </div>
                        <div class="col-md-2"></div>
                    </div>
                    <div class="row">
                        <div class="col-md-5">Agent Password</div>
                        <div class="col-md-1">
                            <!-- <input type="checkbox" checked="checked" onclick="anableHotel()"
                            id="che_hotelCode" /> -->
                        </div>
                        <div class="col-md-4">
                            <input autocomplete="off" type="text" id="agentpasword"
                                   name="agentpasword"/>
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
            <div class="clear col-md-12 textAreaDiv" style="text-align: center; background-color: #9bbae9;">
					<textarea rows="3" cols="10" class="textAreaFullWidth">
                        ${receiveText}


                    </textarea>


                <div class="col-md-5"></div>
                <!-- <div class="clear">&nbsp;</div>
            <div class="clear">&nbsp;</div> -->
            </div>

            <div class="clear col-md-12 textAreaDiv" style="text-align: center; background-color: #9bbae9;">
                Talcache-api Return Text
                <textarea rows="25" cols="50" class="textAreaFullWidth">
                    ${finaltext}
                </textarea>


                <div class="col-md-5"></div>
                <!-- <div class="clear">&nbsp;</div>
            <div class="clear">&nbsp;</div> -->
            </div>
            <div class="clear col-md-12 textAreaDiv" style="text-align: center; background-color: #9bbae9;">
                PICK-Return Text
                <textarea rows="25" cols="50" class="textAreaFullWidth">
                    ${pickreturn}
                </textarea>


                <div class="col-md-5"></div>
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

