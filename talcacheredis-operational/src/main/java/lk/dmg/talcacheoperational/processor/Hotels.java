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
import lk.dmg.common.model.HotelCode;
import lk.dmg.common.repository.HotelCodeVsHotelsHashServiceImpl;
import lk.dmg.common.repository.HotelHashRedisServiceImpl;
import lk.dmg.common.repository.ResortHashRedisServiceImpl;
import lk.dmg.talcacheoperational.model.AvailabilityRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hotels implements SubHotelOperation {
    private static final Logger log = LoggerFactory.getLogger(Hotels.class);
    @Override
    public Boolean canHandle(AvailabilityRequestVO availabilityRequestVO) {
        return true;
    }

    @Override
    public List<String> getHotelsList(AvailabilityRequestVO availabilityRequestVO, ResortHashRedisServiceImpl resortHashRedisService, HotelCodeVsHotelsHashServiceImpl hotelCodeVsHotelsHashService) throws IOException, TalcacheException {
        if (log.isDebugEnabled()) {
            log.debug("Get subHotels for HotelCodes count :" + availabilityRequestVO.getHotelCode().size());
        }
        Set<String> subHotels = new HashSet<>();
        for (String code : availabilityRequestVO.getHotelCode()) {
            HotelCode hotelCode = hotelCodeVsHotelsHashService.get(lk.dmg.common.util.Constants.HOTELCODE_VS_HOTELS_HASH, code, HotelCode.class);
            subHotels.addAll(hotelCode.getSubHotelCodes());
        }
        if (log.isDebugEnabled()) {
            log.debug("List of HotelCode :" + subHotels.size());
        }
        return new ArrayList<>(subHotels);
    }

    @Override
    public List<Hotel> getAllHotels(Set<String> keys, HotelHashRedisServiceImpl hotelHashRedisService) throws TalcacheException {
        List<Hotel> hotelList = hotelHashRedisService.getByKeySet(lk.dmg.common.util.Constants.HOTEL_BY_DATE_HASH, keys, Hotel.class);
        if (log.isDebugEnabled()) {
            log.debug("List of Hotel :" + hotelList.size());
        }
        return hotelList;
    }
}