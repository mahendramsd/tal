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

package lk.dmg.talcacheoperational.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class AvailabilityResponseSubVO {

    private String segmentId="";//"[SUB]
    private String subHotelCode="";
    private String categoryCode="";//Not Need
    private String resortCode="";
    private String minPrice="";//price min of subhotel without room codes
    private String maxPrice="";
    private String NotInUsePos45="";
    private String ratePlanDesc="";
    private String rateplanCode="";
    private String notInUsePos48="";
    private String uratePlanCode="";//Same as rate plan code
    private List<AvalabilityResponseRoomVO> avalabilityResponseRoomVO = new ArrayList<>();

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getSubHotelCode() {
        return subHotelCode;
    }

    public void setSubHotelCode(String subHotelCode) {
        this.subHotelCode = subHotelCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getResortCode() {
        return resortCode;
    }

    public void setResortCode(String resortCode) {
        this.resortCode = resortCode;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getNotInUsePos45() {
        return NotInUsePos45;
    }

    public void setNotInUsePos45(String notInUsePos45) {
        NotInUsePos45 = notInUsePos45;
    }

    public String getRatePlanDesc() {
        return ratePlanDesc;
    }

    public void setRatePlanDesc(String ratePlanDesc) {
        this.ratePlanDesc = ratePlanDesc;
    }

    public String getRateplanCode() {
        return rateplanCode;
    }

    public void setRateplanCode(String rateplanCode) {
        this.rateplanCode = rateplanCode;
    }

    public String getNotInUsePos48() {
        return notInUsePos48;
    }

    public void setNotInUsePos48(String notInUsePos48) {
        this.notInUsePos48 = notInUsePos48;
    }

    public String getUratePlanCode() {
        return uratePlanCode;
    }

    public void setUratePlanCode(String uratePlanCode) {
        this.uratePlanCode = uratePlanCode;
    }

    public List<AvalabilityResponseRoomVO> getAvalabilityResponseRoomVO() {
        return avalabilityResponseRoomVO;
    }

    public void setAvalabilityResponseRoomVO(List<AvalabilityResponseRoomVO> avalabilityResponseRoomVO) {
        this.avalabilityResponseRoomVO = avalabilityResponseRoomVO;
    }
}
