package com.dj.mall.platform.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WEB MVC 配置
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(tokenInterceptor);
        // 拦截请求
        interceptorRegistration.addPathPatterns("/platform/auth/**");
    }
}
