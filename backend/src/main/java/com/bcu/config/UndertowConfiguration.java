package com.bcu.config;

import java.util.concurrent.Executors;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfiguration implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers(deploymentInfo -> deploymentInfo.setExecutor(Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual thread#", 1).factory())));
    }
}
