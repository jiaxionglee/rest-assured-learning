package wework.contact;

import io.restassured.http.ContentType;
import wework.Restful;
import wework.Wework;

import static io.restassured.RestAssured.given;

/**
 * Created by jiaxiong on 2019-03-17 14:55
 */
public class Contact extends Restful {

    String random = String.valueOf(System.currentTimeMillis());

    public Contact() {
        reset();
    }

    public void reset() {
        requestSpecification = given()
                .log().all()
                .queryParam("access_token", Wework.getToken())
                .contentType(ContentType.JSON);
    }
}
