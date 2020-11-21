import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestBundle {
    @JsonProperty("packageID")
    public final String packageID;
    
    @JsonProperty("jsScript")
    public final String jsScript;

    @JsonProperty("functionName")
    public final String functionName;

    @JsonProperty("tests")
    public final ArrayList<UnitTest> tests;

    public TestBundle(
            @JsonProperty("packageID") String packageID,
            @JsonProperty("jsScript") String jsScript,
            @JsonProperty("functionName") String functionName,
            @JsonProperty("tests") ArrayList<UnitTest> tests) {
        this.packageID = packageID;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.tests = tests;
    }
}