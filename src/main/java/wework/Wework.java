package wework;

import io.restassured.RestAssured;

/**
 * Created by jiaxiong on 2019-03-17 10:33
 */
public class Wework {

    private static String token;

    private static String getWeworkToken(String secret) {
        return RestAssured.given().log().all()
                .queryParam("corpid", WeworkConfig.getInstance().corpid)
                .queryParam("corpsecret", secret)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all().statusCode(200)
                .extract().path("access_token");
    }

    public static String getToken() {
        //todo
        if (token == null) {
            token = getWeworkToken(WeworkConfig.getInstance().contactSecret);
        }
        return token;
    }
}
