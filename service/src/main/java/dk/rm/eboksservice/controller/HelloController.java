package dk.rm.eboksservice.controller;

import dk.rm.eboksservice.service.HelloService;
import dk.rm.eboksservice.service.model.HelloServiceInput;
import org.openapitools.api.DefaultApi;
import org.openapitools.model.HelloRequest;
import org.openapitools.model.HelloResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements DefaultApi {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public ResponseEntity<HelloResponse> eboksServiceHelloPost(HelloRequest helloRequest) {
        logger.debug("Enter POST hello.");

        var serviceInput = new HelloServiceInput();
        serviceInput.setName(helloRequest.getName());

        var serviceResponse = helloService.helloServiceBusinessLogic(serviceInput);

        var helloResponse = new org.openapitools.model.HelloResponse();
        helloResponse.setName(serviceResponse.getName());
        helloResponse.setNow(serviceResponse.getNow().toOffsetDateTime());

        return ResponseEntity.ok(helloResponse);
    }
}
