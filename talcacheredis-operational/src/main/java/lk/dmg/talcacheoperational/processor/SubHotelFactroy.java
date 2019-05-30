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
import lk.dmg.talcacheoperational.model.AvailabilityRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SubHotelFactroy {
    private static final Logger log = LoggerFactory.getLogger(SubHotelFactroy.class);

    public static SubHotelOperation getSubHotels(AvailabilityRequestVO availabilityRequestVO) {
        List<String> availabilityRequestVOHotelList = availabilityRequestVO.getHotelCode();
        if (availabilityRequestVOHotelList.size() > 0) { /*Get Subhotels  if one or more hotel codes provided.*/
            if (log.isDebugEnabled()) {
                log.debug("Hotels code found in request string");
            }
            Hotels hotel = new Hotels();
            if (hotel.canHandle(availabilityRequestVO)) return hotel;
        } else if (availabilityRequestVO.getCityCode() != null) { /*Hotels not provided but resort/city code provided.*/
            if (log.isDebugEnabled()){
                log.debug("Hotels code not found get subhotel codes based on city/resort code");
            }
            Resort resort = new Resort();
            if (resort.canHandle(availabilityRequestVO)) {
                return resort;
            }
        }
        return null;
    }
}
