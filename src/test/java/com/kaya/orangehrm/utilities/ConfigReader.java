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
    private static Properties properties;

    static {
        try {
            logger.info("config.properties dosyası Classpath üzerinden okunuyor...");

            // 1. ClassLoader ile dosyayı bul (Hardcode yol yok!)
            InputStream inputStream = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("config.properties");

            if (inputStream == null) {
                logger.error("config.properties dosyası Classpath'te bulunamadı!");
                throw new RuntimeException("Kritik Hata: config.properties dosyası eksik!");
            }

            properties = new Properties();

            // 2. Karekter için iyi fikir: UTF-8 ile oku (Türkçe karakter sorunu olmasın)
            // 3. Modern Java: try-with-resources ile reader otomatik kapanır
            try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }

            logger.info("config.properties başarıyla yüklendi.");
            logger.info("Aktif Tarayıcı: {}", properties.getProperty("browser"));
            logger.info("Aktif URL: {}", properties.getProperty("url"));

        } catch (IOException e) {
            logger.error("Config dosyası okunurken IO hatası: {}", e.getMessage());
            throw new RuntimeException("Config dosyası okunamadı!", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Aranan property bulunamadı: {}", key);
        }
        return value;
    }

    public static int getIntProperty(String key) {
        String value = getProperty(key);
        if (value == null) return 0;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            logger.error("Property int'e çevrilemedi (Hatalı değer): {} = {}", key, value);
            return 0;
        }
    }

    public static boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        if (value == null) return false;
        return Boolean.parseBoolean(value.trim());
    }
}