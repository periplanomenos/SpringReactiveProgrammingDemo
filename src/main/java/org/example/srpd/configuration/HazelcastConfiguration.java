package org.example.srpd.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.hazelcast.spring.context.SpringManagedContext;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class: HazelcastConfiguration
 */
@Configuration
public class HazelcastConfiguration {

    @Bean
    public SpringManagedContext managedContext() {
        return new SpringManagedContext();
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config config, SpringManagedContext managedContext) {
        config.setManagedContext(managedContext);

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public Config config() {
        return new Config()
                .setInstanceName("hazelcast-instance");
    }

}