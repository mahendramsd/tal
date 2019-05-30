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

package lk.dmg.talcacheredismanager.service;

import lk.dmg.common.exception.TalcacheException;
import lk.dmg.common.model.*;
import lk.dmg.talcacheredismanager.common.Constants;
import lk.dmg.talcacheredismanager.model.MainHotel;
import lk.dmg.talcacheredismanager.utils.PickObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import lk.dmg.common.repository.*;

@Service
public class HotelService {


    @Autowired
    OccupancyHashRedisServiceImpl occupancyHashRedisService;

    @Autowired
    HotelCodeVsHotelsHashServiceImpl hotelCodeService;

    @Autowired
    ResortHashRedisServiceImpl resortHashRedisService;
    
    @Autowired
    HotelHashRedisServiceImpl hotelHashRedisServiceImpl;
    
    @Autowired
    HotelCodeVsHotelsHashServiceImpl codeVsHotelsHashServiceImpl;

    @Autowired
    FreeNightServiceImpl freeNightService;

    @Autowired
    PickObjectMapper pickObjectMapper;

    @Autowired
    AgentMarkupByDateServiceImpl agentMarkupService;


    private final static Logger log = LoggerFactory.getLogger(HotelService.class);

    Map<String, Resort> resortMap = new HashMap<>();
    Map<String, OccupancyVO>  occupancyRoomMap = new HashMap<>();


    /**
     * @param pickReturn
     * @throws Exception
     */
    public void updateHotelData(String pickReturn) throws Exception {
        String[] mainHotelSegment = PickObjectMapper.splitTokenizer(pickReturn, Constants.MAIN_HOTEL_DELIMITER);

        if (mainHotelSegment.length >= 2) {
            for (int i = 1; i < mainHotelSegment.length; i++) {
                String mainHotelSeg = mainHotelSegment[i];
                MainHotel mainHotel = pickObjectMapper.createMainHotel(mainHotelSeg);
                log.info(i+" saved from "+mainHotelSegment.length+ "main hotel" );
                if (log.isDebugEnabled()) {
                    log.debug("Started redis update" + mainHotel.getMainHotelCode());
                }
                Set<String> hotelCodesSet=new HashSet<>();
                for (Hotel hotel : mainHotel.getSubHotels()) {
                	saveHotelHashToRedis(hotel);
                    addResortToRedis(hotel);
                    hotelCodesSet.add(hotel.getSubHotelCode());
                    addRoomPaxToRedis(hotel);

                }
                saveHotelCodeToRedis(mainHotel.getMainHotelCode(), hotelCodesSet);

            }
            saveResortToRedis(resortMap);
            saveOccupancyToRedis(occupancyRoomMap);
            if (log.isDebugEnabled()) {
                log.debug("Completed redis Successfully !!!!");
            }
        } else {
            throw new TalcacheException("TAL100", "No Hotels", Constants.TAL100);
        }
    }

    /**
     *
     * @param roomPaxMap
     */
    private void saveOccupancyToRedis(Map<String, OccupancyVO> roomPaxMap) throws TalcacheException {
        for (String roomPaxKey : roomPaxMap.keySet()) {
            OccupancyVO  occupancyVO = roomPaxMap.get(roomPaxKey);
            occupancyHashRedisService.set(lk.dmg.common.util.Constants.OCCUPANCY_ROOM_FILE, roomPaxKey, occupancyVO);
            if (log.isDebugEnabled()) {
                log.debug("Add new Room Pax Key Redis : " + roomPaxKey);
            }
        }
    }

