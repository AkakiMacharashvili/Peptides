package Peptides;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PeptidesV3Test {

    private PeptidesV3 peptidesV3;
    private String peptide;
    private String protein;

    @Before
    public void setup() {
        peptide = "RNLKDGHI";
        protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
        List<String> library = List.of(peptide, "ORNLKDGH", "ABERNLKD");
        peptidesV3 = new PeptidesV3(PeptidesV3.DEFAULT_PEPTIDE_SIZE, protein, library);
    }

    @Test
    public void TestBinaryConvert(){
        int curr = 10;
        String result = peptidesV3.convertNumberIntoBinary(10);
        assertEquals(result, "01010");
    }

    @Test
    public void TestStringToBinary(){
        String pep = "ABC";
        String result = "000000000100010";
        assertEquals(result, peptidesV3.convertStringToBinary(pep));
    }

    @Test
    public void TestBinaryToLong(){
        String pep = "000111";
        long num = peptidesV3.convertBinaryStringToNumber(pep);
        long answer = 7;
        assertEquals(num, answer);
    }

    @Test
    public void TestStringToLong(){
        String pep = "BA";
        long answer = 32;
        long result = peptidesV3.StringToLong(pep);
        assertEquals(result, answer);
    }

    @Test
    public void PeptideV2Test(){
        peptidesV3.createKMersDictionaryV3();
        Map<Long, List<Integer>> existingPeptides = peptidesV3.kmers;
        assertNotNull(existingPeptides);
        assertEquals(3, existingPeptides.size());
    }

}
