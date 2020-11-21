import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class TesterActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(UnitTest.class, req -> {
                    ScriptEngine engine = new
                            ScriptEngineManager().getEngineByName("nashorn");
                    engine.eval(req.jsScript);
                    Invocable invocable = (Invocable) engine;
                    String result = invocable.invokeFunction(req.functionName, req.params.toArray()).toString();
                    String verdict;
                    if (result.equals(req.expectedResult)) { verdict = "Pass"; }
                    else { verdict = "Fail"; }
                    System.out.println("Expected result "+req.expectedResult+", actual result "+result);
                    sender().tell(new StoreMessage(req.packageID, req.testName, verdict), self());
                        }
                ).build();
    }
}
