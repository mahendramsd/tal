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
package lk.dmg.talcacheoperational.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Build receiving pick string to java object
 * Example String
 * 27/Mar/2018]1]1;3]1;9]0;0]LON]YYA
 */
public class AvailabilityRequestVO {
    private Date fromDate;
    private Integer noOfDays;
    private List<Integer> roomOccupancy=new ArrayList<>();/*1 -single  2- twin  3- triple*/
    private List<Integer> guestCount=new ArrayList<>();/*1;9]- 1-No of adults , 9 - No of children*/
    private List<Integer> extraBed=new ArrayList<>();/*0;0]-extra bed*/
    private String cityCode;
    private List<String> hotelCode=new ArrayList<>();
    private Date toDate;
    private Boolean singleRoom;
    private Boolean twinRoom;
    private Boolean tripleRoom;


    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public List<Integer> getRoomOccupancy() {
        return roomOccupancy;
    }

    public void setRoomOccupancy(List<Integer> roomOccupancy) {
        this.roomOccupancy = roomOccupancy;
    }

    public List<Integer> getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(List<Integer> guestCount) {
        this.guestCount = guestCount;
    }

    public List<Integer> getExtraBed() {
        return extraBed;
    }

    public void setExtraBed(List<Integer> extraBed) {
        this.extraBed = extraBed;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<String> getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(List<String> hotelCode) {
        this.hotelCode = hotelCode;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Boolean getSingleRoom() {
        return singleRoom;
    }

    public void setSingleRoom(Boolean singleRoom) {
        this.singleRoom = singleRoom;
    }

    public Boolean getTwinRoom() {
        return twinRoom;
    }

    public void setTwinRoom(Boolean twinRoom) {
        this.twinRoom = twinRoom;
    }

    public Boolean getTripleRoom() {
        return tripleRoom;
    }

    public void setTripleRoom(Boolean tripleRoom) {
        this.tripleRoom = tripleRoom;
    }
}
