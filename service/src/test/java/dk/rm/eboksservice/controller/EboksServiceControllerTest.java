package dk.rm.eboksservice.controller;

import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.EboksService;
import dk.rm.eboksservice.service.EboksServiceException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.service.model.EboksServiceOutput;
import dk.rm.eboksservice.templates.Template;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.openapitools.model.SendRequest;
import org.openapitools.model.TemplateResponse;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

public class EboksServiceControllerTest {
    private EboksServiceController eboksServiceController;
    private EboksService eboksService;

    @Before
    public void setup() {
        eboksService = Mockito.mock(EboksService.class);

        eboksServiceController = new EboksServiceController(eboksService, new SimpleMeterRegistry());
    }

    @Test
    public void testEboksServiceSendPost() throws DiasMailException, EboksServiceException {
        // GIVEN
        Mockito.when(eboksService.sendToEboks(Mockito.any())).thenReturn(new EboksServiceOutput("success"));
        var request = new SendRequest();
        request.setCpr("1111111111");
        request.setTemplate("TestTemplate");

        // WHEN
        var result = eboksServiceController.eboksServiceSendPost(request);

        // THEN
        assertNotNull(result);
        assertEquals("success", result.getBody().getMessage());

        var inputArgumentCaptor = ArgumentCaptor.forClass(EboksServiceInput.class);
        Mockito.verify(eboksService, times(1)).sendToEboks(inputArgumentCaptor.capture());

        assertNotNull(inputArgumentCaptor.getValue());
        assertEquals(request.getCpr(), inputArgumentCaptor.getValue().getCpr());
        assertEquals(request.getTemplate(), inputArgumentCaptor.getValue().getTemplate());
    }

    @Test
    public void testEboksServiceTemplatesGet() throws Exception {
        // GIVEN
        List<Template> templates = Arrays.asList(
                new Template("test1", "description1", "body1"),
                new Template("test2", "description2", "body2"));
        Mockito.when(eboksService.getTemplates()).thenReturn(templates);

        // WHEN
        ResponseEntity<List<TemplateResponse>> response = eboksServiceController.eboksServiceTemplatesGet();

        List<TemplateResponse> templateResponses = response.getBody();
        assertNotNull(templateResponses);
        assertEquals(2, templateResponses.size());
        assertTrue(templateResponses.stream()
                .anyMatch(template -> "test1".equals(template.getName()) && "description1".equals(template.getDescription())));
        assertTrue(templateResponses.stream()
                .anyMatch(template -> "test2".equals(template.getName()) && "description2".equals(template.getDescription())));
    }
}
