import io.restassured.builder.ResponseBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Created by jiaxiong on 2019-03-21 10:29
 */
public class TestSchema {

    @Test
    public void testSchema(){

        given().log().all()
                .cookie("device_id=fd9bf9f458e12800817e028437c29732; aliyungf_tc=AQAAABaSyhndMQEA17B4ewrwrB/h+Bwi; _ga=GA1.2.455106336.1552704565; xq_a_token=682c39a460645dafb1ff41f67e0efccba8b0f118; xq_a_token.sig=Bg9acTC-woVSsS6DZvdAtd40CQU; xq_r_token=798a7cab8cd606f61a09fbac15374f1172b00607; xq_r_token.sig=sHSWFNmu_GqEUOK9A-6umfNgFcU; u=571553135035943")
                .param("code","sogo")
                .when().get("https://xueqiu.com/stock/search.json")
                .then().log().all().body(matchesJsonSchemaInClasspath("schema/xueqiu.schema"));
    }

    @Test
    public void testFilter(){

        given().filter((req,res,ctx)-> {
            //next之前篡改request，next之后篡改response
            Response responseOrigin=ctx.next(req,res);
            ResponseBuilder responseBuilder=new ResponseBuilder().clone(responseOrigin);
            String decodeContext=new String(Base64.getDecoder().decode(responseOrigin.body().asString().trim()));

            responseBuilder.setBody(decodeContext);
            return  responseBuilder.build();

        }).log().all()
                    .cookie("device_id=fd9bf9f458e12800817e028437c29732; aliyungf_tc=AQAAABaSyhndMQEA17B4ewrwrB/h+Bwi; _ga=GA1.2.455106336.1552704565; xq_a_token=682c39a460645dafb1ff41f67e0efccba8b0f118; xq_a_token.sig=Bg9acTC-woVSsS6DZvdAtd40CQU; xq_r_token=798a7cab8cd606f61a09fbac15374f1172b00607; xq_r_token.sig=sHSWFNmu_GqEUOK9A-6umfNgFcU; u=571553135035943")
                    .param("code","sogo")
                    .when().get("https://xueqiu.com/stock/search.json")
                    .then().log().all().body(matchesJsonSchemaInClasspath("schema/xueqiu.schema"));
        }

}
