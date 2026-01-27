package com.sist.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${file.upload-dir}")
    private String uploadDir;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 브라우저에서 /download/img.png 로 요청하면
        // 실제로는 C:/upload/img.png 를 찾아라!
        registry.addResourceHandler("/upload/**")
        		.addResourceLocations("file:" + uploadDir);
    }
}