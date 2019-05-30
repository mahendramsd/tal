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


import lk.dmg.common.exception.TalcacheException;
import lk.dmg.talcacheredismanager.common.Constants;
import lk.dmg.talcacheredismanager.service.HotelService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/upload")
public class UploadController {
    private final static Logger log = LoggerFactory.getLogger(UploadController.class);

    @Value("${application.isProdUpdate}")
    private int isProdUpdate;

    @Autowired
    HotelService hotelService;

    @RequestMapping(value = "uploadCapFile", method = RequestMethod.POST)
    ModelAndView uploadCapFileData(@RequestParam("file") MultipartFile file, Model model) {
        Boolean isSave = false;
        int mainHotesCount = 0;
        int subHotesCount = 0;
        int roomCount = 0;
        int productCount = 0;
        String processingTime = "";
        try {
            if (log.isDebugEnabled()) log.debug("UploadController  calling : ");
            long lStartTime = new Date().getTime();
            if (!file.isEmpty()) {
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                if (extension.equals(Constants.FILE_FORMAT)) {
                    File convFile = convertFile(file);
                    InputStream in = new FileInputStream(convFile);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    if (isProdUpdate == 1) {
                        hotelService.updateAgentMarkup(out.toString());
                    } else {
                        hotelService.updateHotelData(out.toString());
                    }
//                        mainHotesCount = hotelService.getMainHotelCount();
//                        subHotesCount = hotelService.getSubHotelCount();
//                        roomCount = roomService.getRoomCount();
//                        productCount = productService.getProductCount();
                    isSave = true;
                    if (log.isDebugEnabled()) {
                        log.debug("MainHotelCount : " + mainHotesCount + " , SubHotelCount : " + subHotesCount + " , RoomCount : " + roomCount + " , ProductCount : " + productCount);
                    }
                } else {
                    log.error("Invalid File Format ! File Format is :" + Constants.FILE_FORMAT);
                }
            } else {
                log.error("File is missing ! Can not be Process");
            }


            long lEndTime = new Date().getTime();
            long output = TimeUnit.MILLISECONDS.toSeconds(lEndTime - lStartTime);
            processingTime = String.valueOf(output);
        } catch (IOException e) {
            log.error("Error occurred while calling the UploadController : " + e);
        } catch (TalcacheException e) {
            log.error("Error occurred while calling the UploadController  : " + e.getErrorMsg());
        } catch (Exception e) {
            log.error("Error occurred while calling the UploadController : " + e);
        }
        model.addAttribute("mainHotesCount", mainHotesCount);
        model.addAttribute("subHotesCount", subHotesCount);
        model.addAttribute("roomCount", roomCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("processingTime", processingTime);
        model.addAttribute("isSave", isSave);
        model.addAttribute("pageType", 1);
        return new ModelAndView("initialCapFileUpdate-tile");
    }

    /**
     * Convert MultipartFile To File
     *
     * @param file
     * @return File
     * @throws IOException
     */
    public File convertFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
