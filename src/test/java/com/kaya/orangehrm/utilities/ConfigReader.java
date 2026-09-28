package com.kaya.orangehrm.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static final Properties properties = new Properties();

    // static blok: sınıf ilk kez kullanıldığında BİR KERE çalışır
    static {
        logger.info("config.properties dosyası classpath üzerinden okunuyor...");

        try (InputStream inputStream = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                logger.error("config.properties classpath'te bulunamadı!");
                throw new RuntimeException("Kritik hata: config.properties dosyası eksik!");
            }

            try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }

            logger.info("config.properties yüklendi. Tarayıcı: {} | URL: {}",
                    properties.getProperty("browser"), properties.getProperty("url"));

        } catch (IOException e) {
            logger.error("config.properties okunurken IO hatası: {}", e.getMessage());
            throw new RuntimeException("config.properties okunamadı!", e);
        }
    }

    // Metin değer okur (başındaki/sonundaki boşlukları temizler)
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Aranan property bulunamadı: {}", key);
            return null;
        }
        return value.trim();
    }

    // Sayısal değer okur (örn. explicitWait=15)
    public static int getIntProperty(String key) {
        String value = getProperty(key);
        if (value == null) {
            throw new RuntimeException("'" + key + "' anahtarı config.properties içinde yok!");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("'" + key + "' sayıya çevrilemedi, değer: " + value, e);
        }
    }

    // true/false değer okur (örn. maximizeWindow=true)
    public static boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return value != null && Boolean.parseBoolean(value);
    }
}