package com.library.library.component;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/11 13:42
 */
public class CustomLibraryBanner implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLibraryBanner.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("META-INF/LibraryBanner.txt");
        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            String bannerText = scanner.useDelimiter("\\A").next();
            Pattern pattern = Pattern.compile("\\{\\s*VERSION\\}");
            Matcher matcher = pattern.matcher(bannerText);
            if (matcher.find()) {
                String versionPlaceholder = matcher.group();
                String versionInfo = "(v" + "1.0.0" + ")";
                versionInfo = StringUtils.leftPad(versionInfo, versionPlaceholder.length());
                bannerText = bannerText.replace(versionPlaceholder, versionInfo);
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(bannerText);
            } else {
                System.out.print(bannerText);
            }
        }
    }
}
