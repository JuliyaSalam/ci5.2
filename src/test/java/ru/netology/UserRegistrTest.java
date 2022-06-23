package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class UserRegistrTest {
    SelenideElement form = $x("//form");
    SelenideElement error = $x("//div[@data-test-id='error-notification']");

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
    }

    @org.junit.jupiter.api.Test
    public void ActiveUser() {
        UserData userActive = UserGenerator.generatorUser("active");
        UserRegistration.registration(userActive);
        form.$x(".//span[@data-test-id='login']//input").val(userActive.getLogin());
        form.$x(".//span[@data-test-id='password']//input").val(userActive.getPassword());
        form.$x(".//button").click();
        $x("//h2").should(text("Личный кабинет"));
    }

    @org.junit.jupiter.api.Test
    public void BlockerUser() {
        UserData userBlocked = UserGenerator.generatorUser("blocked");
        UserRegistration.registration(userBlocked);
        form.$x(".//span[@data-test-id='login']//input").val(userBlocked.getLogin());
        form.$x(".//span[@data-test-id='password']//input").val(userBlocked.getPassword());
        form.$x(".//button").click();
        error.should(visible);
        error.$x(".//div[@class='notification__content']").should(text("Пользователь заблокирован"));
        error.$x(".//button").click();
        error.should(hidden);
    }

    @org.junit.jupiter.api.Test
    public void emptyForm() {
        form.$x(".//button").click();
        form.should(visible);
        $x(".//span[@class='input__sub']").should(text("Поле обязательно для заполнения"));
        error.should(hidden);
    }

    @org.junit.jupiter.api.Test
    public void errorLogin() {
        UserData userActive = UserGenerator.generatorUser("active");
        UserRegistration.registration(userActive);
        form.$x(".//span[@data-test-id='login']//input").val("000");
        form.$x(".//span[@data-test-id='password']//input").val(userActive.getPassword());
        form.$x(".//button").click();
        error.should(visible);
        error.$x(".//div[@class='notification__content']").should(text("Неверно указан логин или пароль"));
        error.$x(".//button").click();
        error.should(hidden);
    }

    @org.junit.jupiter.api.Test
    public void errorPassword() {
        UserData userActive = UserGenerator.generatorUser("active");
        UserRegistration.registration(userActive);
        form.$x(".//span[@data-test-id='login']//input").val(userActive.getLogin());
        form.$x(".//span[@data-test-id='password']//input").val("000");
        form.$x(".//button").click();
        error.should(visible);
        error.$x(".//div[@class='notification__content']").should(text("Неверно указан логин или пароль"));
        error.$x(".//button").click();
        error.should(hidden);
    }

}
