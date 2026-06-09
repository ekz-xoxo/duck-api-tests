package autotests.tests.duckActionControllerTests;

import autotests.payloads.request.DuckPropertiesRequest;
import autotests.payloads.response.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.FlyClient;


public class FlyTest extends FlyClient {

    @Test(description = "уточка с активными крыльями")
    @CitrusTest
    public  void FlyTest1(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequest duck = new DuckPropertiesRequest()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        validateResponse(runner, "createTest/DackPropertiesResponse_rubber.json");
        duckFly(runner,"${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("I am flying :)");
        validateResponse(runner, expectedResponse);
    }
    @Test(description = "уточка с неактивными крыльями")
    @CitrusTest
    public  void FlyTest2(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequest duck = new DuckPropertiesRequest()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");
        createDuck(runner, duck);
        getDuckId(runner);
        duckFly(runner,"${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("I can not fly :C");
        validateResponse(runner, expectedResponse);
    }

    @Test(description = "уточка с неопределенными крыльями")
    @CitrusTest
    public  void FlyTest3(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequest duck = new DuckPropertiesRequest()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState("UNDEFINED");
        createDuck(runner, duck);
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Wings are not detected :(");
        validateResponse(runner, expectedResponse);
    }

}
