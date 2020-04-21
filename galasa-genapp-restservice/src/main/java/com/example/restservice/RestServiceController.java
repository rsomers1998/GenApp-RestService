package com.example.restservice;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.http.HttpMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class RestServiceController {

    private final OkHttpClient httpClient = new OkHttpClient();

    @PostMapping("/addcustomer")
    public RestReponse addcustomer(@RequestParam(value = "host") String host) {
        String json = "{\"LGACUS01Operation\": {\"ca\": {}}}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(host + "/GENAPP/addCustomerDetails")
                .addHeader("User-Agent", "OkHttp Bot")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (!response.isSuccessful())
                return new RestReponse(Integer.toString(response.code()));

            String responseBody = response.body().string();
            System.out.println(responseBody);
            String id = new Gson().fromJson(responseBody, JsonObject.class)
                .get("LGACUS01OperationResponse").getAsJsonObject()
                .get("ca").getAsJsonObject()
                .get("ca_customer_num").getAsString();
            
            return new RestReponse(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new RestReponse("Error");
        }
    }

    @PostMapping("/inquirecustomer")
    public Object inquirecustomer(@RequestParam(value = "host") String host, @RequestParam(value = "id") String id) {
        String json = "{\"LGICUS01Operation\": {\"ca\": {\"ca_customer_num\": " + id + "}}}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder()
                .url(host + "/GENAPP/getCustomerDetails")
                .addHeader("User-Agent", "OkHttp Bot")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (!response.isSuccessful())
                return new RestReponse(Integer.toString(response.code()));

            String responseBody = response.body().string();
            System.out.println(responseBody);
            JsonObject ca = new Gson().fromJson(responseBody, JsonObject.class)
                .get("LGICUS01OperationResponse").getAsJsonObject()
                .get("ca").getAsJsonObject();

            HashMap<String, Object> responseCa = new HashMap<String, Object>();
            for(String key : ca.keySet()) {
                responseCa.put(key, ca.get(key).getAsString());
            }
            HashMap<String, Object> responseOperation = new HashMap<String, Object>();
            responseOperation.put("ca", responseCa);
            HashMap<String, Object> fullResponse = new HashMap<String, Object>();
            fullResponse.put("LGICUS01OperationResponse", responseOperation);

            return fullResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return new RestReponse("Error");
        }
    }

}