package dk.rm.eboksservice.service;

import dk.rm.eboksservice.dias.DiasMailClient;
import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.templates.Template;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EboksServiceImplTest {
    private EboksService eboksService;
    private DiasMailClient diasMailClient;

    @Before
    public void setup() {
        diasMailClient = Mockito.mock(DiasMailClient.class);
        HashMap<String, Template> templates = new HashMap<>();
        templates.put("test", new Template("test", "description", "this is a template"));
        eboksService = new EboksServiceImpl(diasMailClient, templates);
    }

    @Test
    public void testValidInput() throws DiasMailException, EboksServiceException {
        var input = new EboksServiceInput();
        input.setCpr("1234567890");
        input.setTemplate("test");

        var result = eboksService.eboksServiceBusinessLogic(input);
        assertNotNull(result);
        Mockito.verify(diasMailClient).sendMail("1234567890", "this is a template");
    }

    @Test
    public void testInvalidTemplate() throws DiasMailException, EboksServiceException {
        var input = new EboksServiceInput();
        input.setCpr("1234567890");
        input.setTemplate("hest");

        try {
            eboksService.eboksServiceBusinessLogic(input);
        } catch (EboksServiceException e) {
            assertEquals("Invalid template specified: hest", e.getMessage());
        }
    }
}
