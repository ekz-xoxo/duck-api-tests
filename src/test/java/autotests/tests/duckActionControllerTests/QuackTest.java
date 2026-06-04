package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.QuackClient;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class QuackTest extends QuackClient {
    @Test(description = "уточка с нечетным id")
    @CitrusTest
    public void quackTest1(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner,"1");
        validateBadResponse(runner, jsonPath()
                .expression("$.message", "@notEmpty()@"));
    }
    @Test(description = "уточка с четным id")
    @CitrusTest
    public void quackTest2(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner,"2");
        validateBadResponse(runner, jsonPath()
                .expression("$.message", "@notEmpty()@"));
    }


}
