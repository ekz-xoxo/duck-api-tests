package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.FlyClient;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class FlyTest extends FlyClient {

    @Test(description = "уточка с активными крыльями")
    @CitrusTest
    public  void FlyTest1(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        validateCreateResponse(runner, jsonPath()
                .expression("$.id", "@isNumber()@")
                .expression("$.wingsState", "ACTIVE"));
        duckFly(runner,"${duckId}");
        validateResponse( runner, jsonPath()
                .expression("$.message", "I am flying :)"));
    }
    @Test(description = "уточка с неактивными крыльями")
    @CitrusTest
    public  void FlyTest2(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
        validateCreateResponse(runner, jsonPath()
                .expression("$.id", "@isNumber()@")
                .expression("$.wingsState", "FIXED"));
        duckFly(runner,"${duckId}");
        validateResponse( runner, jsonPath()
                .expression("$.message", "I can not fly :C"));
    }
    @Test(description = "уточка с неопределенными крыльями")
    @CitrusTest
    public  void FlyTest3(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "UNDEFINED");
        validateCreateResponse(runner, jsonPath()
                .expression("$.id", "@isNumber()@")
                .expression("$.wingsState", "UNDEFINED"));
        duckFly(runner, "${duckId}");
        validateResponse(runner, jsonPath()
                .expression("$.message", "Wings are not detected :("));
    }

}
