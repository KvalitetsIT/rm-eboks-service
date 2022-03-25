package dk.rm.eboksservice.controller;

import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.EboksService;
import dk.rm.eboksservice.service.EboksServiceException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.service.model.EboksServiceOutput;
import io.micrometer.core.instrument.MeterRegistry;
import org.openapitools.api.DefaultApi;
import org.openapitools.model.SendRequest;
import org.openapitools.model.SendResponse;
import org.openapitools.model.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EboksServiceController implements DefaultApi {
    private static final Logger logger = LoggerFactory.getLogger(EboksServiceController.class);
    private static final String ERROR_COUNTER_METRIC_NAME = "dias.mail.error.count";
    private final EboksService eboksService;
    private final MeterRegistry meterRegistry;

    public EboksServiceController(EboksService eboksService, MeterRegistry meterRegistry) {
        this.eboksService = eboksService;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<SendResponse> eboksServiceSendPost(SendRequest sendRequest) {
        logger.debug("Enter eboksServiceSendPost");

        var serviceInput = new EboksServiceInput();
        serviceInput.setCpr(sendRequest.getCpr());
        serviceInput.setTemplate(sendRequest.getTemplate());

        var sendResponse = new SendResponse();
        try {
            EboksServiceOutput serviceResponse = eboksService.sendToEboks(serviceInput);
            sendResponse.setMessage(serviceResponse.getMessage());
            return ResponseEntity.ok(sendResponse);
        } catch (EboksServiceException e) {
            logger.error("Call to eboksServiceSendPost failed", e);
            sendResponse.setMessage("Call to eboksServiceSendPost failed. " + e.getMessage());
            return ResponseEntity.badRequest().body(sendResponse);
        } catch (DiasMailException e) {
            logger.error("Call to eboksServiceSendPost failed", e);
            sendResponse.setMessage("Call to eboksServiceSendPost failed. " + e.getMessage());
            meterRegistry.counter(ERROR_COUNTER_METRIC_NAME, "code", String.valueOf(e.getStatusCode())).increment();
            return ResponseEntity.internalServerError().body(sendResponse);
        } catch (Exception e) {
            logger.error("Call to eboksServiceSendPost failed", e);
            sendResponse.setMessage("Call to eboksServiceSendPost failed");
            return ResponseEntity.internalServerError().body(sendResponse);
        }

    }

    @Override
    public ResponseEntity<List<TemplateResponse>> eboksServiceTemplatesGet() {
        List<TemplateResponse> templates = eboksService.getTemplates().stream().map(template -> {
            TemplateResponse templateResponse = new TemplateResponse();
            templateResponse.setName(template.getName());
            templateResponse.setDescription(template.getDescription());
            return templateResponse;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(templates);
    }
}
