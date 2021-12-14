package dk.rm.eboksservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EboksServiceProperties {
    @Value( "${DIAS_MAIL_SERVICE_URL}" )
    private String diasMailServiceUrl;

    @Value( "${DIAS_MAIL_SERVICE_RECIPIENT}" )
    private String diasMailServiceRecipient;

    @Value( "${DIAS_MAIL_SERVICE_SENDER}" )
    private String diasMailServiceSender;

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
