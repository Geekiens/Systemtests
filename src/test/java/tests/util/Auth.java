package tests.util;

import io.restassured.response.Response;

import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

public class Auth {

    public static String getAdminToken() {
        Credentials credentials = new Credentials("Admin", "passwort");
        return loginAndGetToken(credentials);
    }

    public static String getModeratorToken() {
        Credentials credentials = new Credentials("Moderator", "passwort");
        return loginAndGetToken(credentials);
    }

    public static String getUserToken() {
        Credentials credentials = new Credentials("User", "passwort");
        return loginAndGetToken(credentials);    }

    private static String loginAndGetToken(Credentials credentials) {
        Response response = given().
                contentType("application/json").
                body(credentials).
                when().
                post("http://localhost:8080/login").
                thenReturn();
        return response.getBody().print();
    }


    public static TokenUser parseUserFromToken(String token) {
        final String[] parts = token.split("\\.");
        try {
            final byte[] userBytes = parseBase64Binary(parts[1]);
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            String str = new String(userBytes, Charset.forName("UTF-8"));
            TokenUser tokenUser = mapper.readValue(str, TokenUser.class);
            return tokenUser;
        } catch (Exception e) {
            return null;
        }
    }
}