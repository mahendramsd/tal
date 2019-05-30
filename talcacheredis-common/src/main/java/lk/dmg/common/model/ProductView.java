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
package lk.dmg.common.model;

import java.io.Serializable;
import java.util.Objects;
public class ProductView implements Serializable {
    private static final long serialVersionUID = 1L;

    private ProductPK id;
    private String adultprice;

    private String allocationstatus;

    private String childprice;

    private String minduration;

    private String mainhotelcode;
    private Integer nopaxperroom;
    private String rateplancode;
    private String rateplandesc;
    private String resortcode;
    private String extrabed;
    private String currency;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ProductPK getId() {
        return id;
    }

    public void setId(ProductPK id) {
        this.id = id;
    }

    public String getAdultprice() {
        return adultprice;
    }

    public void setAdultprice(String adultprice) {
        this.adultprice = adultprice;
    }

    public String getAllocationstatus() {
        return allocationstatus;
    }

    public void setAllocationstatus(String allocationstatus) {
        this.allocationstatus = allocationstatus;
    }

    public String getChildprice() {
        return childprice;
    }

    public void setChildprice(String childprice) {
        this.childprice = childprice;
    }

    public String getMinduration() {
        return minduration;
    }

    public void setMinduration(String minduration) {
        this.minduration = minduration;
    }

    public String getMainhotelcode() {
        return mainhotelcode;
    }

    public void setMainhotelcode(String mainhotelcode) {
        this.mainhotelcode = mainhotelcode;
    }

    public Integer getNopaxperroom() {
        return nopaxperroom;
    }

    public void setNopaxperroom(Integer nopaxperroom) {
        this.nopaxperroom = nopaxperroom;
    }

    public String getRateplancode() {
        return rateplancode;
    }

    public void setRateplancode(String rateplancode) {
        this.rateplancode = rateplancode;
    }

    public String getRateplandesc() {
        return rateplandesc;
    }

    public void setRateplandesc(String rateplandesc) {
        this.rateplandesc = rateplandesc;
    }

    public String getResortcode() {
        return resortcode;
    }

    public void setResortcode(String resortcode) {
        this.resortcode = resortcode;
    }

    public String getExtrabed() {
        return extrabed;
    }

    public void setExtrabed(String extrabed) {
        this.extrabed = extrabed;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductView that = (ProductView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(adultprice, that.adultprice) &&
                Objects.equals(allocationstatus, that.allocationstatus) &&
                Objects.equals(childprice, that.childprice) &&
                Objects.equals(minduration, that.minduration) &&
                Objects.equals(mainhotelcode, that.mainhotelcode) &&
                Objects.equals(nopaxperroom, that.nopaxperroom) &&
                Objects.equals(rateplancode, that.rateplancode) &&
                Objects.equals(rateplandesc, that.rateplandesc) &&
                Objects.equals(resortcode, that.resortcode) &&
                Objects.equals(extrabed, that.extrabed) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, adultprice, allocationstatus, childprice, minduration, mainhotelcode, nopaxperroom, rateplancode, rateplandesc, resortcode, extrabed, currency);
    }
}
