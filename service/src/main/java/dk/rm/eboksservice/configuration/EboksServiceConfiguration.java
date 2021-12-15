package dk.rm.eboksservice.configuration;

import dk.rm.eboksservice.dias.DiasMailClient;
import dk.rm.eboksservice.service.EboksService;
import dk.rm.eboksservice.service.EboksServiceException;
import dk.rm.eboksservice.service.EboksServiceImpl;
import dk.rm.eboksservice.templates.Template;
import dk.rm.eboksservice.templates.TemplateReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

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
    public Map<String, Template> templates(EboksServiceProperties properties) throws EboksServiceException {
        return TemplateReader.readTemplates(properties.getTemplatesPath());
    }

    @Bean
    public EboksService eboksService(DiasMailClient diasMailClient, Map<String, Template> templates) {
        return new EboksServiceImpl(diasMailClient, templates);
    }
}
