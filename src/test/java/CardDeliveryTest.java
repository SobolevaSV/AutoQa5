import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        String date = dateMeeting(3);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Соболева Светлана");
        $("[data-test-id=phone] input").setValue("+12345678910");
        $("[data-test-id=agreement]").click();
        $("button.button_view_extra.button_size_m.button_theme_alfa-on-white").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(text("Встреча забронирована на "), text(date)).shouldBe(visible, Duration.ofSeconds(15));
    }
}