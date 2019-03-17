package wework.contact;

import io.restassured.response.Response;

import java.util.HashMap;

/**
 * Created by jiaxiong on 2019-03-17 10:46
 */
public class Member extends Contact{

    public Response create(HashMap<String,Object> map){
        String body=template("/data/member.json",map);
        reset();
        return requestSpecification
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }
}
