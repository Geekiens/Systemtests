package tests.book;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.util.Auth;
import static io.restassured.RestAssured.*;

public class DeleteBookTest {

    @Test
    public void assertStatusCode() {
        int id = createBook();
        given().auth().oauth2(Auth.getAdminToken()).
                when().
                delete("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200);
    }

    @Test
    public void assertNoExistingBook() {
        given().auth().oauth2(Auth.getAdminToken()).
                when().
                delete("http://localhost:8080/books/{id}", 123456).
                then().
                statusCode(404);
    }

    @Test
    public void assertDeleteAsAdmin() {
        int id = createBook();
        given().auth().oauth2(Auth.getAdminToken()).
                when().
                delete("http://localhost:8080/books/{id}", id);
        checkExistence(id, false);

    }

    @Test
    public void assertDeleteAsModerator() {
        int id = createBook();
        given().auth().oauth2(Auth.getModeratorToken()).
                when().
                delete("http://localhost:8080/books/{id}", id);
        checkExistence(id, false);
    }

    @Test
    public void assertDeleteAsUser() {
        int id = createBook();
        given().auth().oauth2(Auth.getUserToken()).
                when().
                delete("http://localhost:8080/books/{id}", id);
        checkExistence(id, true);
        cleanUp(id);
    }

    private void checkExistence(int id, boolean exist){
        if (exist){
            get("http://localhost:8080/books/{id}", id).
                    then().
                    statusCode(200);
        }
        else {
            get("http://localhost:8080/books/{id}", id).
                    then().
                    statusCode(404);
        }
    }

    private void cleanUp(int id){
        given().auth().oauth2(Auth.getAdminToken()).
                when().
                delete("http://localhost:8080/books/{id}", id);
    }

    private int createBook() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        Response response = given().
                    contentType("application/json").
                    body(book).
                    when().
                    post("http://localhost:8080/books")
                    .thenReturn();

        String body = response.getBody().print();
        return Integer.parseInt(body);
    }
}
