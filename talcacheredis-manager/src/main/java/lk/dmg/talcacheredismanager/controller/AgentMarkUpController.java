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
@RequestMapping("/agentMarkup")
public class AgentMarkUpController {

    private final static Logger log = LoggerFactory.getLogger(AgentMarkUpController.class);
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PICK_REQUEST_DATE_FORMAT);

    @Value("${application.agentCode}")
    private String agentCode;

    @Value("${application.agentPassword}")
    private String agentPassword;

    @Value("${application.agentMarkupAutomateTimeInterval}")
    private String agentMarkupAutomateTimeInterval;

    @Value("${application.talWebHost}")
    private String talWebHost;

    @Value("${application.talWebPort}")
    private String talWebPort;

    @Value("${application.talWebTimeOut}")
    private String talWebTimeOut;

    @Value("${application.agentMarkUpDate}")
    private String agentMarkUpDate;

    @Autowired
    HotelService hotelService;
    Timer timer = null;

    @RequestMapping(value = "startAgentMarkupAutomate", method = RequestMethod.GET)
    @ResponseBody
    Boolean startAutomate() {
        Boolean isStart;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateAgentMarkup();
                TalcacheThreadLocal.mainHotelFlag = true;
            }
        };
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, Long.parseLong(agentMarkupAutomateTimeInterval));
        isStart = true;
        if (log.isDebugEnabled()) {
            log.debug("Timer Task started");
        }
        return isStart;
    }


    @RequestMapping(value = "stopAgentMarkupAutomate", method = RequestMethod.GET)
    @ResponseBody
    Boolean stopAutomate() {
        Boolean isStop = false;
        try {
            timer.cancel();
            isStop = true;
            TalcacheThreadLocal.mainHotelFlag = false;
            log.info("Stop Agent Markup Automate Process !");
        } catch (Exception e) {
            log.error("Error occurred while calling the AgentMarkUpController : stopAutomate process" + e);
        }
        return isStop;
    }


    public void updateAgentMarkup() {
        try {
            Date tomorrow = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(tomorrow);
            c.add(Calendar.DATE, Integer.parseInt(agentMarkUpDate));
            tomorrow = c.getTime();

            String strPick = "";
            strPick += "TALDW\n";

            String tmp = "UPD.PICKPROD]" + simpleDateFormat.format(Calendar.getInstance().getTime()) + "]"
                    + simpleDateFormat.format(tomorrow) + "]]S]NDS1";
            strPick += tmp + "\n";
            strPick += "zzz]" + tmp.length();
            PICKUtil pick = new PICKUtil();
            String returnMsg = pick.pickMessage(strPick, talWebHost, talWebPort, talWebTimeOut);

            if (returnMsg.indexOf("ERR") < 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Ready To Agent Markup Amendment " + returnMsg);
                }

                returnMsg = returnMsg.replaceAll(Constants.MAIN_REPLACE_DELIMITER, "");
                hotelService.updateAgentMarkup(returnMsg);
            }
        } catch (IOException e) {
            log.error("Error occurred while calling the AgentMarkUpController : " + e);
        } catch (TalcacheException e) {
            log.error("Error occurred while calling the AgentMarkUpController : " + e.getErrorMsg());
        } catch (Exception e) {
            log.error("Error occurred while calling the AgentMarkUpController : " + e);
        }

    }
}
