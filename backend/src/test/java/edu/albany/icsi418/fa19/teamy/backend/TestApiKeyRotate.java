package edu.albany.icsi418.fa19.teamy.backend;

import edu.albany.icsi418.fa19.teamy.backend.asset.apis.ApiKeys;
import org.junit.Assert;
import org.junit.Test;

public class TestApiKeyRotate {
    @Test
    public void testRotation() {
        String[] keys = {"7Z40B0HCO8LQT1G2", "I4AG5ZROL2WCLV3U", "S6QQRCBKDDD4Q7WT", "3XXEXTUNG28W33MW", "EY3E2L5YI4F3QQW5"};
        Assert.assertEquals("Initial Index 0", 0, ApiKeys.getIndex());
        Assert.assertEquals("Initial Key", keys[0], ApiKeys.getCurrentKey());
        Assert.assertEquals("Rotate Key 1", keys[1], ApiKeys.rotateKeys());
        Assert.assertEquals("Current Key 1", keys[1], ApiKeys.getCurrentKey());
        Assert.assertEquals("Rotate Key 2", keys[2], ApiKeys.rotateKeys());
        Assert.assertEquals("Current Key 2", keys[2], ApiKeys.getCurrentKey());
        Assert.assertEquals("Rotate Key 3", keys[3], ApiKeys.rotateKeys());
        Assert.assertEquals("Current Key 3", keys[3], ApiKeys.getCurrentKey());
        Assert.assertEquals("Rotate Key 4", keys[4], ApiKeys.rotateKeys());
        Assert.assertEquals("Current Key 4", keys[4], ApiKeys.getCurrentKey());
        Assert.assertEquals("Rotate Back to Key 0", keys[0], ApiKeys.rotateKeys());
        Assert.assertEquals("Current Key 0", keys[0], ApiKeys.getCurrentKey());
    }
}
