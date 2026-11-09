# Sport Category API Automation - RestAssured

Project API Automation Testing untuk endpoint **Sport Category** menggunakan **Java**, **Rest Assured**, dan **TestNG**.

## Tech Stack & Tools
- Java 21 / 17
- Maven
- Rest Assured 5.4.0
- TestNG 7.9.0
- IntelliJ IDEA

## Fitur
1. **Dynamic JSON Handling:** Request payload dibaca langsung dari file `.json` dengan *dynamic values* (timestamp) agar data tidak bentrok saat create.
2. **Authentication:** Otomatisasi login untuk mendapatkan bearer token.
3. **End-to-End CRUD Test Flow:**
    - `POST` Create Category
    - `GET` Categories
    - `POST` Update Category
    - `DELETE` Delete Category

## Cara Menjalankan
Jalankan perintah:
```bash
mvn clean test