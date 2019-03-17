package demo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by jiaxiong on 2019-03-16 10:37
 */
public class Xueqiu {

    @Test
    public void testSearch() {
        //信任https的任何证书
        useRelaxedHTTPSValidation();
        //所有接口都走代理
        //RestAssured.proxy("127.0.0.1",8080);

        //given开头表示输入数据
        given().log().all()
                //设置代理
                //.proxy("127.0.0.1",8888)
                //请求参数
                .queryParam("code", "sogo")
                //header信息
                .header("Cookie", "device_id=fd9bf9f458e12800817e028437c29732; " +
                        "aliyungf_tc=AQAAABaSyhndMQEA17B4ewrwrB/h+Bwi; " +
                        "xq_a_token=8309c28a83ae5d20f26b7fcc22debbcd459794bd; " +
                        "xq_a_token.sig=ekfY9a_we8nNlhOpvhWeZz85MrU;" +
                        " xq_r_token=d55d09822791a788916028e59055668bed1b7018; " +
                        "xq_r_token.sig=h9qWLLwRXV-QxfHHukEC2U76ZDA; " +
                        "_ga=GA1.2.455106336.1552704565; " +
                        "_gid=GA1.2.367752314.1552704565;" +
                        " _gat=1; u=561552704565084")
                //触发条件
                .when()
                //qingqiuurl地址
                .get("https://xueqiu.com/stock/search.json")
                //对结果断言
                .then().log().all()
                //状态码断言
                .statusCode(200)
                //字段断言
                .body("stocks.name", hasItems("搜狗"))
                .body("q", equalTo("sogo"))
                .body("size", is(10))
                .body("stocks.code", hasItems("SOGO"))
                .body("stocks.find{it.code=='SOGO'}.name",equalTo("搜狗"));
    }
}
