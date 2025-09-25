package com.bughunters.mountainspirit.config;

import org.apache.ibatis.annotations.Mapper;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.bughunters.mountainspirit",annotationClass = Mapper.class)
public class ApplConfig {
    @Bean
    public ModelMapper modelMapper() {return new ModelMapper();}
}
