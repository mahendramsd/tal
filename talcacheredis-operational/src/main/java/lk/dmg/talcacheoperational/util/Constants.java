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
 *    Created By : Asanka Anthony
 *
 */

package lk.dmg.talcacheoperational.util;


import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Constants {
    public static final String PICK_RETURN_DATE_FORMAT = "dd/MM/yy";
     public static final String ECHO_TOKEN = "EchoToken";
    public static final String PG_DATE_FORMAT = "yyyy-MM-dd";
    public static final String PICK_STRING_SPLITTER="]";
    public static final String PICK_VARIABLE_SPLITTER=";";
    public static final String INVALID_REQUEST_STRING="INVALID_REQUEST_STRING";
    public static final String INVALID_AGENT_CODE = "INVALID_AGENT_CODE";
    public static final String PICK_DATE_FORMAT="dd/MMM/yyyy";
    public static final String PICK_PRICE_MESSAGE_DATE_FORMAT="dd-MMM-yyyy";
    public static final Integer REQUEST_STRING_SEGMENT_SIZE=5;
    public static final String CALCULATE_TYPE_MIN = "MIN";
    public static final String CALCULATE_TYPE_MAX = "MAX";
    public static final String INVALID_MAIN_HOTEL_DATA="INVALID_MAIN_HOTEL_DATA";
    public static final String PRICE_FORMAT = "0.00";
    public static final String BLOCK_STATUS = "B";
    public static final String CLOSE_STATUS = "C";
    public static final String DEFAULT_CATEGORY_CODE = "HOT";
    public static final String NOT_AVAILABLE_ALLOCATION_MSG = "No Avl";
    public static final String AVAILBALE_ALLOCATION_MSG = "Avl";
    public static final String FREE_SALE_INDICATOR = "F";
    public static final String STD_SALE_INDICATOR = "S";
    public static final String DATA_PROCESSING_ERROR = "DATA_PROCESSING_ERROR";
    public static Marker TIMEINFO = MarkerFactory.getMarker("TIMEINFO");
    public static final String FREE_NIGHT_MSG = "Free Night";
    public static final String FREE_NIGHT_PRE_MSG = "Inc";
    public static final String EARLY_BIRD_TYPE_BOOKING_B = "B";
    public static final String EARLY_BIRD_TYPE_ARRIVAL_A = "A";
    public static final String EARLY_BIRD_TYPE_BOTH_F = "F";

}

