/*
 *  Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *  *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *    This software product contains information which is proprietary to
 *    and considered a trade secret The Data management Group Ltd .
 *    It is expressly agreed that it shall not be reproduced in whole or part,
 *    disclosed, divulged or otherwise made available to any third party directly
 *    or indirectly.  Reproduction of this product for any purpose is prohibited
 *    without written authorisation from the The Data Management Group Ltd
 *    All Rights Reserved.
 *
 *    E-Mail andyj@datam.co.uk
 *    URL : www.datam.co.uk
 *    Created By : Mahendra Sri Dayarathna
 *
 *
 */

$(document).ready(function () {
    $('#div_submit_notify').hide();
    $("#btnStartAvailability").click(function () {
        start();
        $.ajax({
            url: "availability/startAvailabilityAutomate",
            cache: false,
            contentType: false,
            processData: false,
            error: function (text) {
                stop();
                $('#info').html("Error ! Automate Process");
            },
            success: function (text) {
                if (text == true) {
                    $('#info').html("Started Availability Automate Process");
                } else {
                    $('#info').html("Cannot Start Availability Automate Process");
                }

            }

        });
    });
});

$("#btnStoptAvailability").click(function () {
    stop();
    $.ajax({
        type: "GET",
        url: "availability/stopAvailabilityAutomate",
        cache: false,
        contentType: false,
        processData: false,
        error: function (text) {
            $('#info').html("Error ! Automate Process");
        },
        success: function (text) {
            if (text == true) {
                $('#info').html("Stopped Availability Automate Process");
            } else {
                $('#info').html("Cannot Stop Availability Automate Process");
            }

        }

    });
});


function start() {
    $("#btnStartAvailability").attr("disabled", "disabled");
    $("#btnStoptAvailability").removeAttr("disabled");
    $("#div_initial_notify").hide();
    $("#div_submit_notify").show();
    $(".progressLoading").show();
    $("#info").addClass("alert-success");
    $("#info").removeClass("alert-danger");

}

function stop() {
    $("#btnStartAvailability").removeAttr("disabled");
    $("#btnStoptAvailability").attr("disabled", "disabled");
    $(".progressLoading").hide();
    $("#div_initial_notify").hide();
    $("#div_submit_notify").show();
    $("#info").removeClass("alert-success");
    $("#info").addClass("alert-danger");
}