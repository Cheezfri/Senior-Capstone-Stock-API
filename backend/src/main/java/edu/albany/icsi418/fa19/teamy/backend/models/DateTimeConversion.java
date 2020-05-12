package edu.albany.icsi418.fa19.teamy.backend.models;

import org.threeten.bp.OffsetDateTime;

/**
 * Utility Class to convert from java.time.OffsetDateTime to org.threeten.bp.OffsetDateTime
 */
public final class DateTimeConversion {

    private DateTimeConversion(){
        //Utility Classes are not instantiated
    }

    /**
     * converts java.time by (thD = LocalDate && thT = LocalTime && thZo = ZoneOffset)
     *
     * @param offsetDateTime == an instance of java.time.OffsetDateTime
     * @return == an instance of org.threeten.bp.OffsetDateTime
     */

    public static OffsetDateTime toThirteenOffsetDateTime(java.time.OffsetDateTime offsetDateTime) {
        return OffsetDateTime.parse(offsetDateTime.toString());
    }

    /**
     * Converts a org.threeten.bp.OffsetDateTime to a Java native OffsetDateTime.
     *
     * @param offsetDateTime library version of OffsetDateTime
     * @return java.time.OffsetDateTime
     */
    public static java.time.OffsetDateTime toJavaOffsetDateTime(OffsetDateTime offsetDateTime) {
        return java.time.OffsetDateTime.parse(offsetDateTime.toString());
    }
}
