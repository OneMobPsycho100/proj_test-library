package com.library.library.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/3 20:45
 */
@ConfigurationProperties(prefix = "secure.urls")
public class ExcludeUrlsProperties {
    private List<String> exclude = new ArrayList<>();

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }
}
