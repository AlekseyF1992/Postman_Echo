package ru.netology.rest;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanEchoTest {

    @Test
    void shouldReturnSentPlainText() {
        String textToSend = "Все ли верно?";

        given()
                .log()
                .all() // <--- ЛОГИРОВАНИЕ ЗАПРОСА (показывает URL, заголовки и тело)
                .baseUri("https://postman-echo.com")
                .contentType("text/plain; charset=UTF-8")
                .body(textToSend)
                .when()
                .post("/post")
                .then()
                .log().ifValidationFails() // <--- ЛОГИРОВАНИЕ ОТВЕТА (только если тест упадёт, чтобы не засорять консоль)
                .statusCode(200)
                .body("data", equalTo(textToSend));
    }

    @Test
    void shouldReturnSentJsonWithCyrillic() {
        String jsonBody = "{\"name\": \"Иван\", \"city\": \"Москва\"}";

        given()
                .log().all() // <--- ЛОГИРОВАНИЕ ЗАПРОСА
                .baseUri("https://postman-echo.com")
                .contentType("application/json; charset=UTF-8")
                .body(jsonBody)
                .when()
                .post("/post")
                .then()
                .log().ifValidationFails() // <--- ЛОГИРОВАНИЕ ОТВЕТА ПРИ ОШИБКЕ
                .statusCode(200)
                .body("data.name", equalTo("Иван"))
                .body("data.city", equalTo("Москва"));
    }

    @Test
    void shouldReturnSentNestedJson() {
        String jsonBody = "{\"user\": {\"id\": 42, \"role\": \"admin\"}}";

        given()
                .log().all() // <--- ЛОГИРОВАНИЕ ЗАПРОСА
                .baseUri("https://postman-echo.com")
                .contentType("application/json; charset=UTF-8")
                .body(jsonBody)
                .when()
                .post("/post")
                .then()
                .log().ifValidationFails() // <--- ЛОГИРОВАНИЕ ОТВЕТА ПРИ ОШИБКЕ
                .statusCode(200)
                .body("data.user.role", equalTo("admin"))
                .body("data.user.id", equalTo(42));
    }
}