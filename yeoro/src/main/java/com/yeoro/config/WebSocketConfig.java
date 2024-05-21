package com.yeoro.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // 메시지 구독 url: topic 구독
        config.enableSimpleBroker("/sub");

        // 메시지 발행 url
        config.setApplicationDestinationPrefixes("/pub");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("ws")
                .setAllowedOriginPatterns("*");
    }
}