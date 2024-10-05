package org.QA.APIConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import org.QA.generic.FrameworkGenericUtils;
import org.QA.generic.FrameworkGlobalVar;
import org.json.simple.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestConnect {
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

    private static Map<String,String> getAuthMap(){
        Map<String,String> authHeader = new HashMap<>();
        authHeader.put("Authorization",FrameworkGlobalVar.bearerToken);
        return authHeader;
    }
    public static HttpURLConnection createSSLDisabledHttpsUrlConnection(final URL requestUrl){
        HttpURLConnection httpURLConnection;
        try{
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,getTrustManager(), new SecureRandom());
           httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
          // httpURLConnection.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private static TrustManager[] getTrustManager(){
        TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        return null;
    }
    public static JsonObject makeRequest(String step, String url, String requestType,
                                         String requestPayload,Map<String,String> headers,int expectedStatusCode){
        String projectName = FrameworkGlobalVar.projectName;
        HttpURLConnection httpURLConnection;
        String requestStepDescription = "";
        String requestHeaders = "";
        Map<String ,Object> resultInput = new HashMap<>();
        try{
            URL requestUrl = new URL(url);
            String requestProtocol = requestUrl.getProtocol();
            long startTime = Instant.now().toEpochMilli();
           // httpURLConnection = requestProtocol.equals("https") ?
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
