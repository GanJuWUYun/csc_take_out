package com.csc.tackout.config;

import com.csc.tackout.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * ClassName:WebConfig
 * Package:com.csc.tackout.config
 * Description:
 *
 * @Date:24/7/2022 12:13
 * @Author:2394279444@qq.com
 */
@Slf4j
@Configuration
public class WebMVConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("静态资源加载中...");
       registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
       registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 扩展mvc消息转换器
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, mappingJackson2HttpMessageConverter);
    }
}
