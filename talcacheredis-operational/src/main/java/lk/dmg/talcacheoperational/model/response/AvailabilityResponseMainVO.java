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

public class AvailabilityResponseMainVO {

    private String segmentId="}MAIN";
    private String mainHotelCode="";
    private String notInUsePos3="";
    private String notInUsePos4="";
    private String notInUsePos5="";
    private String notInUsePos6="";
    private String notInUsePos7="";
    private String notInUsePos8="";
    private String notInUsePos9="";
    private String minPriceAdult="";
    private String maxPriceAdult="";
    private String notInUsePos12="";
    private List<String> listOfSubHotels=new ArrayList<>();
    private List<AvailabilityResponseSubVO> availabilityResponseSubVO = new ArrayList<>();

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getMainHotelCode() {
        return mainHotelCode;
    }

    public void setMainHotelCode(String mainHotelCode) {
        this.mainHotelCode = mainHotelCode;
    }

    public String getNotInUsePos3() {
        return notInUsePos3;
    }

    public void setNotInUsePos3(String notInUsePos3) {
        this.notInUsePos3 = notInUsePos3;
    }

    public String getNotInUsePos4() {
        return notInUsePos4;
    }

    public void setNotInUsePos4(String notInUsePos4) {
        this.notInUsePos4 = notInUsePos4;
    }

    public String getNotInUsePos5() {
        return notInUsePos5;
    }

    public void setNotInUsePos5(String notInUsePos5) {
        this.notInUsePos5 = notInUsePos5;
    }

    public String getNotInUsePos6() {
        return notInUsePos6;
    }

    public void setNotInUsePos6(String notInUsePos6) {
        this.notInUsePos6 = notInUsePos6;
    }

    public String getNotInUsePos7() {
        return notInUsePos7;
    }

    public void setNotInUsePos7(String notInUsePos7) {
        this.notInUsePos7 = notInUsePos7;
    }

    public String getNotInUsePos8() {
        return notInUsePos8;
    }

    public void setNotInUsePos8(String notInUsePos8) {
        this.notInUsePos8 = notInUsePos8;
    }

    public String getNotInUsePos9() {
        return notInUsePos9;
    }

    public void setNotInUsePos9(String notInUsePos9) {
        this.notInUsePos9 = notInUsePos9;
    }

    public String getMinPriceAdult() {
        return minPriceAdult;
    }

    public void setMinPriceAdult(String minPriceAdult) {
        this.minPriceAdult = minPriceAdult;
    }

    public String getMaxPriceAdult() {
        return maxPriceAdult;
    }

    public void setMaxPriceAdult(String maxPriceAdult) {
        this.maxPriceAdult = maxPriceAdult;
    }

    public String getNotInUsePos12() {
        return notInUsePos12;
    }

    public void setNotInUsePos12(String notInUsePos12) {
        this.notInUsePos12 = notInUsePos12;
    }

    public List<String> getListOfSubHotels() {
        return listOfSubHotels;
    }

    public void setListOfSubHotels(List<String> listOfSubHotels) {
        this.listOfSubHotels = listOfSubHotels;
    }

    public List<AvailabilityResponseSubVO> getAvailabilityResponseSubVO() {
        return availabilityResponseSubVO;
    }

    public void setAvailabilityResponseSubVO(List<AvailabilityResponseSubVO> availabilityResponseSubVO) {
        this.availabilityResponseSubVO = availabilityResponseSubVO;
    }

    @Override
    public String toString() {
        return "AvailabilityResponseMainVO{" +
                "segmentId='" + segmentId + ';' +
                ", mainHotelCode='" + mainHotelCode + '\'' +
                ", notInUsePos3='" + notInUsePos3 + '\'' +
                ", notInUsePos4='" + notInUsePos4 + '\'' +
                ", notInUsePos5='" + notInUsePos5 + '\'' +
                ", notInUsePos6='" + notInUsePos6 + '\'' +
                ", notInUsePos7='" + notInUsePos7 + '\'' +
                ", notInUsePos8='" + notInUsePos8 + '\'' +
                ", notInUsePos9='" + notInUsePos9 + '\'' +
                ", minPriceAdult='" + minPriceAdult + '\'' +
                ", maxPriceAdult='" + maxPriceAdult + '\'' +
                ", notInUsePos12='" + notInUsePos12 + '\'' +
                ", listOfSubHotels='" + listOfSubHotels + '\'' +
                ", availabilityResponseSubVO=" + availabilityResponseSubVO +
                '}';
    }


}
