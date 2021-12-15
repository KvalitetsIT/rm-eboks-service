package dk.rm.eboksservice.controller;

import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.EboksService;
import dk.rm.eboksservice.service.EboksServiceException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.service.model.EboksServiceOutput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.openapitools.model.SendRequest;

import java.time.ZonedDateTime;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

public class EboksServiceControllerTest {
    private EboksServiceController eboksServiceController;
    private EboksService eboksService;

    @Before
    public void setup() {
        eboksService = Mockito.mock(EboksService.class);

        eboksServiceController = new EboksServiceController(eboksService);
    }

    @Test
    public void testCallController() throws DiasMailException, EboksServiceException {
        var request = new SendRequest();
        request.setCpr("1111111111");
        request.setTemplate("TestTemplate");

        var expectedDate = ZonedDateTime.now();
        Mockito.when(eboksService.eboksServiceBusinessLogic(Mockito.any())).then(input -> {
            EboksServiceOutput output = new EboksServiceOutput("success");
            output.setMessage(input.getArgument(0, EboksServiceInput.class).getCpr());
            return output;
        });

        var result = eboksServiceController.eboksServiceSendPost(request);

        assertNotNull(result);
        assertEquals(request.getCpr(), result.getBody().getMessage());

        var inputArgumentCaptor = ArgumentCaptor.forClass(EboksServiceInput.class);
        Mockito.verify(eboksService, times(1)).eboksServiceBusinessLogic(inputArgumentCaptor.capture());

        assertNotNull(inputArgumentCaptor.getValue());
        assertEquals(request.getCpr(), inputArgumentCaptor.getValue().getCpr());
    }
}
