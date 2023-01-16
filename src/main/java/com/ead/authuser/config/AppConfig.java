package com.ead.authuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class AppConfig {

    // ...

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(
            final LocalValidatorFactoryBean factory) {
        return new ValidatingMongoEventListener(factory);
    }

//    @Bean
//    @LoadBalanced
//    UserCourseClient courseClient(){
//
//        InstanceInfo service = eurekaClient.getApplication("course")
//                .getInstances()
//                .get(0);
//        String hostName =  service.getHostName();
//        int port = service.getPort();
//
//        WebClient client = WebClient.builder()
//                .baseUrl("http://" + hostName + ":" + port)
//                .build();
//
//        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client))
//                .build();
//
//        return proxyFactory.createClient(UserCourseClient.class);
//    }

}