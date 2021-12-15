package dk.rm.eboksservice.service;

import dk.rm.eboksservice.dias.DiasMailClient;
import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.templates.Template;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EboksServiceImplTest {
    private EboksService eboksService;
    private DiasMailClient diasMailClient;
    private Template testTemplate;

    @Before
    public void setup() {
        diasMailClient = Mockito.mock(DiasMailClient.class);
        HashMap<String, Template> templates = new HashMap<>();
        testTemplate = new Template("test", "description", "this is a template");
        templates.put("test", testTemplate);
        eboksService = new EboksServiceImpl(diasMailClient, templates);
    }

    @Test
    public void testSendToEboksWithValidInput() throws DiasMailException, EboksServiceException {
        var input = new EboksServiceInput();
        input.setCpr("1234567890");
        input.setTemplate("test");

        var result = eboksService.sendToEboks(input);
        assertNotNull(result);
        Mockito.verify(diasMailClient).sendMail("1234567890", "this is a template");
    }

    @Test
    public void testSendToEboksWithInvalidTemplate() throws DiasMailException, EboksServiceException {
        var input = new EboksServiceInput();
        input.setCpr("1234567890");
        input.setTemplate("hest");

        try {
            eboksService.sendToEboks(input);
        } catch (EboksServiceException e) {
            assertEquals("Invalid template specified: hest", e.getMessage());
        }
    }

    @Test
    public void testGetTemplates() throws Exception {
        Collection<Template> templates = eboksService.getTemplates();

        assertEquals(1, templates.size());
        assertEquals(testTemplate, templates.iterator().next());
    }
}
