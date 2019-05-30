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

public class AvailabilityResponseProductVO {

    private String productCode="";
    private String notInUsePos13="";
    private String specialOfferIndicat="";//Not in use
    private String specialOfferDesc="";//Not in use
    private String priceCurency="";/*currencyCode*/
    private String pricemessage="";//Not in use
    private String adultprice="";
    private String childprice="";
    private String totaladultprice="";
    private String totalchildprice="" ;
    private String adultpricemessage="";
    private String chPriceMessage="";
    private String priceCode="";//Not in use
    private String overlapPriceCode="";//Not in use
    private String minAdPrice="";//Not in use
    private String minChPrice="";//Not in use
    private String minPriceMsg="";//Ask thilan
    private String reductionFlag="";//Not in use
    private String adReductionAmt="";//Not in use
    private String adReductionPerc="";//Not in use
    private String ChReductionAmt="";//Not in use
    private String chReductionPerc="";//Not in use
    private String notInUsePos34="";
    private String notInUsePos35="";
    private String noFreeNightsAd = "";//No Of Free Night for adult
    private String prcAdFreeNght = "";//Use to set Free Night avarage value
    private String BasedOnAdDuration = "";//Based adult duration
    private String NoFreeNightCh="";//Not in use
    private String prcChFreeNght="";//Not in use
    private String basedOnChDuration="";//Not in use
    private String notInUsePos42="" ;//Not in use
    private String coreIdentifierCode="";//Not in use
    private String coreIdentifierDesc="";//Not in use
    private String earlyBirdDiscount="" ;//Not in use
    private String earlyBirdDiscAmt="";//Not in use


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getNotInUsePos13() {
        return notInUsePos13;
    }

    public void setNotInUsePos13(String notInUsePos13) {
        this.notInUsePos13 = notInUsePos13;
    }

    public String getSpecialOfferIndicat() {
        return specialOfferIndicat;
    }

    public void setSpecialOfferIndicat(String specialOfferIndicat) {
        this.specialOfferIndicat = specialOfferIndicat;
    }

    public String getSpecialOfferDesc() {
        return specialOfferDesc;
    }

    public void setSpecialOfferDesc(String specialOfferDesc) {
        this.specialOfferDesc = specialOfferDesc;
    }

    public String getPriceCurency() {
        return priceCurency;
    }

    public void setPriceCurency(String priceCurency) {
        this.priceCurency = priceCurency;
    }

    public String getPricemessage() {
        return pricemessage;
    }

    public void setPricemessage(String pricemessage) {
        this.pricemessage = pricemessage;
    }

    public String getAdultprice() {
        return adultprice;
    }

    public void setAdultprice(String adultprice) {
        this.adultprice = adultprice;
    }

    public String getChildprice() {
        return childprice;
    }

    public void setChildprice(String childprice) {
        this.childprice = childprice;
    }

    public String getTotaladultprice() {
        return totaladultprice;
    }

    public void setTotaladultprice(String totaladultprice) {
        this.totaladultprice = totaladultprice;
    }

    public String getTotalchildprice() {
        return totalchildprice;
    }

    public void setTotalchildprice(String totalchildprice) {
        this.totalchildprice = totalchildprice;
    }

    public String getAdultpricemessage() {
        return adultpricemessage;
    }

    public void setAdultpricemessage(String adultpricemessage) {
        this.adultpricemessage = adultpricemessage;
    }

    public String getChPriceMessage() {
        return chPriceMessage;
    }

