package ru.testmepls;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BaseTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://music.yandex.ru";
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
    }
}
