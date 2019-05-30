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


import lk.dmg.talcacheoperational.model.response.AvailabilityResponseProductVO;
import lk.dmg.talcacheoperational.model.response.AvalabilityResponseRoomVO;
import lk.dmg.talcacheoperational.util.Constants;

import java.util.List;


public class ResponseProducts {
    public String createProductData(List<AvalabilityResponseRoomVO> avalabilityResponseRoomVO, String notInUsePos45, String ratePlanDesc, String rateplanCode, String notInUsePos48, String uratePlanCode) {
        String productdata = "";
        StringBuffer buffer=new StringBuffer();
        buffer.append("]");
        int availblerRoomSize=avalabilityResponseRoomVO.size();
        int roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setProducCode(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }
            else{
                buffer.append(setProducCode(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setNotInUsePos13(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }
            else{
                buffer.append(setNotInUsePos13(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setSpecialOfferIndicat(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }
            else {
                buffer.append(setSpecialOfferIndicat(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setSpecialOfferDesc(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }
            else{
                buffer.append(setSpecialOfferDesc(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setPriceCurency(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }
            else {
                buffer.append(setPriceCurency(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setPriceMessage(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }
            else{
                buffer.append(setPriceMessage(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setAdultPrice(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else{
                buffer.append(setAdultPrice(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setChildPrice(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else{
                buffer.append(setChildPrice(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setTotaladultPrice(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setTotaladultPrice(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setTotalchildprice(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else{
                buffer.append(setTotalchildprice(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setAdultpriceMessage(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else{
                buffer.append(setAdultpriceMessage(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setChPriceMessage(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setChPriceMessage(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setPriceCode(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setPriceCode(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setOverlapPriceCode(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setOverlapPriceCode(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setMinAdPrice(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setMinAdPrice(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setMinChPrice(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setMinChPrice(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setMinPriceMsg(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setMinPriceMsg(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setReductionFlag(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setReductionFlag(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setAdReductionAmt(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setAdReductionAmt(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setAdReductionPerc(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setAdReductionPerc(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setChReductionAmt(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setChReductionAmt(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setChReductionPerc(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setChReductionPerc(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setNotInUsePos34(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setNotInUsePos34(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setNotInUsePos35(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setNotInUsePos35(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setNoFreeNightsAd(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setNoFreeNightsAd(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setPrcAdFreeNght(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setPrcAdFreeNght(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setBasedOnAdDuration(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setBasedOnAdDuration(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setNoFreeNightCh(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setNoFreeNightCh(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setPrcChFreeNght(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setPrcChFreeNght(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setBasedOnChDuration(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setBasedOnChDuration(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setNotInUsePos42(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setNotInUsePos42(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setCoreIdentifierCode(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setCoreIdentifierCode(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setCoreIdentifierDesc(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setCoreIdentifierDesc(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]" + notInUsePos45 + "]" + ratePlanDesc + "]" + rateplanCode + "]" + notInUsePos48 + "]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setEarlyBirdDiscount(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setEarlyBirdDiscount(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append("]");
        roomcount=1;
        for (AvalabilityResponseRoomVO roomVo : avalabilityResponseRoomVO) {
            if(roomcount==availblerRoomSize){
                buffer.append(setEarlyBirdDiscAmt(roomVo.getAvailabilityResponseProductVO()).replace(";",""));
            }else {
                buffer.append(setEarlyBirdDiscAmt(roomVo.getAvailabilityResponseProductVO()));
            }
            roomcount++;
        }
        buffer.append( "]" + uratePlanCode);
        productdata=buffer.toString();
        return productdata;

    }

    private String setProducCode(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getProductCode() + ";");
            } else {
                buffer.append(productVO.getProductCode() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setNotInUsePos13(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getNotInUsePos13() + ";");
            } else {
                buffer.append(productVO.getNotInUsePos13() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setSpecialOfferIndicat(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getSpecialOfferIndicat() + ";");
            } else {
                buffer.append( productVO.getSpecialOfferIndicat() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setSpecialOfferDesc(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getSpecialOfferDesc() + ";");
            } else {
                buffer.append(productVO.getSpecialOfferDesc() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setPriceCurency(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getPriceCurency() + ";");
            } else {
                buffer.append(productVO.getPriceCurency() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setPriceMessage(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getPricemessage() + ";");
            } else {
                buffer.append( productVO.getPricemessage() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setAdultPrice(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getAdultprice()) + ";");
            } else {
                buffer.append( String.valueOf(productVO.getAdultprice()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setChildPrice(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getChildprice()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getChildprice()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setTotaladultPrice(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getTotaladultprice()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getTotaladultprice()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setTotalchildprice(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getTotalchildprice()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getTotalchildprice()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setAdultpriceMessage(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
		String producCodetdata = "";
		int productlistsize = availabilityResponseProductVO.size();
		int loopid = 1;
		StringBuffer buffer = new StringBuffer();
		for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
			if (productlistsize == loopid) {
				if (productVO.getNoFreeNightsAd().length() != 0) {
                    buffer.append(productVO.getAdultpricemessage() + " " + Constants.FREE_NIGHT_PRE_MSG + " " + productVO.getNoFreeNightsAd() + " "
                            + Constants.FREE_NIGHT_MSG + ";");
                } else {
					buffer.append(productVO.getAdultpricemessage() + ";");
				}
			} else {
				if (productVO.getNoFreeNightsAd().length() != 0) {
                    buffer.append(productVO.getAdultpricemessage() + " " + Constants.FREE_NIGHT_PRE_MSG + " " + productVO.getNoFreeNightsAd() + " "
                            + Constants.FREE_NIGHT_MSG + "\\");
                } else {
					buffer.append(productVO.getAdultpricemessage() + "\\");
				}
			}
			loopid++;
		}
		producCodetdata = buffer.toString();
		return producCodetdata;
	}
	private String setChPriceMessage(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
		String producCodetdata = "";
		int productlistsize = availabilityResponseProductVO.size();
		int loopid = 1;
		StringBuffer buffer = new StringBuffer();
		for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
			if (productlistsize == loopid) {
				if (productVO.getNoFreeNightCh().length() != 0) {
                    buffer.append(productVO.getChPriceMessage() + " " + Constants.FREE_NIGHT_PRE_MSG + " " + productVO.getNoFreeNightCh() + " "
                            + Constants.FREE_NIGHT_MSG + ";");
                } else {
					buffer.append(productVO.getChPriceMessage() + ";");
				}
			} else {
				if (productVO.getNoFreeNightCh().length() != 0) {
                    buffer.append(productVO.getChPriceMessage() + " " + Constants.FREE_NIGHT_PRE_MSG + " " + productVO.getNoFreeNightCh() + " "
                            + Constants.FREE_NIGHT_MSG + "\\");
                } else {
					buffer.append(productVO.getChPriceMessage() + "\\");
				}
			}
			loopid++;
		}
		producCodetdata = buffer.toString();
		return producCodetdata;
	}

    private String setPriceCode(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getPriceCode() + ";");
            } else {
                buffer.append(productVO.getPriceCode() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setOverlapPriceCode(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getOverlapPriceCode() + ";");
            } else {
                buffer.append( productVO.getOverlapPriceCode() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setMinAdPrice(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getMinAdPrice() + ";");
            } else {
                buffer.append(productVO.getMinAdPrice() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setMinChPrice(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getMinChPrice() + ";");
            } else {
                buffer.append(productVO.getMinChPrice() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setMinPriceMsg(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getMinPriceMsg() + ";");
            } else {
                buffer.append(productVO.getMinPriceMsg() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setReductionFlag(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getReductionFlag() + ";");
            } else {
                buffer.append(productVO.getReductionFlag() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }


    private String setAdReductionAmt(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getAdReductionAmt()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getAdReductionAmt()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setAdReductionPerc(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getAdReductionPerc()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getAdReductionPerc()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setChReductionAmt(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getChReductionAmt()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getChReductionAmt()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setChReductionPerc(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getChReductionPerc()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getChReductionPerc()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setNotInUsePos34(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getNotInUsePos34() + ";");
            } else {
                buffer.append(productVO.getNotInUsePos34() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setNotInUsePos35(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getNotInUsePos35() + ";");
            } else {
                buffer.append(productVO.getNotInUsePos35() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setNoFreeNightsAd(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
		String producCodetdata = "";
		int productlistsize = availabilityResponseProductVO.size();
		int loopid = 1;
		StringBuffer buffer = new StringBuffer();
		for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
			if (productlistsize == loopid) {
				if (productVO.getNoFreeNightsAd().length() != 0) {
					buffer.append(String.valueOf(productVO.getNoFreeNightsAd()) + ";");
				} else {
					buffer.append(String.valueOf(productVO.getNoFreeNightsAd()) + ";");
				}
			} else {
				if (productVO.getNoFreeNightsAd().length() != 0) {
					buffer.append(
							String.valueOf(productVO.getNoFreeNightsAd())  + "\\");
				} else {
					buffer.append(String.valueOf(productVO.getNoFreeNightsAd()) + "\\");
				}
			}
			loopid++;
		}
		producCodetdata = buffer.toString();
		return producCodetdata;
	}

    private String setPrcAdFreeNght(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getPrcAdFreeNght()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getPrcAdFreeNght()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setBasedOnAdDuration(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append( String.valueOf(productVO.getBasedOnAdDuration()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getBasedOnAdDuration()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setNoFreeNightCh(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
		String producCodetdata = "";
		int productlistsize = availabilityResponseProductVO.size();
		int loopid = 1;
		StringBuffer buffer = new StringBuffer();
		for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
			if (productlistsize == loopid) {
				if(productVO.getNoFreeNightCh().length()!=0) {
					buffer.append(String.valueOf(productVO.getNoFreeNightCh()) + ";");
				}
				else {
					buffer.append(String.valueOf(productVO.getNoFreeNightCh()) + ";");
				}
			} else {
				if(productVO.getNoFreeNightCh().length()!=0) {
				buffer.append(String.valueOf(productVO.getNoFreeNightCh()) + "\\");
				}
				else {
					buffer.append(String.valueOf(productVO.getNoFreeNightCh()) + "\\");
				}
			}
			loopid++;
		}
		producCodetdata = buffer.toString();
		return producCodetdata;

	}

    private String setPrcChFreeNght(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getPrcChFreeNght()) + ";");
            } else {
                buffer.append( String.valueOf(productVO.getPrcChFreeNght()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setBasedOnChDuration(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getBasedOnChDuration()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getBasedOnChDuration()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setNotInUsePos42(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append( productVO.getNotInUsePos42() + ";");
            } else {
                buffer.append(productVO.getNotInUsePos42() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setCoreIdentifierCode(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getCoreIdentifierCode() + ";");
            } else {
                buffer.append(productVO.getCoreIdentifierCode() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setCoreIdentifierDesc(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(productVO.getCoreIdentifierDesc() + ";");
            } else {
                buffer.append(productVO.getCoreIdentifierDesc() + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setEarlyBirdDiscount(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getEarlyBirdDiscount()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getEarlyBirdDiscount()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }

    private String setEarlyBirdDiscAmt(List<AvailabilityResponseProductVO> availabilityResponseProductVO) {
        String producCodetdata = "";
        int productlistsize = availabilityResponseProductVO.size();
        int loopid = 1;
        StringBuffer buffer=new StringBuffer();
        for (AvailabilityResponseProductVO productVO : availabilityResponseProductVO) {
            if (productlistsize == loopid) {
                buffer.append(String.valueOf(productVO.getEarlyBirdDiscAmt()) + ";");
            } else {
                buffer.append(String.valueOf(productVO.getEarlyBirdDiscAmt()) + "\\");
            }
            loopid++;
        }
        producCodetdata=buffer.toString();
        return producCodetdata;
    }
}