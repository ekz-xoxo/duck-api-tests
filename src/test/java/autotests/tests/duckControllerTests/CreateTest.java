package autotests.tests.duckControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckControllerClients.CreateClient;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class CreateTest extends CreateClient {
    @Test(description = "cоздание утки из rubber")
    @CitrusTest
    public void createTest1(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");

        validateResponse(runner, jsonPath()
                .expression("$.color", "yellow")
                .expression("$.height", "0.01")
                .expression("$.material", "rubber")
                .expression("$.sound", "quack")
                .expression("$.wingsState", "ACTIVE")
        );
    }
    @Test(description = "создание утки из wood")
    @CitrusTest
    public void createTest2(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "wood", "quack", "ACTIVE");

        validateResponse(runner, jsonPath()
                .expression("$.color", "yellow")
                .expression("$.height", "0.01")
                .expression("$.material", "wood")
                .expression("$.sound", "quack")
                .expression("$.wingsState", "ACTIVE")
        );
    }

}
