package dk.rm.eboksservice.controller;

import dk.rm.eboksservice.service.EboksService;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import org.openapitools.api.DefaultApi;
import org.openapitools.model.SendRequest;
import org.openapitools.model.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EboksServiceController implements DefaultApi {
    private static final Logger logger = LoggerFactory.getLogger(EboksServiceController.class);
    private final EboksService eboksService;

    public EboksServiceController(EboksService eboksService) {
        this.eboksService = eboksService;
    }

    @Override
    public ResponseEntity<SendResponse> eboksServiceSendPost(SendRequest sendRequest) {
        logger.debug("Enter eboksServiceSendPost");

        var serviceInput = new EboksServiceInput();
        serviceInput.setCpr(sendRequest.getCpr());
        serviceInput.setTemplate(sendRequest.getTemplate());

        var serviceResponse = eboksService.eboksServiceBusinessLogic(serviceInput);

        var sendResponse = new SendResponse();
        sendResponse.setMessage(serviceResponse.getMessage());
        return ResponseEntity.ok(sendResponse);
    }
}
