package com.kaya.orangehrm.hooks;

import com.kaya.orangehrm.base.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("===== Senaryo başlıyor: {} =====", scenario.getName());
        DriverManager.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
            logger.error("Senaryo BAŞARISIZ: {}", scenario.getName());
        }

        logger.info("===== Senaryo bitti: {} | Durum: {} =====",
                scenario.getName(), scenario.getStatus());
        DriverManager.quitDriver();
    }
}