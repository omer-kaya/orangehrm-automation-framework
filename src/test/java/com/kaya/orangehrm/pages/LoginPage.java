package com.kaya.orangehrm.pages;

import com.kaya.orangehrm.base.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    // 2. Logger tanımlaması
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // 3. WebDriver tanımlaması (Henüz başlatmıyoruz, sadece değişkeni tanımlıyoruz)
    private WebDriver driver;

    // Explicit Wait için yardımcı değişken (Daha stabil testler için)

    private WebDriverWait wait;

    // 4. CONSTRUCTOR (Yapıcı Method)
    // Bu sınıf new LoginPage() denildiğinde otomatik çalışır.

    public LoginPage(){
        this.driver= DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("LoginPage sınıfı başlatıldı ve Driver bağlandı.");

    }
    // ==========================================
    // 5. LOCATORS (Sayfadaki elementlerin adresleri)
    // ==========================================

    // OrangeHRM Username input alanı (name attribute'ü en stabilidir)

    // Username input (name attribute veya placeholder ile)
    private By usernameInput = By.name("username");

    // Password input (type="password" en garantisi)
    private By passwordInput = By.xpath("//input[@type='password']");
    // Login butonu (XPath ile type="submit" olan button'ı buluyoruz)

    // Login button (Screenshot'ta görünen: type="submit" ve class="orangehrm-login-button")
    private By loginButton = By.xpath("//button[@type='submit']");

    // Dashboard header (Giriş sonrası doğrulama için)
    private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");



}
