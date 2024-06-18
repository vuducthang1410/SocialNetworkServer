package wint.webchat.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wint.webchat.modelDTO.reponse.AuthGoogleResponseDTO;
import wint.webchat.modelDTO.reponse.UserInfoGoogleResponseDTO;

import java.io.IOException;
import java.util.Arrays;

@Component
public class GoogleAuth {
    @Value("${spring.security.oauth2.resource-server.opaque-token.client-id}")
    private String CLIENT_ID;
    @Value("${spring.security.oauth2.resource-server.opaque-token.client-secret}")
    private String CLIENT_SECRET;
    @Value("${frontend.url}")
    private String URL_FRONTEND;
    private final JsonFactory jsonFactory=JacksonFactory.getDefaultInstance();
    public String getAuthUrl(){
        String url = new GoogleAuthorizationCodeRequestUrl(
                CLIENT_ID,
                URL_FRONTEND,
                Arrays.asList(
                        "email",
                        "profile",
                        "openid"
                )).setAccessType("offline")
                .setApprovalPrompt("force")
                .build();
        return url;
    }
    public AuthGoogleResponseDTO signIn(String authCode) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                jsonFactory,
                CLIENT_ID,
                CLIENT_SECRET,
                Arrays.asList("https://www.googleapis.com/auth/userinfo.profile",
                        "https://www.googleapis.com/auth/userinfo.email")
        ).setAccessType("offline").build();
        GoogleTokenResponse tokenResponse = flow.newTokenRequest(authCode)
                .setRedirectUri(URL_FRONTEND)
                .execute();
        return AuthGoogleResponseDTO.builder()
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .build();
    }
    public UserInfoGoogleResponseDTO extractTokenGoogle(String token){
        try {
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            GenericUrl url = new GenericUrl("https://www.googleapis.com/oauth2/v3/userinfo");
            HttpRequest request = requestFactory.buildGetRequest(url);
            request.getHeaders().setAuthorization("Bearer "+token);
            request.setParser(new JsonObjectParser(jsonFactory));
            HttpResponse response = request.execute();
            if (response.isSuccessStatusCode()) {
                UserInfoGoogleResponseDTO userInfo = response.parseAs(UserInfoGoogleResponseDTO.class);
                return userInfo;
            } else {
                System.err.println("Error retrieving user info: " + response.getStatusCode() + " " + response.getStatusMessage());
                throw new Exception();
            }
        }catch (Exception ioe){
            return null;
        }
    }
}
