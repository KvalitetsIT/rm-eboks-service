package dk.rm.eboksservice.integrationtest;

import org.junit.Test;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.HelloRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HelloIT extends AbstractIntegrationTest {

    private final DefaultApi helloApi;

    public HelloIT() {
        var apiClient = new ApiClient();
        apiClient.setBasePath(getApiBasePath());

        helloApi = new DefaultApi(apiClient);
    }

    @Test
    public void testCallService() throws ApiException {
        var input = new HelloRequest();
        input.setName("John Doe");

        var result = helloApi.eboksServiceHelloPost(input);

        assertNotNull(result);
        assertEquals(input.getName(), result.getName());
        assertNotNull(result.getNow());
    }
}
