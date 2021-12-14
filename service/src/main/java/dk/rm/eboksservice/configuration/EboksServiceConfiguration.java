package dk.rm.eboksservice.configuration;

import dk.rm.eboksservice.dias.DiasMailClient;
import dk.rm.eboksservice.service.EboksService;
import dk.rm.eboksservice.service.EboksServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Configuration
public class EboksServiceConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DiasMailClient diasMailClient(RestTemplate restTemplate, EboksServiceProperties properties) throws URISyntaxException {
        return new DiasMailClient(properties, restTemplate);
    }

    @Bean
    public EboksService eboksService(DiasMailClient diasMailClient) {
        return new EboksServiceImpl(diasMailClient);
    }
}
