package demo;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by jiaxiong on 2019-03-16 10:13
 */
public class Baidu {


    @Test
    public void testGetHtml(){
        given().get("http://www.baidu.com").then().statusCode(200);
    }
}
