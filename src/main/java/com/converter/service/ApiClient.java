
package com.converter.service;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import java.io.*;
import java.net.*;
public class ApiClient {

    public JsonObject getJson(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();

        String line;
        while ((line = in.readLine()) != null) response.append(line);
        in.close();

        return new Gson().fromJson(response.toString(), JsonObject.class);
    }
}
