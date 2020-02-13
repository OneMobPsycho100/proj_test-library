package com.dio.frame.autoconfigure;

import com.dio.frame.mybatisplus.plugin.AutoMaticGenerateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/12 21:45
 */
@Configuration
@Import({AutoMaticGenerateInterceptor.class, CustomBanner.class})
public class FrameWorkAutoConfiguration {
}
