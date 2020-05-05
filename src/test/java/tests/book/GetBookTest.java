package tests.book;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GetBookTest {



    @Test
    public void assertStatusCode() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                statusCode(200);
    }

    @Test
    public void assertSchema() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze()).freeze();
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                assertThat().body(matchesJsonSchemaInClasspath("book-hdr-schema.json").using(jsonSchemaFactory));
    }


    @Test
    public void assertTitle() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("title", equalTo("Der Herr der Ringe - Die Gefährten"));
    }

    @Test
    public void assertAuthor() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("author", equalTo("John R. R. Tolkien"));
    }

    @Test
    public void assertGenre() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("genre", equalTo("Fantasie"));
    }

    @Test
    public void assertPublisher() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("publisher", equalTo("Houghton Mifflin"));
    }

    @Test
    public void assertPages() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("pages", equalTo(608));
    }

    @Test
    public void assertPublishingYear() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("publishingYear", equalTo(1954));
    }

    @Test
    public void assertContent() {
        String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("content", equalTo(content));
    }

    @Test
    public void assertTotalVotes() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("totalVotes", equalTo(2));
    }

    @Test
    public void assertAverageRating() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("averageRating", equalTo(4.5f));
    }

    @Test
    public void assertISBN() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("isbn", equalTo("978-3608939811"));
    }

    @Test
    public void assertLanguages() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("languages", hasItems("Deutsch", "Englisch", "Französisch", "Spanisch"));
    }

    @Test
    public void assertKeywords() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("keywords", hasItems("Zauberer", "Zwerge", "Orks", "Drachen", "mystisch", "episch"));
    }

    @Test
    public void assertHardcoverOffersPrices() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("hardcoverOffers.price", hasItems(17.99F, 17.98F, 19.99F));
    }

    @Test
    public void assertHardcoverOffersVendor() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("hardcoverOffers.vendor", hasItems("Buchladen123.de", "Buchverkauf 24", "Your favorite book vendor"));
    }

    @Test
    public void assertHardcoverOffersAffiliateLink() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("hardcoverOffers.affiliateLink", hasItems("www.Buchladen123.de/978-3608939811?media=hardcover&affiliate=bookreviewer",
                        "www.buch-verkauf24.de/bookreviewer/978-3608939811",
                        "www.your-favorite-book-vendor.com/bookreviewer/978-3608939811"));
    }

    @Test
    public void assertHardcoverOffersMediaType() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("hardcoverOffers.mediaType", hasItems("HARDCOVER", "HARDCOVER", "HARDCOVER"));
    }

    @Test
    public void assertPaperbackOffersPrices() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("paperbackOffers.price", hasItems(12.99F, 14.98F, 16.99F));
    }

    @Test
    public void assertPaperbackOffersVendor() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("paperbackOffers.vendor", hasItems("Buchladen123.de", "Buchverkauf 24", "Your favorite book vendor"));
    }

    @Test
    public void assertPaperbackOffersAffiliateLink() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("paperbackOffers.affiliateLink", hasItems("www.Buchladen123.de/978-3608939811?media=paperback&affiliate=bookreviewer",
                        "www.buch-verkauf24.de/bookreviewer/978-3608939811",
                        "www.your-favorite-book-vendor.com/bookreviewer/978-3608939811"));
    }

    @Test
    public void assertPaperbackOffersMediaType() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("paperbackOffers.mediaType", hasItems("PAPERBACK", "PAPERBACK", "PAPERBACK"));
    }

    @Test
    public void assertEbookOffersPrices() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("ebookOffers.price", hasItems(10.99F, 10.99F, 13.99F));
    }

    @Test
    public void assertEbookOffersVendor() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("ebookOffers.vendor", hasItems("Buchladen123.de", "Buchverkauf 24", "Your favorite book vendor"));
    }

    @Test
    public void assertEbookOffersAffiliateLink() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("ebookOffers.affiliateLink", hasItems("www.Buchladen123.de/978-3608939811?media=ebook&affiliate=bookreviewer",
                        "www.buch-verkauf24.de/bookreviewer/978-3608939811",
                        "www.your-favorite-book-vendor.com/bookreviewer/978-3608939811"));
    }

    @Test
    public void assertebookOffersMediaType() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("ebookOffers.mediaType", hasItems("EBOOK", "EBOOK", "EBOOK"));
    }

    @Test
    public void assertAudiobookOffersPrices() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("audiobookOffers.price", hasItems(9.99F, 9.99F, 13.99F));
    }

    @Test
    public void assertAudiobookOffersVendor() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("audiobookOffers.vendor", hasItems("Buchladen123.de", "Buchverkauf 24", "Your favorite book vendor"));
    }

    @Test
    public void assertAudiobookOffersAffiliateLink() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("audiobookOffers.affiliateLink", hasItems("www.Buchladen123.de/978-3608939811?media=audiobook&affiliate=bookreviewer",
                        "www.buch-verkauf24.de/bookreviewer/978-3608939811",
                        "www.your-favorite-book-vendor.com/bookreviewer/978-3608939811"));
    }

    @Test
    public void assertAudiobookOffersMediaType() {
        when().
                get("http://localhost:8080/books/{id}", 1).
                then().
                body("audiobookOffers.mediaType", hasItems("AUDIOBOOK", "AUDIOBOOK", "AUDIOBOOK"));
    }



}
