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

public class AvalabilityResponseRoomVO {

    private String roomCode="";
    private String availmessage="";
    private Integer noOfPaxPerRoom;
    private String extraBedIndicator="";
    private String noOfExtraBeds="";
    private List<AvailabilityResponseProductVO> availabilityResponseProductVO = new ArrayList<>();

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getAvailmessage() {
        return availmessage;
    }

    public void setAvailmessage(String availmessage) {
        this.availmessage = availmessage;
    }

    public Integer getNoOfPaxPerRoom() {
        return noOfPaxPerRoom;
    }

    public void setNoOfPaxPerRoom(Integer noOfPaxPerRoom) {
        this.noOfPaxPerRoom = noOfPaxPerRoom;
    }

    public String getExtraBedIndicator() {
        return extraBedIndicator;
    }

    public void setExtraBedIndicator(String extraBedIndicator) {
        this.extraBedIndicator = extraBedIndicator;
    }

    public String getNoOfExtraBeds() {
        return noOfExtraBeds;
    }

    public void setNoOfExtraBeds(String noOfExtraBeds) {
        this.noOfExtraBeds = noOfExtraBeds;
    }

    public List<AvailabilityResponseProductVO> getAvailabilityResponseProductVO() {
        return availabilityResponseProductVO;
    }

    public void setAvailabilityResponseProductVO(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        this.availabilityResponseProductVO = availabilityResponseProductVO;
    }
}
