package edu.albany.icsi418.fa19.teamy.backend.asset.apis;

/**
 * ApiKeys holds all the ApiKeys to deal with Api limit problems by rotating keys
 */
public final class ApiKeys {
    private static final String[] keys = {"7Z40B0HCO8LQT1G2", "I4AG5ZROL2WCLV3U", "S6QQRCBKDDD4Q7WT", "3XXEXTUNG28W33MW", "EY3E2L5YI4F3QQW5"};
    private static int index;

    /**
     * Constructor that instantiates count to 0 to start with ApiKey one
     */
    private ApiKeys() {
        // Do not instantiate Utility classes
    }

    /**
     * used to rotateKeys based on count of ApiKeys
     *
     * @return = String of ApiKey
     */
    public static String rotateKeys() {
        if (index < keys.length - 1) {
            index++;
        } else {
            index = 0;
        }
        return keys[index];
    }

    /**
     * Gets all ApiKeys
     *
     * @return = Gets all ApiKeys
     */
    public static String[] getKeys() {
        return keys;
    }

    /**
     * returns the current ApiKey being used based on count
     *
     * @return String of current ApiKey
     */
    public static String getCurrentKey() {
        return keys[index];
    }

    public static int getIndex() {
        return index;
    }
}