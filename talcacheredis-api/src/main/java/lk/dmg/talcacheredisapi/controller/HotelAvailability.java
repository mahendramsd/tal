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

package lk.dmg.talcacheredisapi.controller;


import lk.dmg.common.exception.TalcacheException;
import lk.dmg.talcacheoperational.model.response.AvailabilityResponseMainVO;
import lk.dmg.talcacheoperational.processor.AvailabilityProcessor;
import lk.dmg.talcacheoperational.processor.ResponseData;
import lk.dmg.talcacheoperational.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ha")
public class HotelAvailability extends AbstractEndpoint {
    private final static Logger log = LoggerFactory.getLogger(HotelAvailability.class);

    @Override
    public Logger getLogger() {
        return log;
    }

    @Autowired
    AvailabilityProcessor availabilityProcessor;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String getAvailability(@RequestParam("reqcode") String reqcode, @RequestParam("agentCode") String agentCode) {
        /*reqcode="18/Jan/2018]12]2]2]1]LON]KYY";*/
        /*reqcode="19/Jan/2018]5]1;3]1;9]0;0]LON]KYY";*/
        // 19/Jan/2018%5D5%5D1;3%5D1;9%5D0;0%5DLON%5DKYY
        Date start = new Date();
        MDC.put(Constants.ECHO_TOKEN, createToken(reqcode));
        if (log.isDebugEnabled()) log.debug("Availability endpoint call with : " + reqcode);
        String availabilityString = "";
        if (StringUtils.isNotEmpty(reqcode)) {
            try {
                List<AvailabilityResponseMainVO> availabilityResponseMainVOList = availabilityProcessor.getAvailableHotels(reqcode, agentCode);
                ResponseData responseData = new ResponseData();
                availabilityString = responseData.createResponse(availabilityResponseMainVOList);
            } catch (TalcacheException e) {
                log.error("Error occurred while calling the availability endpoint : " + e.getErrorMsg());
                availabilityString = e.getErrorType();
            } catch (Exception e) {
                log.error("Error occurred while calling the availability endpoint : " + e);
                availabilityString = Constants.INVALID_REQUEST_STRING + e;
            } finally {
                if (log.isDebugEnabled()) {
                    Date end = new Date();
                    long timeDiff = end.getTime() - start.getTime();
                    log.debug("TOTAL PROCESSING TIME : " + String.valueOf(timeDiff));
                }
                MDC.remove(Constants.ECHO_TOKEN);
            }
        } else {
            log.error("Agent Code Not Provided : ");
            availabilityString = Constants.INVALID_AGENT_CODE;
        }
        return availabilityString;
    }
}
