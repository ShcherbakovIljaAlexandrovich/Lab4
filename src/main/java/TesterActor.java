import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class TesterActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestBundle.class, req -> {
                    ScriptEngine engine = new
                            ScriptEngineManager().getEngineByName("nashorn");
                    engine.eval(req.getCode());
                    Invocable invocable = (Invocable) engine;
                    String result = invocable.invokeFunction(req.getFunctionName(), req.getParams()).toString();
                    sender().tell(new StoreMessage(req.getPackageID(), req.getTestName(), result), self());
                        }
                ).build();
    }
}
