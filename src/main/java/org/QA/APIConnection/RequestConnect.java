package org.QA.APIConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.QA.generic.FrameworkGenericUtils;
import org.QA.generic.FrameworkGlobalVar;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class RequestConnect {
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void writeDataToOutputStream(final OutputStream outputStream, final String jsonStringPayload) {
        try (OutputStream stream = outputStream) {
            stream.write(jsonStringPayload.getBytes());
            stream.flush();
        } catch (Exception e) {
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

    public static String setOauthToken(final String accessURL, final String clientID, final String clientSecret,
                                       final String tokenKey, final String tokenType) {
        String grantType = "client_credentials";
        String scope = "openid";
        String accessTokenKey = tokenKey;
        String token = oauth2(accessURL, grantType, clientID, clientSecret, scope, accessTokenKey);
        FrameworkGlobalVar.bearerToken = tokenType + " " + token;
        return token;
    }

    public static String setOauthToken() {
        String bearerToken = "";
        String accessTokenURL = FrameworkGenericUtils.getCurrentProperty("accessTokenUrl", "Undefined");
        String clientId = FrameworkGenericUtils.getCurrentProperty("client_id", "Undefined");
        String clientSecret = FrameworkGenericUtils.getCurrentProperty("client_secret", "Undefined");
        String grantType = FrameworkGenericUtils.getCurrentProperty("grant_type", "client_crendentials");
        String scope = FrameworkGenericUtils.getCurrentProperty("scope", "openid");
        String accessTokenKey = FrameworkGenericUtils.getCurrentProperty("accessTokenKey", "access_token");
        String accessTokenType = FrameworkGenericUtils.getCurrentProperty("accessTokenType", "Bearer");
        if (!accessTokenURL.equals("Undefined") && !clientId.equals("Undefined") && !clientId.equals("Undefined")) {
            bearerToken = oauth2(accessTokenURL, grantType, clientId, clientSecret, scope, accessTokenKey);
            FrameworkGlobalVar.bearerToken = accessTokenType + " " + bearerToken;
        } else {
            bearerToken = FrameworkGenericUtils.getCurrentProperty("token", "Undefined");
            FrameworkGlobalVar.bearerToken = bearerToken;
        }
        return bearerToken;
    }

    private static Map<String, String> getAuthMap() {
        Map<String, String> authHeader = new HashMap<>();
        authHeader.put("Authorization", FrameworkGlobalVar.bearerToken);
        return authHeader;
    }

    public static Response makeRequest(String method, String endpoint, String body, Map<String, String> headers, Map<String, String> queryParams) {
       final Logger logger = Logger.getLogger(RequestConnect.class.getName());
        RequestSpecification request = given();
        logger.info("Making " + method + " request to: " + endpoint);

        // Log headers if available
        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
            logger.info("Request Headers: " + headers);
        }
        // Log query parameters if available
        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
            logger.info("Query Params: " + queryParams);
        }
        // Log body if it's a POST/PUT request
        if (body != null && (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT"))) {
            request.body(body);
            logger.info("Request Body: " + body);
        }
        // Handle different request methods and log the response
        Response response = null;
        switch (method.toUpperCase()) {
            case "GET":
                response = request.get(endpoint);
                break;
            case "POST":
                response = request.post(endpoint);
                break;
            case "PUT":
                response = request.put(endpoint);
                break;
            case "DELETE":
                response = request.delete(endpoint);
                break;
            default:
                logger.severe("Invalid HTTP method: " + method);
                throw new IllegalArgumentException("Invalid HTTP method: " + method);
        }

        // Ensure that the response is not null
        Assert.assertNotNull(response, "The response is null!");

        // Log response details
        logger.info("Response Status Code: " + response.getStatusCode());
        logger.info("Response Body: " + response.getBody().asString());

        // Return the response for further validation
        return response;
    }

    //--------------------------POST--------------------------
    public static Response postRequest(String url, String requestPayload){
        return postRequest(url,requestPayload,(Map<String,String>)null);
    }
    public static Response postRequest(String url, String requestPayload, Map<String,String> headers){
        return makeRequest("post",url,requestPayload,headers,(Map<String,String>)null);
    }
    public static Response postOauthRequest(String url, String requestPayload){
        return postRequest(url,requestPayload,getAuthMap());
    }

   // ----------------------------Get------------------------
    public static Response getRequest(final String url){
        return makeRequest("GET",url,null,null,null);
    }
    public static Response getOauthRequest(final String url){
        return makeRequest("GET",url,null,getAuthMap(),null);
    }
}
