package com.zte.ums.smartsight.policy.domain.constant;

/**
 * Created by 10172605 on 9/6/16.
 */
public final class EnvConstant {
    //kafka environment message
    public static final String ZK_CONNECT = getEnvConst("Kafka_ZK", "127.0.0.1:2181");

    public static final String GROUP_ID = "group1";

    public static final String TOPIC = "result_event";

    public static final String SERIALIZER_CLASS = "kafka.serializer.StringEncoder";

    // hbase environment message
    public static final String ZK_QUORUM = getEnvConst("HBase_IP", "127.0.0.1");

    public static final String ZK_PORT = getEnvConst("HBase_Port", "2181");


    public static final String MAIL_SERVER_HOST = "10.62.54.20";
    public static final String MAIL_SERVER_PORT = "25";
    public static final String MAIL_SERVER_USER_NAME = "tangbaoyu";
    public static final String MAIL_SERVER_PASSWORD = "Aa888888";

    private static String getEnvConst(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null ? defaultValue : value;
    }

}