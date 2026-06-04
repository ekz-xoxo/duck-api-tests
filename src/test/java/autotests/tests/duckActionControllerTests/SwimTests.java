package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.SwimClient;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class SwimTests extends SwimClient {
    @Test(description = "уточка с активными крыльями")
    @CitrusTest
    public  void swim1(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner,"1");
        validateBadResponse( runner, jsonPath()
                .expression("$.message", "Paws are not found (((("));
    }
    @Test(description = "уточка с неcуществующим id")
    @CitrusTest
    public  void swim2(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner,"5");
        validateBadResponse( runner, jsonPath()
                .expression("$.message", "@notEmpty()@"));
    }

}
