package com.kaya.orangehrm.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    // Logger oluştur (log4j2 kullanımı)
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);

    // Properties nesnesi (config.properties dosyasını tutar)
    private static Properties properties;

    // Constructor (sınıf yüklendiğinde otomatik çalışır)
    static {
        try {
            logger.info("config.properties dosyası okunuyor...");
            properties = new Properties();

            // ClassLoader ile dosyayı oku (dosya yolu hardcode değil!)
            InputStream inputStream = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("config.properties");

            if (inputStream == null) {
                logger.error("config.properties dosyası bulunamadı!");
                throw new RuntimeException("config.properties dosyası bulunamadı!");
            }

            properties.load(inputStream);
            logger.info("config.properties başarıyla yüklendi.");
            logger.info("Browser: {}", properties.getProperty("browser"));
            logger.info("URL: {}", properties.getProperty("url"));

        } catch (IOException e) {
            logger.error("config.properties okunurken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("Config dosyası okunamadı!", e);
        }
    }

    // Property değerini String olarak döndür
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property bulunamadı: {}", key);
            return null;
        }
        return value;
    }

    // Property değerini int olarak döndür (timeout'lar için)
    public static int getIntProperty(String key) {
        String value = getProperty(key);
        if (value == null) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.error("Property int'e çevrilemedi: {} = {}", key, value);
            return 0;
        }
    }

    // Property değerini boolean olarak döndür (headless mode için)
    public static boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }
}