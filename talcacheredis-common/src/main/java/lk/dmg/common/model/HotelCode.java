/*
 *
 *   Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *   *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *      This software product contains information which is proprietary to
 *      and considered a trade secret The Data management Group Ltd .
 *      It is expressly agreed that it shall not be reproduced in whole or part,
 *      disclosed, divulged or otherwise made available to any third party directly
 *      or indirectly.  Reproduction of this product for any purpose is prohibited
 *      without written authorisation from the The Data Management Group Ltd
 *      All Rights Reserved.
 *
 *      E-Mail andyj@datam.co.uk
 *      URL : www.datam.co.uk
 *      Created By : Mahendra Sri Dayarathna
 *
 *
 */

package lk.dmg.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelCode implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mainHotelCode;
    private Set<String> subHotelCodes = new HashSet<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMainHotelCode() {
        return mainHotelCode;
    }

    public void setMainHotelCode(String mainHotelCode) {
        this.mainHotelCode = mainHotelCode;
    }

    public Set<String> getSubHotelCodes() {
        return subHotelCodes;
    }

    public void setSubHotelCodes(Set<String> subHotelCodes) {
        this.subHotelCodes = subHotelCodes;
    }
}
