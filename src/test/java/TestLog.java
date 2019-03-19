import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiaxiong on 2019-03-19 08:07
 */
public class TestLog {

    private static Logger logger= LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args){

        logger.debug("this is debug log");
        logger.info("this is info log");
        logger.error("this is error log");
    }
}
