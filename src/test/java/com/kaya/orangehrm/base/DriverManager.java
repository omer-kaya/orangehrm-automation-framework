package com.kaya.orangehrm.base;

import com.kaya.orangehrm.utilities.ConfigReader; // 1. NEYİ ÇAĞIRIYORUZ? ConfigReader'ı!
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverManager {

    // 2. NEYİ TANIMLIYORUZ? ThreadLocal<WebDriver>
    // NEDEN? Paralel test çalıştırıldığında her testin kendi tarayıcı örneği olsun diye.
    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    // 3. NEYİ TANIMLIYORUZ? Logger
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    /**
     * Tarayıcıyı başlatan ve temel ayarlarını yapan method.
     * Test başlamadan önce (örn: @BeforeMethod veya Cucumber @Before hook'unda) çağrılır.
     */
    public static void setupDriver() {
        logger.info("==================================================");
        logger.info("TARAYICI KURULUMU BAŞLATILIYOR...");

        // 4. NEYİ NASIL ÇAĞIRIYORUZ? ConfigReader üzerinden properties değerlerini alıyoruz.
        String browser = ConfigReader.getProperty("browser").toLowerCase().trim();
        boolean isHeadless = ConfigReader.getBooleanProperty("headless");
        int implicitWait = ConfigReader.getIntProperty("implicitWait");

        logger.info("Seçilen Tarayıcı: {}", browser);
        logger.info("Headless Modu: {}", isHeadless);

        // 5. TARAYICIYA KARAR VERME (Switch-Case Yapısı)
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new"); // Yeni headless modu
                }
                chromeOptions.addArguments("--start-maximized"); // Tam ekran başlat
                chromeOptions.addArguments("--disable-notifications"); // Bildirimleri engelle

                logger.info("ChromeDriver oluşturuluyor...");
                driverPool.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                logger.info("FirefoxDriver oluşturuluyor...");
                driverPool.set(new FirefoxDriver(firefoxOptions));
                break;

            default:
                logger.error("Desteklenmeyen tarayıcı tipi: {}", browser);
                throw new RuntimeException("HATA: Desteklenmeyen tarayıcı! Lütfen config.properties'i kontrol edin.");
        }

        // 6. GLOBAL SÜRÜCÜ AYARLARI
        logger.info("Implicit Wait {} saniye olarak ayarlanıyor...", implicitWait);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        logger.info("TARAYICI BAŞARIYLA BAŞLATILDI!");
        logger.info("==================================================");
    }

    /**
     * O anki thread'e ait WebDriver instance'ını döndürür.
     * Page sınıfları ve StepDefinition'lar tarayıcıya hükmetmek için BU methodu çağırır.
     */
    public static WebDriver getDriver() {
        return driverPool.get();
    }

    /**
     * Test bittiğinde tarayıcıyı kapatır ve thread belleğini temizler.
     */
    public static void quitDriver() {
        if (driverPool.get() != null) {
            logger.info("Tarayıcı kapatılıyor ve thread temizleniyor...");
            driverPool.get().quit();
            driverPool.remove(); // ThreadLocal'i temizle, memory leak önle
        }
    }
}