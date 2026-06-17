package autotests.clients.duckActionControllerClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class QuackClient extends DuckClient {
    public void duckQuack(TestCaseRunner runner, String duckId, String repetitionCount, String soundCount ){
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", duckId)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }
}


