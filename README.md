policy

policy的json格式如下
 {
    "policyName":"ems_cpu_policy",
    "condition": {
        "objDN":"appName=app1",
        "eventTypes": [
            10011,
            10021,
            10031
        ]
    },
    "actions": [
        "MAIL",
        "SMS",
        "UI"
    ]
}





一次吐出一个 List<ResultEvent >作为KAFKA的输入输出，此list应该包含所有rule的检测结果
 
public class ResultEvent {
    private int eventType;
    private long startTime;
    private long endTime; // 生成时不需要填写此字段
    private String objDN;
    private String detail;
｝
目前确定的eventType如下
5位数字规则：
第一位表示 监控的OBJDN类型 APP/SERVICE 等
中间3位表示 错误种类
最后一位 1为warning 2为critical
 