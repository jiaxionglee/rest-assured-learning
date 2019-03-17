package wework;

/**
 * Created by jiaxiong on 2019-03-17 10:26
 */
public class WeworkConfig {

    public String openId = "";
    public String secret = "Wl80uneSo8YTh6K9sfdXUjLVVRhxEaJJKP911LjDOIA";
    public String corpid = "wwec3396e8b29f3ee9";
    public String contactSecret = "pwmI-L41hs6k0-nUrd92vwRLddMG8ZkuvgJTfzCOjVU";

//    public String agentId="1000005";
//    public String secret="1JPyY9GvPLZfpvxEDjok-Xt_9v7HIBYJhZUoO6EgNGY";
//    public String corpid = "wwd6da61649bd66fea";
//    public String contactSecret="C7uGOrNyxWWzwBsUyWEbLQdOqoWPz4hNvxj9RIFv-4U";

    private static WeworkConfig weworkConfig;

    public static WeworkConfig getInstance() {
        if (weworkConfig == null) {
            weworkConfig = new WeworkConfig();
        }
        return weworkConfig;
    }
}
