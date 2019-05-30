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

import lk.dmg.common.exception.TalcacheException;
import lk.dmg.common.model.*;
import lk.dmg.common.repository.*;
import lk.dmg.talcacheoperational.model.AvailabilityRequestVO;
import lk.dmg.talcacheoperational.model.response.AvailabilityResponseMainVO;
import lk.dmg.talcacheoperational.model.response.AvailabilityResponseProductVO;
import lk.dmg.talcacheoperational.model.response.AvailabilityResponseSubVO;
import lk.dmg.talcacheoperational.model.response.AvalabilityResponseRoomVO;
import lk.dmg.talcacheoperational.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class AvailabilityProcessor {
    private static final Logger log = LoggerFactory.getLogger(AvailabilityProcessor.class);

    private ResortHashRedisServiceImpl resortHashRedisService;
    private HotelHashRedisServiceImpl hotelHashRedisService;
    private HotelCodeVsHotelsHashServiceImpl hotelCodeVsHotelsHashService;
    private OccupancyHashRedisServiceImpl occupancyHashRedisService;
    private FreeNightServiceImpl freeNightService;
    private AgentMarkupByDateServiceImpl agentMarkupService;
    private EarlyBirdServiceImpl earlyBirdService;
    private AgentFreeNightByDateServiceImpl agentFreeNightByDateService;
    private AgentEarlyBirdByDateServiceImpl agentEarlyBirdByDateService;

    @Autowired
    public AvailabilityProcessor(ResortHashRedisServiceImpl resortHashRedisService, HotelHashRedisServiceImpl hotelHashRedisService, HotelCodeVsHotelsHashServiceImpl hotelCodeVsHotelsHashService, OccupancyHashRedisServiceImpl occupancyHashRedisService, FreeNightServiceImpl freeNightService, AgentMarkupByDateServiceImpl agentMarkupService, EarlyBirdServiceImpl earlyBirdService, AgentFreeNightByDateServiceImpl agentFreeNightByDateService, AgentEarlyBirdByDateServiceImpl agentEarlyBirdByDateService) {
        this.resortHashRedisService = resortHashRedisService;
        this.hotelHashRedisService = hotelHashRedisService;
        this.hotelCodeVsHotelsHashService = hotelCodeVsHotelsHashService;
        this.occupancyHashRedisService = occupancyHashRedisService;
        this.freeNightService = freeNightService;
        this.agentMarkupService = agentMarkupService;
        this.earlyBirdService = earlyBirdService;
        this.agentFreeNightByDateService = agentFreeNightByDateService;
        this.agentEarlyBirdByDateService = agentEarlyBirdByDateService;
    }

    /**
     * getAvailableHotels request pick string
     * availability pick return string
     *
     * @param request
     * @param agentCode
     * @return String
     * @throws TalcacheException
     */
    public List<AvailabilityResponseMainVO> getAvailableHotels(String request, String agentCode) throws TalcacheException, IOException {
        Map<String, AvailabilityResponseMainVO> availabilityResponseMainVOListFinal = new HashMap<>();
        Map<String, AvailabilityResponseMainVO> availabilityResponseMainVOListMap = new HashMap<>();
        AvailabilityRequestVO availabilityRequestVO = convertRequestStringToObject(request);
        SubHotelOperation subHotelOperation = SubHotelFactroy.getSubHotels(availabilityRequestVO);
        List<String> hotelList = new ArrayList<>();//Resort vs Hotels
        List<Hotel> allHotelList = new ArrayList<>();
        if (subHotelOperation != null) {
            hotelList = subHotelOperation.getHotelsList(availabilityRequestVO, resortHashRedisService, hotelCodeVsHotelsHashService);
            Map<String, List<String>> mainsubHotelMap = new HashMap<>();//prevent duplicate main hotel
            if (log.isDebugEnabled()) {
                log.debug("Availble Hotel List : " + hotelList.size());
            }
            // find mainHotelCode and subhotelList any hotelCode in redis
            for (String hotelCode : hotelList) {
                HotelCode hotelCodeVO = hotelCodeVsHotelsHashService.get(lk.dmg.common.util.Constants.HOTELCODE_VS_HOTELS_HASH, hotelCode, HotelCode.class);
                String mainHotelCode = hotelCodeVO.getMainHotelCode();
                mainsubHotelMap.putIfAbsent(mainHotelCode, new ArrayList<>(hotelCodeVO.getSubHotelCodes()));
            }
            if (log.isDebugEnabled()) {
                log.debug("Availble No Of MainHotels " + mainsubHotelMap.size());
            }
            // Create MainHotel Response for all Main HotelCode
            for (String mainHotelCode : mainsubHotelMap.keySet()) {
                AvailabilityResponseMainVO availabilityResponseMainVO = new AvailabilityResponseMainVO();
                availabilityResponseMainVO.setMainHotelCode(mainHotelCode);
                availabilityResponseMainVO.setListOfSubHotels(mainsubHotelMap.get(mainHotelCode));
                availabilityResponseMainVOListMap.put(mainHotelCode, availabilityResponseMainVO);
            }
            /**
             * get requested Dates and redis key generate
             */
            List<Date> requestedDates = getRequestedDays(availabilityRequestVO);
            Set<String> redisKeys = buildKeySet(hotelList, requestedDates);
            /**
             * All Hotels data set base on date in redis
             */
            allHotelList = subHotelOperation.getAllHotels(redisKeys, hotelHashRedisService);

            //Add Subhotel SubHotelVsHotelMap
            Map<String, List<Hotel>> subhotelVsHotelsMap = new HashMap<>();
            for (Hotel hotel : allHotelList) {
                if (hotel != null) {
                    if (subhotelVsHotelsMap.containsKey(hotel.getSubHotelCode())) {
                        List<Hotel> hotels = subhotelVsHotelsMap.get(hotel.getSubHotelCode());
                        hotels.add(hotel);
                        subhotelVsHotelsMap.put(hotel.getSubHotelCode(), hotels);
                    } else {
                        List<Hotel> hotels = new ArrayList<>();
                        hotels.add(hotel);
                        subhotelVsHotelsMap.put(hotel.getSubHotelCode(), hotels);
                    }
                }
            }
            if (subhotelVsHotelsMap.size() == 0) {
                throw new TalcacheException("STI32", "No Hotels found for request string :", Constants.INVALID_REQUEST_STRING);
            }
            /**
             * Build requested occupancy to set
             */
            Set<String> occupancySet = new HashSet<>();
            for (Integer reqOccupancy : availabilityRequestVO.getRoomOccupancy()) {
                if (reqOccupancy != 0) {
                    occupancySet.add(reqOccupancy.toString());
                }
            }

            /**
             * Build requested roomcodes  to sub hotel
             */
            List<OccupancyVO> occupancyVOS = occupancyHashRedisService.getByKeySet(lk.dmg.common.util.Constants.OCCUPANCY_ROOM_FILE, occupancySet, OccupancyVO.class);

            List<AvalabilityResponseRoomVO> avalabilityResponseRoomVOS = new ArrayList<>();
            for (OccupancyVO occupancyVO : occupancyVOS) {
                for (String occupancyRoomCode : occupancyVO.getRoomCodeList()) {
                    AvalabilityResponseRoomVO responseRoomVO = new AvalabilityResponseRoomVO();
                    responseRoomVO.setRoomCode(occupancyRoomCode);
                    responseRoomVO.setNoOfPaxPerRoom(Integer.parseInt(occupancyVO.getOccupancyCode()));
                    avalabilityResponseRoomVOS.add(responseRoomVO);
                }
            }
            /**
             * Build room code base Hotel->Room map
             */
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PICK_RETURN_DATE_FORMAT);
            Map<String, Room> roomCode_HotelMap = new HashMap<>();//RoomCode -> Room
            for (Hotel hotel : allHotelList) {
                if (hotel != null) {
                    for (AvalabilityResponseRoomVO responseRoomVO : avalabilityResponseRoomVOS) {
                        for (Room room : hotel.getRooms()) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(lk.dmg.common.util.Constants.REDIS_KEY_DATE);
                            try {
                                Date date = simpleDateFormat.parse( room.getDate());
                                roomCode_HotelMap.put(room.getMainHotelCode() + ":" + room.getSubHotelCode() + ":" + room.getRoomCode() + ":" + dateFormat.format(date), room);
                            } catch (ParseException e) {
                                log.error("254", "Redis data date format error", e);
                                throw new TalcacheException("STI75", "Redis data date format error :", Constants.INVALID_REQUEST_STRING,e);
                            }

                        }
                    }
                }
            }
            for (String mainhotelCode : availabilityResponseMainVOListMap.keySet()) {
                List<String> subHotelsList = availabilityResponseMainVOListMap.get(mainhotelCode).getListOfSubHotels();
                List<AvailabilityResponseSubVO> availabilityResponseSubVOList = new ArrayList<>();
                Map<String, String> loopedMainHotel = new HashMap<>();//prevent duplicate main hotel
                for (String subHotelCode : subHotelsList) {
                    List<Hotel> hotels = subhotelVsHotelsMap.get(subHotelCode);
                    if (hotels != null) {
                        for (Hotel hotel : hotels) {
                            if (hotel != null && !loopedMainHotel.containsKey(hotel.getSubHotelCode())) {
                                AvailabilityResponseSubVO availabilityResponseSubVO = new AvailabilityResponseSubVO();
                                availabilityResponseSubVO.setSubHotelCode(hotel.getSubHotelCode());
                                availabilityResponseSubVO.setAvalabilityResponseRoomVO(avalabilityResponseRoomVOS);//Room list equal for all sub hotels
                                loopedMainHotel.put(hotel.getSubHotelCode(), hotel.getSubHotelCode());
                                availabilityResponseSubVOList.add(availabilityResponseSubVO);
                            }
                        }

                    }
                }
                /**-------------------------------------------
                 * Add to main hotel base return data map
                 */
                AvailabilityResponseMainVO availabilityResponseMainVO = availabilityResponseMainVOListMap.get(mainhotelCode);
                if (availabilityResponseMainVO != null) {
                    availabilityResponseMainVO.setAvailabilityResponseSubVO(availabilityResponseSubVOList);
                }
            }
            /**
             * Fill room data
             */
            Date date1 = new Date();
            Map<String, String> loopedSubHotels = new HashMap<>();
            for (AvailabilityResponseMainVO availabilityResponseMainVO : availabilityResponseMainVOListMap.values()) { //Main Hotel Loop
                for (AvailabilityResponseSubVO availabilityResponseSubVO : availabilityResponseMainVO.getAvailabilityResponseSubVO()) {//Sub Hotel Loop
                    for (AvalabilityResponseRoomVO avalabilityResponseRoomVO : availabilityResponseSubVO.getAvalabilityResponseRoomVO()) {//Room Code loop
                        String mainHotelCode = availabilityResponseMainVO.getMainHotelCode();
                        String subHotelCode = availabilityResponseSubVO.getSubHotelCode();
                        String roomCode = avalabilityResponseRoomVO.getRoomCode();
                        Boolean isAvailable = true;
                        List<Room> roomListAllDaysAvail = new ArrayList<>();
                        for (Date date : requestedDates) {
                            String key = mainHotelCode + ":" + subHotelCode + ":" + roomCode + ":" + dateFormat.format(date);
                            if (roomCode_HotelMap.containsKey(key)) {//CHECK ALLOCATION IS AVAILABLE
                                roomListAllDaysAvail.add(roomCode_HotelMap.get(key));
                            } else {
                                isAvailable = false;
                            }
                        }
                        if (isAvailable) {
                            /*IS FREE NIGHT ELIGIBLE*/
                            Boolean isFreeNightEligible = false;
                            Boolean isFreeNightAvailable = false;
                            Boolean isEBDEligible = false;
                            Integer requestedNoOfDays = availabilityRequestVO.getNoOfDays();
                            Integer requestedOccupancy = avalabilityResponseRoomVO.getNoOfPaxPerRoom();
                            String productCode = roomListAllDaysAvail.get(0).getProductCode();
                            Integer adultNoOfFreeNight = -1;
                            Integer maximumMatchingDurationIndex = -1;
                            Integer maximumMatchingDurationValue = -1;
                            Integer maximumMatchingOccupancyIndex = -1;
                            /*Child Parameters*/
                            Boolean isChildFreeNightAvailable = false;
                            Integer childNoOfFreeNight = -1;
                            Integer childMaximumMatchingDurationIndex = -1;
                            Integer childMaximumMatchingDurationValue = -1;
                            Integer childMaximumMatchingOccupancyIndex = -1;
                            Boolean isMarkupEligible = true;
                            Set<String> agentKeyset= buildKeySetForAgentByDate(agentCode,requestedDates,productCode);
                            List<AgentFreeNightByDayVO> agentFreeNightByDayVOS=agentFreeNightByDateService.getByKeySet(lk.dmg.common.util.Constants.AGENT_FREE_NIGHT_BY_DAY_FILE,agentKeyset,AgentFreeNightByDayVO.class);
                            if (agentFreeNightByDayVOS!=null){
                                isFreeNightEligible=isFreeNightAccepted(agentCode,subHotelCode,agentFreeNightByDayVOS);
                            }
                            List<AgentEarlyBirdByDayVO> agentEarlyBirdByDayVOS=agentEarlyBirdByDateService.getByKeySet(lk.dmg.common.util.Constants.AGENT_EARLY_BIRD_BY_DAY_FILE,agentKeyset,AgentEarlyBirdByDayVO.class);
                            if (agentEarlyBirdByDayVOS!=null){
                                isEBDEligible=isEarlyBirdAccepted(agentCode,subHotelCode,agentEarlyBirdByDayVOS);
                            }


                            /*EBD*/
                            Double ebdDiscount = 0.00;
                            Integer ebdMaximumMatchingDurationValue = -1;
                            Boolean isEBDAvailable = false;
                            if (isEBDEligible) {
                                EarlyBirdVO earlyBirdVO = earlyBirdService.get(lk.dmg.common.util.Constants.EARLY_BIRD_FILE, subHotelCode, EarlyBirdVO.class);
                                if (earlyBirdVO != null) {
                                    List<Integer> relatedOccupancyIndexList = getRelatedOccupancyList(earlyBirdVO.getOccupancys(), requestedOccupancy);
                                    for (int relatedOccupancyIndex = 0; relatedOccupancyIndex < relatedOccupancyIndexList.size(); relatedOccupancyIndex++) {
                                        String relatedEarlyBirdType = earlyBirdVO.getEarlyBirdTypes().get(relatedOccupancyIndex);
                                        Date requestedFromDate = availabilityRequestVO.getFromDate();
                                        Date requestedToDate = availabilityRequestVO.getToDate();
                                        if (relatedEarlyBirdType.equalsIgnoreCase(Constants.EARLY_BIRD_TYPE_ARRIVAL_A)) {
                                            Integer ebdMaximumMatchingDurationIndex = isEBDAvailabiityArrival(earlyBirdVO, requestedFromDate, requestedToDate, relatedOccupancyIndex, requestedNoOfDays);
                                            String fromArrivalDateStr = earlyBirdVO.getFromArrivalDates().get(relatedOccupancyIndex);
                                            String toArrivalDateStr = earlyBirdVO.getToArrivalDate().get(relatedOccupancyIndex);
                                            String ebdDurations = earlyBirdVO.getUpToNoOfDays().get(relatedOccupancyIndex);
                                            String[] ebdDurationArray = ebdDurations.split("\\\\");
                                            if (ebdMaximumMatchingDurationIndex > -1) {
                                                isEBDAvailable = true;
                                                ebdMaximumMatchingDurationValue = Integer.parseInt(ebdDurationArray[ebdMaximumMatchingDurationIndex]);
                                                ebdDiscount = Double.parseDouble(earlyBirdVO.getDiscounts().get(relatedOccupancyIndex).split("\\\\")[ebdMaximumMatchingDurationIndex]);
                                                if (log.isDebugEnabled())
                                                    log.debug(adultNoOfFreeNight + " EBD A Type found! Base on Occupancy index: " + relatedOccupancyIndex + " Duration index: " + ebdMaximumMatchingDurationIndex + " Dicount % " + ebdDiscount);
                                            }

                                        } else if (relatedEarlyBirdType.equalsIgnoreCase(Constants.EARLY_BIRD_TYPE_BOOKING_B)) {
                                            Integer ebdMaximumMatchingDurationIndex = isEBDAvailabiityBooking(earlyBirdVO, requestedFromDate, requestedToDate, relatedOccupancyIndex, requestedNoOfDays);
                                            String ebdDurations = earlyBirdVO.getUpToNoOfDays().get(relatedOccupancyIndex);
                                            String[] ebdDurationArray = ebdDurations.split("\\\\");
                                            if (ebdMaximumMatchingDurationIndex > -1) {
                                                isEBDAvailable = true;
                                                ebdMaximumMatchingDurationValue = Integer.parseInt(ebdDurationArray[ebdMaximumMatchingDurationIndex]);
                                                ebdDiscount = Double.parseDouble(earlyBirdVO.getDiscounts().get(relatedOccupancyIndex).split("\\\\")[ebdMaximumMatchingDurationIndex]);
                                                if (log.isDebugEnabled())
                                                    log.debug(adultNoOfFreeNight + " EBD B Type found! Base on Occupancy index: " + relatedOccupancyIndex + " Duration index: " + ebdMaximumMatchingDurationIndex + " Dicount % " + ebdDiscount);
                                            }
                                        } else if (relatedEarlyBirdType.equalsIgnoreCase(Constants.EARLY_BIRD_TYPE_BOTH_F)) {
                                            Integer ebdMaximumMatchingDurationIndex = isEBDAvailabiityBooking(earlyBirdVO, requestedFromDate, requestedToDate, relatedOccupancyIndex, requestedNoOfDays);
                                            Integer ebdMaximumMatchingDurationIndex_A = isEBDAvailabiityArrival(earlyBirdVO, requestedFromDate, requestedToDate, relatedOccupancyIndex, requestedNoOfDays);
                                            String ebdDurations = earlyBirdVO.getUpToNoOfDays().get(relatedOccupancyIndex);
                                            String[] ebdDurationArray = ebdDurations.split("\\\\");
                                            if (ebdMaximumMatchingDurationIndex > -1 && ebdMaximumMatchingDurationIndex_A > -1) {
                                                isEBDAvailable = true;
                                                ebdMaximumMatchingDurationValue = Integer.parseInt(ebdDurationArray[ebdMaximumMatchingDurationIndex]);
                                                ebdDiscount = Double.parseDouble(earlyBirdVO.getDiscounts().get(relatedOccupancyIndex).split("\\\\")[ebdMaximumMatchingDurationIndex]);
                                                if (log.isDebugEnabled())
                                                    log.debug(adultNoOfFreeNight + " EBD F Type found! Base on Occupancy index: " + relatedOccupancyIndex + " Duration index: " + ebdMaximumMatchingDurationIndex + " Dicount % " + ebdDiscount);
                                            }
                                        }
                                    }

                                }

                            }
                            /*Free Night Calculation*/
                            if (isFreeNightEligible) {
                                FreeNightVO freeNightVO = freeNightService.get(lk.dmg.common.util.Constants.FREE_NIGHT_FILE, subHotelCode, FreeNightVO.class);
                                if (freeNightVO != null) {
                                    /*Get matching occupancies*/
                                    List<Integer> relatedOccupancyIndexList = getRelatedOccupancyList(freeNightVO.getOccupancy(), requestedOccupancy);
                                    /*ADULT FREE NIGHT*/
                                    for (int relatedOccupancyIndex = 0; relatedOccupancyIndex < relatedOccupancyIndexList.size(); relatedOccupancyIndex++) {
                                        String freeNightDurations = freeNightVO.getAdultDurations().get(relatedOccupancyIndex);
                                        maximumMatchingDurationIndex=isFreeNightAvailabilityBooking(freeNightDurations,availabilityRequestVO,freeNightVO,relatedOccupancyIndex);
                                        if (maximumMatchingDurationIndex>-1){
                                            isFreeNightAvailable = true;
                                            adultNoOfFreeNight = Integer.parseInt(freeNightVO.getAdultNoOfFreeNights().get(relatedOccupancyIndex).split("\\\\")[maximumMatchingDurationIndex]);
                                            maximumMatchingDurationValue = Integer.parseInt(freeNightVO.getAdultDurations().get(relatedOccupancyIndex).split("\\\\")[maximumMatchingDurationIndex]);
                                            adultNoOfFreeNight = Integer.parseInt(freeNightVO.getAdultNoOfFreeNights().get(relatedOccupancyIndex).split("\\\\")[maximumMatchingDurationIndex]);
                                            if (log.isDebugEnabled())
                                                log.debug(adultNoOfFreeNight + " Adult free night found! Base on Occupancy index: " + relatedOccupancyIndex + " Duration index: " + maximumMatchingDurationIndex);
                                        }

                                        /*CHILD FREE NIGHT*/
                                        String childFreeNightDurations = freeNightVO.getChildDurations().get(relatedOccupancyIndex);
                                        childMaximumMatchingDurationIndex=isFreeNightAvailabilityBooking(childFreeNightDurations,availabilityRequestVO,freeNightVO,relatedOccupancyIndex);
                                        if (childMaximumMatchingDurationIndex>-1){
                                            isChildFreeNightAvailable=true;
                                            childNoOfFreeNight = Integer.parseInt(freeNightVO.getChildNoOfFreeNights().get(relatedOccupancyIndex).split("\\\\")[childMaximumMatchingDurationIndex]);

                                        }
                                    }

                                } else {
                                    if (log.isDebugEnabled())
                                        log.debug("Free Night allowed but not found in " + lk.dmg.common.util.Constants.FREE_NIGHT_FILE + "ProductCode/HotelCode: " + productCode + "/" + subHotelCode);
                                }
                            }
                            AgentMarkupByDayVO markupByDayVO=null;
                            Integer noPaxPerRoom = NumberUtils.toInt(roomListAllDaysAvail.get(0).getNoOfRoomPax()); //No of pax equal for single room
                            String avlMsg = generateAvlMsg(roomListAllDaysAvail, availabilityRequestVO);
                            if (!availabilityResponseMainVOListFinal.containsKey(mainHotelCode)) {
                                AvailabilityResponseMainVO mainVO = new AvailabilityResponseMainVO();
                                mainVO.setMainHotelCode(mainHotelCode);
                                mainVO.setMaxPriceAdult("0.00");
                                mainVO.setMinPriceAdult("0.00");
                                mainVO.setListOfSubHotels(availabilityResponseMainVO.getListOfSubHotels());
                                ArrayList<AvailabilityResponseSubVO> subVOS = new ArrayList<>();
                                AvailabilityResponseSubVO subVO = new AvailabilityResponseSubVO();
                                subVO.setSubHotelCode(subHotelCode);
                                subVO.setCategoryCode(Constants.DEFAULT_CATEGORY_CODE);
                                subVO.setMaxPrice(Constants.PRICE_FORMAT);
                                subVO.setMinPrice(Constants.PRICE_FORMAT);
                                subVO.setRateplanCode(roomListAllDaysAvail.get(0).getRatePlanCode());
                                subVO.setRatePlanDesc(roomListAllDaysAvail.get(0).getRatePlanDesc());
                                subVO.setResortCode(roomListAllDaysAvail.get(0).getResortCode());
                                subVO.setUratePlanCode(roomListAllDaysAvail.get(0).getRatePlanCode());

                                ArrayList<AvalabilityResponseRoomVO> roomVOS = new ArrayList<>();
                                AvalabilityResponseRoomVO responseRoomVO = new AvalabilityResponseRoomVO();
                                responseRoomVO.setRoomCode(roomCode);
                                responseRoomVO.setNoOfPaxPerRoom(Integer.parseInt(roomListAllDaysAvail.get(0).getNoOfRoomPax()));
                                responseRoomVO.setAvailmessage(avlMsg);
                                responseRoomVO.setNoOfExtraBeds(roomListAllDaysAvail.get(0).getNoOfExtraBed());
                                responseRoomVO.setExtraBedIndicator(roomListAllDaysAvail.get(0).getNoOfExtraBed() != null && roomListAllDaysAvail.get(0).getNoOfExtraBed().length() > 0 ? "Y" : "");
                                /**
                                 * Add product
                                 */
                                List<AvailabilityResponseProductVO> availabilityResponseProductVOS = new ArrayList<>();
                                AvailabilityResponseProductVO availabilityResponseProductVO = new AvailabilityResponseProductVO();
                                Double adultPrice = 0.0;
                                Double childPrice = 0.0;
                                for (Room room : roomListAllDaysAvail) {
                                    adultPrice += NumberUtils.toDouble(room.getAdultPrice());
                                    Double markupPrecentage=0.00;
                                    if (isMarkupEligible) {
                                        String agentMarkupKey = buildAgentMarkupKey(productCode, room.getDate());
                                         markupByDayVO = agentMarkupService.get(lk.dmg.common.util.Constants.AGENT_MARKUP_BY_DATE_FILE, agentMarkupKey, AgentMarkupByDayVO.class);
                                        if (markupByDayVO != null) {
                                             markupPrecentage = calculateMarkUpByDay(markupByDayVO, agentCode,availabilityRequestVO);
                                            Double markupAmount = (markupPrecentage / 100) * NumberUtils.toDouble(room.getAdultPrice());
                                            adultPrice = adultPrice + markupAmount;
                                        }
                                        if (log.isDebugEnabled())
                                            log.debug("Markup added for AgentCode: " + agentCode + " HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Markup% +" + markupPrecentage + " Adult Price: " + adultPrice);
                                    }
                                    if (NumberUtils.isNumber(room.getChildPrice())) {
                                        childPrice += NumberUtils.toDouble(room.getChildPrice());
                                        if (isMarkupEligible) {
                                            Double markupAmount = (markupPrecentage / 100) * NumberUtils.toDouble(room.getChildPrice());
                                            childPrice = childPrice + markupAmount;
                                            if (log.isDebugEnabled())
                                                log.debug("Markup added for AgentCode: " + agentCode + " HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Markup% +" + markupPrecentage + " Child Price: " + childPrice);
                                        }
                                    }
                                }

                                /*FREE NIGHT DEDUCTION PRICES*/

                                /*Free Night Implementation*/
                                Double adultFreeNightDeductionPrice = 0.00;
                                Double adultFreeNightAvaragePrice = 0.00;
                                if (isFreeNightAvailable) {
                                    adultFreeNightAvaragePrice = calculateFreeNightAvaragePrice(adultPrice, requestedNoOfDays);
                                    adultFreeNightDeductionPrice = calculateFreeNightDeductionPrice(adultFreeNightAvaragePrice, adultNoOfFreeNight);
                                }
                                Double childFreeNightDeductionPrice = 0.00;
                                Double childFreeNightAvaragePrice = 0.00;
                                if (isChildFreeNightAvailable) {
                                    childFreeNightAvaragePrice = calculateFreeNightAvaragePrice(childPrice, requestedNoOfDays);
                                    childFreeNightDeductionPrice = calculateFreeNightDeductionPrice(childFreeNightAvaragePrice, childNoOfFreeNight);
                                }

                                /*EBD DEDUCTION PRICE*/
                                Double ebdDeductionPrice = 0.0;
                                if (isEBDAvailable) {
                                    ebdDeductionPrice = (ebdDiscount / 100) * adultPrice;
                                    availabilityResponseProductVO=setEbdValues(availabilityResponseProductVO,ebdDiscount,ebdDeductionPrice);
                                }
                                if (isFreeNightAvailable) {
                                    availabilityResponseProductVO = setFreeNightInfo(availabilityResponseProductVO, adultNoOfFreeNight, maximumMatchingDurationValue, adultFreeNightAvaragePrice, adultNoOfFreeNight, childFreeNightAvaragePrice);
                                }

                                Integer paxPerOccupancy = getPaxRoomByOccupancy(availabilityRequestVO, noPaxPerRoom);
                                availabilityResponseProductVO.setAdultprice(priceFormatter(adultPrice - adultFreeNightDeductionPrice - ebdDeductionPrice));
                                Double TotalAdultPrice = (adultPrice - adultFreeNightDeductionPrice - ebdDeductionPrice) * paxPerOccupancy * noPaxPerRoom;
                                if (log.isDebugEnabled())
                                    log.debug(" Total adult price Calculation Based On HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Adult Price: " + adultPrice + " Adult FreeNight Deduction Price: " + adultFreeNightDeductionPrice + " ebdDeductionPrice : " + ebdDeductionPrice);
                                availabilityResponseProductVO.setProductCode(roomListAllDaysAvail.get(0).getProductCode());
                                availabilityResponseProductVO.setPriceCurency(roomListAllDaysAvail.get(0).getCurrencyCode());
                                availabilityResponseProductVO.setTotaladultprice(priceFormatter(TotalAdultPrice));
                                availabilityResponseProductVO.setAdultpricemessage(generateAdultPriceMsg(roomListAllDaysAvail, markupByDayVO, agentCode,availabilityRequestVO));
                                //set child price
                                if (checkExtraBedIsRequested(availabilityRequestVO)) {//extra bed requested
                                    availabilityResponseProductVO.setChildprice(String.valueOf(childPrice));
                                    availabilityResponseProductVO.setTotalchildprice(String.valueOf(childPrice - childFreeNightDeductionPrice - ebdDeductionPrice));
                                    if (log.isDebugEnabled())
                                        log.debug(" Total child price Calculation Based On HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Child Price: " + childPrice + " Child FreeNight Deduction Price: " + childFreeNightDeductionPrice + " ebdDeductionPrice : " + ebdDeductionPrice);
                                } else {
                                    availabilityResponseProductVO.setChildprice("0.00");
                                    availabilityResponseProductVO.setTotalchildprice("");
                                }

                                if (checkExtraBedIsRequested(availabilityRequestVO)) {//extra bed requested
                                    availabilityResponseProductVO.setChPriceMessage(generateChildPriceMsg(roomListAllDaysAvail, markupByDayVO,agentCode,availabilityRequestVO));
                                } else {
                                    availabilityResponseProductVO.setChPriceMessage("");
                                }
                                availabilityResponseProductVOS.add(availabilityResponseProductVO);
                                responseRoomVO.setAvailabilityResponseProductVO(availabilityResponseProductVOS);
                                roomVOS.add(responseRoomVO);
                                subVO.setAvalabilityResponseRoomVO(roomVOS);
                                subVOS.add(subVO);
                                mainVO.setAvailabilityResponseSubVO(subVOS);
                                availabilityResponseMainVOListFinal.put(mainHotelCode, mainVO);
                                loopedSubHotels.put(subHotelCode, subHotelCode);
                            } else { /*If exiting main hotel*/
                                if (loopedSubHotels.containsKey(subHotelCode)) {
                                    AvailabilityResponseMainVO mainVO = availabilityResponseMainVOListFinal.get(mainHotelCode);
                                    List<AvailabilityResponseSubVO> subVOS = mainVO.getAvailabilityResponseSubVO();
                                    for (AvailabilityResponseSubVO subVO : subVOS) {
                                        if (subVO.getSubHotelCode().equalsIgnoreCase(subHotelCode)) {
                                            List<AvalabilityResponseRoomVO> responseRoomVOs = subVO.getAvalabilityResponseRoomVO();
                                            AvalabilityResponseRoomVO roomVO = new AvalabilityResponseRoomVO();
                                            roomVO.setRoomCode(roomCode);
                                            roomVO.setNoOfPaxPerRoom(Integer.parseInt(roomListAllDaysAvail.get(0).getNoOfRoomPax()));
                                            roomVO.setAvailmessage(avlMsg);
                                            roomVO.setNoOfExtraBeds(roomListAllDaysAvail.get(0).getNoOfExtraBed());
                                            roomVO.setExtraBedIndicator(roomListAllDaysAvail.get(0).getNoOfExtraBed() != null && roomListAllDaysAvail.get(0).getNoOfExtraBed().length() > 0 ? "Y" : "");
                                            /**
                                             * Add product
                                             */
                                            List<AvailabilityResponseProductVO> availabilityResponseProductVOS = new ArrayList<>();
                                            AvailabilityResponseProductVO availabilityResponseProductVO = new AvailabilityResponseProductVO();
                                            Double adultPrice = 0.0;
                                            Double childPrice = 0.0;
                                            for (Room room : roomListAllDaysAvail) {
                                                adultPrice += NumberUtils.toDouble(room.getAdultPrice());
                                                Double markupPrecentage=0.00;
                                                if (isMarkupEligible) {
                                                    String agentMarkupKey = buildAgentMarkupKey(productCode, room.getDate());
                                                    markupByDayVO = agentMarkupService.get(lk.dmg.common.util.Constants.AGENT_MARKUP_BY_DATE_FILE, agentMarkupKey, AgentMarkupByDayVO.class);
                                                    if (markupByDayVO != null) {
                                                        markupPrecentage = calculateMarkUpByDay(markupByDayVO, agentCode,availabilityRequestVO);
                                                        Double markupAmount = (markupPrecentage / 100) * NumberUtils.toDouble(room.getAdultPrice());
                                                        adultPrice = adultPrice + markupAmount;
                                                    }
                                                    if (log.isDebugEnabled())
                                                        log.debug("Markup added for AgentCode: " + agentCode + " HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Markup% +" + markupPrecentage + " Adult Price: " + adultPrice);
                                                }
                                                if (NumberUtils.isNumber(room.getChildPrice())) {
                                                    childPrice += NumberUtils.toDouble(room.getChildPrice());
                                                    if (isMarkupEligible) {
                                                        Double markupAmount = (markupPrecentage / 100) * NumberUtils.toDouble(room.getChildPrice());
                                                        childPrice = childPrice + markupAmount;
                                                        if (log.isDebugEnabled())
                                                            log.debug("Markup added for AgentCode: " + agentCode + " HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Markup% +" + markupPrecentage + " Child Price: " + childPrice);
                                                    }
                                                }
                                            }
                                            /*Free Night Implementation*/
                                            Double adultFreeNightDeductionPrice = 0.00;
                                            Double adultFreeNightAvaragePrice = 0.00;
                                            if (isFreeNightAvailable) {
                                                adultFreeNightAvaragePrice = calculateFreeNightAvaragePrice(adultPrice, requestedNoOfDays);
                                                adultFreeNightDeductionPrice = calculateFreeNightDeductionPrice(adultFreeNightAvaragePrice, adultNoOfFreeNight);
                                            }
                                            Double childFreeNightDeductionPrice = 0.00;
                                            Double childFreeNightAvaragePrice = 0.00;
                                            if (isChildFreeNightAvailable) {
                                                childFreeNightAvaragePrice = calculateFreeNightAvaragePrice(childPrice, requestedNoOfDays);
                                                childFreeNightDeductionPrice = calculateFreeNightDeductionPrice(childFreeNightAvaragePrice, childNoOfFreeNight);
                                            }
                                            /*EBD PRICE*/
                                            /*EBD DEDUCTION PRICE*/
                                            Double ebdDeductionPrice = 0.0;
                                            if (isEBDAvailable) {
                                                ebdDeductionPrice = (ebdDiscount / 100) * adultPrice;
                                                availabilityResponseProductVO=setEbdValues(availabilityResponseProductVO,ebdDiscount,ebdDeductionPrice);
                                            }

                                            if (isFreeNightAvailable) {
                                                availabilityResponseProductVO = setFreeNightInfo(availabilityResponseProductVO, adultNoOfFreeNight, maximumMatchingDurationValue, adultFreeNightAvaragePrice, adultNoOfFreeNight, childFreeNightAvaragePrice);
                                            }


                                            Integer paxPerOccupancy = getPaxRoomByOccupancy(availabilityRequestVO, noPaxPerRoom);
                                            availabilityResponseProductVO.setAdultprice(priceFormatter(adultPrice - adultFreeNightDeductionPrice - ebdDeductionPrice));
                                            Double TotalAdultPrice = (adultPrice - adultFreeNightDeductionPrice - ebdDeductionPrice) * paxPerOccupancy * noPaxPerRoom;
                                            if (log.isDebugEnabled())
                                                log.debug(" Total adult price Calculation Based On HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Adult Price: " + adultPrice + " Adult FreeNight Deduction Price: " + adultFreeNightDeductionPrice + " ebdDeductionPrice : " + ebdDeductionPrice);


                                            availabilityResponseProductVO.setProductCode(roomListAllDaysAvail.get(0).getProductCode());
                                            availabilityResponseProductVO.setPriceCurency(roomListAllDaysAvail.get(0).getCurrencyCode());
                                            availabilityResponseProductVO.setTotaladultprice(priceFormatter(TotalAdultPrice));
                                            availabilityResponseProductVO.setAdultpricemessage(generateAdultPriceMsg(roomListAllDaysAvail, markupByDayVO, agentCode,availabilityRequestVO));
                                            if (checkExtraBedIsRequested(availabilityRequestVO)) {//extra bed requested
                                                availabilityResponseProductVO.setChPriceMessage(generateChildPriceMsg(roomListAllDaysAvail, markupByDayVO, agentCode,availabilityRequestVO));
                                            } else {
                                                availabilityResponseProductVO.setChPriceMessage("");
                                            }
                                            //set child price
                                            if (checkExtraBedIsRequested(availabilityRequestVO)) {//extra bed requested
                                                availabilityResponseProductVO.setChildprice(String.valueOf(childPrice - childFreeNightDeductionPrice - ebdDeductionPrice));
                                                availabilityResponseProductVO.setTotalchildprice(String.valueOf(childPrice));
                                                if (log.isDebugEnabled())
                                                    log.debug(" Total child price Calculation Based On HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Child Price: " + childPrice + " Child FreeNight Deduction Price: " + childFreeNightDeductionPrice + " ebdDeductionPrice : " + ebdDeductionPrice);

                                            } else {
                                                availabilityResponseProductVO.setChildprice("0.00");
                                                availabilityResponseProductVO.setTotalchildprice("");
                                            }
                                            availabilityResponseProductVOS.add(availabilityResponseProductVO);
                                            roomVO.setAvailabilityResponseProductVO(availabilityResponseProductVOS);
                                            responseRoomVOs.add(roomVO);
                                            subVO.setAvalabilityResponseRoomVO(responseRoomVOs);
                                        }
                                    }
                                } else {//IF SUBHOTEL FIRST TIME
                                    AvailabilityResponseMainVO mainVO = availabilityResponseMainVOListFinal.get(mainHotelCode);
                                    List<AvailabilityResponseSubVO> subVOS = mainVO.getAvailabilityResponseSubVO();
                                    AvailabilityResponseSubVO subVO = new AvailabilityResponseSubVO();
                                    subVO.setSubHotelCode(subHotelCode);
                                    subVO.setSubHotelCode(subHotelCode);
                                    subVO.setCategoryCode(Constants.DEFAULT_CATEGORY_CODE);
                                    subVO.setMaxPrice(Constants.PRICE_FORMAT);
                                    subVO.setMinPrice(Constants.PRICE_FORMAT);
                                    subVO.setRateplanCode(roomListAllDaysAvail.get(0).getRatePlanCode());
                                    subVO.setRatePlanDesc(roomListAllDaysAvail.get(0).getRatePlanDesc());
                                    subVO.setResortCode(roomListAllDaysAvail.get(0).getResortCode());
                                    subVO.setUratePlanCode(roomListAllDaysAvail.get(0).getRatePlanCode());

                                    ArrayList<AvalabilityResponseRoomVO> roomVOS = new ArrayList<>();
                                    AvalabilityResponseRoomVO responseRoomVO = new AvalabilityResponseRoomVO();
                                    responseRoomVO.setRoomCode(roomCode);
                                    responseRoomVO.setNoOfPaxPerRoom(Integer.parseInt(roomListAllDaysAvail.get(0).getNoOfRoomPax()));
                                    responseRoomVO.setAvailmessage(avlMsg);
                                    responseRoomVO.setNoOfExtraBeds(roomListAllDaysAvail.get(0).getNoOfExtraBed());
                                    responseRoomVO.setExtraBedIndicator(roomListAllDaysAvail.get(0).getNoOfExtraBed() != null && roomListAllDaysAvail.get(0).getNoOfExtraBed().length() > 0 ? "Y" : "");
                                    /**
                                     * Add product
                                     */
                                    List<AvailabilityResponseProductVO> availabilityResponseProductVOS = responseRoomVO.getAvailabilityResponseProductVO();
                                    AvailabilityResponseProductVO availabilityResponseProductVO = new AvailabilityResponseProductVO();
                                    Double adultPrice = 0.0;
                                    Double childPrice = 0.0;

                                    for (Room room : roomListAllDaysAvail) {
                                        adultPrice += NumberUtils.toDouble(room.getAdultPrice());
                                        Double markupPrecentage=0.00;
                                        if (isMarkupEligible) {
                                            String agentMarkupKey = buildAgentMarkupKey(productCode, room.getDate());
                                            markupByDayVO = agentMarkupService.get(lk.dmg.common.util.Constants.AGENT_MARKUP_BY_DATE_FILE, agentMarkupKey, AgentMarkupByDayVO.class);
                                            if (markupByDayVO != null) {
                                                markupPrecentage = calculateMarkUpByDay(markupByDayVO, agentCode,availabilityRequestVO);
                                                Double markupAmount = (markupPrecentage / 100) * NumberUtils.toDouble(room.getAdultPrice());
                                                adultPrice = adultPrice + markupAmount;
                                            }
                                            if (log.isDebugEnabled())
                                                log.debug("Markup added for AgentCode: " + agentCode + " HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Markup% +" + markupPrecentage + " Adult Price: " + adultPrice);
                                        }
                                        if (NumberUtils.isNumber(room.getChildPrice())) {
                                            childPrice += NumberUtils.toDouble(room.getChildPrice());
                                            if (isMarkupEligible) {
                                                Double markupAmount = (markupPrecentage / 100) * NumberUtils.toDouble(room.getChildPrice());
                                                childPrice = childPrice + markupAmount;
                                                if (log.isDebugEnabled())
                                                    log.debug("Markup added for AgentCode: " + agentCode + " HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Markup% +" + markupPrecentage + " Child Price: " + childPrice);
                                            }
                                        }
                                    }
                                    /*Free Night Implementation*/
                                    Double adultFreeNightDeductionPrice = 0.00;
                                    Double adultFreeNightAvaragePrice = 0.00;
                                    if (isFreeNightAvailable) {
                                        adultFreeNightAvaragePrice = calculateFreeNightAvaragePrice(adultPrice, requestedNoOfDays);
                                        adultFreeNightDeductionPrice = calculateFreeNightDeductionPrice(adultFreeNightAvaragePrice, adultNoOfFreeNight);

                                    }
                                    Double childFreeNightDeductionPrice = 0.00;
                                    Double childFreeNightAvaragePrice = 0.00;
                                    if (isFreeNightAvailable) {
                                        childFreeNightAvaragePrice = calculateFreeNightAvaragePrice(childPrice, requestedNoOfDays);
                                        childFreeNightDeductionPrice = calculateFreeNightDeductionPrice(childFreeNightAvaragePrice, childNoOfFreeNight);

                                    }
                                    //EBD PRICE
                                    //EBD DEDUCTION PRICE
                                    Double ebdDeductionPrice = 0.0;
                                    if (isEBDAvailable) {
                                        ebdDeductionPrice = (ebdDiscount / 100) * adultPrice;
                                        availabilityResponseProductVO=setEbdValues(availabilityResponseProductVO,ebdDiscount,ebdDeductionPrice);
                                    }

                                    if (isFreeNightAvailable) {
                                        availabilityResponseProductVO = setFreeNightInfo(availabilityResponseProductVO, adultNoOfFreeNight, maximumMatchingDurationValue, adultFreeNightAvaragePrice, adultNoOfFreeNight, childFreeNightAvaragePrice);

                                    }

                                    Integer paxPerOccupancy = getPaxRoomByOccupancy(availabilityRequestVO, noPaxPerRoom);
                                    availabilityResponseProductVO.setAdultprice(priceFormatter(adultPrice - adultFreeNightDeductionPrice - ebdDeductionPrice));
                                    Double TotalAdultPrice = (adultPrice - adultFreeNightDeductionPrice - ebdDeductionPrice) * paxPerOccupancy * noPaxPerRoom;
                                    if (log.isDebugEnabled())
                                        log.debug(" Total adult price Calculation Based On HotelCode: " + subHotelCode + " RoomCode: " + roomCode + " Adult Price: " + adultPrice + " Adult FreeNight Deduction Price: " + adultFreeNightDeductionPrice + " ebdDeductionPrice : " + ebdDeductionPrice);


                                    availabilityResponseProductVO.setProductCode(roomListAllDaysAvail.get(0).getProductCode());
                                    availabilityResponseProductVO.setPriceCurency(roomListAllDaysAvail.get(0).getCurrencyCode());
                                    availabilityResponseProductVO.setTotaladultprice(priceFormatter(TotalAdultPrice));
                                    availabilityResponseProductVO.setAdultpricemessage(generateAdultPriceMsg(roomListAllDaysAvail, markupByDayVO, agentCode,availabilityRequestVO));
                                    if (checkExtraBedIsRequested(availabilityRequestVO)) {//extra bed requested
                                        availabilityResponseProductVO.setChPriceMessage(generateChildPriceMsg(roomListAllDaysAvail, markupByDayVO, agentCode,availabilityRequestVO));
                                    } else {
                                        availabilityResponseProductVO.setChPriceMessage("");
                                    }
                                    //set child price
                                    if (checkExtraBedIsRequested(availabilityRequestVO)) {//extra bed requested
                                        availabilityResponseProductVO.setChildprice(String.valueOf(childPrice - childFreeNightDeductionPrice - ebdDeductionPrice));
                                        availabilityResponseProductVO.setTotalchildprice(String.valueOf(childPrice));
                                    } else {
                                        availabilityResponseProductVO.setChildprice("0.00");
                                        availabilityResponseProductVO.setTotalchildprice("");
                                    }
                                    availabilityResponseProductVOS.add(availabilityResponseProductVO);
                                    responseRoomVO.setAvailabilityResponseProductVO(availabilityResponseProductVOS);

                                    roomVOS.add(responseRoomVO);
                                    subVO.setAvalabilityResponseRoomVO(roomVOS);
                                    subVOS.add(subVO);
                                    mainVO.setAvailabilityResponseSubVO(subVOS);
                                    availabilityResponseMainVOListFinal.put(mainHotelCode, mainVO);
                                    loopedSubHotels.put(subHotelCode, subHotelCode);
                                }

                            }

                        }
                    }
                }
            }
            // room details
            if (log.isDebugEnabled()) {
                Date endDate = new Date();
                long diff = endDate.getTime() - date1.getTime();
                log.debug("Time for processing JAVA :" + diff);
            }
        }
        //Build Result PICK String
        List<AvailabilityResponseMainVO> resultList = new ArrayList<>(availabilityResponseMainVOListFinal.values());
        return resultList;
    }

    private Integer isFreeNightAvailabilityBooking(String freeNightDurations, AvailabilityRequestVO availabilityRequestVO, FreeNightVO freeNightVO, int relatedOccupancyIndex) {
        Integer maximumMatchingDurationValue=0;
        Integer maximumMatchingDurationIndex=-1;
        String[] freeNightArray = freeNightDurations.split("\\\\");
        for (int i = 0; i < freeNightArray.length; i++) {
            Integer freeNightDuration = Integer.parseInt(freeNightArray[i]);
            Integer requestedNoOfDays=availabilityRequestVO.getNoOfDays();
            if ((requestedNoOfDays >= freeNightDuration) && (maximumMatchingDurationValue < freeNightDuration)) {
                Date requestedFromDate = availabilityRequestVO.getFromDate();
                Date requestedToDate = availabilityRequestVO.getToDate();
                try {
                    String freeNightFromDateStr = freeNightVO.getFromDates().get(relatedOccupancyIndex);
                    Date freeNightFromDate = convertPickDateTypeToUtilDate(freeNightFromDateStr, Constants.PICK_RETURN_DATE_FORMAT);
                    String freeNightToDateStr = freeNightVO.getToDates().get(relatedOccupancyIndex);
                    Date freeNightToDate = convertPickDateTypeToUtilDate(freeNightToDateStr, Constants.PICK_RETURN_DATE_FORMAT);
                    if (freeNightFromDate.compareTo(requestedToDate) <= 0 && dateDiff(freeNightFromDate,requestedFromDate)>=0) {
                        if (freeNightToDate.compareTo(requestedFromDate) >= 0) {
                            if (dateDiff(freeNightFromDate, requestedToDate) >= freeNightDuration) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(requestedFromDate);
                                calendar.set(Calendar.DATE, freeNightDuration);
                                Date calDate = calendar.getTime();/*Requested From Date + Free Night Duration <=Free Night To Date*/
                                if (calDate.compareTo(freeNightToDate) <= 0) {
                                    maximumMatchingDurationValue = freeNightDuration;
                                    maximumMatchingDurationIndex = i;
                                   }
                            }
                        }
                    }
                } catch (ParseException e) {
                    log.error("254", "Adult Free Night date format error", e);
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("No of Adult free night not matching ");
            }
        }
        return maximumMatchingDurationIndex;
    }

    /**
     *
     * @param productCode
     * @param date
     * @return
     */
    private String buildAgentMarkupKey(String productCode, String date) {
      /*  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(lk.dmg.common.util.Constants.REDIS_KEY_DATE);
        String keyDate = simpleDateFormat.format(date);*/
        String key=productCode+ lk.dmg.common.util.Constants.REDIS_KEY_SEPARATOR+date;
        return key;
    }


    private Boolean isEarlyBirdAccepted(String agentCode, String subHotelCode, List<AgentEarlyBirdByDayVO> agentEarlyBirdByDayVOS) {
        for (AgentEarlyBirdByDayVO birdByDayVO:agentEarlyBirdByDayVOS){
            if (birdByDayVO!=null){
                List<String> stringList= birdByDayVO.getEbdEnableHotels();
                stringList.stream().anyMatch(streamStr->streamStr.equals(subHotelCode));
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param agentCode
     * @param subHotelCode
     * @param agentFreeNightByDayVOS
     * @return
     */
    private boolean isFreeNightAccepted(String agentCode, String subHotelCode, List<AgentFreeNightByDayVO> agentFreeNightByDayVOS) {
        for (AgentFreeNightByDayVO nightByDayVO:agentFreeNightByDayVOS){
            if (nightByDayVO!=null){
               List<String> stringList= nightByDayVO.getFreeNightEnableHotels();
               stringList.stream().anyMatch(streamStr->streamStr.equals(subHotelCode));
               return true;
            }
        }
        return false;
    }

    /**
     *
     * @param agentCode
     * @param requestedDates
     * @param productCode
     * @return
     */
    private Set<String> buildKeySetForAgentByDate(String agentCode, List<Date> requestedDates, String productCode) {
        Set<String> keyset = new HashSet<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(lk.dmg.common.util.Constants.REDIS_KEY_DATE);
        for (Date date : requestedDates) {
            String keyDate = simpleDateFormat.format(date);
            String key = productCode + lk.dmg.common.util.Constants.REDIS_KEY_SEPARATOR + agentCode+ lk.dmg.common.util.Constants.REDIS_KEY_SEPARATOR+keyDate;
            keyset.add(key);
        }
        return keyset;
    }

    /**
     *
     * @param availabilityResponseProductVO
     * @param ebdDiscount
     * @param ebdDeductionPrice
     * @return
     */
    private AvailabilityResponseProductVO setEbdValues(AvailabilityResponseProductVO availabilityResponseProductVO, Double ebdDiscount, Double ebdDeductionPrice) {
        availabilityResponseProductVO.setEarlyBirdDiscount(priceFormatter(ebdDiscount));
        availabilityResponseProductVO.setEarlyBirdDiscAmt(priceFormatter(ebdDeductionPrice));
        return availabilityResponseProductVO;
    }

    private AvailabilityResponseProductVO setFreeNightInfo(AvailabilityResponseProductVO availabilityResponseProductVO, Integer adultNoOfFreeNight, Integer maximumMatchingDurationValue, Double adultFreeNightAvaragePrice, Integer adultNoOfFreeNight1, Double childFreeNightAvaragePrice) {
        availabilityResponseProductVO.setNoFreeNightsAd(String.valueOf(adultNoOfFreeNight));
        availabilityResponseProductVO.setBasedOnAdDuration(String.valueOf(maximumMatchingDurationValue));
        availabilityResponseProductVO.setPrcAdFreeNght(String.valueOf(adultFreeNightAvaragePrice*adultNoOfFreeNight));
        availabilityResponseProductVO.setNoFreeNightsAd(String.valueOf(adultNoOfFreeNight));
        availabilityResponseProductVO.setBasedOnAdDuration(String.valueOf(maximumMatchingDurationValue));
        availabilityResponseProductVO.setPrcChFreeNght(String.valueOf(childFreeNightAvaragePrice*adultNoOfFreeNight));
        return availabilityResponseProductVO;
    }

    /**
     * @param agentMarkupByDayVO
     * @param agentCode
     * @return
     */
    private Double calculateMarkUpByDay(AgentMarkupByDayVO agentMarkupByDayVO, String agentCode, AvailabilityRequestVO availabilityRequestVO) {
        Double markupRate = 0.0;
        Date fromDate=availabilityRequestVO.getFromDate();
        if (agentMarkupByDayVO!=null) {
            List<String> includedAgentList = agentMarkupByDayVO.getAgentIncluded();
            int index = 0;
            for (String agent : includedAgentList) {
                if (agentCode.equalsIgnoreCase(agent)) {
                    String markupFromDate=agentMarkupByDayVO.getMarkupFromDates().get(index);
                    String markupToDate=agentMarkupByDayVO.getMarkupToDate().get(index);
                    try {
                        Date fromMarkupDate= convertPickDateTypeToUtilDate(markupFromDate, Constants.PICK_RETURN_DATE_FORMAT);
                        Date toMarkupDate = convertPickDateTypeToUtilDate(markupToDate, Constants.PICK_RETURN_DATE_FORMAT);
                        if (dateDiff(fromMarkupDate,fromDate)>=0 && dateDiff(toMarkupDate,fromDate)<=0){
                            String[] markupRates = agentMarkupByDayVO.getMarkup().get(index).split("\\\\");
                            markupRate = Double.parseDouble(markupRates[index]);
                        }

                    } catch (ParseException e) {
                        log.warn("EBD date formats not valid: " + markupFromDate + " OR " + markupToDate);
                        if (log.isDebugEnabled())
                            log.debug("EBD date formats not valid: " + markupFromDate + " OR " + markupToDate);
                    }


                }
                index++;
            }
        }
        return markupRate;

    }

    private Integer isEBDAvailabiityArrival(EarlyBirdVO earlyBirdVO, Date requestedFromDate, Date requestedToDate, int relatedOccupancyIndex, Integer requestedNoOfDays) {
        Integer ebdMaximumMatchingDurationValue = -1;
        Integer ebdMaximumMatchingDurationIndex = -1;
        String fromArrivalDateStr = earlyBirdVO.getFromArrivalDates().get(relatedOccupancyIndex);
        String toArrivalDateStr = earlyBirdVO.getToArrivalDate().get(relatedOccupancyIndex);
        String ebdDurations = earlyBirdVO.getUpToNoOfDays().get(relatedOccupancyIndex);
        String[] ebdDurationArray = ebdDurations.split("\\\\");
        for (int i = 0; i < ebdDurationArray.length; i++) {
            Integer edbDuration = Integer.parseInt(ebdDurationArray[i]);
            if (ebdMaximumMatchingDurationValue < edbDuration) {
                try {
                    Date fromArrivalDate = convertPickDateTypeToUtilDate(fromArrivalDateStr, Constants.PICK_RETURN_DATE_FORMAT);
                    Date toArrivalDate = convertPickDateTypeToUtilDate(toArrivalDateStr, Constants.PICK_RETURN_DATE_FORMAT);
                    if (dateDiff(fromArrivalDate,requestedFromDate)>=0 && dateDiff(requestedFromDate,toArrivalDate)>=0){
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fromArrivalDate);
                        calendar.set(Calendar.DATE, -edbDuration);
                        Date calDate = calendar.getTime();
                        if (dateDiff(new Date(),calDate)>=0){
                            ebdMaximumMatchingDurationValue = edbDuration;
                            ebdMaximumMatchingDurationIndex=i;
                        }
                    }

//                    /*if (dateDiff(arrivalDate, fromArrivalDate) >= 0) {
//                        ebdMaximumMatchingDurationValue = edbDuration;
//                        ebdMaximumMatchingDurationIndex = i;
//                    }*/
//                  if (dateDiff(requestedFromDate, fromArrivalDate) <= 0 && dateDiff(fromArrivalDate,requestedFromDate)>=0) {
//                        if (toArrivalDate.compareTo(requestedFromDate) >= 0) {
//                            if (dateDiff(fromArrivalDate, requestedToDate) >= edbDuration) {
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.setTime(requestedFromDate);
//                                calendar.set(Calendar.DATE, edbDuration);
//                                Date calDate = calendar.getTime();//Requested From Date + EBD Duration <=EBD Arrival To Date
//                                if (calDate.compareTo(toArrivalDate) <= 0) {
//
//                                    ebdMaximumMatchingDurationValue = edbDuration;
//                                    ebdMaximumMatchingDurationIndex=i;
//                                }
//                            }
//                        }
//                    }
                } catch (ParseException e) {
                    log.warn("EBD date formats not valid: " + fromArrivalDateStr + " OR " + toArrivalDateStr);
                    if (log.isDebugEnabled())
                        log.debug("EBD date formats not valid: " + fromArrivalDateStr + " OR " + toArrivalDateStr);
                }
            }
        }
        return ebdMaximumMatchingDurationIndex;
    }

    private Integer isEBDAvailabiityBooking(EarlyBirdVO earlyBirdVO, Date requestedFromDate, Date requestedToDate, int relatedOccupancyIndex, Integer requestedNoOfDays) {
        Integer ebdMaximumMatchingDurationValue = -1;
        Integer ebdMaximumMatchingDurationIndex = -1;
        String fromBookingDateStr = earlyBirdVO.getFromBookingDates().get(relatedOccupancyIndex);
        String toBookingDateStr = earlyBirdVO.getToBookingDates().get(relatedOccupancyIndex);
        String ebdDurations = earlyBirdVO.getUpToNoOfDays().get(relatedOccupancyIndex);
        String[] ebdDurationArray = ebdDurations.split("\\\\");
        for (int i = 0; i < ebdDurationArray.length; i++) {
            Integer edbDuration = Integer.parseInt(ebdDurationArray[i]);
            if ((requestedNoOfDays <= edbDuration) && (ebdMaximumMatchingDurationValue < edbDuration)) {
                try {
                    Date fromBookingDate = convertPickDateTypeToUtilDate(fromBookingDateStr, Constants.PICK_RETURN_DATE_FORMAT);
                    Date toBookingDate = convertPickDateTypeToUtilDate(toBookingDateStr, Constants.PICK_RETURN_DATE_FORMAT);
                    if (dateDiff(requestedFromDate, fromBookingDate) <= 0) {
                        if (toBookingDate.compareTo(requestedFromDate) >= 0) {
                            if (dateDiff(fromBookingDate, requestedToDate) >= edbDuration) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(requestedFromDate);
                                calendar.set(Calendar.DATE, edbDuration);
                                Date calDate = calendar.getTime();/*Requested From Date + EBD Duration <=EBD Arrival To Date*/
                                if (calDate.compareTo(toBookingDate) <= 0) {
                                    ebdMaximumMatchingDurationValue = edbDuration;
                                    ebdMaximumMatchingDurationIndex = i;

                                }
                            }
                        }

                    }
                } catch (ParseException e) {
                    if (log.isDebugEnabled())
                        log.debug("EBD date formats not valid: " + fromBookingDateStr + " OR " + toBookingDateStr);
                }
            }
        }
        return ebdMaximumMatchingDurationIndex;
    }

    private Double calculateFreeNightAvaragePrice(Double price, Integer requestedNoOfDays) {
        return (price / requestedNoOfDays);
    }

    /**
     * @param adultFreeNightAvaragePrice
     * @param noOfFreeNight
     * @return
     */
    private Double calculateFreeNightDeductionPrice(Double adultFreeNightAvaragePrice, Integer noOfFreeNight) {
        return adultFreeNightAvaragePrice * noOfFreeNight;
    }

    /**
     * @param occupancys
     * @param requestedOccupancy
     * @return
     */
    private List<Integer> getRelatedOccupancyList(List<String> occupancys, Integer requestedOccupancy) {
        List<Integer> relatedOccupancyIndexList = new ArrayList<>();
        Integer loopIndex = 0;
        for (String occupancy : occupancys) {
            if (occupancy.equalsIgnoreCase("All")) {
                relatedOccupancyIndexList.add(loopIndex);
            } else {
                Integer intOccupancy = Integer.parseInt(occupancy);
                if (intOccupancy.equals(requestedOccupancy)) {
                    relatedOccupancyIndexList.add(loopIndex);
                }
            }
            loopIndex++;
        }
        return relatedOccupancyIndexList;
    }

    /**
     * @param roomListAllDaysAvail
     * @return
     */
    private String generateAvlMsg(List<Room> roomListAllDaysAvail, AvailabilityRequestVO availabilityRequestVO) {
        String avlMsg = Constants.NOT_AVAILABLE_ALLOCATION_MSG;
        Integer noPaxPerRoom = NumberUtils.toInt(roomListAllDaysAvail.get(0).getNoOfRoomPax());
        Integer noOfPax = getPaxCountByOccupancy(availabilityRequestVO, noPaxPerRoom);
        Integer requestedRoomCount = noOfPax / noPaxPerRoom;
        for (Room room : roomListAllDaysAvail) {
            if (NumberUtils.isNumber(room.getAvailabilityStatus())) {
                Integer allocation = Integer.parseInt(room.getAvailabilityStatus());
                if (allocation >= requestedRoomCount) {
                    int day = getReleaseDay(room);
                    if (day != 0) {
                        Date today = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(today);
                        calendar.set(Calendar.DATE, day);
                        Date releaseDate = calendar.getTime();
                        if (releaseDate.compareTo(availabilityRequestVO.getFromDate()) <= 0) {
                            avlMsg = Constants.AVAILBALE_ALLOCATION_MSG;
                        } else {
                            avlMsg = Constants.NOT_AVAILABLE_ALLOCATION_MSG;
                            break;
                        }
                    } else {
                        avlMsg = Constants.AVAILBALE_ALLOCATION_MSG;
                    }
                } else {
                    avlMsg = Constants.NOT_AVAILABLE_ALLOCATION_MSG;
                    break;
                }
            } else {
                avlMsg = Constants.NOT_AVAILABLE_ALLOCATION_MSG;
                break;
            }
        }
        return avlMsg;
    }

    /**
     * @param room
     * @return
     */
    private int getReleaseDay(Room room) {
        int day = 0;
        if (StringUtils.isNotEmpty(room.getPriorityIndicator())) {
            if (room.getPriorityIndicator().equalsIgnoreCase(Constants.FREE_SALE_INDICATOR)) {
                if (NumberUtils.isNumber(room.getFreeSaleReleaseDays())) {
                    day = NumberUtils.toInt(room.getFreeSaleReleaseDays());
                } else {
                    day = NumberUtils.toInt(room.getStdReleaseDays());
                }
            } else if (room.getPriorityIndicator().equalsIgnoreCase(Constants.STD_SALE_INDICATOR)) {
                if (NumberUtils.isNumber(room.getStdReleaseDays())) {
                    day = NumberUtils.toInt(room.getStdReleaseDays());
                } else {
                    day = NumberUtils.toInt(room.getFreeSaleReleaseDays());
                }
            } else {
                day = 0;
            }
        }
        return day;
    }

    /**
     * @param availabilityRequestVO
     * @param noPaxPerRoom
     * @return
     */
    private Integer getPaxCountByOccupancy(AvailabilityRequestVO availabilityRequestVO, Integer noPaxPerRoom) {
        Integer occupancyIndex = 0;
        Integer guessCount = 0;
        for (Integer occupancy : availabilityRequestVO.getRoomOccupancy()) {
            if (occupancy.intValue() == noPaxPerRoom.intValue()) {
                guessCount = availabilityRequestVO.getGuestCount().get(occupancyIndex);
                break;
            }
            occupancyIndex++;
        }
        return guessCount;
    }

    /**
     * @param roomListAllDaysAvail
     * @param agentMarkupByDayVO
     * @param agentCode
     * @return
     */
    private String generateChildPriceMsg(List<Room> roomListAllDaysAvail, AgentMarkupByDayVO agentMarkupByDayVO, String agentCode,AvailabilityRequestVO availabilityRequestVO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PICK_PRICE_MESSAGE_DATE_FORMAT);
        String priceMsg = "";
        String date = "";
        Double price = 0.00;
        int noOfNight = 0;
        for (int i = 0; i < roomListAllDaysAvail.size(); i++) {
            Room room = roomListAllDaysAvail.get(i);
            try {
                Date roomDate = convertPickDateTypeToUtilDate(room.getDate(), lk.dmg.common.util.Constants.REDIS_KEY_DATE);

            Double markupPrecentage = calculateMarkUpByDay( agentMarkupByDayVO, agentCode,availabilityRequestVO);
            Double markupAmount = (markupPrecentage / 100) * NumberUtils.toDouble(room.getChildPrice());
            //Add Default value = 0 for child price
            if (room.getChildPrice().equals("")) {
                room.setChildPrice("0");
            }
            if (i == 0) {
                date = simpleDateFormat.format(roomDate);
                price = NumberUtils.toDouble(room.getChildPrice())+markupAmount;
                noOfNight = 1;
                if (roomListAllDaysAvail.size() == 1) {
                    priceMsg += "," + date + " " + noOfNight + "nts@" + priceFormatter(price * NumberUtils.toDouble(room.getNoOfRoomPax()));
                }
            } else {
                if (price.equals(NumberUtils.toDouble(room.getChildPrice()))) {
                    noOfNight++;
                    if (i == roomListAllDaysAvail.size() - 1) {
                        priceMsg += "," + date + " " + noOfNight + "nts@" + priceFormatter(price);
                    }
                } else {
                    priceMsg += "," + date + " " + noOfNight + "nts@" + priceFormatter(price);
                    date = simpleDateFormat.format(roomDate);
                    price = NumberUtils.toDouble(room.getChildPrice())+markupAmount;
                    noOfNight = 1;
                    if (i == roomListAllDaysAvail.size() - 1) {
                        priceMsg += "," + date + " " + noOfNight + "nts@" + priceFormatter(price);
                    }
                }
            }
            } catch (ParseException e) {
                log.error("1187", "Adult msg date format error", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Child Price MSG : " + priceMsg);
        }
        return (priceMsg != "" ? priceMsg.substring(1) : "");
    }
    /**
     * check client request extra bed or not =child request
     *
     * @param availabilityRequestVO
     */
    private Boolean checkExtraBedIsRequested(AvailabilityRequestVO availabilityRequestVO) {
        Boolean isRequested = false;
        for (Integer extrabed : availabilityRequestVO.getExtraBed()) {
            if (extrabed > 0) {
                isRequested = true;
                break;

            }
        }
        return isRequested;
    }

    /**
     * @param roomListAllDaysAvail
     * @param agentMarkupByDayVO
     * @param agentCode
     * @return
     */
    private String generateAdultPriceMsg(List<Room> roomListAllDaysAvail, AgentMarkupByDayVO agentMarkupByDayVO, String agentCode,AvailabilityRequestVO availabilityRequestVO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PICK_PRICE_MESSAGE_DATE_FORMAT);
        String priceMsg = "";
        String date = "";
        Double price = 0.00;
        int noOfNight = 0;
        for (int i = 0; i < roomListAllDaysAvail.size(); i++) {
            Room room = roomListAllDaysAvail.get(i);
            try {
                Date roomDate = convertPickDateTypeToUtilDate(room.getDate(), lk.dmg.common.util.Constants.REDIS_KEY_DATE);
                Double markupPrecentage = calculateMarkUpByDay(agentMarkupByDayVO, agentCode, availabilityRequestVO);
                Double markupAmount = (markupPrecentage / 100) * NumberUtils.toDouble(room.getAdultPrice());
                if (i == 0) {
                    date = simpleDateFormat.format(roomDate);
                    price = NumberUtils.toDouble(room.getAdultPrice()) + markupAmount;
                    noOfNight = 1;
                    if (roomListAllDaysAvail.size() == 1) {
                        priceMsg += "," + date + " " + noOfNight + "nts@" + priceFormatter(price * NumberUtils.toDouble(room.getNoOfRoomPax()));
                    }
                } else {
                    if (price.equals(NumberUtils.toDouble(room.getAdultPrice()) + markupAmount)) {
                        noOfNight++;
                        if (i == roomListAllDaysAvail.size() - 1) {
                            priceMsg += "," + date + " " + noOfNight + "nts@" + priceFormatter(price * NumberUtils.toDouble(room.getNoOfRoomPax()));
                        }
                    } else {
                        priceMsg += "," + date + " " + noOfNight + "nts@" + priceFormatter(price * NumberUtils.toDouble(room.getNoOfRoomPax()));
                        date = simpleDateFormat.format(roomDate);
                        price = NumberUtils.toDouble(room.getAdultPrice()) + markupAmount;
                        noOfNight = 1;
                        if (i == roomListAllDaysAvail.size() - 1) {
                            priceMsg += "," + date + " " + noOfNight + "nts@" + priceFormatter(price * NumberUtils.toDouble(room.getNoOfRoomPax()));
                        }
                    }
                }
            } catch (ParseException e) {
                log.error("1187", "Adult msg date format error", e);
            }
        }
        return (priceMsg != "" ? priceMsg.substring(1) : "");
    }
    /**
     * ]1;3]1;9] requested pax in a room
     * 9/3=3 PaxRoom
     *
     * @param availabilityRequestVO
     * @param noOfRoomPax
     */
    private Integer getPaxRoomByOccupancy(AvailabilityRequestVO availabilityRequestVO, int noOfRoomPax) {
        Integer occupancyIndex = 0;
        Integer guessCount = 0;
        for (Integer occupancy : availabilityRequestVO.getRoomOccupancy()) {
            if (occupancy == noOfRoomPax) {
                guessCount = availabilityRequestVO.getGuestCount().get(occupancyIndex);
                guessCount = guessCount / occupancy;
                break;
            }
            occupancyIndex++;
        }
        return guessCount;
    }

    /**
     * @param adultPrice
     * @return
     */
    private String priceFormatter(Double adultPrice) {
        DecimalFormat decimalFormat = new DecimalFormat(Constants.PRICE_FORMAT);
        return decimalFormat.format(adultPrice);
    }

    /**
     * @param hotelList
     * @param keyList
     * @return
     */
    private Map<String, Hotel> addDataToMap(List<Hotel> hotelList, List<String> keyList) {
        Map<String, Hotel> hotelMap = new HashMap<>();
        for (int i = 0; i < hotelList.size(); i++) {
            hotelMap.put(keyList.get(i), hotelList.get(i));
        }
        return hotelMap;
    }

    /**
     * @param hotelList
     * @param requestedDates
     * @return
     */
    private Set<String> buildKeySet(List<String> hotelList, List<Date> requestedDates) {
        Set<String> keySet = new HashSet<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(lk.dmg.common.util.Constants.REDIS_KEY_DATE);
        for (Date date : requestedDates) {
            String keyDate = simpleDateFormat.format(date);
            for (String hotelCode : hotelList)
                keySet.add(hotelCode + lk.dmg.common.util.Constants.REDIS_KEY_SEPARATOR + keyDate);
        }
        return keySet;
    }

    /**
     *
     * @param availabilityRequestVO
     * @return
     */
    private List<Date> getRequestedDays(AvailabilityRequestVO availabilityRequestVO) {
        List<Date> dateList = new ArrayList<>();
        Integer requestedNoOfDays = availabilityRequestVO.getNoOfDays();
        dateList.add(availabilityRequestVO.getFromDate());
        Date loopDate = availabilityRequestVO.getFromDate();
        Calendar c = Calendar.getInstance();
        while (requestedNoOfDays > 1) {
            c.setTime(loopDate); // Setting the from date
            c.add(Calendar.DATE, 1);
            loopDate = c.getTime();
            dateList.add(loopDate);
            requestedNoOfDays--;
        }
        return dateList;
    }

    /**
     * Convert pick string to java object
     * 27/Mar/2018]1]1;3]1;9]0;0]LON]YYA
     *
     * @param request
     * @return
     */
    private AvailabilityRequestVO convertRequestStringToObject(String request) throws TalcacheException {
        String seg[] = request.split(Constants.PICK_STRING_SPLITTER);
        AvailabilityRequestVO availabilityRequestVO = new AvailabilityRequestVO();
        if (validatePickRequestString(seg)) {/*Check request string required parameters are null*/
            try {
                availabilityRequestVO.setFromDate(convertPickDateTypeToUtilDate(seg[0], Constants.PICK_DATE_FORMAT));
                availabilityRequestVO.setCityCode(seg[5]);
                if (seg.length >= 7) {
                    availabilityRequestVO.setHotelCode(getSegemntToStringArrayList(seg[6]));
                }
                availabilityRequestVO.setExtraBed(getSegemntToIntArrayList(seg[4]));
                availabilityRequestVO.setGuestCount(getSegemntToIntArrayList(seg[3]));
                availabilityRequestVO.setNoOfDays(Integer.parseInt(seg[1]));
                availabilityRequestVO.setRoomOccupancy(getSegemntToIntArrayList(seg[2]));
                availabilityRequestVO.setToDate(toDateCalculation(availabilityRequestVO));
                availabilityRequestVO = setRoomRequest(availabilityRequestVO);
                if (log.isDebugEnabled()) {
                    log.debug("Request object :" + availabilityRequestVO.toString());
                }
            } catch (ParseException e) {
                throw new TalcacheException("CRSO64", "Date format exception required format :" + Constants.PICK_DATE_FORMAT + " Found :" + seg[0], Constants.INVALID_REQUEST_STRING);
            } catch (NumberFormatException ex) {
                throw new TalcacheException("STI84", "Segment to integer conversion fail due to value :" + ex, Constants.INVALID_REQUEST_STRING);
            }
        } else {
            throw new TalcacheException("AP68", "Invalid pick string", Constants.INVALID_REQUEST_STRING);
        }
        return availabilityRequestVO;
    }

    /**
     * set requested room Occupancy
     *
     * @param availabilityRequestVO
     */
    private AvailabilityRequestVO setRoomRequest(AvailabilityRequestVO availabilityRequestVO) {
        for (Integer occupancy : availabilityRequestVO.getRoomOccupancy()) {
            if (occupancy == 1) {
                availabilityRequestVO.setSingleRoom(true);
            } else if (occupancy == 2) {
                availabilityRequestVO.setTwinRoom(true);
            } else if (occupancy == 3) {
                availabilityRequestVO.setTripleRoom(true);
            }
        }
        return availabilityRequestVO;
    }

    /**
     * calculate to date
     * Todate=FromDate+NoOfDates
     *
     * @param availabilityRequestVO
     * @return
     */
    private Date toDateCalculation(AvailabilityRequestVO availabilityRequestVO) {
        if (log.isDebugEnabled()) {
            log.debug("Calculate to date");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(availabilityRequestVO.getFromDate()); // Setting the from date
        c.add(Calendar.DATE, availabilityRequestVO.getNoOfDays()); // Increasing no of days
        return c.getTime();
    }

    /**
     *
     * @param multiSegment
     * @return
     * @throws TalcacheException
     */
    private ArrayList<String> getSegemntToStringArrayList(String multiSegment) throws TalcacheException {
        String seg[] = multiSegment.trim().split(Constants.PICK_VARIABLE_SPLITTER);/*Split multi segment value by ;*/
        ArrayList<String> valueArrayList = new ArrayList<>();
        for (String value : seg) {
            if (value != null && !value.isEmpty()) {
                valueArrayList.add(value);
            } else {
                throw new TalcacheException("GEB83", "Request pick string validation failed.Required value empty or null in request string segment String[] convertion : " + multiSegment, Constants.INVALID_REQUEST_STRING);
            }
        }
        return valueArrayList;
    }

    /**
     *
     * @param segmentStr
     * @return
     * @throws TalcacheException
     * @throws NumberFormatException
     */
    private ArrayList<Integer> getSegemntToIntArrayList(String segmentStr) throws TalcacheException, NumberFormatException {
        String seg[] = segmentStr.trim().split(Constants.PICK_VARIABLE_SPLITTER);
        ArrayList<Integer> valueArrayList = new ArrayList<>();
        for (String value : seg) {
            if (value != null && !value.isEmpty()) {
                int elimentVal = Integer.parseInt(value);
                valueArrayList.add(elimentVal);
            } else {
                throw new TalcacheException("GEB83", "Request pick string validation failed.Required value empty or null in request string segment Int[] convertion : " + segmentStr, Constants.INVALID_REQUEST_STRING);
            }
        }
        return valueArrayList;
    }

    /**
     * Check pick string required parameters
     *
     * @param seg
     * @return
     */
    private boolean validatePickRequestString(String[] seg) {
        boolean isValid = true;
        if (seg.length >= Constants.REQUEST_STRING_SEGMENT_SIZE) {
            for (String value : seg) {
                if (StringUtils.isEmpty(value)) {
                    isValid = false;
                    log.error("Request pick string validation failed.Required value empty or null in request string" + seg);
                    break;
                }
            }
        } else {
            isValid = false;
        }
        return isValid;
    }

    /**
     * @param date
     * @return
     * @throws ParseException
     */
    private Date convertPickDateTypeToUtilDate(String date, String dateFormat) throws ParseException {
        SimpleDateFormat simplePickDateFormat = new SimpleDateFormat(dateFormat);
        Date utilDate = simplePickDateFormat.parse(date);
        return utilDate;
    }

    /**
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    private Integer dateDiff(Date fromDate, Date toDate) {
        long diff = toDate.getTime() - fromDate.getTime();
        diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return Math.toIntExact(diff);
    }

    /**
     *
     * @param fromDate
     * @param toDate
     * @param date
     * @return
     */
    private boolean isDateInBetween(Date fromDate, Date toDate, Date date) {
        return !(date.before(fromDate) || date.after(toDate));
    }

}