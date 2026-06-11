package autotests.tests.duckActionControllerTests;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;
@Epic("Тесты duck-action-controller")
@Feature("Параметры уточки")
@Story("Эндпоинт /api/duck/action/properties")
public class PropertiesTest extends DuckClient {
    @Test(description = "Проверка Properties с нечетным id", dataProvider = "oddDucks")
    @CitrusTest
    @CitrusParameters({"id", "color", "height", "material", "sound", "wingsState", "runner"})
    public void propertiesOddIdTest(String id,
                                    String color,
                                    String height,
                                    String material,
                                    String sound,
                                    String wingsState,
                                    @Optional @CitrusResource TestCaseRunner runner) {

        duckProperties(runner, id);

        validateResponse(runner, jsonPath()
                .expression("$.color", color)
                .expression("$.material", material)
                .expression("$.sound", sound)
                .expression("$.wingsState", wingsState));
    }

    @Test(description = "Проверка Properties с четным id", dataProvider = "evenDucks")
    @CitrusTest
    @CitrusParameters({"id", "color", "height", "material", "sound", "wingsState", "runner"})

    public void propertiesEvenIdTest(String id,
                                     String color,
                                     String height,
                                     String material,
                                     String sound,
                                     String wingsState,
                                     @Optional @CitrusResource TestCaseRunner runner) {

        duckProperties(runner, id);

        validateResponse(runner, jsonPath()
                .expression("$.color", color)
                .expression("$.material", material)
                .expression("$.sound", sound)
                .expression("$.wingsState", wingsState));
    }

    @DataProvider(name = "oddDucks")
    public Object[][] oddDucks() {
        return new Object[][]{
                {"1", "pink", "0.01", "rubber", "quack", "ACTIVE", null},
                {"3", "yellow", "0.02", "rubber", "quack", "ACTIVE", null},
                {"5", "yellow", "0.01", "wood", "quack", "ACTIVE", null},
                {"7", "yellow", "0.01", "rubber", "quack-quack", "ACTIVE", null},
                {"9", "yellow", "0.01", "rubber", "quack", "FIXED", null}
        };
    }

    @DataProvider(name = "evenDucks")
    public Object[][] evenDucks() {
        return new Object[][]{
                {"2", "pink", "0.01", "rubber", "quack", "ACTIVE", null},
                {"4", "yellow", "0.02", "rubber", "quack", "ACTIVE", null},
                {"6", "yellow", "0.01", "wood", "quack", "ACTIVE", null},
                {"8", "yellow", "0.01", "rubber", "quack-quack", "ACTIVE", null},
                {"10", "yellow", "0.01", "rubber", "quack", "FIXED", null}
        };
    }


}

