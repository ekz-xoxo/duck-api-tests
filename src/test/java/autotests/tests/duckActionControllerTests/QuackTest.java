package autotests.tests.duckActionControllerTests;

import autotests.clients.DuckClient;
import autotests.payloads.response.SoundResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты duck-action-controller")
@Feature("Звуки уточки")
@Story("Эндпоинт /api/duck/action/quack")
public class QuackTest extends DuckClient {
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
