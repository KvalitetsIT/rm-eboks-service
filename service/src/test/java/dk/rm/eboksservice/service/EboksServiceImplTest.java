package dk.rm.eboksservice.service;

import dk.rm.eboksservice.service.model.EboksServiceInput;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EboksServiceImplTest {
    private EboksService eboksService;

    @Before
    public void setup() {
        eboksService = new EboksServiceImpl();
    }

    @Test
    public void testValidInput() {
        var input = new EboksServiceInput();
        input.setCpr(UUID.randomUUID().toString());

        var result = eboksService.eboksServiceBusinessLogic(input);
        assertNotNull(result);
    }
}
