package com.makersweb.mwusers.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makersweb.mwusers.infrastructure.json.Json;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author aaristides
 */
@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return Json.mapper();
    }

}
