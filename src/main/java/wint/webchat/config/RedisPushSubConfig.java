package wint.webchat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import wint.webchat.redis.AuthListener;
import wint.webchat.redis.MailListener;

@Configuration
@RequiredArgsConstructor
public class RedisPushSubConfig {
    private final AuthListener authListener;
    private final LettuceConnectionFactory lettuceConnectionFactory;
    private final MailListener mailListener;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory);
        container.addMessageListener(authListener, new ChannelTopic("auth-channel"));
        container.addMessageListener(mailListener,new ChannelTopic("mail-channel"));
        return container;
    }

}
