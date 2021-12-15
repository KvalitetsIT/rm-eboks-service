package dk.rm.eboksservice.dias;

import dk.rm.eboksservice.configuration.EboksServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class DiasMailClient {
    private static final Logger logger = LoggerFactory.getLogger(DiasMailClient.class);
    private final URI url;
    private final String recipient;
    private final String sender;
    private final RestTemplate restTemplate;

    public DiasMailClient(EboksServiceProperties properties, RestTemplate restTemplate) throws URISyntaxException {
        this.url = new URI(properties.getDiasMailServiceUrl());
        recipient = properties.getDiasMailServiceRecipient();
        sender = properties.getDiasMailServiceSender();
        this.restTemplate = restTemplate;
        logger.info("Dias mail client initialized with url: " + url);
    }

    public void sendMail(String subject, String body) throws DiasMailException {
        DiasMailRequest diasRequest = new DiasMailRequest(recipient, sender, subject, body);
        RequestEntity<DiasMailRequest> requestEntity = new RequestEntity<>(diasRequest, HttpMethod.POST, url);
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new DiasMailException("Dias mail service failed with status " + response.getStatusCodeValue() + ". " + response.getBody());
        }
    }

    private class DiasMailRequest {
        private String recipient;
        private String sender;
        private String subject;
        private String body;
        private boolean html = true;

        public DiasMailRequest(String recipient, String sender, String subject, String body) {
            this.recipient = recipient;
            this.sender = sender;
            this.subject = subject;
            this.body = body;
        }

        public String getRecipient() {
            return recipient;
        }

        public String getSender() {
            return sender;
        }

        public String getSubject() {
            return subject;
        }

        public String getBody() {
            return body;
        }

        public boolean isHtml() {
            return html;
        }
    }
}
