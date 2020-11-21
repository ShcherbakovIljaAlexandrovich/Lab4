import java.util.List;

public class TestMessage {
    private String packageID;
    private String testName;
    private String code;
    private String functionName;
    private List params;
    private String expectedResult;

    public String getPackageID() {
        return packageID;
    }

    public String getTestName() {
        return testName;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public String getCode() {
        return code;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List getParams() { return params; }

    public TestMessage(String packageID, String testName, String code, String functionName, List params, String expectedResult) {
        this.packageID = packageID;
        this.testName = testName;
        this.code = code;
        this.functionName = functionName;
        this.params = params;
        this.expectedResult = expectedResult;
    }
}