package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.SwimClient;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class SwimTest extends SwimClient {
    @Test(description = "уточка с существующим id")
    @CitrusTest
    public  void swimTest1(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        validateCreateResponse(runner, jsonPath()
                        .expression("$.id", "@isNumber()@"));
        duckSwim(runner,"${duckId}");
        validateBadResponse( runner, jsonPath()
                .expression("$.message", "Paws are not found (((("));
    }

    @Test(description = "уточка с неcуществующим id")
    @CitrusTest
    public  void swimTest2(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner,"999");
        validateBadResponse( runner, jsonPath()
                .expression("$.message", "Paws are not found (((("));
    }

}
