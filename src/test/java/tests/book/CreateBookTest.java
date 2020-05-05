package tests.book;


import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.util.Auth;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class CreateBookTest {

    @Test
    public void assertStatusCode() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books")
                .thenReturn();
        assertThat(response.getStatusCode(), equalTo(201));
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithAuthorAndTitle() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");

        Response response = given().
                contentType("application/json").
                body(book).
        when().
                post("http://localhost:8080/books").

        thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("title", equalTo("title")).
                body("author", equalTo("author"));

        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithGenre() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setGenre("genre");

        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books").
                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("genre", equalTo("genre"));

        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithKeywords() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        String[] keywords = {"keyword1", "keyword2"};
        book.setKeywords(keywords);

        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books").

                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("keywords", hasItems(keywords));

        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithLanguages() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        String[] languages = {"lan1", "lan2"};
        book.setLanguages(languages);

        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books").

                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("languages", hasItems(languages));

        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithPublisher() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setPublisher("publisher");

        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books").

                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("publisher", equalTo("publisher"));

        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithPages() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setPages(123);

        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books").

                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("pages", equalTo(123));

        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithIsbn() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setIsbn("978-3608939811");

        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books").

                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("isbn", equalTo("978-3608939811"));

        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithPublishingYear() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setPublishingYear(2000);

        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books").

                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("publishingYear", equalTo(2000));

        deleteCreatedUser(id);
    }

    @Test
    public void CreateBookWithPublishingContent() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setContent("content");

        Response response = given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books").

                thenReturn();
        String body = response.getBody().print();
        int id = Integer.parseInt(body);
        get("http://localhost:8080/books/{id}", id).
                then().
                statusCode(200).
                body("content", equalTo("content"));

        deleteCreatedUser(id);
    }

    @Test
    public void assertStatusCodeOnBookWithoutTitle() {
        Book book = new Book();
        book.setTitle("title");
        given().
                contentType("application/json").
                body(book).
        when().
                post("http://localhost:8080/books")
        .then()
                .statusCode(400);
    }

    @Test
    public void assertStatusCodeOnBookWithoutAuthor() {
        Book book = new Book();
        book.setAuthor("author");
        given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books")
                .then()
                .statusCode(400);
    }

    @Test
    public void assertStatusCodeOnInvalidIsbn() {
        Book book = new Book();
        book.setTitle("title");
        book.setAuthor("author");
        book.setIsbn("1");
        given().
                contentType("application/json").
                body(book).
                when().
                post("http://localhost:8080/books")
                .then()
                .statusCode(400);
    }

    private void deleteCreatedUser(int id) {
        given().auth().oauth2(Auth.getAdminToken()).
        when().
                delete("http://localhost:8080/books/{id}", id);
    }


}
