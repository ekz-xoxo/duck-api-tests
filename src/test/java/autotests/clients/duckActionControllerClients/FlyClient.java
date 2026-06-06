package autotests.clients.duckActionControllerClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FlyClient extends DuckClient {
    public void duckFly(TestCaseRunner runner, String duckId){
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", duckId)
        );
    }
}


