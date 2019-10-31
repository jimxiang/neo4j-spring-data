package com.service.guarantee;

import com.service.guarantee.configuration.ThreadConfiguration;
import com.service.guarantee.executor.VisibleThreadPoolTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableAsync
public class GuaranteeApplication {
    private static final Logger logger = LoggerFactory.getLogger(GuaranteeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GuaranteeApplication.class, args);
    }

    @Profile("dev")
    @Bean
    public String devBean() {
        return "dev";
    }

    @Profile("prod")
    @Bean
    public String prodBean() {
        return "prod";
    }

    @Autowired
    private ThreadConfiguration config;

    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        logger.info("Start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setThreadNamePrefix(config.getPrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
