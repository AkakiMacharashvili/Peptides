package Peptides;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PeptidesV1Test {

    private PeptidesV1 peptidesV1;
    private PeptidesV2 peptidesV2;
    private String peptide;
    private String protein;

    @Before
    public void setup() {
        peptide = "RNLKDGHI";
        protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
        List<String> library = List.of(peptide, "ORNLKDGH", "ABCDEFGH");
        peptidesV1 = new PeptidesV1(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
    }

    @Test
    public void test() {
        List<Integer> positions = peptidesV1.search(peptide);
        assertTrue(positions.contains(3));
        assertTrue(positions.contains(23));
    }

    @Test
    public void testKMersExistAndFilledCorrectly() throws Exception {
        assertNotNull(peptidesV1.kmers);
        assertEquals(34, peptidesV1.kmers.size());
    }

    @Test
    public void testSpecificKMerExistsAtTwoPositions() throws Exception {
        assertEquals(List.of(3, 23), peptidesV1.kmers.get(peptide));
    }

    @Test
    public void testSearchLibraryPeptidesIsEmpty() throws Exception {
        peptidesV1 = new PeptidesV1(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, List.of("HELLO", "BELLO"));
        Map<String, List<Integer>> existingPeptides = peptidesV1.searchLibrary();
        assertNotNull(existingPeptides);
        assertTrue(existingPeptides.isEmpty());
    }

    @Test
    public void testSearchLibraryPeptides() throws Exception {
        Map<String, List<Integer>> existingPeptides = peptidesV1.searchLibrary();
        assertNotNull(existingPeptides);
        assertEquals(2, existingPeptides.size());
    }

    @Test
    public void testAlphabetInit() throws Exception {
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new String(Benchmark.ALPHABET));
    }

    @Test
    public void PeptideV2Test(){

    }


}