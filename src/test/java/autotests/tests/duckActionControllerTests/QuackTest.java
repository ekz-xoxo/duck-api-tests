package autotests.tests.duckActionControllerTests;

import autotests.payloads.response.SoundResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.QuackClient;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-action-controller")
@Feature("Звуки уточки")
@Story("Эндпоинт /api/duck/action/quack")
public class QuackTest extends QuackClient {
    @Test(description = "уточка с нечетным id")
    @CitrusTest
    public void quackTest1(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        duckQuack(runner,"123", "1","1");
        SoundResponse expectedResponse = new SoundResponse();
        expectedResponse.setSound("quack");
        validateResponse(runner, expectedResponse);
        updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}");
        validateDuckNotInDb(runner, id);
    }

    @Test(description = "уточка с четным id")
    @CitrusTest
    public void quackTest2(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","124");
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        duckQuack(runner,"124","1","1");
        SoundResponse expectedResponse = new SoundResponse();
        expectedResponse.setSound("moo");
        validateResponse(runner, expectedResponse);
        updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}");
        validateDuckNotInDb(runner, id);
    }


}
