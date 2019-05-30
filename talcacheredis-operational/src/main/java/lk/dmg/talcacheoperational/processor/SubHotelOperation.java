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

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface SubHotelOperation {
    Boolean canHandle(AvailabilityRequestVO availabilityRequestVO);

    List<String> getHotelsList(AvailabilityRequestVO availabilityRequestVO, ResortHashRedisServiceImpl resortHashRedisService, HotelCodeVsHotelsHashServiceImpl hotelCodeVsHotelsHashService) throws IOException, TalcacheException;

    List<Hotel> getAllHotels(Set<String> keys, HotelHashRedisServiceImpl hotelHashRedisService) throws TalcacheException;
}
