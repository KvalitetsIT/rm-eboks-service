package dk.rm.eboksservice.integrationtest;

import org.junit.Ignore;
import org.junit.Test;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.SendRequest;

import static org.junit.Assert.assertNotNull;

public class EboksServiceIT extends AbstractIntegrationTest {

    private final DefaultApi serviceApi;

    public EboksServiceIT() {
        var apiClient = new ApiClient();
        apiClient.setBasePath(getApiBasePath());

        serviceApi = new DefaultApi(apiClient);
    }

    @Test
    @Ignore ("We need a test dias mail server url")
    public void testCallService() throws ApiException {
        var input = new SendRequest();
        input.setCpr("1111111111");
        input.setTemplate("TestTemplate");

        var result = serviceApi.eboksServiceSendPost(input);

        assertNotNull(result);
    }
}
