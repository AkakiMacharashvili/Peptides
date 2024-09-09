package Peptides;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PeptidesV4Test {

    private PeptidesV4 peptidesV4;
    private String peptide;
    private String protein;

    @Before
    public void setup() {
        peptide = "RNLKDGHI";
        protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
        List<String> library = List.of(peptide, "ORNLKDGH", "ABERNLKD");
        peptidesV4 = new PeptidesV4(PeptidesV4.DEFAULT_PEPTIDE_SIZE, protein, library);

    }

    @Test
    public void testGenerateLibrary() {

        System.out.println(peptidesV4.library.size());

        assertEquals(3, peptidesV4.library.size());
        assertTrue(peptidesV4.library.contains(peptidesV4.StringToLong(peptide)));
        assertTrue(peptidesV4.library.contains(peptidesV4.StringToLong("ORNLKDGH")));
        assertTrue(peptidesV4.library.contains(peptidesV4.StringToLong("ABERNLKD")));
    }

    @Test
    public void testCreateKMersDictionaryV4() {
        peptidesV4.createKMersDictionaryV4();
        assertNotNull(peptidesV4.kmers);
        assertTrue(peptidesV4.kmers.containsKey(peptidesV4.StringToLong(peptide)));
        assertTrue(peptidesV4.kmers.containsKey(peptidesV4.StringToLong("ORNLKDGH")));
        assertTrue(peptidesV4.kmers.containsKey(peptidesV4.StringToLong("ABERNLKD")));
    }


    @Test
    public void testConvertNumberIntoBinary() {
        String binary = peptidesV4.convertNumberIntoBinary(10);
        assertEquals("01010", binary);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertNumberIntoBinaryOutOfRange() {
        peptidesV4.convertNumberIntoBinary(32);
    }

    @Test
    public void testConvertBinaryStringToNumber() {
        Long number = peptidesV4.convertBinaryStringToNumber("01010");
        assertEquals(Long.valueOf(10), number);
    }

    @Test
    public void testStringToLong() {
        Long number = peptidesV4.StringToLong(peptide);
        assertNotNull(number);
    }

    @Test
    public void testDisplayKeys() {
        peptidesV4.createKMersDictionaryV4();
        peptidesV4.kmers.forEach((k, v) -> System.out.println(k + ": " + v));
        // Check console output manually or use a logger with assertions if needed
    }
}
