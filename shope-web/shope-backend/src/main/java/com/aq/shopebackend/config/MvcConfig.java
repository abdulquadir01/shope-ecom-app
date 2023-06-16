package com.aq.shopebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;



@Configuration
public class MvcConfig implements WebMvcConfigurer {
    String dirName = "user-photos";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path userPhotosDir = Paths.get(dirName);

        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/"+ dirName + "/**")
                .addResourceLocations("file:/" +userPhotosPath+ "/");
    }
}
