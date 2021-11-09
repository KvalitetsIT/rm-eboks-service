package dk.rm.eboksservice.configuration;

import dk.rm.eboksservice.service.HelloService;
import dk.rm.eboksservice.service.HelloServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfiguration{
    @Bean
    public HelloService helloService() {
        return new HelloServiceImpl();
    }
}
