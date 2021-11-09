package dk.rm.eboksservice.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    private static GenericContainer eboksService;
    private static String apiBasePath;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                if(eboksService != null) {
                    logger.info("Stopping eboks service container: " + eboksService.getContainerId());
                    eboksService.getDockerClient().stopContainerCmd(eboksService.getContainerId()).exec();
                }
            }
        });

        try {
            setup();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setup() throws IOException, URISyntaxException {
        var runInDocker = Boolean.getBoolean("runInDocker");
        logger.info("Running integration test in docker container: " + runInDocker);

        ServiceStarter serviceStarter;
        serviceStarter = new ServiceStarter();
        if(runInDocker) {
            eboksService = serviceStarter.startServicesInDocker();
            apiBasePath = "http://" + eboksService.getContainerIpAddress() + ":" + eboksService.getMappedPort(8080);
        }
        else {
            serviceStarter.startServices();
            apiBasePath = "http://localhost:8080";
        }
    }

    String getApiBasePath() {
        return apiBasePath;
    }
}
