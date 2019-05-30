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
package lk.dmg.talcacheredismanager.controller;


import lk.dmg.talcacheredismanager.utils.TalcacheThreadLocal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InitController {


    @RequestMapping("/")
    String home() {
        return "defaultTemplate";
    }
    @RequestMapping(value = "loadInitialUpdate", method = RequestMethod.GET)
    String loadInitialUpdate() {
        return "initialCapFileUpdate-tile";
    }

    @RequestMapping(value = "getXml", method = RequestMethod.GET)
    String getXml() {
        return "xmlForm-tile";
    }

    @RequestMapping(value = "getAvailability", method = RequestMethod.GET)
    String getavailability() {
        return "apitest-tile";
    }

    @RequestMapping(value = "getAgentMarkup", method = RequestMethod.GET)
    String getAgentMarkup(Model model) {
        model.addAttribute("isProcess", TalcacheThreadLocal.avalabilityFlag);
        return "agentMarkup-tile";
    }

    @RequestMapping(value = "getUpdates", method = RequestMethod.GET)
    String getupdates(Model model) {
        model.addAttribute("isProcess", TalcacheThreadLocal.avalabilityFlag);
        return "hotelsAvailability-tile";
    }

    @RequestMapping(value = "getMainHotelUpdate", method = RequestMethod.GET)
    String getmainhotelupdate(Model model) {
        model.addAttribute("isProcess", TalcacheThreadLocal.mainHotelFlag);
        return "mainHotelAutomate-tile";
    }
}
