package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class testForm {
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
    void shouldSendCapsLockForm() {
        open("http://localhost:9999/");
        SelenideElement form = $("[action]");
        form.$("[data-test-id=name] input").setValue("ИВАНОВ ИВАН");
        form.$("[data-test-id=phone] input").setValue("+79991112233");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendFormWithPatronymic() {
        open("http://localhost:9999/");
        SelenideElement form = $("[action]");
        form.$("[data-test-id=name] input").setValue("Иванов Иван Иванович");
        form.$("[data-test-id=phone] input").setValue("+79991112233");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldBeErrorForFirstField() {
        open("http://localhost:9999/");
        SelenideElement form = $("[action]");
        form.$("[data-test-id=name] input").setValue("Ivanov Ivan");
        form.$("[data-test-id=phone] input").setValue("+79991112233");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $(".input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldBeErrorForSecondField() {
        open("http://localhost:9999/");
        SelenideElement form = $("[action]");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("77787268");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}


