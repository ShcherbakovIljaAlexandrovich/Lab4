import java.util.Map;

public class ResponseMessage {
    private String key;
    private Map<String, String> value;

    public String getKey() {
        return key;
    }

    public Map<String, String> getValue() {
        return value;
    }

    public ResponseMessage(String key, Map<String, String> value) {
        this.key = key;
        this.value = value;
    }
}
