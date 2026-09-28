package com.kaya.orangehrm.stepdefinitions;

import com.kaya.orangehrm.base.DriverManager;
import com.kaya.orangehrm.pages.LoginPage;
import com.kaya.orangehrm.utilities.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.sql.Driver;

public class LoginSteps {
    private LoginPage loginPage;

    @Given("kullanıcı OrangeHRM giriş sayfasındadır")
    public void kullanıcıOrangeHRMGirişSayfasındadır() {
        DriverManager.getDriver().get(ConfigReader.getProperty("url"));
        loginPage = new LoginPage();
    }

    @When("kullanıcı {string} ve {string} bilgileriyle giriş yapar")
    public void kullanıcıVeBilgileriyleGirişYapar(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("dashboard sayfası görüntülenir")
    public void dashboardSayfasıGörüntülenir() {
        Assert.assertTrue(loginPage.isDashboardDisplayed(),
                "Dashboard başlığı görüntülenemedi, giriş başarısız olabilir.");
    }
}
