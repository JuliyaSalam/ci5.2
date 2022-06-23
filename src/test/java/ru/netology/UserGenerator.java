package ru.netology;

import com.github.javafaker.Faker;

public class UserGenerator {
    public static UserData generatorUser(String status) {
        return new UserData(generateLogin(), generatePassword(), status);
    }

    private static String generateLogin() {
        Faker faker = new Faker();
        return faker.name().username();
    }

    private static String generatePassword() {
        Faker faker = new Faker();
        return faker.internet().password();
    }
}
