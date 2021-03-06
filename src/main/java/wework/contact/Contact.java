package wework.contact;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import wework.Api;
import wework.Wework;

import static io.restassured.RestAssured.given;

/**
 * Created by jiaxiong on 2019-03-17 14:55
 */
public class Contact extends Api {

    String random = String.valueOf(System.currentTimeMillis());

    @Override
    public RequestSpecification getDefaultRequestSpecification(){
        RequestSpecification requestSpecification=super.getDefaultRequestSpecification();
        requestSpecification.queryParam("access_token", Wework.getToken())
                .contentType(ContentType.JSON);
        requestSpecification.filter((req,res,ctx)->{
            return ctx.next(req,res);
        });
        return requestSpecification;
    }
}
