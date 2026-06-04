package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.FlyClient;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class FlyTests extends FlyClient {

    @Test(description = "уточка с активными крыльями")
    @CitrusTest
    public  void FlyTest1(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner,"1");
        validateResponse( runner, jsonPath()
                .expression("$.message", "I am flying :)"));
    }
    @Test(description = "уточка с неактивными крыльями")
    @CitrusTest
    public  void FlyTest2(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner,"2");
        validateResponse( runner, jsonPath()
                .expression("$.message", "I can not fly :C"));
    }
    @Test(description = "уточка с неопределенными крыльями")
    @CitrusTest
    public  void FlyTest3(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner, "3");
        validateBadResponse(runner, jsonPath()
                .expression("$.message", "@notEmpty()@"));
    }

}
