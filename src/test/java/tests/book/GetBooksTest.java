package tests.book;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.common.mapper.*;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class GetBooksTest {


    @Test
    public void assertStatusCode() {
        given().when().get("http://localhost:8080/books")
                .then().statusCode(200);
    }

    @Test
    public void assertLengthOfList() {
        List<Map<String, Object>> products = get("http://localhost:8080/books").as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(products, hasSize(3));
    }

    @Test
    public void assertSchema() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();
        when().
                get("http://localhost:8080/books").
        then().
                assertThat().body(matchesJsonSchemaInClasspath("list-books-schema.json").using(jsonSchemaFactory));
    }


// hasItems(hasEntry("someProperty", "someValue")


/*

         Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("id", 1);
        expected.put("title", "Der Herr der Ringe - Die Gefährten");
        expected.put("author", "John R. R. Tolkien");
        expected.put("averageRating", 5.0);
        expected.put("totalVotes", 1);

        when().get("http://localhost:8080/books")

        .then().assertThat().body(hasItem(expected));



     String[] ids = {
                "Der Herr der Ringe - Die Gefährten",
                "Der Herr der Ringe - Die zwei Türme",
                "Der Herr der Ringe - Die Rückkehr des Königs"};
        given().when().get("http://localhost:8080/books")
                .then().body("title", equalTo(ids));
        String[] ids = {
                "Der Herr der Ringe - Die Gefährten",
                "Der Herr der Ringe - Die zwei Türme",
                "Der Herr der Ringe - Die Rückkehr des Königs"};
        given().when().get("http://localhost:8080/books")
                .then().body("title", equalTo(ids));*/

}
