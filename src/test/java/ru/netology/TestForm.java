package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class TestForm {

    @Nested
    public class PositiveTests {
        @Test
        void shouldSendCorrectForm() {
            open("http://localhost:9999/");
            SelenideElement form = $("[action]");
            form.$("[data-test-id=name] input").setValue("Иванов Иван");
            form.$("[data-test-id=phone] input").setValue("+79991112233");
            form.$("[data-test-id=agreement] .checkbox__box").click();
            form.$("[role=button]").click();
            $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }

        @Test
        void shouldSendFormWithDoubleName() {
            open("http://localhost:9999/");
            SelenideElement form = $("[action]");
            form.$("[data-test-id=name] input").setValue("Иванов-Кузьмин Иван");
            form.$("[data-test-id=phone] input").setValue("+79991112233");
            form.$("[data-test-id=agreement] .checkbox__box").click();
            form.$("[role=button]").click();
            $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }

        @Test
        void shouldBeErrorIfInvalidNameEntered() {
            open("http://localhost:9999/");
            SelenideElement form = $("[action]");
            form.$("[data-test-id=name] input").setValue("Ivanov Ivan");
            form.$("[data-test-id=phone] input").setValue("+79991112233");
            form.$("[data-test-id=agreement] .checkbox__box").click();
            form.$("[role=button]").click();
            $(".input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        void shouldBeErrorIfInvalidNumberPhoneEntered() {
            open("http://localhost:9999/");
            SelenideElement form = $("[action]");
            form.$("[data-test-id=name] input").setValue("Иванов Иван");
            form.$("[data-test-id=phone] input").setValue("77787268");
            form.$("[data-test-id=agreement] .checkbox__box").click();
            form.$("[role=button]").click();
            $("[data-test-id=phone] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        }

        @Test
        void shouldBeErrorIfUncheckedCheckbox() {
            open("http://localhost:9999/");
            SelenideElement form = $("[action]");
            form.$("[data-test-id=name] input").setValue("Иванов Иван");
            form.$("[data-test-id=phone] input").setValue("+79991112233");
            form.$("[role=button]").click();
            $(".checkbox__text").shouldHave(Condition.cssValue("color", "rgba(255, 92, 92, 1)"));
        }
    }

    @Nested
    public class NegativeTests {
        @Test
        void shouldBeErrorIfNameWithPatronymic() {
            open("http://localhost:9999/");
            SelenideElement form = $("[action]");
            form.$("[data-test-id=name] input").setValue("Иванов Иван Иванович");
            form.$("[data-test-id=phone] input").setValue("+79991112233");
            form.$("[data-test-id=agreement] .checkbox__box").click();
            form.$("[role=button]").click();
            $(".input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }

        @Test
        void shouldBeErrorIfNameWrittenCapsLock() {
            open("http://localhost:9999/");
            SelenideElement form = $("[action]");
            form.$("[data-test-id=name] input").setValue("ИВАНОВ ИВАН");
            form.$("[data-test-id=phone] input").setValue("+79991112233");
            form.$("[data-test-id=agreement] .checkbox__box").click();
            form.$("[role=button]").click();
            $(".input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }
    }
}


