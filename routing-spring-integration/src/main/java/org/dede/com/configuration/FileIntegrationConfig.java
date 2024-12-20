package org.dede.com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.nio.file.Files;

@Configuration
public class FileIntegrationConfig {

    @Bean
    public IntegrationFlow uppercaseFlow() {
        return IntegrationFlow.from(sourceDirectory(), c -> c.poller(Pollers.fixedDelay(10000)))
                .transform(new FileToStringTransformer())
                .transform(s -> s.toString().toUpperCase())
                .handle(targetDirectory())
                .get();
    }

    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File("D:\\WORK\\IntellijWorkspace\\camelPlay\\routing-spring-integration\\src\\main\\resources\\input"));
        return messageSource;
    }

    @Bean
    public MessageHandler targetDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("D:\\WORK\\IntellijWorkspace\\camelPlay\\routing-spring-integration\\src\\main\\resources\\output"));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        return handler;
    }
}
