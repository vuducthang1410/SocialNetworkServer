package wint.webchat.config;

import io.netty.util.concurrent.ThreadPerTaskExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);//số luồng tối thiều
        executor.setMaxPoolSize(10);//số luồng tối đa
        executor.setQueueCapacity(1000);//kích thước hàng đợi chờ
        executor.setThreadNamePrefix("AsyncThreadRedis-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
//    AbortPolicy (chính sách từ chối): Một RuntimeException sẽ được ném ra khi hàng đợi đã đầy và có thêm task mới được gửi đến.
//        CallerRunsPolicy (chính sách chạy của người gọi): Task mới sẽ được thực thi bởi luồng gọi đến ThreadPoolTaskExecutor.
//        DiscardPolicy (chính sách loại bỏ): Task mới sẽ được loại bỏ nếu hàng đợi đã đầy.
//        DiscardOldestPolicy (chính sách loại bỏ cũ nhất): Task cũ nhất trong hàng đợi sẽ được loại bỏ để làm cho chỗ cho task mới. tôi cần code minh họa về 4 cách này trong spring boot


