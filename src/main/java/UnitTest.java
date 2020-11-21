import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnitTest {
    public final String packageID;
    public final String jsScript;
    public final String functionName;

    @JsonProperty("testName")
    public final String testName;

    @JsonProperty("expectedResult")
    public final String expectedResult;

    @JsonProperty("params")
    public final ArrayList<Integer> params;
    
    public UnitTest(String packageID,
                    String jsScript,
                    String functionName,
                    @JsonProperty("testName") String testName,
                    @JsonProperty("expectedResult") String expectedResult,
                    @JsonProperty("params") ArrayList<Integer> params) {
        this.packageID = packageID;
        this.jsScript= jsScript;
        this.functionName= functionName;
        this.testName= testName;
        this.expectedResult = expectedResult;
        this.params = params;
    }

    public static ArrayList<UnitTest> formFromBundle(TestBundle bundle) {
        ArrayList<UnitTest> tests = new ArrayList<>();
        for (UnitTest t: bundle.tests) {
            tests.add(new UnitTest(
                    bundle.packageID,
                    bundle.jsScript,
                    bundle.functionName,
                    t.testName,
                    t.expectedResult,
                    t.params));
        }
        return tests;
    }
}
