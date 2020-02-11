//package com.library.library.annotation;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.event.ContextRefreshedEvent;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.CodeSource;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class CustomLibraryBanner implements ApplicationListener<ContextRefreshedEvent> {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(CustomLibraryBanner.class);
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//
//		try {
//			InputStream inputStream = Thread.currentThread()
//					.getContextClassLoader()
//					.getResourceAsStream("META-INF/LibraryBanner.text");
//
//			String bannerText = buildBannerText();
//			if (LOGGER.isInfoEnabled()) {
//				LOGGER.info(bannerText);
//			} else {
//				System.out.print(bannerText);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}
