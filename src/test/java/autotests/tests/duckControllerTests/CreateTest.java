package autotests.tests.duckControllerTests;

import autotests.clients.DuckClient;
import autotests.clients.duckControllerClients.CreateClient;
import autotests.payloads.request.DuckPropertiesRequest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-controller")
@Feature("Создание уточки")
@Story("Эндпоинт /api/duck/create")
public class CreateTest extends CreateClient {
    @Test(description = "cоздание утки из rubber")
    @CitrusTest
    public void createTest1(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequest duck = new DuckPropertiesRequest()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner,duck);
        validateDuckInDb(runner,"@IsNumber","yellow","0.01","rubber", "quack", "ACTIVE");
        updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}");
        getDuckId(runner);
        validateDuckNotInDb(runner, "${duckId}");

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
        createDuck(runner,duck);
        validateDuckInDb(runner,"@IsNumber","yellow","0.01","rubber", "quack", "ACTIVE");
        updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}");
        getDuckId(runner);
        validateDuckNotInDb(runner, "${duckId}");
    }

}
