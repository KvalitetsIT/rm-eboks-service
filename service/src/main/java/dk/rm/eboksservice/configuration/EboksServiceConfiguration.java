package dk.rm.eboksservice.configuration;

import dk.rm.eboksservice.service.EboksService;
import dk.rm.eboksservice.service.EboksServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EboksServiceConfiguration {
    @Bean
    public EboksService eboksService() {
        return new EboksServiceImpl();
    }
}
