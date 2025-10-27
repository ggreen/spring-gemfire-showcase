/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.gemfire.showcase.remove.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.io.csv.CsvReader;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.MessageBuilder;

import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;


    @Value("${spring.rabbitmq.username:guest}")
    private String username = "guest";

    @Value("${spring.rabbitmq.password:guest}")
    private String password   = "guest";

    @Value("${spring.rabbitmq.host:127.0.0.1}")
    private String hostname = "localhost";

    @Bean
    ConnectionNameStrategy connectionNameStrategy(){
        return (connectionFactory) -> applicationName;
    }

    @Bean
    public MessageConverter converter(ObjectMapper objectMapper) {
        return new MessageConverter() {
            @Override
            public Object fromMessage(Message<?> message, Class<?> targetClass) {
                return CsvReader.parse(new String((byte[])message.getPayload(), StandardCharsets.UTF_8));
            }

            @SneakyThrows
            @Override
            public Message<?> toMessage(Object payload, MessageHeaders headers) {
                return MessageBuilder.withPayload(objectMapper.writeValueAsBytes(payload)).build();
            }
        };
    }

}
