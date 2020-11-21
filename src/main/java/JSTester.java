import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.routing.BalancingPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletionStage;
import scala.concurrent.Future;

import static akka.http.javadsl.server.Directives.*;

public class JSTester {

    private final static Timeout timeout = Timeout.create(Duration.ofSeconds(5));

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class), "store");
        ActorRef testAggregatorActor = system.actorOf(new BalancingPool(5).props(
                Props.create(TesterActor.class)), "testAggregator");
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                createRoute(system, testAggregatorActor, storeActor).flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    public static Route createRoute(ActorSystem system, ActorRef testAggregatorActor, ActorRef storeActor) {
        return route(
                get(() -> parameter("packageID", (id) -> {
                    Future<Object> f = Patterns.ask(storeActor, new GetMessage(id), timeout);
                    return completeOKWithFuture(f, Jackson.marshaller());
                })),
                post(() -> entity(
                        Jackson.unmarshaller(TestBundle.class), testBundle -> {
                            ArrayList<UnitTest> tests = UnitTest.formFromBundle(testBundle);
                            for(UnitTest t: tests) {
                                testAggregatorActor.tell(t, storeActor);
                            }
                            return complete("Tests acknowledged.");
                        }
                ))
        );
    }
}
