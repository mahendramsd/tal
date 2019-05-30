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
/*
package lk.dmg.common.repository;

import lk.dmg.common.model.HotelCode;
import lk.dmg.common.service.RedisHashMapAbstractService;
import lk.dmg.talcacheredisapi.util.Constants;
import org.springframework.stereotype.Component;


@Component
public class HotelCodeVsHotelsHashServiceImpl extends RedisHashMapAbstractService<HotelCode> {

    @Override
    public void setVoClass(Class<HotelCode> redisVOClass) {
        super.setVoClass(HotelCode.class);
    }

    public void setValue(String hashKey, HotelCode value) {
        super.set(Constants.HOTELCODE_VS_HOTELS_HASH, hashKey, value);
    }
    public HotelCode getValue(String hashKey) {
        return super.get(Constants.HOTELCODE_VS_HOTELS_HASH, hashKey);
    }
    
}*/


package lk.dmg.common.repository;

import lk.dmg.common.model.FreeNightVO;
import lk.dmg.common.service.RedisAbstractService;
import org.springframework.stereotype.Component;

@Component
public class FreeNightServiceImpl extends RedisAbstractService<FreeNightVO> {
}
