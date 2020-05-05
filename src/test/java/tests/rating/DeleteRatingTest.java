package tests.rating;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.util.Auth;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class DeleteRatingTest {

    @Test
    public void assertStatusCode() {
        int id = createRating();
        given().auth().oauth2(Auth.getAdminToken()).
                when().
                delete("http://localhost:8080/ratings/{id}", id).
                then().
                statusCode(200);
    }

    @Test
    public void assertDeleteAsAdmin() {
        int id = createRating();
        given().auth().oauth2(Auth.getAdminToken()).
                when().
                delete("http://localhost:8080/ratings/{id}", id);
        checkExistence(false);

    }

    @Test
    public void assertDeleteAsModerator() {
        int id = createRating();
        given().auth().oauth2(Auth.getModeratorToken()).
                when().
                delete("http://localhost:8080/ratings/{id}", id);
        checkExistence(true);
        cleanUp(id);
    }

    @Test
    public void assertDeleteAsUser() {
        int id = createRating();
        given().auth().oauth2(Auth.getUserToken()).
                when().
                delete("http://localhost:8080/ratings/{id}", id);
        checkExistence(true);
        cleanUp(id);
    }

    private void cleanUp(int id){
        given().auth().oauth2(Auth.getAdminToken()).
                when().
                delete("http://localhost:8080/ratings/{id}", id);
    }

    private void checkExistence(boolean exist) {
        List<Map<String, Object>> ratings = get("http://localhost:8080/books/{id}/ratings", 2).as(new TypeRef<List<Map<String, Object>>>() {});
        if (exist){
            assertThat(ratings, hasSize(1));
        }
        else {
            assertThat(ratings, hasSize(0));
        }
    }

    private int createRating() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);
        Response response = given().auth().oauth2(Auth.getAdminToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2)
                .thenReturn();

        String body = response.getBody().print();
        return Integer.parseInt(body);
    }
}
