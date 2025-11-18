package com.converter.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class CurrencyService {

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/6d4264c38af8f691e4ba4e21";

    private final ApiClient apiClient;

    public CurrencyService() {
        this.apiClient = new ApiClient();
    }

    // ✔ Cargar lista de monedas
    public Map<String, String> loadSymbols() throws Exception {

        JsonObject json = apiClient.getJson(BASE_URL + "/codes");

        JsonArray array = json.getAsJsonArray("supported_codes");

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < array.size(); i++) {
            JsonArray item = array.get(i).getAsJsonArray();
            String code = item.get(0).getAsString();
            String desc = item.get(1).getAsString();
            map.put(code, desc);
        }

        return map;
    }

    // ✔ Conversión de moneda correcta
    public double convert(String from, String to, double amount) throws Exception {

        JsonObject json = apiClient.getJson(BASE_URL + "/latest/" + from);

        JsonObject rates = json.getAsJsonObject("conversion_rates");

        if (!rates.has(to))
            throw new Exception("Moneda no encontrada.");

        double rate = rates.get(to).getAsDouble();

        return amount * rate;
    }
}
