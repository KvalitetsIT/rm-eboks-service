package dk.rm.eboksservice.service;

import dk.rm.eboksservice.dias.DiasMailClient;
import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EboksServiceImplTest {
    private EboksService eboksService;

    @Before
    public void setup() {
        DiasMailClient diasMailClient = Mockito.mock(DiasMailClient.class);
        eboksService = new EboksServiceImpl(diasMailClient);
    }

    @Test
    public void testValidInput() throws DiasMailException {
        var input = new EboksServiceInput();
        input.setCpr(UUID.randomUUID().toString());

        var result = eboksService.eboksServiceBusinessLogic(input);
        assertNotNull(result);
    }
}
