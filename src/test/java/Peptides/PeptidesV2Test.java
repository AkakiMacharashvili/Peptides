package Peptides;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PeptidesV2Test {

    private PeptidesV2 peptidesV2;
    private String peptide;
    private String protein;

    @Before
    public void setup() {
        peptide = "RNLKDGHI";
        protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
        List<String> library = List.of(peptide, "ORNLKDGH", "ABCDEFGH");
        peptidesV2 = new PeptidesV2(PeptidesV2.DEFAULT_PEPTIDE_SIZE, protein, library);
    }

    @Test
    public void testSearchProtein() {
        Map<String, List<Integer>> peptideMap = peptidesV2.searchProtein();
        assertNotNull(peptideMap);
        assertTrue(peptideMap.containsKey(peptide));
        assertTrue(peptideMap.containsKey("ORNLKDGH"));
        assertFalse(peptideMap.containsKey("ABCDEFGH"));
    }

    @Test
    public void testPeptidePositions() {
        Map<String, List<Integer>> peptideMap = peptidesV2.searchProtein();
        assertEquals(List.of(3, 23), peptideMap.get(peptide));
    }

    @Test
    public void testEmptyPeptideMap() {
        peptidesV2 = new PeptidesV2(PeptidesV2.DEFAULT_PEPTIDE_SIZE, protein, List.of("HELLO", "BELLO"));
        Map<String, List<Integer>> peptideMap = peptidesV2.searchProtein();
        assertNotNull(peptideMap);
        assertTrue(peptideMap.isEmpty());
    }

    @Test
    public void testAlphabetInit() throws Exception {
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new String(Benchmark.ALPHABET));
    }

    @Test
    public void testDisplayKeys() {
        peptidesV2.displayKeys();
    }
}
