package com.kaya.orangehrm.pages;

import com.kaya.orangehrm.base.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    private WebDriver driver;
    private WebDriverWait wait;

    // ==========================================
    // LOCATORS
    // ==========================================
    private By usernameInput = By.name("username");
    private By passwordInput = By.xpath("//input[@type='password']");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");

    // Yanlış kullanıcı adı/şifre sonrası çıkan kırmızı uyarı
    private By invalidCredentialsMessage = By.cssSelector(".oxd-alert-content-text");

    // Boş bırakılan alanın altında çıkan "Required" uyarısı
    private By requiredMessage = By.cssSelector(".oxd-input-field-error-message");

    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    public LoginPage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        logger.info("LoginPage sınıfı başlatıldı ve Driver bağlandı.");
    }

    // ==========================================
    // ACTIONS
    // ==========================================
    public void login(String username, String password) {
        logger.info("Giriş işlemi başlatılıyor: {}", username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
        logger.debug("Kullanıcı adı girildi: {}", username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        logger.debug("Şifre girildi.");

        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
        logger.debug("Login butonuna tıklandı.");
    }

    public boolean isDashboardDisplayed() {
        logger.info("Dashboard sayfasının açıldığı doğrulanıyor...");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader));
            logger.info("Dashboard başlığı başarıyla görüntülendi.");
            return true;
        } catch (Exception e) {
            logger.error("Dashboard başlığı görüntülenemedi! Hata: {}", e.getMessage());
            return false;
        }
    }

    public String getInvalidCredentialsMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(invalidCredentialsMessage));
        String text = driver.findElement(invalidCredentialsMessage).getText();
        logger.info("Hata mesajı okundu: {}", text);
        return text;
    }

    public String getRequiredMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(requiredMessage));
        String text = driver.findElement(requiredMessage).getText();
        logger.info("Uyarı mesajı okundu: {}", text);
        return text;
    }
}