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

package lk.dmg.talcacheoperational.processor;


import lk.dmg.common.exception.TalcacheException;
import lk.dmg.common.model.Hotel;
import lk.dmg.common.repository.HotelCodeVsHotelsHashServiceImpl;
import lk.dmg.common.repository.HotelHashRedisServiceImpl;
import lk.dmg.common.repository.ResortHashRedisServiceImpl;
import lk.dmg.talcacheoperational.model.AvailabilityRequestVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Resort implements SubHotelOperation {
    private static final Logger log = LoggerFactory.getLogger(Resort.class);

    @Override
    public Boolean canHandle(AvailabilityRequestVO availabilityRequestVO) {
        return true;
    }

    @Override
    public List<String> getHotelsList(AvailabilityRequestVO availabilityRequestVO, ResortHashRedisServiceImpl resortHashRedisService, HotelCodeVsHotelsHashServiceImpl hotelCodeVsHotelsHashService) throws TalcacheException {
        if (log.isDebugEnabled())
        log.debug("Get subhotels for resort code :" + availabilityRequestVO.getCityCode());
        /*resortHashRedisService.setVoClass(lk.dmg.common.model.Resort.class);*/
        Date date = new Date();
        lk.dmg.common.model.Resort resort = resortHashRedisService.get(lk.dmg.common.util.Constants.RESORT_VS_HOTELS_HASH, availabilityRequestVO.getCityCode(), lk.dmg.common.model.Resort.class);
        if (log.isDebugEnabled())
            log.debug("Get subhotels for resort code Finished. No Of subHotels:" + resort.getSubHotels().size());
        if (log.isDebugEnabled()) {
            Date endDate = new Date();
            long diff = endDate.getTime() - date.getTime();
            log.debug("Time for processing resortcode :" + diff);
        }
        return resort.getSubHotels();
    }

    @Override
    public List<Hotel> getAllHotels(Set<String> keys, HotelHashRedisServiceImpl hotelHashRedisService) throws TalcacheException {
        Date date = new Date();
        List<Hotel> hotelList = hotelHashRedisService.getByKeySet(lk.dmg.common.util.Constants.HOTEL_BY_DATE_HASH, keys, Hotel.class);
        if (log.isDebugEnabled()) {
            Date endDate = new Date();
            long diff = endDate.getTime() - date.getTime();
            log.debug("Time for processing all hotel keys :" + diff);
        }
        return hotelList;
    }
}
