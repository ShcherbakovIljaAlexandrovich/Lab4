import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TestInfo {
    @JsonProperty("testName")
    public final String testName;

    @JsonProperty("expectedResult")
    public final String expectedResult;

    @JsonProperty("params")
    public final ArrayList<Integer> params;

    public TestInfo(@JsonProperty("testName") String testName,
                    @JsonProperty("expectedResult") String expectedResult,
                    @JsonProperty("params") ArrayList<Integer> params) {
        this.testName= testName;
        this.expectedResult = expectedResult;
        this.params = params;
    }
}
