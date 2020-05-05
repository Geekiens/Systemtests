package tests.rating;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.book.Book;
import tests.util.Auth;
import tests.util.MailSlurp;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateRatingTest {

    @Test
    public void assertStatusCode() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);

        Response response = given().auth().oauth2(Auth.getAdminToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2)
                .thenReturn();
        assertThat(response.getStatusCode(), equalTo(201));
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        deleteCreatedRating(id);
    }

    @Test
    public void CreatedRating() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);

        Response response = given().auth().oauth2(Auth.getAdminToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2).
                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
       List<Map<String, Object>> ratings = get("http://localhost:8080/books/{id}/ratings", 2).as(new TypeRef<List<Map<String, Object>>>() {});
       assertThat(ratings, hasSize(1));

        deleteCreatedRating(id);
    }

    @Test
    public void CreatedRatingHasTitle() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);

        Response response = given().auth().oauth2(Auth.getAdminToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2).
                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        List<Map<String, Object>> ratings = get("http://localhost:8080/books/{id}/ratings", 2).as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(ratings, hasSize(1));
        assertThat("title", equalTo(ratings.get(0).get("title")));

        deleteCreatedRating(id);
    }

    @Test
    public void CreatedRatingHasScore() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);

        Response response = given().auth().oauth2(Auth.getAdminToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2).
                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        List<Map<String, Object>> ratings = get("http://localhost:8080/books/{id}/ratings", 2).as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(5, equalTo(ratings.get(0).get("score")));

        deleteCreatedRating(id);
    }

    @Test
    public void CreatedRatingHasContent() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);
        rating.setContent("content");


        Response response = given().auth().oauth2(Auth.getAdminToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2).
                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        List<Map<String, Object>> ratings = get("http://localhost:8080/books/{id}/ratings", 2).as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat("content", equalTo(ratings.get(0).get("content")));

        deleteCreatedRating(id);
    }

    @Test
    public void CreatedRatingHasUserId() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);

        Response response = given().auth().oauth2(Auth.getAdminToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2).
                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        List<Map<String, Object>> ratings = get("http://localhost:8080/books/{id}/ratings", 2).as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(3, equalTo(ratings.get(0).get("userId")));

        deleteCreatedRating(id);
    }

    @Test
    public void CreatedRatingHasAuthor() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);

        Response response = given().auth().oauth2(Auth.getAdminToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2).
                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        List<Map<String, Object>> ratings = get("http://localhost:8080/books/{id}/ratings", 2).as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat("Admin", equalTo(ratings.get(0).get("author")));

        deleteCreatedRating(id);
    }

    @Test
    public void assertEmailLowRatingNoComment() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);

        Response response = given().auth().oauth2(Auth.getModeratorToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2)
                .thenReturn();
        assertThat(response.getStatusCode(), equalTo(201));
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        deleteCreatedRating(id);
        MailSlurp mailSlurp = new MailSlurp();
        mailSlurp.checkLowRatingEmail();
    }

    @Test
    public void assertEmailHighRatingNoComment() {
        Rating rating = new Rating();
        rating.setTitle("title");
        rating.setScore(5);

        Response response = given().auth().oauth2(Auth.getUserToken()).
                contentType("application/json").
                body(rating).
                when().
                post("http://localhost:8080/books/{id}/ratings", 2)
                .thenReturn();
        assertThat(response.getStatusCode(), equalTo(201));
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        deleteCreatedRating(id);
        MailSlurp mailSlurp = new MailSlurp();
        mailSlurp.checkHighRatingEmail();
    }

    private void deleteCreatedRating(int id) {
        given().auth().oauth2(Auth.getAdminToken()).
                when().
                delete("http://localhost:8080/ratings/{id}", id);
    }




}
