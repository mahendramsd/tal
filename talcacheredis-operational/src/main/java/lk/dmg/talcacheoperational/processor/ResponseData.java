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
 *    Created By : Thilan Jayasinghe
 *
 */
package lk.dmg.talcacheoperational.processor;


import lk.dmg.common.exception.TalcacheException;
import lk.dmg.talcacheoperational.model.response.AvailabilityResponseMainVO;
import lk.dmg.talcacheoperational.model.response.AvailabilityResponseSubVO;
import lk.dmg.talcacheoperational.model.response.AvalabilityResponseRoomVO;
import lk.dmg.talcacheoperational.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ResponseData {
    private static final Logger log = LoggerFactory.getLogger(ResponseData.class);

    public String createResponse(AvailabilityResponseMainVO availabilityResponseMainVO)
            throws TalcacheException {
        if (log.isDebugEnabled()) {
            log.debug("Request Main Hotels :" + availabilityResponseMainVO.toString());
        }
        String response = "";
        AvailabilityResponseMainVO responseMainVo = availabilityResponseMainVO;
        if (responseMainVo != null) {
            response = createMainHotelData(responseMainVo);
            response = response + "\n" + createSubhotelData(responseMainVo);
        } else {
            log.error("Main Hotels not found.");
            throw new TalcacheException("CR001", "Main hotel not found",
                                        Constants.INVALID_MAIN_HOTEL_DATA);
        }
        return response;
    }

    public String createResponse(List<AvailabilityResponseMainVO> availabilityResponseMainVOList)
            throws TalcacheException {
        if (log.isDebugEnabled()) {
            log.debug("Request Main Hotels  :" + availabilityResponseMainVOList.size());
        }
        String response = "";
        StringBuffer buffer = new StringBuffer();
        List<AvailabilityResponseMainVO> responseMainVolist = availabilityResponseMainVOList;
        if (responseMainVolist.size() != 0) {
            for (AvailabilityResponseMainVO responseMainVO : responseMainVolist) {
                buffer.append(createMainHotelData(responseMainVO));
                buffer.append(System.lineSeparator());
                buffer.append(createSubhotelData(responseMainVO));
            }
            response = buffer.toString();
        } else {
            log.error("Main Hotels not found.");
            throw new TalcacheException("CR002", "Main hotels not found",
                                        Constants.INVALID_MAIN_HOTEL_DATA);
        }
        return response;
    }

    private String createMainHotelData(AvailabilityResponseMainVO mainhotelvo) {
        AvailabilityResponseMainVO responseMainVo = mainhotelvo;
        String mainhoteldata = "";
        mainhoteldata = "}MAIN" + "]" + responseMainVo.getMainHotelCode() + "]" + responseMainVo
                .getNotInUsePos3() + "]" +
                        responseMainVo.getNotInUsePos4() + "]" + responseMainVo.getNotInUsePos5()
                        + "]" + responseMainVo.getNotInUsePos6() + "]" +
                        responseMainVo.getNotInUsePos7() + "]" + responseMainVo.getNotInUsePos8()
                        + "]" +responseMainVo.getNotInUsePos9()+"]"+ responseMainVo.getMinPriceAdult()+"]"
                        +responseMainVo.getMaxPriceAdult()+ "]" + responseMainVo.getNotInUsePos12() + "]" + getSubHotelList(
                responseMainVo.getListOfSubHotels())+"]]";
        return mainhoteldata;
    }

    private String getSubHotelList(List<String> listOfSubHotels) {
        String subhotellist = "";
        int subhotellistsize = listOfSubHotels.size();
        int loopid = 1;
        StringBuffer buffer = new StringBuffer();
        for (String subhotelcode : listOfSubHotels) {
            if (subhotellistsize == loopid) {
                buffer.append(subhotelcode);
            } else {
                buffer.append(subhotelcode + ";");
            }
            loopid = loopid + 1;
        }
        subhotellist = buffer.toString();
        return subhotellist;
    }


    private String createSubhotelData(AvailabilityResponseMainVO mainhotelvo) {
        String subhoteldsata = "";
        ResponseProducts responseProducts = new ResponseProducts();
        StringBuffer buffer = new StringBuffer();
        for (AvailabilityResponseSubVO subvo : mainhotelvo.getAvailabilityResponseSubVO()) {
            buffer.append(
                    "}SUB]" + subvo.getSubHotelCode() + "]" + subvo.getCategoryCode() + "]" + subvo
                            .getResortCode() + "]");
            buffer.append(createRoomdata(subvo.getAvalabilityResponseRoomVO()));
            buffer.append(String.valueOf(subvo.getMinPrice()) + "]" + String
                    .valueOf(subvo.getMaxPrice()));
            buffer.append(responseProducts.createProductData(subvo.getAvalabilityResponseRoomVO(),
                                                             subvo.getNotInUsePos45(),
                                                             subvo.getRatePlanDesc(),
                                                             subvo.getRateplanCode(),
                                                             subvo.getNotInUsePos48(),
                                                             subvo.getUratePlanCode()));
        }
        subhoteldsata = buffer.toString();
        return subhoteldsata;
    }

    private String createRoomdata(List<AvalabilityResponseRoomVO> avalabilityResponseRoomVO) {
        String roomdata = "";
        StringBuffer buffer = new StringBuffer();
        int roomlistsize = avalabilityResponseRoomVO.size();
        int loopid = 1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if (roomlistsize == loopid) {
                buffer.append(roomVo.getRoomCode());
            } else {
                buffer.append(roomVo.getRoomCode() + ";");
            }
            loopid = loopid + 1;
        }
        buffer.append("]");
        loopid = 1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if (roomlistsize == loopid) {
                buffer.append(roomVo.getAvailmessage());
            } else {
                buffer.append(roomVo.getAvailmessage() + ";");
            }
            loopid = loopid + 1;
        }


        buffer.append("]");
        loopid = 1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if (roomlistsize == loopid) {
                buffer.append(String.valueOf(roomVo.getNoOfPaxPerRoom()));
            } else {
                buffer.append(String.valueOf(roomVo.getNoOfPaxPerRoom()) + ";");
            }
            loopid = loopid + 1;
        }
        loopid = 1;
        buffer.append("]");
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if (roomlistsize == loopid) {
                buffer.append(roomVo.getExtraBedIndicator());
            } else {
                buffer.append(roomVo.getExtraBedIndicator() + ";");
            }
            loopid = loopid + 1;
        }
        loopid = 1;
        buffer.append("]");
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if (roomlistsize == loopid) {
                buffer.append(String.valueOf(roomVo.getNoOfExtraBeds()));
            } else {
                buffer.append(String.valueOf(roomVo.getNoOfExtraBeds()) + ";");
            }
            loopid = loopid + 1;
        }
        buffer.append("]");
        roomdata = buffer.toString();
        return roomdata;
    }
}
