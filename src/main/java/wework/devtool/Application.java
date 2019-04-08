package wework.devtool;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import wework.Api;
import wework.Wework;

/**
 * Created by jiaxiong on 2019-03-24 17:11
 */
public class Application extends Api {

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
