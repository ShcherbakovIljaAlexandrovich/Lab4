import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;

public class StoreActor extends AbstractActor {
    private Map<String, Map<String, String>> store = new HashMap<>();
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, m -> {
                    if (!store.containsKey(m.getpackageID())) {
                        store.put(m.getpackageID(), new HashMap<>());
                    }
                    store.get(m.getpackageID()).put(m.getTestName(), m.getresult());
                    System.out.println("receive message! "+m.toString());
                })
                .match(GetMessage.class, req -> sender().tell(
                        new ResponseMessage(req.getPackageID(), store.get(req.getPackageID())), self())
                ).build();
    }
}