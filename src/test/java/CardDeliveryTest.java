import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    public String dateMeeting(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldOrderCardDelivery() {
        $("[data-test-id=city] input").setValue("Ижевск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String date = dateMeeting(4);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Соболева Светлана");
        $("[data-test-id=phone] input").setValue("+12345678910");
        $("[data-test-id=agreement]").click();
        $(".button_theme_alfa-on-white.button_view_extra").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на "), text(date)).shouldBe(visible, Duration.ofSeconds(15));
    }
}