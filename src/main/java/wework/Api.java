package wework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

/**
 * Created by jiaxiong on 2019-03-17 14:46
 */
public class Api {

    static HashMap<String, Object> query = new HashMap<String, Object>();

    public Api(){
        useRelaxedHTTPSValidation();
    }

    public RequestSpecification getDefaultRequestSpecification() {
        return given().log().all();
    }

    public static String template(String path, HashMap<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Api.class.getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    public Response getResponseFromHar(String pattern, String path, HashMap<String, Object> map) {
        //从har读取请求，进行更新
        Restful restful = getApiFromHar(path, pattern);
        restful = updateApiFromMap(restful, map);
        return getResponseFromApi(restful);
    }

    public Restful getApiFromHar(String path, String pattern) {

        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile(new File(
                    URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8")));
            HarRequest request=new HarRequest();
            Boolean match = false;
            for (HarEntry entry : har.getLog().getEntries()) {
                request = entry.getRequest();
                if (request.getUrl().matches(pattern)) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                request = null;
            }

            Restful restful = new Restful();
            restful.method = request.getMethod().name().toLowerCase();
            restful.url = request.getUrl();
            request.getQueryString().forEach(q -> {
                restful.query.put(q.getName(), q.getValue());
            });

            restful.body = request.getPostData().getText();
            return restful;
        } catch (HarReaderException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Restful getApiFromyaml(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(path), Restful.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Restful updateApiFromMap(Restful restful, HashMap<String, Object> map) {
        if (restful.method.toLowerCase().contains("get")) {
            map.entrySet().forEach(entry -> {
                restful.query.replace(entry.getKey(), entry.getValue().toString());
            });
        }

        if (restful.method.toLowerCase().contains("post")) {
            if (map.containsKey("_body")) {
                restful.body = map.get("_body").toString();
            }
            if (map.containsKey("_file")) {
                String filePath = map.get("_file").toString();
                map.remove("_file");
                restful.body = template(filePath, map);
            }
        }

        return restful;
    }

    public Response getResponseFromApi(Restful restful) {
        RequestSpecification requestSpecification = getDefaultRequestSpecification();

        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }
        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }
        String[] url=updateUrl(restful.url);
        return requestSpecification.log().all()
                .header("Host",url[0])
                .request(restful.method, url[1])
                .then().log().all().extract().response();
    }

    //支持多环境
    private String[] updateUrl(String url) {
        HashMap<String,String> hosts=WeworkConfig.getInstance().env.get(WeworkConfig.getInstance().current);
        String host="";
        String urlNew="";
        for (Map.Entry<String,String> entry:hosts.entrySet()){
            if (url.contains(entry.getKey())){
                host=entry.getKey();
                urlNew=url.replace(entry.getKey(),entry.getValue());
            }
        }
        return new String[]{host,urlNew};

    }

    public Response getResponseFromYaml(String path, HashMap<String, Object> map) {
        Restful restful = getApiFromyaml(path);
        restful = updateApiFromMap(restful, map);
        return getResponseFromApi(restful);
    }
}
