package wework;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by jiaxiong on 2019-03-17 10:26
 */
public class WeworkConfig {

    public String openId = "";
    public String secret = "Wl80uneSo8YTh6K9sfdXUjLVVRhxEaJJKP911LjDOIA";
    public String corpid = "wwec3396e8b29f3ee9";
    public String contactSecret = "pwmI-L41hs6k0-nUrd92vwRLddMG8ZkuvgJTfzCOjVU";

    public String current="test";
    public HashMap<String,HashMap<String,String>> env;

    private static WeworkConfig weworkConfig;

    public static WeworkConfig getInstance() {
        if (weworkConfig == null) {
            weworkConfig = load("/config/WeworkConfig.yaml");
        }
        return weworkConfig;
    }

    public static WeworkConfig load(String path){
        ObjectMapper mapper=new ObjectMapper((new YAMLFactory()));
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(path),WeworkConfig.class);
//            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(WeworkConfig.getInstance()));
//            System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
