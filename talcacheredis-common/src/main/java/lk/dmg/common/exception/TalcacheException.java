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
 */
package lk.dmg.common.exception;


public class TalcacheException extends Exception {
    /**
     * errorCode=unique code to identify
     * errorMsg=error description
     * errorType=type of error from manager constance
     */
    private String errorCode;
    private String errorMsg;
    private String errorType;

    /**
     * @param errorCode
     * @param errorMsg
     * @param errorType
     */
    public TalcacheException(String errorCode, String errorMsg, String errorType) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
    }

    public TalcacheException(String message, String errorCode, String errorMsg, String errorType) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
    }

    /**
     * @param message
     * @param cause
     * @param errorCode
     * @param errorMsg
     * @param errorType
     */
    public TalcacheException(String errorCode, String errorMsg, String errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
    }

    /**
     * @param cause
     * @param errorCode
     * @param errorMsg
     * @param errorType
     */
    public TalcacheException(String errorCode, String errorMsg, String errorType, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     * @param errorCode
     * @param errorMsg
     * @param errorType
     */
    public TalcacheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorMsg, String errorType) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
