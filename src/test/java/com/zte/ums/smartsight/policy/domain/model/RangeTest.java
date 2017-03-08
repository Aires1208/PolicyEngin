package com.zte.ums.smartsight.policy.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * Created by root on 11/8/16.
 */
public class RangeTest {
    private Range range;
    private Range unCheckRange;
    private Range checkedRange;

    @Before
    public void setUp() throws Exception {
        range = new Range(1478591495730l, 1478595095730l);
        unCheckRange = Range.createUncheckedRange(1478591495730l, 1478595095730l);
        checkedRange = new Range(1478591495731l, 1478595095731l, true);
    }

    @Test
    public void getFrom() throws Exception {
        Assert.assertEquals(1478591495730l, unCheckRange.getFrom());

    }

    @Test
    public void getTo() throws Exception {
        Assert.assertEquals(1478595095730l, unCheckRange.getTo());

    }

    @Test
    public void getRange() throws Exception {
        Assert.assertEquals(60 * 1000 * 60, unCheckRange.getRange());
    }

    @Test
    public void validate() throws Exception {
        try {
            Range range = new Range(123456, 123);
        } catch (IllegalArgumentException e) {
            Assert.assertThat(e.getMessage(), containsString("invalid range:"));

        }

    }

    @Test
    public void equals() throws Exception {
        Assert.assertTrue(range.equals(unCheckRange));
    }

    @Test
    public void should_be_equals() throws Exception {
        Assert.assertTrue(range.equals(range));
    }

    @Test
    public void should_be_null() throws Exception {
        Assert.assertFalse(range.equals(null));
    }

    @Test
    public void should_be_not_equals_when_from_not_same() throws Exception {
        Assert.assertFalse(range.equals(new Range(1478591495734l, 1478595095730l)));
    }

    @Test
    public void should_be_not_equals_when_to_not_same() throws Exception {
        Assert.assertFalse(range.equals(new Range(1478591495730l, 1478595095732l)));
    }


    @Test
    public void splitRangeForTimeRanges() throws Exception {
        Assert.assertEquals(1478591495730l, unCheckRange.splitRangeForTimeRanges(10, 100).get(0).getFrom());

    }

    @Test
    public void should_be_return_Ranges() throws Exception {
        Assert.assertEquals(1478591495730l, unCheckRange.splitRangeForTimeRanges(0, 100).get(0).getFrom());

    }

    @Test
    public void should_be_return_Ranges_with_not_spi() throws Exception {
        Assert.assertEquals(1478591495730l, unCheckRange.splitRangeForTimePoints(0).get(0).longValue());

    }

    @Test
    public void splitRangeForTimePoints() throws Exception {
        Assert.assertEquals(new Long(1478591495730l), unCheckRange.splitRangeForTimePoints(10).get(0));
//        System.out.println(unCheckRange.splitRangeForTimePoints(10).get(0));
    }

    @Test
    public void hashCodeTest() {
        Assert.assertEquals(1571740608, unCheckRange.hashCode());

    }

    @Test
    public void toStringTest() {
//        System.out.println(unCheckRange.toString());
        Assert.assertEquals("Range{from=1478591495730, to=1478595095730, range=3600000}", unCheckRange.toString());

    }

}