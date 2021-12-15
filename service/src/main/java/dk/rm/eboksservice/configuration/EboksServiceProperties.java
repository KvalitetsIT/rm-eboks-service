package dk.rm.eboksservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class EboksServiceProperties {
    private static final Logger logger = LoggerFactory.getLogger(EboksServiceProperties.class);

    @Value( "${DIAS_MAIL_SERVICE_URL}" )
    private String diasMailServiceUrl;

    @Value( "${DIAS_MAIL_SERVICE_RECIPIENT}" )
    private String diasMailServiceRecipient;

    @Value( "${DIAS_MAIL_SERVICE_SENDER}" )
    private String diasMailServiceSender;

    @PostConstruct
    private void init() {
        logger.info(toString());
    }

    @Override
    public String toString() {
        return "Eboks service properties: {" +
                "DIAS_MAIL_SERVICE_URL=" + getDiasMailServiceUrl() +
                ", DIAS_MAIL_SERVICE_RECIPIENT=" + getDiasMailServiceRecipient() +
                ", DIAS_MAIL_SERVICE_SENDER=" + getDiasMailServiceSender() +
                '}';
    }

    public String getDiasMailServiceUrl() {
        return diasMailServiceUrl;
    }

    public String getDiasMailServiceRecipient() {
        return diasMailServiceRecipient;
    }

    public String getDiasMailServiceSender() {
        return diasMailServiceSender;
    }
}
