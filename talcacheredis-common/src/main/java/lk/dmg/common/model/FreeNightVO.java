/*
 *
 *  *  Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *  *  *  PROPRIETARY AND COPYRIGHT NOTICE.
 *  *
 *  *    This software product contains information which is proprietary to
 *  *    and considered a trade secret The Data management Group Ltd .
 *  *    It is expressly agreed that it shall not be reproduced in whole or part,
 *  *    disclosed, divulged or otherwise made available to any third party directly
 *  *    or indirectly.  Reproduction of this product for any purpose is prohibited
 *  *    without written authorisation from the The Data Management Group Ltd
 *  *    All Rights Reserved.
 *  *
 *  *    E-Mail andyj@datam.co.uk
 *  *    URL : www.datam.co.uk
 *  *    Written By : Asanka Anthony
 *  *
 *
 */

package lk.dmg.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RedisHash("FreeNightVO")
public class FreeNightVO implements Serializable {
    @Id
    private String subHotelCode;
    private String mainHotelCode;
    private List<String> occupancy = new ArrayList<>();
    private List<String> fromDates = new ArrayList<>();
    private List<String> toDates = new ArrayList<>();
    private List<String> applyToAgentInds = new ArrayList<>();
    private List<String> displayInds = new ArrayList<>();
    private List<String> adultDurations = new ArrayList<>();
    private List<String> adultNoOfFreeNights = new ArrayList<>();
    private List<String> childDurations = new ArrayList<>();
    private List<String> childNoOfFreeNights = new ArrayList<>();

    public String getSubHotelCode() {
        return subHotelCode;
    }

    public void setSubHotelCode(String subHotelCode) {
        this.subHotelCode = subHotelCode;
    }

    public String getMainHotelCode() {
        return mainHotelCode;
    }

    public void setMainHotelCode(String mainHotelCode) {
        this.mainHotelCode = mainHotelCode;
    }

    public List<String> getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(List<String> occupancy) {
        this.occupancy = occupancy;
    }

    public List<String> getFromDates() {
        return fromDates;
    }

    public void setFromDates(List<String> fromDates) {
        this.fromDates = fromDates;
    }

    public List<String> getToDates() {
        return toDates;
    }

    public void setToDates(List<String> toDates) {
        this.toDates = toDates;
    }

    public List<String> getApplyToAgentInds() {
        return applyToAgentInds;
    }

    public void setApplyToAgentInds(List<String> applyToAgentInds) {
        this.applyToAgentInds = applyToAgentInds;
    }

    public List<String> getDisplayInds() {
        return displayInds;
    }

    public void setDisplayInds(List<String> displayInds) {
        this.displayInds = displayInds;
    }

    public List<String> getAdultDurations() {
        return adultDurations;
    }

    public void setAdultDurations(List<String> adultDurations) {
        this.adultDurations = adultDurations;
    }

    public List<String> getAdultNoOfFreeNights() {
        return adultNoOfFreeNights;
    }

    public void setAdultNoOfFreeNights(List<String> adultNoOfFreeNights) {
        this.adultNoOfFreeNights = adultNoOfFreeNights;
    }

    public List<String> getChildDurations() {
        return childDurations;
    }

    public void setChildDurations(List<String> childDurations) {
        this.childDurations = childDurations;
    }

    public List<String> getChildNoOfFreeNights() {
        return childNoOfFreeNights;
    }

    public void setChildNoOfFreeNights(List<String> childNoOfFreeNights) {
        this.childNoOfFreeNights = childNoOfFreeNights;
    }

    @Override
    public String toString() {
        return "FreeNightVO{" +
                "subHotelCode='" + subHotelCode + '\'' +
                ", mainHotelCode='" + mainHotelCode + '\'' +
                ", occupancy=" + occupancy +
                ", fromDates=" + fromDates +
                ", toDates=" + toDates +
                ", applyToAgentInd=" + applyToAgentInds +
                ", displayInd='" + displayInds + '\'' +
                ", adultDurations=" + adultDurations +
                ", adultNoOfFreeNight=" + adultNoOfFreeNights +
                ", childDuration=" + childDurations +
                ", childNoOfFreeNight=" + childNoOfFreeNights +
                '}';
    }
}
