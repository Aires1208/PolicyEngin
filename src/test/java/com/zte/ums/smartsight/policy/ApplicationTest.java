package com.zte.ums.smartsight.policy;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by root on 11/8/16.
 */
public class ApplicationTest {
    @Test
    public void should_return_true_when_set_Debug_is_true() throws Exception {
        Application.setDebug(true);

        assertTrue(Application.isDebug());
    }

    @Test
    public void should_return_false_when_set_Debug_is_false() throws Exception {
        Application.setDebug(false);

        assertFalse(Application.isDebug());
    }

    @Test
    public void should_return_false_when_get_default_Debug() throws Exception {

        assertFalse(Application.isDebug());
    }

}