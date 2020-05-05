package tests.util;
import com.mailslurp.api.api.*;
import com.mailslurp.client.*;
import com.mailslurp.models.*;
import org.joda.time.DateTimeUtils;
import org.threeten.bp.ZoneId;

import java.util.Date;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class MailSlurp {

    private final String API_KEY = "0bf7b18c2a9bb2106177214a5c097cf9d2d9608e829e53d1ed8dd759e1859b81";
    private final String lowRatingInboxId = "20fd08d4";
    private final String highRatingInboxId = "2f0df9b8";


    ApiClient defaultClient;
    InboxControllerApi inboxControllerApi;
    Inbox inbox;
    CommonActionsControllerApi commonActionsControllerApi;
    final private String ratingSubject = "Schreib einen Kommentar zu deiner Bewertung";


    public MailSlurp() {
        defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setApiKey(API_KEY);
        inboxControllerApi = new InboxControllerApi(defaultClient);
        commonActionsControllerApi = new CommonActionsControllerApi(defaultClient);
        try{
        inboxControllerApi.getInboxes().forEach(i -> {
            try{
            commonActionsControllerApi.emptyInbox(i.getId());
            }catch (Exception e){}
        });
        }catch (Exception e){}


    }

    public void checkLowRatingEmail() {
        try{
            inboxControllerApi.getInboxes().forEach(i -> {
                if(i.getName().equals("LowRating")){
                    inbox = i;
                }
            }
            );
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(inbox.getName());
        String body = "Hallo Moderator,\r\n\r\n danke für deine Bewertung! Mach deine Bewertung noch wertvoller, indem du einen Kommentar schreibst.\r\nWas hat dir besonders gut gefallen? Kennst du vielleicht andere Bücher die Lesern dieses Buches gefallen könnten?\r\n Teile jetzt deine Meinung mit Anderen!\r\n\r\n Viele Grüße\r\n dein Book-Reviewer Team\r\n";
        checkEmail(ratingSubject, body);
    }

    public void checkHighRatingEmail() {
        try{
            inboxControllerApi.getInboxes().forEach(i -> {
                if(i.getName().equals("HighRating")){
                    inbox = i;
                }
                    }
            );
        } catch (Exception e){
            e.printStackTrace();
        }
        String body = "Hallo User,\r\n\r\n danke für deine Bewertung! Mach deine Bewertung noch wertvoller, indem du einen Kommentar schreibst.\r\nWas hat dir besonders gut gefallen? Kennst du vielleicht andere Bücher die Lesern dieses Buches gefallen könnten?\r\n Teile jetzt deine Meinung mit Anderen!\r\n\r\n Viele Grüße\r\n dein Book-Reviewer Team\r\n";
        checkEmail(ratingSubject, body);
    }


    private void checkEmail(String subject, String body) {
        WaitForControllerApi waitForControllerApi = new WaitForControllerApi(defaultClient);
        Email email = null;
        try {
            email = waitForControllerApi
                    .waitForLatestEmail(inbox.getId(), 20000L, true);
        }catch(Exception e) {
            e.printStackTrace();
        }
        Long createdAt = email.getCreatedAt().plusSeconds(30).atZoneSameInstant(ZoneId.systemDefault()).toInstant().toEpochMilli();
        assertThat(createdAt, greaterThan(new Date().getTime()));
        assertThat(email.getSubject(), equalTo(subject));
        assertThat(email.getBody(), equalTo(body));
    }

}
