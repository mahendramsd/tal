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

@RedisHash("EarlyBirdVO")
public class EarlyBirdVO implements Serializable {
    @Id
    private String subHotelCode;
    private String mainHotelCode;
    private List<String> occupancys = new ArrayList<>();
    private List<String> earlyBirdTypes = new ArrayList<>();
    private List<String> fromBookingDates = new ArrayList<>();
    private List<String> toBookingDates = new ArrayList<>();
    private List<String> fromArrivalDates = new ArrayList<>();
    private List<String> toArrivalDate = new ArrayList<>();
    private List<String> upToNoOfDays = new ArrayList<>();
    private List<String> discounts = new ArrayList<>();

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

    public List<String> getOccupancys() {
        return occupancys;
    }

    public void setOccupancys(List<String> occupancys) {
        this.occupancys = occupancys;
    }

    public List<String> getEarlyBirdTypes() {
        return earlyBirdTypes;
    }

    public void setEarlyBirdTypes(List<String> earlyBirdTypes) {
        this.earlyBirdTypes = earlyBirdTypes;
    }

    public List<String> getFromBookingDates() {
        return fromBookingDates;
    }

    public void setFromBookingDates(List<String> fromBookingDates) {
        this.fromBookingDates = fromBookingDates;
    }

    public List<String> getToBookingDates() {
        return toBookingDates;
    }

    public void setToBookingDates(List<String> toBookingDates) {
        this.toBookingDates = toBookingDates;
    }

    public List<String> getFromArrivalDates() {
        return fromArrivalDates;
    }

    public void setFromArrivalDates(List<String> fromArrivalDates) {
        this.fromArrivalDates = fromArrivalDates;
    }

    public List<String> getToArrivalDate() {
        return toArrivalDate;
    }

    public void setToArrivalDate(List<String> toArrivalDate) {
        this.toArrivalDate = toArrivalDate;
    }

    public List<String> getUpToNoOfDays() {
        return upToNoOfDays;
    }

    public void setUpToNoOfDays(List<String> upToNoOfDays) {
        this.upToNoOfDays = upToNoOfDays;
    }

    public List<String> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<String> discounts) {
        this.discounts = discounts;
    }

    @Override
    public String toString() {
        return "EarlyBirdVO{" +
                "subHotelCode='" + subHotelCode + '\'' +
                ", mainHotelCode='" + mainHotelCode + '\'' +
                ", occupancys=" + occupancys +
                ", earlyBirdTypes=" + earlyBirdTypes +
                ", fromBookingDates=" + fromBookingDates +
                ", toBookingDates=" + toBookingDates +
                ", fromArrivalDates=" + fromArrivalDates +
                ", toArrivalDate=" + toArrivalDate +
                ", upToNoOfDays=" + upToNoOfDays +
                ", discounts=" + discounts +
                '}';
    }
}
