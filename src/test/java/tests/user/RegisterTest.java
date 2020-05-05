package tests.user;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.util.Auth;
import tests.util.Credentials;
import tests.util.RandomUser;
import tests.util.TokenUser;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterTest {
    @Test
    public void assertStatusCode() {
        NewUser newUser = new NewUser(RandomUser.randomUsername(), "email@email.de", "passowrd");

        given().
                contentType("application/json").
                body(newUser).
        when().
                post("http://localhost:8080/register").
                then().
                statusCode(200);
    }

    // @Test
    public void assertUserCreated() {
        NewUser newUser = new NewUser(RandomUser.randomUsername(), "email@email.de", "passowrd");

        given().
                contentType("application/json").
                body(newUser).
                when().
                post("http://localhost:8080/register").
                then().
                statusCode(200);
        userCreatedCorrectly(newUser);

    }

    private void userCreatedCorrectly(NewUser newUser) {
        Credentials credentials = new Credentials(newUser.getUsername(), newUser.getPassword());
        Response response = given().
                contentType("application/json").
                body(credentials).
                when().
                post("http://localhost:8080/login").
                thenReturn();
        String token = response.getBody().print();
        assertThat(token, notNullValue());
        TokenUser tokenUser = Auth.parseUserFromToken(token);
        assertThat(tokenUser, notNullValue());
        assertThat(tokenUser.getRole(), equalTo("user"));
        assertThat(tokenUser.getUsername(), equalTo(newUser.getUsername()));
    }

    private static org.hamcrest.Matcher<java.lang.Object> notNullValue() {
        return org.hamcrest.core.IsNull.notNullValue();
    }





}
