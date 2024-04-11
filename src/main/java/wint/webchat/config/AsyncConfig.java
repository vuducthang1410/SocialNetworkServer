package wint.webchat.config;

import io.netty.util.concurrent.ThreadPerTaskExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);//số luồng tối thiều
        executor.setMaxPoolSize(10);//số luồng tối đa
        executor.setQueueCapacity(100);//kích thước hàng đợi chờ
        executor.setThreadNamePrefix("AsyncThreadRedis-");
        executor.initialize();
        return executor;
    }
}
