package wint.webchat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import wint.webchat.enums.ChannelRedis;
import wint.webchat.redis.AuthRedis.AuthListener;
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
        container.addMessageListener(authListener, new ChannelTopic(ChannelRedis.AUTH_CHANNEL.getChannelValue()));
        container.addMessageListener(mailListener,new ChannelTopic(ChannelRedis.MAIL_CHANNEL.getChannelValue()));
        return container;
    }

}
