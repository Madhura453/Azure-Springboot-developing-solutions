package orgazure.durable.orchestration.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.durabletask.DurableTaskClient;
import com.microsoft.durabletask.OrchestrationRunner;
import com.microsoft.durabletask.azurefunctions.DurableActivityTrigger;
import com.microsoft.durabletask.azurefunctions.DurableClientContext;
import com.microsoft.durabletask.azurefunctions.DurableClientInput;
import com.microsoft.durabletask.azurefunctions.DurableOrchestrationTrigger;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerFunction {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("StartHelloCities")
    public HttpResponseMessage startHelloCities(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            @DurableClientInput(name = "durableContext") DurableClientContext durableClientContext,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");
        DurableTaskClient durableTaskClient=durableClientContext.getClient();
        String instanceId=durableTaskClient.scheduleNewOrchestrationInstance("HelloCities");
        return durableClientContext.createCheckStatusResponse(request,instanceId);

    }


    @FunctionName("HelloCities")
    public String helloCitiesOrchestrator(@DurableOrchestrationTrigger(name="runtimeState") String runtimeState)
    {
        return OrchestrationRunner.loadAndRun(runtimeState,ctx->{
            String result="";
            result+=ctx.callActivity("SayHello","Hydrabad",String.class).await()+",";
            result+=ctx.callActivity("SayHello","tirupati",String.class).await()+",";
            result+=ctx.callActivity("SayHello","tirumala",String.class).await();
            return result;
        });
    }

    /**
     * This is the activity function that gets invoked by the orchestrator function.
     */
    @FunctionName("SayHello")
    public String sayHello(@DurableActivityTrigger(name="name") String name)
    {
    return String.format("Hello %s",name);
    }
}
