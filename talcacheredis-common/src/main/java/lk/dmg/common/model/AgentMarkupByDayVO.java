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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AgentMarkupByDayVO implements Serializable {
    @Id
    private String productCode;
    private String productDate;
    private String productDesc;
    private String allAgentInd;
    private List<String> agentExcluded = new ArrayList<>();
    private List<String> agentIncluded = new ArrayList<>();
    private List<String> markupFromDates = new ArrayList<>();
    private List<String> markupToDate = new ArrayList<>();
    private List<String> markup = new ArrayList<>();

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getAllAgentInd() {
        return allAgentInd;
    }

    public void setAllAgentInd(String allAgentInd) {
        this.allAgentInd = allAgentInd;
    }

    public List<String> getAgentExcluded() {
        return agentExcluded;
    }

    public void setAgentExcluded(List<String> agentExcluded) {
        this.agentExcluded = agentExcluded;
    }

    public List<String> getAgentIncluded() {
        return agentIncluded;
    }

    public void setAgentIncluded(List<String> agentIncluded) {
        this.agentIncluded = agentIncluded;
    }

    public List<String> getMarkupFromDates() {
        return markupFromDates;
    }

    public void setMarkupFromDates(List<String> markupFromDates) {
        this.markupFromDates = markupFromDates;
    }

    public List<String> getMarkupToDate() {
        return markupToDate;
    }

    public void setMarkupToDate(List<String> markupToDate) {
        this.markupToDate = markupToDate;
    }

    public List<String> getMarkup() {
        return markup;
    }

    public void setMarkup(List<String> markup) {
        this.markup = markup;
    }

    @Override
    public String toString() {
        return "AgentMarkupByDayVO{" +
                "productCode='" + productCode + '\'' +
                ", productDate='" + productDate + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", allAgentInd='" + allAgentInd + '\'' +
                ", agentExcluded=" + agentExcluded +
                ", agentIncluded=" + agentIncluded +
                ", markupFromDates=" + markupFromDates +
                ", markupToDate=" + markupToDate +
                ", markup=" + markup +
                '}';
    }
}
