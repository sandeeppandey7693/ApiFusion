package org.QA.APITest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import org.QA.generic.FrameworkGlobalVar;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

import static io.restassured.RestAssured.given;

public class APIClientConnect {
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void writeDataToOutputStream(final OutputStream outputStream, final String jsonStringPayload) {
        try (OutputStream stream = outputStream) {
            stream.write(jsonStringPayload.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String oauth2(String accessTokenUrl, String grantType, String clientId, String clientSecret, String scope, String accessTokenKey) {
        try {
            Response response = given().auth().preemptive().basic(clientId, clientSecret)
                    .relaxedHTTPSValidation().contentType("application/x-www-form-urlencoded")
                    .formParam("grant_type", grantType).formParam("scope", scope).post(accessTokenUrl);
            JsonParser parser = new JsonParser();
            Object object = parser.parse(response.getBody().asPrettyString());
            JSONObject jsonObject = (JSONObject) object;
            String token = String.valueOf(jsonObject.get(accessTokenKey));
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String setOauthToken(final String accessURL,final String clientID, final String clientSecret,
                                       final String tokenKey, final String tokenType){
        String grantType = "client_credentials";
        String scope = "openid";
        String accessTokenKey = tokenKey;
        String token = oauth2(accessURL,grantType,clientID,clientSecret,scope,accessTokenKey);
        FrameworkGlobalVar.bearerToken = tokenType + " " + token;
        return token;
    }
}
