package com.kaya.orangehrm.stepdefinitions;

import com.kaya.orangehrm.base.DriverManager;
import com.kaya.orangehrm.pages.LoginPage;
import com.kaya.orangehrm.utilities.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("kullanıcı OrangeHRM giriş sayfasındadır")
    public void kullaniciOrangeHRMGirisSayfasindadir() {
        DriverManager.getDriver().get(ConfigReader.getProperty("url"));
        loginPage = new LoginPage();
    }

    @When("kullanıcı {string} ve {string} bilgileriyle giriş yapar")
    public void kullaniciBilgileriyleGirisYapar(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("dashboard sayfası görüntülenir")
    public void dashboardSayfasiGoruntulenir() {
        Assert.assertTrue(loginPage.isDashboardDisplayed(),
                "Dashboard başlığı görüntülenemedi, giriş başarısız olabilir.");
    }

    @Then("{string} hata mesajı görüntülenir")
    public void hataMesajiGoruntulenir(String beklenenMesaj) {
        Assert.assertEquals(loginPage.getInvalidCredentialsMessage(), beklenenMesaj);
    }

    @Then("{string} uyarısı görüntülenir")
    public void uyariGoruntulenir(String beklenenUyari) {
        Assert.assertEquals(loginPage.getRequiredMessage(), beklenenUyari);
    }
}