package com.kaya.orangehrm.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    // ThreadLocal: paralel çalışmada her thread'in kendi driver'ı olur
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        if (driver.get() == null) {
            logger.info("Chrome driver başlatılıyor...");
            WebDriverManager.chromedriver().setup();
            WebDriver chromeDriver = new ChromeDriver();
            chromeDriver.manage().window().maximize();
            driver.set(chromeDriver);
            logger.info("Chrome driver başarıyla başlatıldı.");
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("Chrome driver kapatıldı.");
        }
    }
}