package wint.webchat.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import wint.webchat.modelDTO.ResponseAuthData;

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
    public ResponseAuthData signIn(String authCode) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                CLIENT_ID,
                CLIENT_SECRET,
                Arrays.asList("https://www.googleapis.com/auth/userinfo.profile",
                        "https://www.googleapis.com/auth/userinfo.email")
        ).setAccessType("offline").build();
        GoogleTokenResponse tokenResponse = flow.newTokenRequest(authCode)
                .setRedirectUri(URL_FRONTEND)
                .execute();
        new ResponseAuthData();
        return ResponseAuthData.builder()
                .refreshToken(tokenResponse.getRefreshToken())
                .accessToken(tokenResponse.getAccessToken()).build();
    }
    public ResponseEntity<ResponseAuthData> extractTokenGoogle(String token){
        try {
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            GenericUrl url = new GenericUrl("https://www.googleapis.com/oauth2/v3/userinfo");
            HttpRequest request = requestFactory.buildGetRequest(url);
            request.getHeaders().setAuthorization("Bearer "+token);
            request.setParser(new JsonObjectParser(jsonFactory));
            return null;
        }catch (IOException ioe){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
