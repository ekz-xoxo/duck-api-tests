package autotests.tests.duckActionControllerTests;

import autotests.payloads.request.DuckPropertiesRequest;
import autotests.payloads.response.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.SwimClient;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class SwimTest extends SwimClient {
    @Test(description = "уточка с существующим id")
    @CitrusTest
    public  void swimTest1(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequest duck = new DuckPropertiesRequest()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        validateResponse(runner, "createTest/DackPropertiesResponse_rubber.json");
        duckSwim(runner,"${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Paws are not found ((((");
        validateResponse(runner, HttpStatus.NOT_FOUND, expectedResponse);
    }

    @Test(description = "уточка с неcуществующим id")
    @CitrusTest
    public  void swimTest2(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner,"999");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Paws are not found ((((");
        validateResponse(runner, HttpStatus.NOT_FOUND, expectedResponse);
    }

}
