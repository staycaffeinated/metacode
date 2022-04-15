<#include "/common/Copyright.ftl">
package ${project.basePackage}.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * When support for LocalDate in request parameters is needed
 */
@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

    /*
     * These formats are arbitrary choices; change these to your desired formats.
     */
    private static final List<String> SUPPORTED_FORMATS = Arrays.asList("dd-MM-yyyy", "yyyy-MM-dd");

    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS = SUPPORTED_FORMATS.stream()
        .map(DateTimeFormatter::ofPattern).toList();

    @Override
    public LocalDate convert(String source) {
        for (DateTimeFormatter dateTimeFormatter : DATE_TIME_FORMATTERS) {
            try {
                return LocalDate.parse(source, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                // keep this empty so all parsers run
            }
        }
        throw new DateTimeException(String.format("Unable to parse [%s]. Supported formats are: %s", source, SUPPORTED_FORMATS));
    }
}