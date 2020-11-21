import java.util.Map;

public class StoreMessage {
    private String packageID;
    private String testName;
    private String result;

    public String getpackageID() {
        return packageID;
    }

    public String getTestName() {
        return testName;
    }

    public String getresult() {
        return result;
    }

    public StoreMessage(String packageID, String testName, String result) {
        this.packageID = packageID;
        this.testName = testName;
        this.result = result;
    }
}
