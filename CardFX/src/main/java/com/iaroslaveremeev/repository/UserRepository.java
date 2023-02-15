package com.iaroslaveremeev.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.dto.ResponseResult;
import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.util.Constants;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UserRepository {

    public UserRepository() {
    }

    private static InputStream getData(String link, String method) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            if (httpURLConnection.getResponseCode() == 400){
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getErrorStream()))){
                    throw new IOException(new ResponseResult<>(bufferedReader.readLine()).getMessage());
                }
            }
            return httpURLConnection.getInputStream();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
        return null;
    }

    public User register(User user) throws IOException {
        try (InputStream inputStream = getData(Constants.SERVER_URL + "/registration?" +
                "&login=" + URLEncoder.encode(user.getLogin(), StandardCharsets.UTF_8) +
                "&password=" + URLEncoder.encode(user.getPassword(), StandardCharsets.UTF_8) +
                "&name=" + URLEncoder.encode(user.getName(), StandardCharsets.UTF_8), "POST")) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseResult<User> result = mapper.readValue(inputStream, new TypeReference<>() {});
            return result.getData();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Registration failed!");
            alert.show();
        }
        return null;
    }

}
