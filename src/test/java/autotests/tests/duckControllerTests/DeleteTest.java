package autotests.tests.duckControllerTests;

import autotests.payloads.request.DuckPropertiesRequest;
import autotests.payloads.response.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckControllerClients.DeleteClient;

public class DeleteTest extends DeleteClient {
    @Test(description = "удаление утки")
    @CitrusTest
    public void deleteDuckTest(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequest duck = new DuckPropertiesRequest()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Duck is deleted");
        validateResponse(runner,expectedResponse);
    }

}
