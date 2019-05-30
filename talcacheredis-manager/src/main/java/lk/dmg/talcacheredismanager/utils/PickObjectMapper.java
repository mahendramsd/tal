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
package lk.dmg.talcacheredismanager.utils;

import lk.dmg.common.exception.TalcacheException;
import lk.dmg.common.model.*;

import lk.dmg.common.repository.*;
import lk.dmg.talcacheredismanager.common.Constants;
import lk.dmg.talcacheredismanager.model.MainHotel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class PickObjectMapper {


    @Autowired
    FreeNightServiceImpl freeNightService;

    @Autowired
    EarlyBirdServiceImpl earlyBirdService;

    @Autowired
    AgentMarkupByDateServiceImpl agentMarkupByDateService;

    @Autowired
    AgentFreeNightByDateServiceImpl agentFreeNightByDateService;

    @Autowired
    AgentEarlyBirdByDateServiceImpl agentEarlyBirdByDateService;


    private final static Logger log = LoggerFactory.getLogger(PickObjectMapper.class);
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(lk.dmg.common.util.Constants.REDIS_KEY_DATE);
    static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern(lk.dmg.common.util.Constants.REDIS_KEY_DATE);


    /**
     * Map Pick String To List<MainHotel>
     *
     * @param str
     * @return
     * @throws ParseException
     * @throws InterruptedException
     */
//    public static List<MainHotel> create(String str) throws TalcacheException, ParseException {
//        List<MainHotel> hotels = new ArrayList<>();
//
//        String[] mainHotelSegment = splitTokenizer(str, Constants.MAIN_HOTEL_DELIMITER);
//        if (mainHotelSegment.length >= 2) {
//            for (int i = 1; i < mainHotelSegment.length; i++) {
//                String mainHotelSeg = mainHotelSegment[i];
//                hotels.add(createMainHotel(mainHotelSeg));
//            }
//        } else {
//            throw new TalcacheException("TAL100", "No Hotels", Constants.TAL100);
//        }
//        return hotels;
//    }

    /**
     * Create MainHotel Using Pick String
     *
     * @param mainHotelSegment
     * @return MainHotel
     */
    public MainHotel createMainHotel(String mainHotelSegment) throws TalcacheException, ParseException {
        MainHotel mainHotel = new MainHotel();
        try {
            String[] subHotelSegment = splitTokenizer(mainHotelSegment, Constants.SUB_HOTEL_DELIMITER_ALC);

            // MainHotel
            String[] mainSegment = splitTokenizer(subHotelSegment[0], Constants.SEGMENT_DELIMITER);
            mainHotel.setMainHotelCode(mainSegment[0]);
            String[] subHotels = stringTokenizer(mainSegment[1], Constants.SUB_SEGMENT_DELIMITER);
            String[] mainRoomSegment = stringTokenizer(mainSegment[4], Constants.SUB_SEGMENT_DELIMITER);
            List<Hotel> subHotelList = new ArrayList<>();
            // Sub hotel
            for (int j = 0; j < subHotels.length; j++) {
                if (mainRoomSegment.length > j && StringUtils.isNotEmpty(mainRoomSegment[j])) {
                    String subHotelCode = subHotels[j];
                    String ratePlanCode = stringTokenizer(mainSegment[2], Constants.SUB_SEGMENT_DELIMITER)[j];
                    String ratePlanDesc = stringTokenizer(mainSegment[3], Constants.SUB_SEGMENT_DELIMITER)[j];
                    String resortCode = mainSegment[7];
                    String countryCode = mainSegment[8];

                    String[] numPaxPerRoomList = stringTokenizer(mainSegment[5], Constants.SUB_SEGMENT_DELIMITER);
                    String[] extraBedList = stringTokenizer(mainSegment[6], Constants.SUB_SEGMENT_DELIMITER);
                    String[] numPaxPerRooms = splitTokenizer(numPaxPerRoomList[j], Constants.MIN_SEGMENT_DELIMITER);
                    String[] extraBeds = stringTokenizer(extraBedList[j], Constants.MIN_SEGMENT_DELIMITER);

                    // sub hotel segment
                    for (int l = 1; l < subHotelSegment.length; l++) {
                        String[] subSegment = splitTokenizer(subHotelSegment[l], Constants.SUB_HOTEL_DELIMITER_PRC);

                        if (subSegment.length == Constants.SEGMENT_LENGTH) {
                            // Allocations
                            String[] subSegment_ALC = stringTokenizer(subSegment[0], Constants.SEGMENT_DELIMITER);
                            String[] roomCodesListALC = stringTokenizer(subSegment_ALC[5],
                                    Constants.SUB_SEGMENT_DELIMITER);
                            String[] availabilityStatus = stringTokenizer(subSegment_ALC[6],
                                    Constants.SUB_SEGMENT_DELIMITER);
                            String[] avlStatusStd = stringTokenizer(subSegment_ALC[8], Constants.SUB_SEGMENT_DELIMITER);
                            String[] noOfBookdStd = stringTokenizer(subSegment_ALC[9], Constants.SUB_SEGMENT_DELIMITER);
                            String[] noRealeseDaysStd = stringTokenizer(subSegment_ALC[10],
                                    Constants.SUB_SEGMENT_DELIMITER);
                            String[] avlStatusFre = stringTokenizer(subSegment_ALC[11],
                                    Constants.SUB_SEGMENT_DELIMITER);
                            String[] noOfBookdFre = stringTokenizer(subSegment_ALC[12],
                                    Constants.SUB_SEGMENT_DELIMITER);
                            String[] noRealeseDaysFre = stringTokenizer(subSegment_ALC[13],
                                    Constants.SUB_SEGMENT_DELIMITER);

                            List<String> roomCodesListPRC = new ArrayList<>();
                            List<String> adultPriceListPRC = new ArrayList<>();
                            List<String> clildPriceListPRC = new ArrayList<>();
                            List<String> minDurationListPRC = new ArrayList<>();
                            // Price
                            String[] freeNight = splitTokenizer(subSegment[1], Constants.FREE_NIGHT_DELIMITER);
                            // Add FreeNight and EBD
                            addFreeNightAndEBD(freeNight);
                            String[] subSegment_PRC = stringTokenizer(freeNight[0], Constants.SEGMENT_DELIMITER);

                            if (subSegment_PRC.length > 6) {
                                roomCodesListPRC = stringToList(subSegment_PRC[6], Constants.SUB_SEGMENT_DELIMITER);
                            }
                            if (subSegment_PRC.length > 7) {
                                adultPriceListPRC = stringToList(subSegment_PRC[7],
                                        Constants.SUB_SEGMENT_DELIMITER);
                            }
                            if (subSegment_PRC.length > 8) {
                                clildPriceListPRC = stringToList(subSegment_PRC[8],
                                        Constants.SUB_SEGMENT_DELIMITER);
                            }
                            if (subSegment_PRC.length > 9) {
                                minDurationListPRC = stringToList(subSegment_PRC[9],
                                        Constants.SUB_SEGMENT_DELIMITER);
                            }
                            // Data ALC
                            String subHotelCodeALC = subSegment_ALC[0];
                           /* if (log.isDebugEnabled()) {
                                log.debug("SubHotelCode Allocation : " + subHotelCodeALC);
                            }*/
                            String priorityIndicator = subSegment_ALC[1];
                            String[] fromDates_ALC = stringTokenizer(subSegment_ALC[2],
                                    Constants.SUB_SEGMENT_DELIMITER);
                            String[] toDates_ALC = splitTokenizer(subSegment_ALC[3], Constants.SUB_SEGMENT_DELIMITER);

                            for (int f1 = 0; f1 < fromDates_ALC.length; f1++) {

                                if (StringUtils.isNotEmpty(toDates_ALC[f1])) {
                                    LocalDate fromDateALC = LocalDate.parse(fromDates_ALC[f1], localDateFormatter);
                                    LocalDate toDateALC = LocalDate.parse(toDates_ALC[f1], localDateFormatter);
                                    for (LocalDate date1 = fromDateALC; date1
                                            .isBefore(toDateALC.plusDays(1)); date1 = date1.plusDays(1)) {
                                        if (subHotelCode.equalsIgnoreCase(subHotelCodeALC)) {
                                           /* if (log.isDebugEnabled()) {
                                                log.debug("Redis Key : " + subHotelCodeALC + "_" + date1);
                                            }*/
                                            Hotel hotel = new Hotel();
                                            hotel.setSubHotelCode(subHotelCode);
                                            hotel.setMainHotelCode(mainHotel.getMainHotelCode());
                                            hotel.setRatePlanCode(ratePlanCode);
                                            hotel.setRatePlanDesc(ratePlanDesc);
                                            hotel.setResortCode(resortCode);
                                            hotel.setDate(simpleDateFormat.format(convertToDateViaLocatDate(date1)));
                                            hotel.setCountryCode(countryCode);
                                            hotel.setPriorityIndicator(priorityIndicator);
                                            String[] roomCodesALC = stringTokenizer(roomCodesListALC[f1],
                                                    Constants.MIN_SEGMENT_DELIMITER);
                                            String[] availabilityStatusALC = stringTokenizer(availabilityStatus[f1],
                                                    Constants.MIN_SEGMENT_DELIMITER);
                                            String[] avlStatusStdALC = stringTokenizer(avlStatusStd[f1],
                                                    Constants.MIN_SEGMENT_DELIMITER);
                                            String[] noOfBookdStdALC = stringTokenizer(noOfBookdStd[f1],
                                                    Constants.MIN_SEGMENT_DELIMITER);
                                            String[] noRealeseDaysStdALC = stringTokenizer(noRealeseDaysStd[f1],
                                                    Constants.MIN_SEGMENT_DELIMITER);
                                            String[] avlStatusFreALC = stringTokenizer(avlStatusFre[f1],
                                                    Constants.MIN_SEGMENT_DELIMITER);
                                            String[] noOfBookdFreALC = stringTokenizer(noOfBookdFre[f1],
                                                    Constants.MIN_SEGMENT_DELIMITER);
                                            String[] noRealeseDaysFreALC = stringTokenizer(noRealeseDaysFre[f1],
                                                    Constants.MIN_SEGMENT_DELIMITER);
                                            List<Room> rooms = new ArrayList<>();
                                            for (int ra = 0; ra < roomCodesALC.length; ra++) {
                                                if (log.isDebugEnabled()) {
                                                    log.debug("Allocation Status : " + availabilityStatusALC[ra]);
                                                }
                                                if (!availabilityStatusALC[ra]
                                                        .equalsIgnoreCase(Constants.ALLOCATION_BLOCK_STATUS) && !numPaxPerRooms[ra].equals("0")) {
                                                    Room room = new Room();
                                                    room.setRoomCode(roomCodesALC[ra]);
                                                    room.setSubHotelCode(subHotelCode);
                                                    room.setMainHotelCode(mainHotel.getMainHotelCode());
                                                    room.setDate(simpleDateFormat.format(convertToDateViaLocatDate(date1)));
                                                    room.setRatePlanCode(ratePlanCode);
                                                    room.setResortCode(resortCode);
                                                    room.setRatePlanDesc(ratePlanDesc);
                                                    room.setCountryCode(countryCode);
                                                    room.setPriorityIndicator(priorityIndicator);
                                                    room.setNoOfRoomPax(numPaxPerRooms[ra]);
                                                    room.setNoOfExtraBed(extraBeds[ra]);
                                                    room.setAvailabilityStatus(availabilityStatusALC[ra]);
                                                    room.setStdAvailabilityStatus(avlStatusStdALC[ra]);
                                                    room.setStdReleaseDays(noRealeseDaysStdALC[ra]);
                                                    room.setStdBooked(noOfBookdStdALC[ra]);
                                                    room.setFreeSaleAvailabilityStatus(avlStatusFreALC[ra]);
                                                    room.setFreeSaleBooked(noOfBookdFreALC[ra]);
                                                    room.setFreeSaleReleaseDays(noRealeseDaysFreALC[ra]);


                                                    String subHotelCodePRC = subSegment_PRC[0];
                                                    room.setCurrencyCode(subSegment_PRC[1]);

                                                    String[] fromDates_PRC = stringTokenizer(subSegment_PRC[2],
                                                            Constants.SUB_SEGMENT_DELIMITER);
                                                    String[] toDates_PRC = stringTokenizer(subSegment_PRC[3],
                                                            Constants.SUB_SEGMENT_DELIMITER);
                                                    String[] productCode = stringTokenizer(subSegment_PRC[5],
                                                            Constants.SUB_SEGMENT_DELIMITER);

                                                    for (int f2 = 0; f2 < fromDates_PRC.length; f2++) {
                                                        LocalDate fromDatePRC = LocalDate.parse(fromDates_PRC[f2],
                                                                localDateFormatter);
                                                        LocalDate toDatePRC = LocalDate.parse(toDates_PRC[f2],
                                                                localDateFormatter);
                                                        for (LocalDate date2 = fromDatePRC; date2.isBefore(
                                                                toDatePRC.plusDays(1)); date2 = date2.plusDays(1)) {
                                                            if (date1.equals(date2)
                                                                    && subHotelCode.equalsIgnoreCase(subHotelCodePRC)) {
                                                                if (log.isDebugEnabled()) {
                                                                    log.debug(
                                                                            "HotelCode and Date match in PRICE Segment");
                                                                }
                                                                List<String> roomCodesPRC = new ArrayList();
                                                                List<String> adultPricesPRC = new ArrayList();
                                                                List<String> clildPricesPRC = new ArrayList();
                                                                List<String> minDurationPRC = new ArrayList();

                                                                if (roomCodesListPRC.size() > f2) {
                                                                    roomCodesPRC = stringToList(
                                                                            roomCodesListPRC.get(f2),
                                                                            Constants.MIN_SEGMENT_DELIMITER);
                                                                }
                                                                if (adultPriceListPRC.size() > f2) {
                                                                    adultPricesPRC = stringToList(
                                                                            adultPriceListPRC.get(f2),
                                                                            Constants.MIN_SEGMENT_DELIMITER);
                                                                }

                                                                if (clildPriceListPRC.size() > f2) {
                                                                    clildPricesPRC = stringToList(
                                                                            clildPriceListPRC.get(f2),
                                                                            Constants.MIN_SEGMENT_DELIMITER);
                                                                }

                                                                if (minDurationListPRC.size() > f2) {
                                                                    minDurationPRC = stringToList(
                                                                            minDurationListPRC.get(f2),
                                                                            Constants.MIN_SEGMENT_DELIMITER);
                                                                }

                                                                for (int r = 0; r < roomCodesPRC.size(); r++) {
                                                                   /* if (log.isDebugEnabled()) {
                                                                        log.debug("Room Adult Price: " + roomCodesPRC[r]
                                                                                + " : " + adultPricesPRC[r]);
                                                                    }*/
                                                                    if (room.getRoomCode()
                                                                            .equalsIgnoreCase(roomCodesPRC.get(r))
                                                                            && StringUtils
                                                                            .isNotEmpty(adultPricesPRC.get(r))) {
                                                                        room.setProductCode(productCode[f2]);
                                                                        room.setAdultPrice(adultPricesPRC.get(r));
                                                                        room.setChildPrice(clildPricesPRC.get(r));
                                                                        room.setMinDuration(minDurationPRC.get(r));
                                                                        rooms.add(room);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            hotel.setRooms(rooms);
                                            if (!hotel.getRooms().isEmpty()) {
                                               /* if (log.isDebugEnabled()) {
                                                    log.debug("Create new Redis key Hotel: " + hotel.getSubHotelCode());
                                                }*/
                                                subHotelList.add(hotel);
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
                mainHotel.setSubHotels(subHotelList);
                /*if (log.isDebugEnabled()) {
                    log.debug("Create New MainHotel: " + mainHotel.getMainHotelCode());
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new TalcacheException("CAP200", "Cannot cast Cap file to hotel ", Constants.CAP200, e.getMessage());

        }
        return mainHotel;
    }

    /**
     * @param freeNight
     */
    private void addFreeNightAndEBD(String[] freeNight) throws TalcacheException {
        for (int i = 1; i < freeNight.length; i++) {
            String seg = freeNight[i];
            String[] ebdmain = splitTokenizer(seg, Constants.EBD_DELIMITER);
            if (ebdmain.length > 0) {
                saveFreeNight(ebdmain[0]);
            }
            if (ebdmain.length > 1) {
                saveEBD(ebdmain[1]);
            }
        }

    }

    /**
     * @param seg
     */
    private void saveEBD(String seg) throws TalcacheException {
        String[] segment = stringTokenizer(seg, Constants.SEGMENT_DELIMITER);
        EarlyBirdVO earlyBirdVO = new EarlyBirdVO();
        earlyBirdVO.setSubHotelCode(segment[0]);
        earlyBirdVO.setMainHotelCode(segment[1]);
        String[] occupancys = stringTokenizer(segment[3], Constants.SUB_SEGMENT_DELIMITER);
        String[] earlyBirdTypes = stringTokenizer(segment[4], Constants.SUB_SEGMENT_DELIMITER);
        String[] fromBookingDates = stringTokenizer(segment[5], Constants.SUB_SEGMENT_DELIMITER);
        String[] toBookingDates = stringTokenizer(segment[6], Constants.SUB_SEGMENT_DELIMITER);
        String[] fromArrivalDates = stringTokenizer(segment[7], Constants.SUB_SEGMENT_DELIMITER);
        String[] toArrivalDate = stringTokenizer(segment[8], Constants.SUB_SEGMENT_DELIMITER);
        String[] upToNoOfDays = stringTokenizer(segment[9], Constants.SUB_SEGMENT_DELIMITER);
        String[] discounts = stringTokenizer(segment[10], Constants.SUB_SEGMENT_DELIMITER);

        earlyBirdVO.setOccupancys(Arrays.asList(occupancys));
        earlyBirdVO.setEarlyBirdTypes(Arrays.asList(earlyBirdTypes));
        earlyBirdVO.setFromBookingDates(Arrays.asList(fromBookingDates));
        earlyBirdVO.setToBookingDates(Arrays.asList(toBookingDates));
        earlyBirdVO.setFromArrivalDates(Arrays.asList(fromArrivalDates));
        earlyBirdVO.setToArrivalDate(Arrays.asList(toArrivalDate));
        earlyBirdVO.setUpToNoOfDays(Arrays.asList(upToNoOfDays));
        earlyBirdVO.setDiscounts(Arrays.asList(discounts));

        if (log.isDebugEnabled()) {
            log.debug("Create EBD Object" + earlyBirdVO.getSubHotelCode());
        }
        earlyBirdService.set(lk.dmg.common.util.Constants.EARLY_BIRD_FILE, earlyBirdVO.getSubHotelCode(), earlyBirdVO);
        if (log.isDebugEnabled()) {
            log.debug("Save EBD" + earlyBirdVO.getSubHotelCode());
        }

    }

    /**
     * @param dateToConvert
     * @return
     */
    public static Date convertToDateViaLocatDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Used for delimiter multiple Characters
     *
     * @param pickReturn
     * @param delimiter
     * @return String[]
     */
    public static String[] splitTokenizer(String pickReturn, String delimiter) {
        return pickReturn.split(delimiter);
    }

    /**
     * Only for used delimiter Single Character
     *
     * @param pickReturn
     * @param delimiter
     * @return
     */
    public static String[] stringTokenizer(String pickReturn, String delimiter) {
        StringTokenizer st = new StringTokenizer(pickReturn, delimiter);
        if (log.isDebugEnabled()) {
            log.debug("PICK String Split To : " + pickReturn);
        }
        return st.getArray();
    }

    public static List<String> stringToList(String pickReturn, String delimiter) {
        StringTokenizer st = new StringTokenizer(pickReturn, delimiter);
        if (log.isDebugEnabled()) {
            log.debug("PICK String Split To : " + pickReturn);
        }
        return Arrays.asList(st.getArray());
    }

    /**
     * @param freeNightString
     * @return
     */
    public void saveFreeNight(String freeNightString) throws TalcacheException {
        String[] segment = stringTokenizer(freeNightString, Constants.SEGMENT_DELIMITER);
        FreeNightVO freeNightVO = new FreeNightVO();
        freeNightVO.setSubHotelCode(segment[0]);
        freeNightVO.setMainHotelCode(segment[1]);
        String[] occupancys = stringTokenizer(segment[3], Constants.SUB_SEGMENT_DELIMITER);
        String[] fromDates = stringTokenizer(segment[4], Constants.SUB_SEGMENT_DELIMITER);
        String[] toDates = stringTokenizer(segment[5], Constants.SUB_SEGMENT_DELIMITER);
        String[] agentInds = stringTokenizer(segment[6], Constants.SUB_SEGMENT_DELIMITER);
        String[] displayInds = stringTokenizer(segment[7], Constants.SUB_SEGMENT_DELIMITER);
        String[] adultDurations = stringTokenizer(segment[8], Constants.SUB_SEGMENT_DELIMITER);
        String[] adultNoOfFreeNights = stringTokenizer(segment[9], Constants.SUB_SEGMENT_DELIMITER);
        String[] childDurations = stringTokenizer(segment[10], Constants.SUB_SEGMENT_DELIMITER);
        String[] childNoOfFreeNights = stringTokenizer(segment[11], Constants.SUB_SEGMENT_DELIMITER);
        freeNightVO.setOccupancy(Arrays.asList(occupancys));
        freeNightVO.setFromDates(Arrays.asList(fromDates));
        freeNightVO.setToDates(Arrays.asList(toDates));
        freeNightVO.setApplyToAgentInds(Arrays.asList(agentInds));
        freeNightVO.setDisplayInds(Arrays.asList(displayInds));
        freeNightVO.setAdultDurations(Arrays.asList(adultDurations));
        freeNightVO.setAdultNoOfFreeNights(Arrays.asList(adultNoOfFreeNights));
        freeNightVO.setChildDurations(Arrays.asList(childDurations));
        freeNightVO.setChildNoOfFreeNights(Arrays.asList(childNoOfFreeNights));
        if (log.isDebugEnabled()) {
            log.debug("Create FreeNight Object" + freeNightVO.getSubHotelCode());
        }
        freeNightService.set(lk.dmg.common.util.Constants.FREE_NIGHT_FILE, freeNightVO.getSubHotelCode(), freeNightVO);
        if (log.isDebugEnabled()) {
            log.debug("Save FreeNight" + freeNightVO.getSubHotelCode());
        }
    }

    /**
     * @param agentMarkup
     * @return
     */
    public void createAgentMarks(String agentMarkup) throws TalcacheException {
        String[] markupSegment = PickObjectMapper.splitTokenizer(agentMarkup, Constants.PROD_DELIMITER);
        for (int i = 1; i < markupSegment.length; i++) {
            String[] mark = stringTokenizer(markupSegment[i], Constants.SEGMENT_DELIMITER);
            AgentMarkupByDayVO agentMarkupByDayVO = new AgentMarkupByDayVO();
            String productCode = generateProductCode(mark[0]);
            agentMarkupByDayVO.setProductCode(productCode);
            agentMarkupByDayVO.setProductDesc(mark[1]);
            agentMarkupByDayVO.setAllAgentInd(mark[2]);
            String[] agentExcluded = stringTokenizer(mark[3], Constants.SUB_SEGMENT_DELIMITER);
            String[] agentIncluded = stringTokenizer(mark[4], Constants.SUB_SEGMENT_DELIMITER);
            String[] markupFromDates = stringTokenizer(mark[5], Constants.SUB_SEGMENT_DELIMITER);
            String[] markupToDates = stringTokenizer(mark[6], Constants.SUB_SEGMENT_DELIMITER);
            String[] markups = stringTokenizer(mark[7], Constants.SUB_SEGMENT_DELIMITER);

            agentMarkupByDayVO.setAgentExcluded(Arrays.asList(agentExcluded));
            agentMarkupByDayVO.setAgentIncluded(Arrays.asList(agentIncluded));
            agentMarkupByDayVO.setMarkupFromDates(Arrays.asList(markupFromDates));
            agentMarkupByDayVO.setMarkupToDate(Arrays.asList(markupToDates));
            agentMarkupByDayVO.setMarkup(Arrays.asList(markups));
            // Save AgentMarkup By day
            saveAgentMarkupsByDay(agentMarkupByDayVO);
            if (mark.length > 14) {
                String[] hotelsWithFreeNights = stringTokenizer(mark[10], Constants.SUB_SEGMENT_DELIMITER);
                String[] agentEnabledForFreeNights = stringTokenizer(mark[11], Constants.SUB_SEGMENT_DELIMITER);
                String[] hotelsWithEarlyBird = stringTokenizer(mark[12], Constants.SUB_SEGMENT_DELIMITER);
                String[] ebdEnabledAgents = stringTokenizer(mark[13], Constants.SUB_SEGMENT_DELIMITER);
                String[] freeNightFromDates = stringTokenizer(mark[14], Constants.SUB_SEGMENT_DELIMITER);
                String[] freeNightToDates = stringTokenizer(mark[15], Constants.SUB_SEGMENT_DELIMITER);
                // Save Agent FreeNight By Day
                saveAgentFreeNightByDay(productCode, hotelsWithFreeNights, agentEnabledForFreeNights, freeNightFromDates, freeNightToDates);
                if (mark.length >= 17) {
                    String[] ebdFromDates = stringTokenizer(mark[16], Constants.SUB_SEGMENT_DELIMITER);
                    String[] ebdToDates = stringTokenizer(mark[17], Constants.SUB_SEGMENT_DELIMITER);
                    // Save Agent EBD By Day
                    saveAgentEBDByDay(productCode, hotelsWithEarlyBird, ebdEnabledAgents, ebdFromDates, ebdToDates);
                } else {
                    log.error("Agent EDB Not Available" + productCode);
                }
            } else {
                log.error("Agent FreeNight Not Available" + productCode);
            }
        }
    }

    /**
     * @param code
     * @return
     */
    private String generateProductCode(String code) {
        String productCode = "";
        String[] codes = stringTokenizer(code, Constants.PRODUCT_DELIMITER);
        String[] removedElement = Arrays.copyOf(codes, codes.length - 1);
        if (removedElement.length == 1) {
            productCode = removedElement[0];
            return productCode;
        } else {
            for (String element : removedElement) {
                productCode += element + Constants.PRODUCT_DELIMITER;
            }
            return productCode.substring(0, productCode.length() - 1);
        }


    }

    /**
     * @param productCode
     * @param hotelsWithEarlyBird
     * @param ebdEnabledAgents
     * @param ebdFromDates
     * @param ebdToDates
     */
    private void saveAgentEBDByDay(String productCode, String[] hotelsWithEarlyBird, String[] ebdEnabledAgents, String[] ebdFromDates, String[] ebdToDates) throws TalcacheException {
        //EBD Enable Agents Loop
        for (int i = 0; i < ebdEnabledAgents.length; i++) {
            String[] ebdAgents = stringTokenizer(ebdEnabledAgents[i], Constants.MIN_SEGMENT_DELIMITER);
            String[] ebdFromDate = stringTokenizer(ebdFromDates[i], Constants.MIN_SEGMENT_DELIMITER);
            String[] ebdToDate = stringTokenizer(ebdToDates[i], Constants.MIN_SEGMENT_DELIMITER);
            for (int a = 0; a < ebdAgents.length; a++) {
                String agentCode = ebdAgents[a];
                String date1 = ebdFromDate[a];
                String date2 = ebdToDate[a];
                if (StringUtils.isNotEmpty(date1) && StringUtils.isNotEmpty(date2)) {
                    LocalDate fromDate = LocalDate.parse(date1, localDateFormatter);
                    LocalDate toDate = LocalDate.parse(date2, localDateFormatter);
                    for (LocalDate d1 = fromDate; d1.isBefore(toDate.plusDays(1)); d1 = d1.plusDays(1)) {
                        AgentEarlyBirdByDayVO earlyBirdByDayVO = new AgentEarlyBirdByDayVO();
                        String keyDate = simpleDateFormat.format(convertToDateViaLocatDate(d1));
                        earlyBirdByDayVO.setAgentEbdByDayKey(productCode + Constants.REDIS_KEY_DELIMITER + agentCode + Constants.REDIS_KEY_DELIMITER + keyDate);
                        earlyBirdByDayVO.setProductCode(productCode);
                        earlyBirdByDayVO.setEbdAgent(agentCode);
                        earlyBirdByDayVO.setEdbDate(simpleDateFormat.format(convertToDateViaLocatDate(d1)));
                        earlyBirdByDayVO.setEbdFromDate(simpleDateFormat.format(convertToDateViaLocatDate(fromDate)));
                        earlyBirdByDayVO.setEbdToDate(simpleDateFormat.format(convertToDateViaLocatDate(toDate)));
                        earlyBirdByDayVO.setEbdEnableHotels(Arrays.asList(hotelsWithEarlyBird));
                        agentEarlyBirdByDateService.set(lk.dmg.common.util.Constants.AGENT_EARLY_BIRD_BY_DAY_FILE, earlyBirdByDayVO.getAgentEbdByDayKey(), earlyBirdByDayVO);
                        if (log.isDebugEnabled()) {
                            log.debug("Save New AgentEBDByDay Key Redis : " + earlyBirdByDayVO.getAgentEbdByDayKey());
                        }
                    }
                } else {
                    log.error("EBD FromDate and ToDate Can't be Empty ! , FromDate : " + date1 + "ToDate : " + date2);
                }

            }
        }
    }

    /**
     * @param productCode
     * @param hotelsWithFreeNights
     * @param agentEnabledForFreeNights
     * @param freeNightFromDates
     * @param freeNightToDates
     */
    private void saveAgentFreeNightByDay(String productCode, String[] hotelsWithFreeNights, String[] agentEnabledForFreeNights, String[] freeNightFromDates, String[] freeNightToDates) throws TalcacheException {
        //FreeNight Enable Agents Loop
        for (int i = 0; i < agentEnabledForFreeNights.length; i++) {
            String[] freeNightAgents = stringTokenizer(agentEnabledForFreeNights[i], Constants.MIN_SEGMENT_DELIMITER);
            String[] freeNightFromDate = stringTokenizer(freeNightFromDates[i], Constants.MIN_SEGMENT_DELIMITER);
            String[] freeNightToDate = stringTokenizer(freeNightToDates[i], Constants.MIN_SEGMENT_DELIMITER);
            for (int a = 0; a < freeNightAgents.length; a++) {
                String agentCode = freeNightAgents[a];
                String date1 = freeNightFromDate[a];
                String date2 = freeNightToDate[a];
                if (StringUtils.isNotEmpty(date1) && StringUtils.isNotEmpty(date2)) {
                    LocalDate fromDate = LocalDate.parse(date1, localDateFormatter);
                    LocalDate toDate = LocalDate.parse(date2, localDateFormatter);
                    for (LocalDate d1 = fromDate; d1.isBefore(toDate.plusDays(1)); d1 = d1.plusDays(1)) {
                        AgentFreeNightByDayVO freeNightByDayVO = new AgentFreeNightByDayVO();
                        String keyDate = simpleDateFormat.format(convertToDateViaLocatDate(d1));
                        freeNightByDayVO.setAgentFreeNightByDayKey(productCode + Constants.REDIS_KEY_DELIMITER + agentCode + Constants.REDIS_KEY_DELIMITER + keyDate);
                        freeNightByDayVO.setProductCode(productCode);
                        freeNightByDayVO.setFreeNightAgent(agentCode);
                        freeNightByDayVO.setFreeNightDate(simpleDateFormat.format(convertToDateViaLocatDate(d1)));
                        freeNightByDayVO.setFreeNightFromDate(simpleDateFormat.format(convertToDateViaLocatDate(fromDate)));
                        freeNightByDayVO.setFreeNightToDate(simpleDateFormat.format(convertToDateViaLocatDate(toDate)));
                        freeNightByDayVO.setFreeNightEnableHotels(Arrays.asList(hotelsWithFreeNights));
                        agentFreeNightByDateService.set(lk.dmg.common.util.Constants.AGENT_FREE_NIGHT_BY_DAY_FILE, freeNightByDayVO.getAgentFreeNightByDayKey(), freeNightByDayVO);
                        if (log.isDebugEnabled()) {
                            log.debug("Save New AgentFreeNightByDay Key Redis : " + freeNightByDayVO.getAgentFreeNightByDayKey());
                        }
                    }
                } else {
                    log.error("FreeNight FromDate and ToDate Can't be Empty ! , FromDate : " + date1 + "ToDate : " + date2);
                }

            }
        }
    }

    /**
     * @param agentMarkupByDayVO
     */
    private void saveAgentMarkupsByDay(AgentMarkupByDayVO agentMarkupByDayVO) throws TalcacheException {

        for (int i = 0; i < agentMarkupByDayVO.getMarkupFromDates().size(); i++) {
            String date1[] = stringTokenizer(agentMarkupByDayVO.getMarkupFromDates().get(i), Constants.MIN_SEGMENT_DELIMITER);
            String date2[] = stringTokenizer(agentMarkupByDayVO.getMarkupToDate().get(i), Constants.MIN_SEGMENT_DELIMITER);
            for (int d = 0; d < date1.length; d++) {
                String fromDay = date1[d];
                String toDay = date2[d];
                if (StringUtils.isNotEmpty(fromDay) && StringUtils.isNotEmpty(toDay)) {
                    LocalDate fromDate = LocalDate.parse(fromDay, localDateFormatter);
                    LocalDate toDate = LocalDate.parse(toDay, localDateFormatter);
                    for (LocalDate d1 = fromDate; d1.isBefore(toDate.plusDays(1)); d1 = d1.plusDays(1)) {
                        AgentMarkupByDayVO newMarkup = agentMarkupByDayVO;
                        newMarkup.setProductDate(simpleDateFormat.format(convertToDateViaLocatDate(d1)));
                        String keyDate = simpleDateFormat.format(convertToDateViaLocatDate(d1));
                        agentMarkupByDateService.set(lk.dmg.common.util.Constants.AGENT_MARKUP_BY_DATE_FILE, newMarkup.getProductCode() + Constants.REDIS_KEY_DELIMITER + keyDate, newMarkup);
                        if (log.isDebugEnabled()) {
                            log.debug("Save New AgentMarkupByDay Key Redis : " + agentMarkupByDayVO.getProductCode());
                        }
                    }
                } else {
                    log.error("AgentMarkup FromDate and ToDate Can't be Empty ! , FromDate : " + date1 + "ToDate : " + date2);
                }
            }
        }
    }
}
