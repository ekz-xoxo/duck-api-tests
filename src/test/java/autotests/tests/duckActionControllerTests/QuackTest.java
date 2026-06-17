package autotests.tests.duckActionControllerTests;

import autotests.payloads.response.SoundResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.QuackClient;

public class QuackTest extends QuackClient {
    @Test(description = "уточка с нечетным id")
    @CitrusTest
    public void quackTest1(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner,"1", "1","1");
        SoundResponse expectedResponse = new SoundResponse();
        expectedResponse.setSound("quack");
        validateResponse(runner, expectedResponse);
    }
    @Test(description = "уточка с четным id")
    @CitrusTest
    public void quackTest2(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner,"2","1","1");
        SoundResponse expectedResponse = new SoundResponse();
        expectedResponse.setSound("moo");
        validateResponse(runner, expectedResponse);
    }


}
