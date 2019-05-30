package lk.dmg.common.model;

import java.io.Serializable;

public class Room implements Serializable {
    private String roomCode;
    private String subHotelCode;
    private String mainHotelCode;
    private String date;
    private String ratePlanCode;
    private String ratePlanDesc;
    private String countryCode;
    private String priorityIndicator;
    private String resortCode;
    private String noOfRoomPax;
    private String noOfExtraBed;
    private String availabilityStatus;
    private String stdAvailabilityStatus;
    private String stdReleaseDays;
    private String stdBooked;
    private String freeSaleAvailabilityStatus;
    private String freeSaleBooked;
    private String freeSaleReleaseDays;
    private String productCode;
    private String adultPrice;
    private String childPrice;
    private String minDuration;
    private String currencyCode;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getResortCode() {
        return resortCode;
    }

    public void setResortCode(String resortCode) {
        this.resortCode = resortCode;
    }

    public String getRatePlanDesc() {
        return ratePlanDesc;
    }

    public void setRatePlanDesc(String ratePlanDesc) {
        this.ratePlanDesc = ratePlanDesc;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPriorityIndicator() {
        return priorityIndicator;
    }

    public void setPriorityIndicator(String priorityIndicator) {
        this.priorityIndicator = priorityIndicator;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getNoOfRoomPax() {
        return noOfRoomPax;
    }

    public void setNoOfRoomPax(String noOfRoomPax) {
        this.noOfRoomPax = noOfRoomPax;
    }

    public String getNoOfExtraBed() {
        return noOfExtraBed;
    }

    public void setNoOfExtraBed(String noOfExtraBed) {
        this.noOfExtraBed = noOfExtraBed;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getStdAvailabilityStatus() {
        return stdAvailabilityStatus;
    }

    public void setStdAvailabilityStatus(String stdAvailabilityStatus) {
        this.stdAvailabilityStatus = stdAvailabilityStatus;
    }

    public String getStdReleaseDays() {
        return stdReleaseDays;
    }

    public void setStdReleaseDays(String stdReleaseDays) {
        this.stdReleaseDays = stdReleaseDays;
    }

    public String getStdBooked() {
        return stdBooked;
    }

    public void setStdBooked(String stdBooked) {
        this.stdBooked = stdBooked;
    }

    public String getFreeSaleAvailabilityStatus() {
        return freeSaleAvailabilityStatus;
    }

    public void setFreeSaleAvailabilityStatus(String freeSaleAvailabilityStatus) {
        this.freeSaleAvailabilityStatus = freeSaleAvailabilityStatus;
    }

    public String getFreeSaleBooked() {
        return freeSaleBooked;
    }

    public void setFreeSaleBooked(String freeSaleBooked) {
        this.freeSaleBooked = freeSaleBooked;
    }

    public String getFreeSaleReleaseDays() {
        return freeSaleReleaseDays;
    }

    public void setFreeSaleReleaseDays(String freeSaleReleaseDays) {
        this.freeSaleReleaseDays = freeSaleReleaseDays;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(String adultPrice) {
        this.adultPrice = adultPrice;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(String childPrice) {
        this.childPrice = childPrice;
    }

    public String getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(String minDuration) {
        this.minDuration = minDuration;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomCode='" + roomCode + '\'' +
                ", noOfRoomPax='" + noOfRoomPax + '\'' +
                ", noOfExtraBed='" + noOfExtraBed + '\'' +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", stdAvailabilityStatus='" + stdAvailabilityStatus + '\'' +
                ", stdReleaseDays='" + stdReleaseDays + '\'' +
                ", stdBooked='" + stdBooked + '\'' +
                ", freeSaleAvailabilityStatus='" + freeSaleAvailabilityStatus + '\'' +
                ", freeSaleBooked='" + freeSaleBooked + '\'' +
                ", freeSaleReleaseDays='" + freeSaleReleaseDays + '\'' +
                ", productCode='" + productCode + '\'' +
                ", adultPrice='" + adultPrice + '\'' +
                ", childPrice='" + childPrice + '\'' +
                ", minDuration='" + minDuration + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
