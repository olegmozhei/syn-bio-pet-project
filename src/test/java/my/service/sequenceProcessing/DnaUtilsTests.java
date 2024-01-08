package my.service.sequenceProcessing;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class DnaUtilsTests {
    DnaUtils sut = new DnaUtils();

    @Test
    public void checkThatTwoStringsMatches(){
        Assert.isTrue(sut.compareLengthEqualStrings("ACGTACGT", "ACGTACGT"), "Strings are not matched");
    }

    @Test
    public void checkThatTwoStringsMatchesSecondTest(){
        Assert.isTrue(sut.compareLengthEqualStrings("GTGRGWC", "GTGAGTC"), "Strings are not matched");
    }

    @Test
    public void checkMotifInString(){
        JSONArray actualResult = sut.findMotifInString("AAAAA", "TTTTTAAAAA");
        Assert.isTrue(actualResult.length() == 1, "Can't find motif");
    }
}
