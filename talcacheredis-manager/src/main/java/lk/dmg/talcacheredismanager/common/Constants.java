/*
 *
 *   Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *   *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *      This software product contains information which is proprietary to
 *      and considered a trade secret The Data management Group Ltd .
 *      It is expressly agreed that it shall not be reproduced in whole or part,
 *      disclosed, divulged or otherwise made available to any third party directly
 *      or indirectly.  Reproduction of this product for any purpose is prohibited
 *      without written authorisation from the The Data Management Group Ltd
 *      All Rights Reserved.
 *
 *      E-Mail andyj@datam.co.uk
 *      URL : www.datam.co.uk
 *      Created By : Mahendra Sri Dayarathna
 *
 *
 */

package lk.dmg.talcacheredismanager.common;


public class Constants {
    public static final String PICK_REQUEST_DATE_FORMAT = "dd MM yy";
    public static final String MAIN_HOTEL_DELIMITER = "}MAIN]";
    public static final String SUB_HOTEL_DELIMITER_ALC = "}SUBALC]";
    public static final String SUB_HOTEL_DELIMITER_PRC = "}SUBPRC]";
    public static final String SEGMENT_DELIMITER = "]";
    public static final String SUB_SEGMENT_DELIMITER = ";";
    public static final String MIN_SEGMENT_DELIMITER = "\\\\";
    public static final String PRODUCT_DELIMITER = "-";
    public static final String MAIN_REPLACE_DELIMITER = "}}zzz";
    public static final String SUB_REPLACE_DELIMITER = "}";
    public static final String TAL100 = "NO Main Hotel ! Empty Cap File";
    public static final String CAP200 = "Invalid Cap File";
    public static final String TAL400 = "Unexpected Server Error";

    public static final String FILE_FORMAT = "cap";
    public static final String COMMUNICATION_ERROR = "Error! Communication ";
    public static final String PICK_TIMEOUT_ERROR = "PICK  time out encountered";
    public static final String PICK_ERROR = "Error ! Pick Return Error";
    public static final String NO_AVAILBLE_HOTELS_FOUND_ERROR = "Error ! No Availble Hotels Found";
    public static final String NO_AVAILBLE_MAIN_HOTELS_FOUND_ERROR = "Error ! No Availble Main Hotels Found";
    public static final String ALLOCATION_BLOCK_STATUS = "B";
    public static final int SEGMENT_LENGTH = 2;
    public static final String RESORT_VS_HOTELS_HASH="RESORT_VS_HOTELS_FILE";
    public static final String HOTEL_BY_DATE_HASH="HOTEL_BY_DATE_FILE";
    public static final String HOTELCODE_VS_HOTELS_HASH="HOTEL_CODE_VS_HOTELS_FILE";
    public static final String OCCUPANCY_ROOM_FILE="OCCUPANCY_ROOM_FILE";


    public static final String FREE_NIGHT_DELIMITER = "}FREE]";
    public static final String EBD_DELIMITER = "}EBD]";
    public static final String PROD_DELIMITER = "}PROD]";
    public static final String REDIS_KEY_DELIMITER = ":";
}

