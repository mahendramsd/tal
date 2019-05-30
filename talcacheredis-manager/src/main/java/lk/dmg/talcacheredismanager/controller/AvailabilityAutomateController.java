/*
 *     Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *     *  PROPRIETARY AND COPYRIGHT NOTICE.
 *
 *        This software product contains information which is proprietary to
 *        and considered a trade secret The Data management Group Ltd .
 *        It is expressly agreed that it shall not be reproduced in whole or part,
 *        disclosed, divulged or otherwise made available to any third party directly
 *        or indirectly.  Reproduction of this product for any purpose is prohibited
 *        without written authorisation from the The Data Management Group Ltd
 *        All Rights Reserved.
 *
 *        E-Mail andyj@datam.co.uk
 *        URL : www.datam.co.uk
 *        Created By : Mahendra Sri Dayarathna
 */

package lk.dmg.talcacheredismanager.controller;


import lk.dmg.common.exception.TalcacheException;
import lk.dmg.talcacheredismanager.common.Constants;
import lk.dmg.talcacheredismanager.service.HotelService;
import lk.dmg.talcacheredismanager.utils.PICKUtil;
import lk.dmg.talcacheredismanager.utils.TalcacheThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequestMapping("/availability")
public class AvailabilityAutomateController {

    private final static Logger log = LoggerFactory.getLogger(AvailabilityAutomateController.class);
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PICK_REQUEST_DATE_FORMAT);

    @Value("${application.agentCode}")
    private String agentCode;

    @Value("${application.agentPassword}")
    private String agentPassword;

    @Value("${application.availabilityAutomateTimeInterval}")
    private String availabilityAutomateTimeInterval;

    @Value("${application.availabilityDate}")
    private String availabilityDate;

    @Value("${application.talWebHost}")
    private String talWebHost;

    @Value("${application.talWebPort}")
    private String talWebPort;

    @Value("${application.talWebTimeOut}")
    private String talWebTimeOut;

    @Autowired
    HotelService hotelService;

    Timer timer = null;


    @RequestMapping(value = "startAvailabilityAutomate", method = RequestMethod.GET)
    @ResponseBody
    Boolean startAutomate() {
        Boolean isStart;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateAvailability();
                TalcacheThreadLocal.avalabilityFlag = true;
            }
        };
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, Long.parseLong(availabilityAutomateTimeInterval));
        isStart = true;
        if (log.isDebugEnabled()) {
            log.debug("TimerTask started");
        }
        return isStart;
    }

    @RequestMapping(value = "stopAvailabilityAutomate", method = RequestMethod.GET)
    @ResponseBody
    Boolean stopAutomate() {
        Boolean isStop = false;
        try {
            timer.cancel();
            isStop = true;
            TalcacheThreadLocal.avalabilityFlag = false;
            log.info("Stop Availability Automate Process !");
        } catch (Exception e) {
            log.error("Error occurred while calling the AvailabilityAutomateController : stopAutomate process" + e);
        }
        return isStop;
    }

    public void updateAvailability() {
        try {
            Date toDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(toDate);
            c.add(Calendar.DATE, Integer.parseInt(availabilityDate));
            toDate = c.getTime();

            String strPick = "";
            strPick += "TALDW\n";
            String tmp = "UPD.PICKCACHE]" + agentCode + "]" + agentPassword + "]"
                    + simpleDateFormat.format(Calendar.getInstance().getTime()) + "]" + simpleDateFormat.format(toDate)
                    + "]1;2;3;4]]]S]ATH01]H]N]Y";
            strPick += tmp + "\n";
            strPick += "zzz]" + tmp.length();

            PICKUtil pick = new PICKUtil();
            String returnMsg = pick.pickMessage(strPick, talWebHost, talWebPort, talWebTimeOut);

            if (returnMsg.indexOf("ERR") < 0) {
                returnMsg = returnMsg.replaceAll(Constants.MAIN_REPLACE_DELIMITER, "");
                //returnMsg =returnMsg.replaceAll(Constants.SUB_REPLACE_DELIMITER, "");
                hotelService.updateHotelData(returnMsg);
            }
        } catch (IOException e) {
            log.error("Error occurred while calling the AvailabilityAutomateController : " + e);
        } catch (TalcacheException e) {
            log.error("Error occurred while calling the AvailabilityAutomateController : " + e.getErrorMsg());
        } catch (Exception e) {
            log.error("Error occurred while calling the AvailabilityAutomateController : " + e);
        }

    }

}
