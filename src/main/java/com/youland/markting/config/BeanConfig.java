package com.youland.markting.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

@Configuration
public class BeanConfig {

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }

    @Bean
    public UrlPathHelper urlPathHelper() {
        return UrlPathHelper.defaultInstance;
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("encompass-schedule-task");
        return threadPoolTaskScheduler;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule timeModule = new SimpleModule();
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
        timeModule.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        timeModule.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        timeModule.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        timeModule.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        timeModule.addSerializer(Instant.class, new ToStringSerializer());
        timeModule.addSerializer(LocalDate.class, new ToStringSerializer());
        timeModule.addSerializer(LocalTime.class, new ToStringSerializer());
        timeModule.addSerializer(LocalDateTime.class, new ToStringSerializer());
        mapper.registerModule(timeModule);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

}
