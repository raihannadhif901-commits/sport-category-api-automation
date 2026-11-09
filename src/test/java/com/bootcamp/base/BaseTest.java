package com.bootcamp.base;

import com.bootcamp.utils.JsonReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

public class BaseTest {


    protected static final String BASE_URL = "https://sport-reservation-2-api-bootcamp.do.dibimbing.id";
    protected static String token;

    @BeforeSuite
    public void setupSuite() {
        RestAssured.baseURI = BASE_URL;


        RestAssured.useRelaxedHTTPSValidation();

        String loginPayload = JsonReader.readJsonFile("payloads/login.json");

        try {
            Response response = given()
                    .header("Accept", "application/json")
                    .contentType(ContentType.JSON)
                    .body(loginPayload)
                    .when()
                    .post("/api/v1/login")
                    .then()
                    .extract()
                    .response();

            System.out.println("Status Code Login: " + response.getStatusCode());
            System.out.println("Response Login: " + response.getBody().asPrettyString());

            if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
                token = response.jsonPath().getString("data.token");
                System.out.println(">>> LOGIN BERHASIL! Token: " + token);
            } else {
                System.err.println(">>> Login gagal dengan status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println(">>> Terjadi kendala saat request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}