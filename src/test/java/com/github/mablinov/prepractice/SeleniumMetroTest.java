package com.github.mablinov.prepractice;

import com.github.mablinov.prepractice.pageobject.MetroHomePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumMetroTest {
    // создай поля для драйвера и страницы
    private WebDriver driver;
    private MetroHomePage metroPage;

    // создай константы для тестовых данных
    private static final String CITY_SAINTP = "Санкт-Петербург";
    private static final String STATION_SPORTIVNAYA = "Спортивная";
    private static final String STATION_LUBYANKA = "Лубянка";
    private static final String STATION_KRASNOGVARD = "Красногвардейская";

    // все предварительные действия вынеси в Before
    @Before
    public void setUp() {
        // открой браузер Chrome
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "B:\\WebDriver\\bin\\chromedriver-win64\\chromedriver.exe");
        //   options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        // перейди на страницу тестового приложения
        driver.get("https://qa-metro.stand-2.praktikum-services.ru/");
        // создай объект класса страницы стенда
        metroPage = new MetroHomePage(driver);
        // дождись загрузки страницы
        metroPage.waitForLoadHomePage();
    }

    // проверь, как работает выбор города
    @Test
    public void checkChooseCityDropdown() {
        // выбери Санкт-Петербург в списке городов
        metroPage.chooseCity(CITY_SAINTP);
        // проверь, что станция метро «Спортивная» видна через 8 секунд
        metroPage.waitForStationVisibility(STATION_SPORTIVNAYA);
    }

    @Test
    public void checkRouteApproxTimeIsDisplayed() {
        // построй маршрут от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
        // проверь, что у первого маршрута списка отображается нужное примерное время поездки
        Assert.assertEquals("≈ 36 мин.", metroPage.getApproximateRouteTime(0));
    }

    // проверь отображение станции «Откуда» в карточке маршрута
    @Test
    public void checkRouteStationFromIsCorrect() {
        // построй маршрут от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
        // проверь, что отображается корректное название станции начала маршрута

        Assert.assertEquals(STATION_LUBYANKA, metroPage.getRouteStationFrom());
    }
    // проверь отображение станции «Куда» в карточке маршрута
    @Test
    public void checkRouteStationToIsCorrect() {
        // открой браузер
        metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
        // проверили, что отображается корректное название станции начала маршрута
        Assert.assertEquals(STATION_KRASNOGVARD, metroPage.getRouteStationTo());
    }

    // добавь метод с аннотацией After для закрытия браузера
    @After
    public void tearDown() {
        // закрой браузер
        driver.quit();
    }

}