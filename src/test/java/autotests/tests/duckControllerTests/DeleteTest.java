package autotests.tests.duckControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckControllerClients.DeleteClient;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class DeleteTest extends DeleteClient {
    @Test(description = "удаление утки")
    @CitrusTest
    public void deleteDuckTest(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        deleteDuck(runner, "${duckId}");

        validateResponse(runner, jsonPath()
                .expression("$.message", "Duck is deleted")
        );
    }

}
