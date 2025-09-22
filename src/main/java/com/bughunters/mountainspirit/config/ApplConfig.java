package com.bughunters.mountainspirit.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplConfig {
    @Bean
    public ModelMapper modelMapper() {return new ModelMapper();}
}
