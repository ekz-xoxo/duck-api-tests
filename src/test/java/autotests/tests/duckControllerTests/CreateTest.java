package autotests.tests.duckControllerTests;

import autotests.clients.DuckClient;
import autotests.payloads.request.DuckPropertiesRequest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateTest extends DuckClient {
    @Test(description = "cоздание утки из rubber")
    @CitrusTest
    public void createTest1(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequest duck = new DuckPropertiesRequest()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        validateResponse(runner, "createTest/DackPropertiesResponse_rubber.json");

    }
    @Test(description = "создание утки из wood")
    @CitrusTest
    public void createTest2(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequest duck = new DuckPropertiesRequest()
                .color("yellow")
                .height(0.01)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        validateResponse(runner, "createTest/DackPropertiesResponse_wood.json");
    }

}
