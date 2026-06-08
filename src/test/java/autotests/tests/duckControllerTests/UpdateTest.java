package autotests.tests.duckControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckControllerClients.UpdateClient;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class UpdateTest extends UpdateClient {
    @Test(description = "обновление цвета и высоты утки")
    @CitrusTest
    public void updateTest1(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        validateCreateResponse(runner, jsonPath()
                .expression("$.id", "@isNumber()@"));
        updateDuck(runner, "${duckId}","pink", 0.05, "rubber", "quack", "ACTIVE");
        validateResponse(runner, jsonPath()
                .expression("$.message", "Duck with id = ${duckId} is updated"));

    }
    @Test(description = "обновление звука и цвета утки")
    @CitrusTest
    public void updateTest2(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        validateCreateResponse(runner, jsonPath()
                .expression("$.id", "@isNumber()@"));
        updateDuck(runner, "${duckId}","red", 0.01, "rubber", "quack-quack", "ACTIVE");

        validateResponse(runner, jsonPath()
                .expression("$.message", "Duck with id = ${duckId} is updated"));

    }

}
