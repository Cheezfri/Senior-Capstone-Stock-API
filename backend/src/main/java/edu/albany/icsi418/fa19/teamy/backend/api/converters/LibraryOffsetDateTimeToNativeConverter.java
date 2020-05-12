package edu.albany.icsi418.fa19.teamy.backend.api.converters;

import org.springframework.core.convert.converter.Converter;
import org.threeten.bp.OffsetDateTime;

public class LibraryOffsetDateTimeToNativeConverter implements Converter<OffsetDateTime, java.time.OffsetDateTime> {
    @Override
    public java.time.OffsetDateTime convert(OffsetDateTime source) {
        return java.time.OffsetDateTime.parse(source.toString());
    }
}
