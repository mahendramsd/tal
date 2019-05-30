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
package lk.dmg.talcacheredismanager.model;


import lk.dmg.common.model.FreeNightVO;
import lk.dmg.common.model.Hotel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainHotel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String mainHotelCode;
    private List<Hotel> subHotels = new ArrayList<>();
    private List<FreeNightVO> freeNightVOS = new ArrayList<>();


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMainHotelCode() {
        return mainHotelCode;
    }

    public void setMainHotelCode(String mainHotelCode) {
        this.mainHotelCode = mainHotelCode;
    }

    public List<Hotel> getSubHotels() {
        return subHotels;
    }

    public void setSubHotels(List<Hotel> subHotels) {
        this.subHotels = subHotels;
    }

    public List<FreeNightVO> getFreeNightVOS() {
        return freeNightVOS;
    }

    public void setFreeNightVOS(List<FreeNightVO> freeNightVOS) {
        this.freeNightVOS = freeNightVOS;
    }
}
