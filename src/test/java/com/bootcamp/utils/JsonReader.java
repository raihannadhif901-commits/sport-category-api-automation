package com.bootcamp.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonReader {


    public static String readJsonFile(String relativePath) {
        try {
            String path = "src/test/resources/" + relativePath;
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("Gagal membaca file JSON: " + relativePath, e);
        }
    }


    public static String getDynamicPayload(String relativePath, Map<String, String> replacements) {
        String payload = readJsonFile(relativePath);
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            payload = payload.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return payload;
    }
}