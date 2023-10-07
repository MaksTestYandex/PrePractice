package com.github.mablinov.prepractice;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;

public class DriverFactory extends ExternalResource {
    private WebDriver driver;

    @Override
    protected void before() {
        if (System.getProperty("browser", "chrome").equals("firefox")) {
            setUpFirefox();
        } else {
            setUpChrome();
        }
    }

    @Override
    protected void after() {
        driver.quit();
    }

    private void setUpChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    private void setUpFirefox() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        var service = new GeckoDriverService.Builder()
                .usingDriverExecutable(new File("B:/WebDriver/bin/geckodriver-v0.33.0-win64/geckodriver.exe"))
                .build();

        var options = new FirefoxOptions()
                .setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");

        driver = new FirefoxDriver(service, options);
    }

    public WebDriver getDriver() {
        return driver;
    }

}