    public void setChPriceMessage(String chPriceMessage) {
        this.chPriceMessage = chPriceMessage;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    public String getOverlapPriceCode() {
        return overlapPriceCode;
    }

    public void setOverlapPriceCode(String overlapPriceCode) {
        this.overlapPriceCode = overlapPriceCode;
    }

    public String getMinAdPrice() {
        return minAdPrice;
    }

    public void setMinAdPrice(String minAdPrice) {
        this.minAdPrice = minAdPrice;
    }

    public String getMinChPrice() {
        return minChPrice;
    }

    public void setMinChPrice(String minChPrice) {
        this.minChPrice = minChPrice;
    }

    public String getMinPriceMsg() {
        return minPriceMsg;
    }

    public void setMinPriceMsg(String minPriceMsg) {
        this.minPriceMsg = minPriceMsg;
    }

    public String getReductionFlag() {
        return reductionFlag;
    }

    public void setReductionFlag(String reductionFlag) {
        this.reductionFlag = reductionFlag;
    }

    public String getAdReductionAmt() {
        return adReductionAmt;
    }

    public void setAdReductionAmt(String adReductionAmt) {
        this.adReductionAmt = adReductionAmt;
    }

    public String getAdReductionPerc() {
        return adReductionPerc;
    }

    public void setAdReductionPerc(String adReductionPerc) {
        this.adReductionPerc = adReductionPerc;
    }

    public String getChReductionAmt() {
        return ChReductionAmt;
    }

    public void setChReductionAmt(String chReductionAmt) {
        ChReductionAmt = chReductionAmt;
    }

    public String getChReductionPerc() {
        return chReductionPerc;
    }

    public void setChReductionPerc(String chReductionPerc) {
        this.chReductionPerc = chReductionPerc;
    }

    public String getNotInUsePos34() {
        return notInUsePos34;
    }

    public void setNotInUsePos34(String notInUsePos34) {
        this.notInUsePos34 = notInUsePos34;
    }

    public String getNotInUsePos35() {
        return notInUsePos35;
    }

    public void setNotInUsePos35(String notInUsePos35) {
        this.notInUsePos35 = notInUsePos35;
    }

    public String getNoFreeNightsAd() {
        return noFreeNightsAd;
    }

    public void setNoFreeNightsAd(String noFreeNightsAd) {
        this.noFreeNightsAd = noFreeNightsAd;
    }

    public String getPrcAdFreeNght() {
        return prcAdFreeNght;
    }

    public void setPrcAdFreeNght(String prcAdFreeNght) {
        this.prcAdFreeNght = prcAdFreeNght;
    }

    public String getBasedOnAdDuration() {
        return BasedOnAdDuration;
    }

    public void setBasedOnAdDuration(String basedOnAdDuration) {
        BasedOnAdDuration = basedOnAdDuration;
    }

    public String getNoFreeNightCh() {
        return NoFreeNightCh;
    }

    public void setNoFreeNightCh(String noFreeNightCh) {
        NoFreeNightCh = noFreeNightCh;
    }

    public String getPrcChFreeNght() {
        return prcChFreeNght;
    }

    public void setPrcChFreeNght(String prcChFreeNght) {
        this.prcChFreeNght = prcChFreeNght;
    }

    public String getBasedOnChDuration() {
        return basedOnChDuration;
    }

    public void setBasedOnChDuration(String basedOnChDuration) {
        this.basedOnChDuration = basedOnChDuration;
    }

    public String getNotInUsePos42() {
        return notInUsePos42;
    }

    public void setNotInUsePos42(String notInUsePos42) {
        this.notInUsePos42 = notInUsePos42;
    }

    public String getCoreIdentifierCode() {
        return coreIdentifierCode;
    }

    public void setCoreIdentifierCode(String coreIdentifierCode) {
        this.coreIdentifierCode = coreIdentifierCode;
    }

    public String getCoreIdentifierDesc() {
        return coreIdentifierDesc;
    }

    public void setCoreIdentifierDesc(String coreIdentifierDesc) {
        this.coreIdentifierDesc = coreIdentifierDesc;
    }

    public String getEarlyBirdDiscount() {
        return earlyBirdDiscount;
    }

    public void setEarlyBirdDiscount(String earlyBirdDiscount) {
        this.earlyBirdDiscount = earlyBirdDiscount;
    }

    public String getEarlyBirdDiscAmt() {
        return earlyBirdDiscAmt;
    }

    public void setEarlyBirdDiscAmt(String earlyBirdDiscAmt) {
        this.earlyBirdDiscAmt = earlyBirdDiscAmt;
    }
}
