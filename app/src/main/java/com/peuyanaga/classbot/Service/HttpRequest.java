package com.peuyanaga.classbot.Service;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.peuyanaga.classbot.Model.ServerResponse;

/**
 * Created by DELL on 2020-04-30.
 */

public class HttpRequest {

    public HttpRequest(){}
    //
    public String httpGETRequest(String urlString){
        String response = "";
        try {

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() != 200){ throw new RuntimeException("Failed " + conn.getContentEncoding()); }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String output;
            StringBuilder builder = new  StringBuilder();

            while((output = bufferedReader.readLine()) != null){ builder.append(output); }

            response = builder.toString();

            conn.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
    //
    public ServerResponse httpPOSTRequest(String urlString, String jsonData){
        ServerResponse response = new ServerResponse();
        try {

            Gson gson = new Gson();
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonData.getBytes());
            outputStream.flush();

            //if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED){ throw new RuntimeException("Failed " + conn.getResponseCode()); }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String output;
            StringBuilder builder = new  StringBuilder();

            while((output = bufferedReader.readLine()) != null){ builder.append(output); }

            System.out.println(builder.toString());
            response = gson.fromJson(builder.toString(), ServerResponse.class);

            conn.disconnect();
        } catch (Exception ex) {
            response.setMessage("Error");
            response.setStatus("Error");
        }
        return response;
    }
    //
    public ServerResponse httpPUTRequest(String urlString, String jsonData){
        ServerResponse response = new ServerResponse();
        try {

            Gson gson = new Gson();
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonData.getBytes());
            outputStream.flush();

            //if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED){ throw new RuntimeException("Failed " + conn.getResponseCode()); }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String output;
            StringBuilder builder = new  StringBuilder();

            while((output = bufferedReader.readLine()) != null){ builder.append(output); }

            //Log.d("Server", builder.toString());
            response = gson.fromJson(builder.toString(), ServerResponse.class);
            conn.disconnect();
        } catch (Exception ex) {
            response.setMessage("Error");
            response.setStatus("Error");

        }
        return response;
    }
    //
    public String encode(String value){
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
