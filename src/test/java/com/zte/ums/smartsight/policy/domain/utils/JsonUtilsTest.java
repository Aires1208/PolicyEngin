package com.zte.ums.smartsight.policy.domain.utils;

import com.zte.ums.smartsight.policy.domain.model.Position;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by root on 11/7/16.
 */
public class JsonUtilsTest {
    JsonUtils jsonUtils;

    @Test
    public void setup() {
        jsonUtils = new JsonUtils();
    }

    @Test
    public void should_be_true_when_Position_was_given_as_follow() throws Exception {
        Position position = new Position();
        position.setExist(true);
        position.setPolicyName("Call_Heavy");
        String ObjToString = jsonUtils.serialize(position);
        Assert.assertEquals("{\"exist\":true,\"policyName\":\"Call_Heavy\"}", ObjToString);

    }

    @Test
    public void should_be_equals_when_position_is_call_heavy_call_deserialize() throws Exception {
        Assert.assertEquals("Call_Heavy", jsonUtils.deserialize("{\"exist\":true,\"policyName\":\"Call_Heavy\"}", Position.class).getPolicyName());
    }


    @Test
    public void should_be_true_when_position_was_gaven_call_obj2JSON() throws Exception {
        Position position = new Position();
        position.setExist(true);
        position.setPolicyName("Call_Heavy");
        Assert.assertEquals("Call_Heavy", jsonUtils.obj2JSON(position).getString("policyName"));
    }

}