package com.eme22.citasApp.model.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class QueryConverterFactory extends Converter.Factory {
    public static QueryConverterFactory create() {
        return new QueryConverterFactory();
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == LocalDate.class) {
            return DateQueryConverter.INSTANCE;
        }
        return null;
    }

    private static final class DateQueryConverter implements Converter<LocalDate, String> {
        static final DateQueryConverter INSTANCE = new DateQueryConverter();

        private static final ThreadLocal<DateTimeFormatter> DF = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("MM/dd/yy"));

        @Override
        public String convert(LocalDate date) {
            return DF.get().format(date);
        }
    }
}