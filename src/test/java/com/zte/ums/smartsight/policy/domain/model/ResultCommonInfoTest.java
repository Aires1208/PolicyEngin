package com.zte.ums.smartsight.policy.domain.model;

import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by root on 11/8/16.
 */
public class ResultCommonInfoTest {
    private ResultCommonInfo resultCommonInfo;

    @Before
    public void setup() {
        resultCommonInfo = new ResultCommonInfo();
        CommonInfo commonInfo = new CommonInfo();
        ResMsg resMsg = new ResMsg();
        resMsg.setResInfo("Test resmsg !");
        resMsg.setStatus(0);
        commonInfo.setResMsg(resMsg);
        commonInfo.setPolicyActions(newArrayList("Mail action", "UI action"));
        commonInfo.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL));

        resultCommonInfo.setCommonInfo(JsonUtils.obj2JSON(commonInfo));

    }

    @Test
    public void should_be_return_commoninfo_getCommonInfo() throws Exception {
        System.out.println(resultCommonInfo.toString());
        Assert.assertEquals("[\"APP_CALLHEAVY_CRITICAL\"]", resultCommonInfo.getCommonInfo().get("eventTypes").toString());
    }

}