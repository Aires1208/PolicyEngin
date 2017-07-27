package com.zte.ums.smartsight.policy.domain.system;

import com.zte.ums.smartsight.policy.domain.constant.EnvConstant;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 * Created by aires on 2016/9/30.
 */
@Configuration
public class ApplicationContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContext.class);

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return container -> {
            if (TomcatEmbeddedServletContainerFactory.class.isAssignableFrom(container.getClass())) {
                int cacheSize = 1024 * 1024;
                LOGGER.info("Customizing tomcat factory. New cache size (KB) is " + cacheSize / 1024);
                TomcatEmbeddedServletContainerFactory tomcatFactory = (TomcatEmbeddedServletContainerFactory) container;
                tomcatFactory.addContextCustomizers((context) -> {
                    StandardRoot standardRoot = new StandardRoot(context);
                    standardRoot.setCacheMaxSize(cacheSize);
                    context.setResources(standardRoot);
                });
            }
        };
    }


    public org.apache.hadoop.conf.Configuration configuration() {
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", EnvConstant.ZK_QUORUM);
        conf.set("hbase.zookeeper.property.clientPort", EnvConstant.ZK_PORT);
        return conf;
    }

    @Bean
    public HbaseTemplate hbaseTemplate() {
        return new HbaseTemplate(configuration());
    }

}
