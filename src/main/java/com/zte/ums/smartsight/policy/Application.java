package com.zte.ums.smartsight.policy;

import com.zte.ums.smartsight.policy.domain.core.cache.InitializationCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by 10172605 on 2016/9/26.
 */

@SpringBootApplication
@EnableSwagger2
public class Application {
    private static boolean debug = false;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Application.debug = debug;
    }

    private static class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
        @Override
        public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            InitializationCache initializationTables = contextRefreshedEvent.getApplicationContext().getBean(InitializationCache.class);
            initializationTables.initialCache();
        }
    }
}
