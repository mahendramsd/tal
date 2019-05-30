/*
 *
 *  *  Copyright (c) 1995-2018,  The Data Management Group Ltd   All Rights Reserved.
 *  *  *  PROPRIETARY AND COPYRIGHT NOTICE.
 *  *
 *  *    This software product contains information which is proprietary to
 *  *    and considered a trade secret The Data management Group Ltd .
 *  *    It is expressly agreed that it shall not be reproduced in whole or part,
 *  *    disclosed, divulged or otherwise made available to any third party directly
 *  *    or indirectly.  Reproduction of this product for any purpose is prohibited
 *  *    without written authorisation from the The Data Management Group Ltd
 *  *    All Rights Reserved.
 *  *
 *  *    E-Mail andyj@datam.co.uk
 *  *    URL : www.datam.co.uk
 *  *    Written By : Asanka Anthony
 *  *
 *
 */
package lk.dmg.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.dmg.common.exception.TalcacheException;
import lk.dmg.common.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class RedisAbstractService<E extends Serializable> {
    private static final Logger log = LoggerFactory.getLogger(RedisAbstractService.class);
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> hashOperations;
    @Autowired
    ObjectMapper objectMapper;

    @PostConstruct
    void setProperties() {
        hashOperations = redisTemplate.opsForHash();
    }

    /**
     * Put data to redis
     *
     * @param redisFileName
     * @param hashKey
     * @param value
     * @throws TalcacheException
     */
    public void set(String redisFileName, String hashKey, E value) throws TalcacheException {
        long lStartTime = Instant.now().toEpochMilli();
        try {
            hashOperations.put(redisFileName, hashKey, objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            throw new TalcacheException("42", "Data Serializer error", Constants.DATA_PROCESSING_ERROR, e);
        }
        if (log.isDebugEnabled()) {
            long lEndTime = Instant.now().toEpochMilli();
            long output = lEndTime - lStartTime;
            log.debug("Retrieve File: " + redisFileName + " By Key: " + hashKey + " Value : " + value.toString() + " Elapsed time in milliseconds: " + output);
        }
    }

    /**
     * @param redisFileName
     * @param hashKey
     * @param eClass
     * @return
     * @throws TalcacheException
     */
    public E get(String redisFileName, String hashKey, Class<E> eClass) throws TalcacheException {
        long lStartTime = Instant.now().toEpochMilli();
        String result = hashOperations.get(redisFileName, hashKey);
        E e = null;
        if (result != null) {
            try {
                e = objectMapper.readValue(result, eClass);
            } catch (IOException e1) {
                throw new TalcacheException("43", "Data deserializer error", Constants.DATA_PROCESSING_ERROR, e1);
            }
            if (log.isDebugEnabled()) {
                long lEndTime = Instant.now().toEpochMilli();
                long output = lEndTime - lStartTime;
                log.debug("Retrieve File: " + redisFileName + " By Key: " + hashKey + " Class : " + eClass.getSimpleName() + " Elapsed time in milliseconds: " + output);
            }
        }
        return e;
    }

    /**
     * @param redisFileName
     * @param hashKeySet
     * @return
     * @throws TalcacheException
     */
    public List<E> getByKeySet(String redisFileName, Set<String> hashKeySet, Class<E> eClass) throws TalcacheException {
        long lStartTime = Instant.now().toEpochMilli();
        List<E> myObjects = new ArrayList<>();
        List<String> s = hashOperations.multiGet(redisFileName, hashKeySet);
        if (log.isDebugEnabled()) {
            long lEndTime = Instant.now().toEpochMilli();
            long output = lEndTime - lStartTime;
            log.debug("Retrieve File: " + redisFileName + " By Total Keys: " + hashKeySet.size() + " Class : " + eClass.getSimpleName() + "Elapsed time in milliseconds: " + output);
        }

        try {
            long lStartTime1 = Instant.now().toEpochMilli();
            JavaType type = objectMapper.getTypeFactory().
                    constructCollectionType(List.class, eClass);
            myObjects = objectMapper.readValue(s.toString(), type);
            long lEndTime1 = Instant.now().toEpochMilli();
            long output = lEndTime1 - lStartTime1;
            log.debug("Elapsed time in milliseconds TO JSON: " + output);
        } catch (IOException e) {
            throw new TalcacheException("44", "Data List deserializer error", Constants.DATA_PROCESSING_ERROR, e);
        }

        return myObjects;

    }

}


