package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.util.Arrays.asList;

public class MyParamsTest {
    //@Disabled
    @ValueSource(strings = {"The girl from Ipanema", "Corcovado"})
    @ParameterizedTest(name = "При поиске в яндексе музыке по запросу {0} в результатах отображается композиция с названием {0}")
    void yandexMusicTestCommon(String trackName) {
        Selenide.open("https://music.yandex.ru/radio");
        $("button[type='submit']").click();
        $("input").setValue(trackName).sendKeys(Keys.ENTER);;
       $$(".serp-snippet__tracks").find(text(trackName)).shouldBe(visible);
    }
    //    @CsvFileSource(resources = "test_data/trackName.csv")
   //@Disabled
    @CsvSource(value = {
            "The girl from Ipanema, Astrud Gilberto",
            "Corcovado, João Gilberto"})
    @ParameterizedTest(name = "При поиске в яндексе музыке по запросу {0} в результатах отображается композиция с названием {1}")
    void yandexMusicTestComplex(String trackName, String artistName) {
        Selenide.open("https://music.yandex.ru/radio");
        $("button[type='submit']").click();
        $("input").setValue(trackName).sendKeys(Keys.ENTER);;
        $$(".serp-snippet__tracks").find(text(artistName)).shouldBe(visible);
    }

    static Stream<Arguments> yandexMusicTestVeryComplexDataProvider() {
        return Stream.of(
                Arguments.of("The Girl From Ipanema", List.of("The Girl From Ipanema (Garota De Ipanema)" + " " +  "João Gilberto," + " " + "Stan Getz")),
                Arguments.of("Corcovado", List.of("Corcovado" + " "+ "João Gilberto," + " " + "Astrud Gilberto"))
        );
    }
    //@Disabled
    @MethodSource(value = "yandexMusicTestVeryComplexDataProvider")
    @ParameterizedTest(name = "При поиске в яндексе музыке по запросу {0} в результатах отображается композиция с названием {1}")
    void yandexMusicTestVeryComplex(String trackName, List<String> artistName) {
        Selenide.open("https://music.yandex.ru/radio");
        $("button[type='submit']").click();
        $("input").setValue(trackName).sendKeys(Keys.ENTER);;
        $$("[class='page-search__snippet page-search__tracks serp-snippet serp-snippet_compact']")
                .shouldHave(CollectionCondition.texts(artistName));
    }


    @EnumSource(EnumArtists.class)
    @ParameterizedTest
    void enumTest(EnumArtists enumData) {
        Selenide.open("https://music.yandex.ru/radio");
        $("button[type='submit']").click();
        $("input").setValue(enumData.search).sendKeys(Keys.ENTER);;
        $$(".serp-snippet__tracks").find(text(enumData.result)).shouldBe(visible);
    }

}
