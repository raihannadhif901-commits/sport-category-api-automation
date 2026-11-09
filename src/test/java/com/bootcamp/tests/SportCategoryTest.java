package com.bootcamp.tests;

import com.bootcamp.base.BaseTest;
import com.bootcamp.utils.JsonReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SportCategoryTest extends BaseTest {

    private static String categoryId;
    private static String createdCategoryName;


    // METHOD CREATE (POST)
    @Test(priority = 1, description = "Membuat kategori baru dengan dynamic data dari file JSON")
    public void testCreateCategory() {
        createdCategoryName = "Sport_" + System.currentTimeMillis();

        Map<String, String> values = new HashMap<>();
        values.put("category_name", createdCategoryName);

        String payload = JsonReader.getDynamicPayload("payloads/createCategory.json", values);

        Response response = given()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/v1/sport-categories/create")
                .then()
                .statusCode(200)
                .body("error", equalTo(false))
                .body("message", equalTo("data saved"))
                .body("result.name", equalTo(createdCategoryName))
                .extract()
                .response();


        int id = response.jsonPath().getInt("result.id");
        categoryId = String.valueOf(id);

        System.out.println(">>> SUCCESS! Created Category ID: " + categoryId);
        Assert.assertNotNull(categoryId, "Category ID tidak boleh null!");
    }


    // METHOD GET (Categories)
    @Test(priority = 2, dependsOnMethods = "testCreateCategory", description = "Mengambil seluruh daftar sport category")
    public void testGetCategories() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/v1/sport-categories")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo(false))
                .extract()
                .response();

        System.out.println(">>> SUCCESS! GET Categories status: " + response.getStatusCode());
    }


    // METHOD UPDATE (POST)
    @Test(priority = 3, dependsOnMethods = "testCreateCategory", description = "Update kategori menggunakan dynamic data")
    public void testUpdateCategory() {
        String updatedName = "Updated_" + System.currentTimeMillis();

        Map<String, String> values = new HashMap<>();
        values.put("updated_category_name", updatedName);

        String payload = JsonReader.getDynamicPayload("payloads/updateCategory.json", values);

        Response response = given()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/v1/sport-categories/update/" + categoryId)
                .then()
                .statusCode(200)
                .body("error", equalTo(false))
                .extract()
                .response();

        System.out.println(">>> SUCCESS! Update Category status: " + response.getStatusCode());
        System.out.println("Response Update Body: " + response.getBody().asPrettyString());
    }

    //METHOD DELETE (DELETE)
    @Test(priority = 4, dependsOnMethods = "testUpdateCategory", description = "Menghapus kategori yang telah dibuat")
    public void testDeleteCategory() {
        Response response = given()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/v1/sport-categories/delete/" + categoryId)
                .then()
                .statusCode(200)
                .body("error", equalTo(false))
                .extract()
                .response();

        System.out.println(">>> SUCCESS! Delete Category status: " + response.getStatusCode());
        System.out.println("Response Delete Body: " + response.getBody().asPrettyString());
    }
}