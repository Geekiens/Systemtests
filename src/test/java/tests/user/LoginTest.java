package tests.user;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.util.Auth;
import tests.util.Credentials;
import tests.util.TokenUser;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginTest {

    @Test
    public void assertStatusCode() {
        Credentials credentials = new Credentials("Admin", "passwort");
        given().
                contentType("application/json").
                body(credentials).
                when().
                post("http://localhost:8080/login").
                then().
                statusCode(200);
    }

    @Test
    public void assertData() {
        Credentials credentials = new Credentials("Admin", "passwort");
        Response response = given().
                contentType("application/json").
                body(credentials).
                when().
                post("http://localhost:8080/login").
                thenReturn();

        String token = response.getBody().print();
        assertThat(token, notNullValue());
        /*
        TokenUser tokenUser = Auth.parseUserFromToken(token);
        assertThat(tokenUser, notNullValue());
        assertThat(tokenUser.getRole(), equalTo("admin"));
        assertThat(tokenUser.getUsername(), equalTo("Admin"));
        assertThat(tokenUser.getUserId(), notNullValue());
        assertThat(tokenUser.getExp(), greaterThan(tokenUser.getIat()));

         */
    }

    private static org.hamcrest.Matcher<java.lang.Object> notNullValue() {
        return org.hamcrest.core.IsNull.notNullValue();
    }
}
