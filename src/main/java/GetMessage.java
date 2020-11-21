import akka.actor.AbstractActor;

public class GetMessage {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public GetMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
