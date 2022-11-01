package com.myprojects.myportfolio.clients.utils;

import com.myprojects.myportfolio.clients.general.views.CustomStringToIViewConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(new CustomStringToIViewConverter());
    }
}
