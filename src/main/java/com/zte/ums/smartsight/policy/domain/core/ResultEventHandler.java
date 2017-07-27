package com.zte.ums.smartsight.policy.domain.core;

import com.zte.ums.smartsight.policy.domain.constant.PolicyConstant;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import com.zte.ums.smartsight.policy.domain.utils.TimeUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by aires on 9/26/16.
 */
@Component
public class ResultEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResultEventHandler.class);

    @Autowired
    private HbaseTemplate hbaseTemplate;

    /**
     * insert and update resultEvent
     */
    public void insert(String tableName, ResultEvent resultEvent) {
        if (null == resultEvent) {
            throw new PolicyException("ResultEvent must be not null");
        }
        String objDN = resultEvent.getObjDN();
        if (null == objDN || objDN.isEmpty()) {
            objDN = "unknown";
        }
        String rowKey;
        if ("ActiveEvent".equalsIgnoreCase(tableName)) {
            rowKey = objDN;
        } else {
            long startTime = resultEvent.getStartTime();
            rowKey = generateRowKey(objDN, startTime);
        }
        byte[] resultEventValue = JsonUtils.serialize(resultEvent).getBytes();
        hbaseTemplate.put(tableName, rowKey, PolicyConstant.EVENT_CF, String.valueOf(resultEvent.getEventType()), resultEventValue);
    }


    private String generateRowKey(String objDN, long timestamp) {
        long reverseTimestamp = TimeUtils.reverseTimeMillis(timestamp);
        return objDN + "^" + reverseTimestamp;
    }

    /**
     * get signal result event
     */
    public ResultEvent getSignalResultEvent(String tableName, String objDN, String eventCode) {
        logger.info("2 tableName:" + tableName);
        logger.info("3 objDN:" + objDN);
        logger.info("4 eventCode:" + eventCode);
        return hbaseTemplate.get(tableName, objDN, (result, rowNum) -> {
            logger.info("5 getSignalResultEvent eventCode:" + eventCode);
            logger.info("6 getSignalResultEvent result:" + result.toString());
            return getResultEvent(eventCode, result);
        });
    }

    public ResultEvent getResultEvent(String eventCode, Result result) {
        ResultEvent resultEvent = new ResultEvent();
        String rowKey = Bytes.toString(result.getRow());
        logger.info("7 rowKey:" + rowKey);
        byte[] resultEventBytes = result.getValue(Bytes.toBytes(PolicyConstant.EVENT_FAMILY_NAME), Bytes.toBytes(eventCode));
        logger.info("8 resultEventBytes:" + resultEventBytes);
        if (null != resultEventBytes && resultEventBytes.length > 0) {
            String resultEventJson = Bytes.toString(resultEventBytes);
            logger.info("9 resultEventJson:" + resultEventJson);
            resultEvent = JsonUtils.deserialize(resultEventJson, ResultEvent.class);

        }
        logger.info("10 getResultEvent resultEvent:" + resultEvent.toString());
        return resultEvent;
    }

    /**
     * get result events list
     */
//    public List<ResultEvent> getResultEvents(String tableName, String objDN, int eventCode, Range range) {
//        return hbaseTemplate.find(tableName, new Scan(Bytes.toBytes(generateRowKey(objDN, range.getTo())), Bytes.toBytes(generateRowKey(objDN, range.getFrom()))), resultScanner -> {
//            List<ResultEvent> resultEvents = newArrayList();
//            for (Result result : resultScanner) {
//                String rowKey = Bytes.toString(result.getRow());
//                logger.info("rowKey:" + rowKey);
//                byte[] resultEventBytes = result.getValue(Bytes.toBytes(PolicyConstant.EVENT_FAMILY_NAME), Bytes.toBytes(String.valueOf(eventCode)));
//                String resultEvent = Bytes.toString(resultEventBytes);
//                resultEvents.add(JsonUtils.deserialize(resultEvent, ResultEvent.class));
//            }
//            return resultEvents;
//        });
//    }
}
