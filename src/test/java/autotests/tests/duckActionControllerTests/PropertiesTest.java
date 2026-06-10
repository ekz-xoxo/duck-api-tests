package autotests.tests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckActionControllerClients.PropertiesClient;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;
@Epic("Тесты duck-action-controller")
@Feature("Параметры уточки")
@Story("Эндпоинт /api/duck/action/properties")
public class PropertiesTest extends PropertiesClient {
    @Test(description = "Проверка Properties с четным id")
    @CitrusTest
    public void propertiesTest1(@Optional @CitrusResource TestCaseRunner runner){
      duckProperties(runner,"2");
        validateResponse( runner, jsonPath()
                .expression("$.color", "yellow")
                .expression("$.height", "@isNumber()@")
                .expression("$.material", "rubber")
                .expression("$.sound", "quack")
                .expression("$.wingsState", "ACTIVE")
        );
    }
    @Test(description = "Проверка Properties с нечетным id")
    @CitrusTest
    public void propertiesTest2(@Optional @CitrusResource TestCaseRunner runner){
        duckProperties(runner,"1");
        validateResponse( runner, jsonPath()
                .expression("$.color", "yellow")
                .expression("$.height", "@isNumber()@")
                .expression("$.material", "rubber")
                .expression("$.sound", "quack")
                .expression("$.wingsState", "ACTIVE")
        );
    }


}

