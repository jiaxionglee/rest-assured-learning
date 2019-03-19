package service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.service.CacheManagerImpl;

/**
 * Created by jiaxiong on 2019-03-18 19:41
 */
public class CacheListener {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private CacheManagerImpl cacheManagerImpl;

    public CacheListener(CacheManagerImpl cacheManagerImpl) {
        this.cacheManagerImpl = cacheManagerImpl;
    }

    public void startListen() {
        new Thread() {
            public void run() {
                while (true) {
                    for (String key : cacheManagerImpl.getAllKeys()) {
                        if (cacheManagerImpl.isTimeOut(key)) {
                            cacheManagerImpl.clearByKey(key);
                            logger.info(key + "缓存被清除");
                        }
                    }
                }
            }
        }.start();

    }
}
