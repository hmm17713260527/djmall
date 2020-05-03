package com.dj.mall.platform;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform
 * @ClassName: PlatformApplication
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/1 21:19
 * @Version: 1.0
 */
@EnableDubbo
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}
