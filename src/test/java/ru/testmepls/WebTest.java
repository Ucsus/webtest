package ru.testmepls;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.testmepls.data.Locale;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WebTest extends BaseTest{
        @DisplayName("Проверка результатов поиска")
        @ValueSource (strings = {"The Dead South", "Rancid"})
        @ParameterizedTest(name = "Запрос {0}")
        void yaMusicSearchCommonTest(String testData) {
            open("/home");
            executeJavaScript("$('.pay-promo-popup').remove()");
            executeJavaScript("$('.deco-pane-overlay').remove()");
            $(".d-search__button").click();
            $(".d-input__field").setValue(testData).pressEnter();
            $(".serp-snippet__artists").shouldHave(text(testData));
        }

        @DisplayName("Проверка жанров")
        @CsvSource(value = {
                "Rancid | панк, ска, иностранный рок",
                "The Dead South | музыка мира, кантри, американская музыка"},
                delimiter = '|'
        )
        @ParameterizedTest(name = "Группа: {0}, жанр: {1}")
        void yaMusicSearchCommonTestWithGenre(String bandName, String bandGenre) {
            open("/home");
            executeJavaScript("$('.pay-promo-popup').remove()");
            executeJavaScript("$('.deco-pane-overlay').remove()");
            $(".d-search__button").click();
            $(".d-input__field").setValue(bandName).pressEnter();
            $$(".serp-snippet__artists").shouldHave(CollectionCondition.texts(bandGenre));
        }

        static Stream<Arguments> yaMusicSiteButtonsText() {
            return Stream.of(
                    Arguments.of(Locale.UK, List.of("Правовласникам", "Угода користувача", "Довідка")),
                    Arguments.of(Locale.EN, List.of("Copyright Holders", "Terms", "Help")),
                    Arguments.of(Locale.RU, List.of("Правообладателям", "Пользовательское соглашение", "Справка"))
                    );
        }
        @MethodSource
        @ParameterizedTest(name = "Проверка отображения названия кнопок для локали {0}")
        void yaMusicSiteButtonsText(Locale locale, List<String> buttonsTexts) {
            open("/");
            executeJavaScript("$('.pay-promo-popup').remove()");
            executeJavaScript("$('.deco-pane-overlay').remove()");
            $(".footer").scrollTo();
            $(".d-lang-switcher").click();
            $$(".d-select__item a").find(text(locale.name())).click();
            $$(".footer__left a").filter(visible).shouldHave(CollectionCondition.texts(buttonsTexts));
        }

}
