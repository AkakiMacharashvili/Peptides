package Peptides;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static Peptides.Benchmark.generateLibrary;
import static Peptides.Benchmark.generateProtein;
import static org.junit.Assert.assertEquals;

public class ResultComparison {
    private static final int PROTEIN_SIZE = 10_000;
    private static final int LIBRARY_SIZE = 100_000;
    private static final int PEPTIDE_SIZE = 8;
    static final byte[] ALPHABET = new byte[26];

    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c-'A'] = c;
        }
    }

    @Test
    public void resultsTest(){

        String protein = generateProtein(PROTEIN_SIZE);
        List<String> library = generateLibrary(LIBRARY_SIZE);
        PeptidesV1 peptidesV1 = new PeptidesV1(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        Map<String, List<Integer>> V1 = peptidesV1.searchLibrary();

        PeptidesV2 peptidesV2 = new PeptidesV2(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        Map<String, List<Integer>> V2 = peptidesV2.searchProtein();

        PeptidesV3 peptidesV3 = new PeptidesV3(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        peptidesV3.createKMersDictionaryV3();

        PeptidesV4 peptidesV4 = new PeptidesV4(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        peptidesV4.createKMersDictionaryV4();

        assertEquals(V1, V2);
        assertEquals(V1, peptidesV3.kmers);
        assertEquals(V1, peptidesV4.kmers);
    }

}
