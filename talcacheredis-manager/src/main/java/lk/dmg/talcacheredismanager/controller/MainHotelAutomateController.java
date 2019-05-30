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
@RequestMapping("/mainHotel")
public class MainHotelAutomateController {

    private final static Logger log = LoggerFactory.getLogger(MainHotelAutomateController.class);
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PICK_REQUEST_DATE_FORMAT);

    @Value("${application.agentCode}")
    private String agentCode;

    @Value("${application.agentPassword}")
    private String agentPassword;

    @Value("${application.mainHotelAutomateTimeInterval}")
    private String mainHotelAutomateTimeInterval;

    @Value("${application.talWebHost}")
    private String talWebHost;

    @Value("${application.talWebPort}")
    private String talWebPort;

    @Value("${application.talWebTimeOut}")
    private String talWebTimeOut;

    @Value("${application.mainHotelDate}")
    private String mainHotelDate;

    @Autowired
    HotelService hotelService;

    Timer timer = null;

    @RequestMapping(value = "startMainHotelAutomate", method = RequestMethod.GET)
    @ResponseBody
    Boolean startAutomate() {
        Boolean isStart;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateMainHotels();
                TalcacheThreadLocal.mainHotelFlag = true;
            }
        };
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, Long.parseLong(mainHotelAutomateTimeInterval));
        isStart = true;
        if (log.isDebugEnabled()) {
            log.debug("Timer Task started");
        }
        return isStart;
    }

    @RequestMapping(value = "stopMainHotelAutomate", method = RequestMethod.GET)
    @ResponseBody
    Boolean stopAutomate() {
        Boolean isStop = false;
        try {
            timer.cancel();
            isStop = true;
            TalcacheThreadLocal.mainHotelFlag = false;
            log.info("Stop Main Hotel Automate Process !");
        } catch (Exception e) {
            log.error("Error occurred while calling the MainHotelAutomateController : stopAutomate process" + e);
        }
        return isStop;
    }

    public void updateMainHotels() {
        try {
            Date tomorrow = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(tomorrow);
            c.add(Calendar.DATE, Integer.parseInt(mainHotelDate));
            tomorrow = c.getTime();

            String strPick = "";
            strPick += "TALDW\n";

            String tmp = "UPD.PICKCACHE]" + agentCode + "]" + agentPassword + "]"
                    + simpleDateFormat.format(Calendar.getInstance().getTime()) + "]"
                    + simpleDateFormat.format(tomorrow) + "]1;2;3;4]]]S]ATH01]H]N]]Y";
            strPick += tmp + "\n";
            strPick += "zzz]" + tmp.length();
            PICKUtil pick = new PICKUtil();
            String returnMsg = pick.pickMessage(strPick, talWebHost, talWebPort, talWebTimeOut);

            if (returnMsg.indexOf("ERR") < 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Ready To Main Hotel Amendment " + returnMsg);
                }
                returnMsg = returnMsg.replaceAll(Constants.MAIN_REPLACE_DELIMITER, "");
                //returnMsg = returnMsg.replaceAll(Constants.SUB_REPLACE_DELIMITER, "");
                hotelService.updateHotelData(returnMsg);
            }
        } catch (IOException e) {
            log.error("Error occurred while calling the MainHotelAutomateController : " + e);
        } catch (TalcacheException e) {
            log.error("Error occurred while calling the MainHotelAutomateController : " + e.getErrorMsg());
        } catch (Exception e) {
            log.error("Error occurred while calling the MainHotelAutomateController : " + e);
        }

    }

}
