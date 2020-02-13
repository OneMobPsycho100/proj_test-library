package com.dio.frame.autoconfigure;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.InputStream;
import java.security.CodeSource;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/11 13:42
 */
public class CustomBanner implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBanner.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String bannerText = getBannerText();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(bannerText);
        } else {
            System.out.print(bannerText);
        }
    }

    private String getBannerText() {
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("META-INF/custom-banner.txt");
        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            String bannerText = scanner.useDelimiter("\\A").next();
            Pattern pattern = Pattern.compile("\\{\\s*VERSION\\}");
            Matcher matcher = pattern.matcher(bannerText);
            if (matcher.find()) {
                String versionPlaceholder = matcher.group();
                String versionInfo = getVersion(CustomBanner.class, "");
                if (StringUtils.isNoneBlank(versionInfo)) {
                    versionInfo = "(v" + versionInfo + ")";
                }
                versionInfo = StringUtils.leftPad(versionInfo, versionPlaceholder.length());
                return bannerText.replace(versionPlaceholder, versionInfo);
            }
        }
        return null;
    }

        /**
         * Gets version.
         *
         * @param cls
         *            the cls
         * @param defaultVersion
         *            the default version
         * @return the version
         */
        private String getVersion ( final Class<?> cls, final String defaultVersion){
            try {
                // find version info from MANIFEST.MF first
                String version = cls.getPackage().getImplementationVersion();
                if (version == null || version.length() == 0) {
                    version = cls.getPackage().getSpecificationVersion();
                }
                if (version == null || version.length() == 0) {
                    // guess version fro jar file name if nothing's found from MANIFEST.MF
                    CodeSource codeSource = cls.getProtectionDomain().getCodeSource();
                    if (codeSource == null) {
                        LOGGER.info("No codeSource for class " + cls.getName() + " when getVersion, use default version "
                                + defaultVersion);
                    } else {
                        String file = codeSource.getLocation().getFile();
                        if (file != null && file.length() > 0 && file.endsWith(".jar")) {
                            file = file.substring(0, file.length() - 4);
                            int i = file.lastIndexOf('/');
                            if (i >= 0) {
                                file = file.substring(i + 1);
                            }
                            i = file.indexOf("-");
                            if (i >= 0) {
                                file = file.substring(i + 1);
                            }
                            while (file.length() > 0 && !Character.isDigit(file.charAt(0))) {
                                i = file.indexOf("-");
                                if (i >= 0) {
                                    file = file.substring(i + 1);
                                } else {
                                    break;
                                }
                            }
                            version = file;
                        }
                    }
                }
                // return default version if no version info is found
                return version == null || version.length() == 0 ? defaultVersion : version;
            } catch (Throwable e) {
                // return default version when any exception is thrown
                LOGGER.error("return default version, ignore exception " + e.getMessage(), e);
                return defaultVersion;
            }
        }
    }