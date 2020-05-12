package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api.converters;

import org.springframework.core.convert.converter.Converter;
import org.threeten.bp.OffsetDateTime;

public class StringToOffsetDateTimeConverter implements Converter<String, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(String source) {
        return OffsetDateTime.parse(source);
    }
}
