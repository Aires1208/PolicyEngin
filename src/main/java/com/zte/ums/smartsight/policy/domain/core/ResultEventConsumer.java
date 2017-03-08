package com.zte.ums.smartsight.policy.domain.core;

import com.zte.ums.smartsight.policy.domain.constant.EnvConstant;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 10183966 on 9/6/16.
 */
@Service
public class ResultEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultEventConsumer.class);

    @Autowired
    private PolicyManagerCenter policyManagerCenter;

    @Autowired
    private ResultEventHandler resultEventHandler;

    private ConsumerConnector getConsumerConnector() {
        Properties props = getProperties();
        return Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    }

    private Properties getProperties() {
        Properties props = new Properties();
//        props.put("zookeeper.connect","10.62.100.76:2181");
        props.put("zookeeper.connect", EnvConstant.ZK_CONNECT);
        props.put("group.id", EnvConstant.GROUP_ID);
        return props;
    }

    public void consume() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(EnvConstant.TOPIC, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams = getConsumerConnector().createMessageStreams(map);
        List<KafkaStream<byte[], byte[]>> streams = topicMessageStreams.get(EnvConstant.TOPIC);
        if (null != streams) {
            LOGGER.info("consumer resultevent ................");
            consumeMessage(streams);
        }
    }

    private void consumeMessage(List<KafkaStream<byte[], byte[]>> streams) {
        for (final KafkaStream<byte[], byte[]> stream : streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
//                List<ResultEvent> resultEvents = JsonUtils.deserializeList(new String(it.next().message()), ResultEvent.class);
                ResultEvent resultEvent = JsonUtils.deserialize(new String(it.next().message()), ResultEvent.class);
                handleResultEvent(resultEvent);
//                }
            }
        }
    }

    protected void handleResultEvent(ResultEvent resultEvent) {
        LOGGER.info("1 resultEvent detail:" + resultEvent.toString());
        try {
            ResultEvent resultEventExist = resultEventHandler.getSignalResultEvent("ActiveEvent", resultEvent.getObjDN(), resultEvent.getEventType());
            LOGGER.info("10 resultEventExist detail:" + resultEventExist.toString());
            if (null != resultEventExist && null != resultEventExist.getObjDN()) {
                resultEventExist.setEventType(resultEvent.getEventType());
                resultEventExist.setEndTime(resultEvent.getStartTime());
                LOGGER.info("11 resultEventExist detail:" + resultEventExist.toString());
                LOGGER.info("12 insert HistoryEvent:" + resultEventExist.toString());
                resultEventHandler.insert("HistoryEvent", resultEventExist);
            }
            LOGGER.info("13 insert ActiveEvent:" + resultEventExist.toString());
            resultEventHandler.insert("ActiveEvent", resultEvent);
            policyManagerCenter.trigger(resultEvent);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}