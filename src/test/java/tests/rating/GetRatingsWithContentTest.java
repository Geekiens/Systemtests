package tests.rating;

import org.testng.annotations.Test;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.common.mapper.*;

import java.util.List;
import java.util.Map;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetRatingsWithContentTest {

    @Test
    public void assertStatusCode() {
        given().when().get("http://localhost:8080/books/{id}/ratings/content", 1)
                .then().statusCode(200);
    }

    @Test
    public void assertLengthOfList() {
        List<Map<String, Object>> products = get("http://localhost:8080/books/{id}/ratings/content", 1).as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(products, hasSize(1));
    }

    @Test
    public void assertSchema() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();
        when().
                get("http://localhost:8080/books/1/ratings/content").
                then().
                assertThat().body(matchesJsonSchemaInClasspath("list-ratings-comment-schema.json").using(jsonSchemaFactory));
    }
}
