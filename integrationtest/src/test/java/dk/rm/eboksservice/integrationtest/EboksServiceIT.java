package dk.rm.eboksservice.integrationtest;

import org.junit.Test;
import org.mockserver.verify.VerificationTimes;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.SendRequest;
import org.openapitools.client.model.SendResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockserver.model.HttpRequest.request;

public class EboksServiceIT extends AbstractIntegrationTest {

    private final DefaultApi serviceApi;

    public EboksServiceIT() {
        var apiClient = new ApiClient();
        apiClient.setBasePath(getApiBasePath());

        serviceApi = new DefaultApi(apiClient);
    }

    @Test
    public void testCallService() throws ApiException {
        var input = new SendRequest();
        input.setCpr("1111111111");
        input.setTemplate("test"); // valid template from templates.json

        SendResponse response = serviceApi.eboksServiceSendPost(input);
        assertNotNull(response);
        assertEquals("success", response.getMessage());
        serviceStarter.getDiasServerMock().verify(request(), VerificationTimes.once());
    }

    @Test
    public void testCallServiceWithInvalidTemplate() throws ApiException {
        var input = new SendRequest();
        input.setCpr("1111111111");
        input.setTemplate("invalid template");

        try {
            serviceApi.eboksServiceSendPost(input);
            fail("Should fail");
        } catch (ApiException e) {
            assertTrue(e.getResponseBody().contains("Invalid template specified: invalid template"));
        }
        serviceStarter.getDiasServerMock().verify(request(), VerificationTimes.exactly(0));
    }
}
