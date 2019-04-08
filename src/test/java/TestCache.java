import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.listener.CacheListener;
import service.service.CacheManagerImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiaxiong on 2019-03-18 19:48
 */
public class TestCache {

        private Logger logger = LoggerFactory.getLogger(getClass());
        /**
         * 测试缓存和缓存失效
         */
        @Test
        public void testCacheManager() {
            CacheManagerImpl cacheManagerImpl = new CacheManagerImpl();
            cacheManagerImpl.putCache("test", "test", 10 * 1000L);
            cacheManagerImpl.putCache("myTest", "myTest", 30 * 1000L);
            CacheListener cacheListener = new CacheListener(cacheManagerImpl);
            cacheListener.startListen();

            logger.info("test:" + cacheManagerImpl.getCacheByKey("test").getdata());
            logger.info("all cache:" + cacheManagerImpl.getCacheAll());
            logger.info("myTest:" + cacheManagerImpl.getCacheByKey("myTest").getdata());
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("test:" + cacheManagerImpl.getCacheByKey("test"));
            logger.info("myTest:" + cacheManagerImpl.getCacheByKey("myTest").getdata());
        }

        /**
         * 测试线程安全
         */
        @Test
        public void testThredSafe() {
            final String key = "thread";
            final CacheManagerImpl cacheManagerImpl = new CacheManagerImpl();
            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = 0; i < 100; i++) {
                exec.execute(new Runnable() {
                    public void run() {
                        if (!cacheManagerImpl.isContains(key)) {
                            cacheManagerImpl.putCache(key, 1, 0);
                        } else {
                            //因为+1和赋值操作不是原子性的，所以把它用synchronize块包起来
                            synchronized (cacheManagerImpl) {
                                int value = (Integer) cacheManagerImpl.getCacheDataByKey(key) + 1;
                                cacheManagerImpl.putCache(key,value , 0);
                            }
                        }
                    }
                });
            }
            exec.shutdown();
            try {
                exec.awaitTermination(1, TimeUnit.DAYS);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            logger.info(cacheManagerImpl.getCacheDataByKey(key).toString());
        }
}