    /**
     * @param hotel
     */
    private void addRoomPaxToRedis(Hotel hotel) {
        for (Room room : hotel.getRooms()) {
            String redisRoomPaxKey = room.getNoOfRoomPax();
            if (StringUtils.isNotEmpty(redisRoomPaxKey)) {
                if (log.isDebugEnabled()) {
                    log.debug("Add Room pax to Map");
                }
                if (occupancyRoomMap.containsKey(redisRoomPaxKey)) {
                    OccupancyVO occupancyVO = occupancyRoomMap.get(redisRoomPaxKey);
                    List<String> roomCodeList = occupancyVO.getRoomCodeList();
                    roomCodeList.add(room.getRoomCode());
                    Set<String> set=new HashSet<>();
                    set.addAll(roomCodeList);
                    roomCodeList.clear();
                    roomCodeList.addAll(set);
                    occupancyVO.setRoomCodeList(roomCodeList);
                    occupancyRoomMap.put(redisRoomPaxKey, occupancyVO);
                } else {
                    OccupancyVO occupancyVO =  new OccupancyVO();
                    occupancyVO.setOccupancyCode(redisRoomPaxKey);
                    List<String> roomCodeList = new ArrayList<>();
                    roomCodeList.add(room.getRoomCode());
                    occupancyVO.setRoomCodeList(roomCodeList);
                    occupancyRoomMap.put(redisRoomPaxKey, occupancyVO);
                }
            }
        }
    }

    /**
     * @param mainHotelCode
     * @param hotelCodes
     */
    private void saveHotelCodeToRedis(String mainHotelCode, Set<String> hotelCodes) throws TalcacheException {
        HotelCode hotelCodeVO = new HotelCode();
        hotelCodeVO.setMainHotelCode(mainHotelCode);
        hotelCodeVO.setSubHotelCodes(hotelCodes);
        for (String hotelCode : hotelCodes) {
            codeVsHotelsHashServiceImpl.set(lk.dmg.common.util.Constants.HOTELCODE_VS_HOTELS_HASH, hotelCode, hotelCodeVO);
		}
    }

    /**
     * @param resortMap
     */
    private void saveResortToRedis(Map<String, Resort> resortMap) throws TalcacheException {
        for (String resortCode : resortMap.keySet()) {
            Resort resort = resortMap.get(resortCode);
            resortHashRedisService.set(lk.dmg.common.util.Constants.RESORT_VS_HOTELS_HASH, resortCode, resort);
            if (log.isDebugEnabled()) {
                log.debug("Add new Resort Key Redis : " + resortCode);
            }
        }
    }

    /**
     * @param hotel
     */
    private void addResortToRedis(Hotel hotel) {
        String redisResortKey = hotel.getResortCode();
        if (StringUtils.isNotEmpty(redisResortKey)) {
            if (resortMap.containsKey(redisResortKey)) {
                Resort resort = resortMap.get(redisResortKey);
                List<String> subHotelList = resort.getSubHotels();               
                subHotelList.add(hotel.getSubHotelCode());
                Set<String> set=new HashSet<>();
                set.addAll(subHotelList);
                subHotelList.clear();
                subHotelList.addAll(set);
                resort.setSubHotels(subHotelList);
                resortMap.put(redisResortKey, resort);
            } else {
                List<String> subHotelsList = new ArrayList<>();
                subHotelsList.add(hotel.getSubHotelCode());
                Resort resort = new Resort();
                resort.setResortCode(hotel.getResortCode());
                resort.setSubHotels(subHotelsList);
                resortMap.put(redisResortKey, resort);
            }
        }

    }
    /**
     * @param hotel
     */
    private void saveHotelHashToRedis(Hotel hotel) throws TalcacheException {
        String redisHotelKey = redisHotelKeyGeneration(hotel);
        hotelHashRedisServiceImpl.set(lk.dmg.common.util.Constants.HOTEL_BY_DATE_HASH, redisHotelKey, hotel);
    }


    /**
     * @param hotel
     * @return
     */
    public String redisHotelKeyGeneration(Hotel hotel) {
        return hotel.getSubHotelCode() + Constants.REDIS_KEY_DELIMITER + hotel.getDate();
    }


    /**
     * @param agentMarkup
     */
    public void updateAgentMarkup(String agentMarkup) throws TalcacheException {
        pickObjectMapper.createAgentMarks(agentMarkup);
    }
}
