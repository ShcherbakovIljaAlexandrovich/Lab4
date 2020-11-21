import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UnitTest {
    public final String packageID;
    public final String jsScript;
    public final String functionName;
    public final String testName;
    public final String expectedResult;
    public final ArrayList<Integer> params;
    
    public UnitTest(String packageID,
                    String jsScript,
                    String functionName,
                    String testName,
                    String expectedResult,
                    ArrayList<Integer> params) {
        this.packageID = packageID;
        this.jsScript= jsScript;
        this.functionName= functionName;
        this.testName= testName;
        this.expectedResult = expectedResult;
        this.params = params;
    }

    public static ArrayList<UnitTest> formFromBundle(TestBundle bundle) {
        ArrayList<UnitTest> tests = new ArrayList<>();
        for (TestInfo t: bundle.tests) {
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
