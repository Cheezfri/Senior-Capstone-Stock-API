package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.configuration;

import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api.converters.LibraryOffsetDateTimeToNativeConverter;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api.converters.StringToOffsetDateTimeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToOffsetDateTimeConverter());
        registry.addConverter(new LibraryOffsetDateTimeToNativeConverter());
    }
}
