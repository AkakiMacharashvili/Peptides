package Peptides;

import java.util.*;

public class Benchmark {

    private static final int PROTEIN_SIZE = 10_000;
    private static final int LIBRARY_SIZE = 100_000;
    private static final int PEPTIDE_SIZE = 8;
    static final byte[] ALPHABET = new byte[26];

    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c-'A'] = c;
        }
    }

    public static void main(String[] args) {

        System.out.println("generating data...");

        String protein = generateProtein(PROTEIN_SIZE);
        List<String> library = generateLibrary(LIBRARY_SIZE);

        PeptidesV1 peptides = new PeptidesV1(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        System.out.println("searching peptidesV1...");
        long start = System.currentTimeMillis();
        Map<String, List<Integer>> V1 = peptides.searchLibrary();
        long stop = System.currentTimeMillis();
        System.out.println("Elapsed: " + (stop - start));

        PeptidesV2 peptidesV2 = new PeptidesV2(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        System.out.println("searching peptidesV2...");
        start = System.currentTimeMillis();
        Map<String, List<Integer>> V2 = peptidesV2.searchProtein();
        stop = System.currentTimeMillis();
        System.out.println("Elapsed: " + (stop - start));

        PeptidesV3 peptidesV3 = new PeptidesV3(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        System.out.println("searching peptidesV3...");
        start = System.currentTimeMillis();
        peptidesV3.createKMersDictionaryV3();
        stop = System.currentTimeMillis();
        System.out.println("Elapsed: " + (stop - start));

        PeptidesV4 peptidesV4 = new PeptidesV4(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        System.out.println("searching peptidesV4...");
        start = System.currentTimeMillis();
        peptidesV4.createKMersDictionaryV4();
        stop = System.currentTimeMillis();
        System.out.println("Elapsed: " + (stop - start));


    }

    public static boolean compareMaps(Map<String, List<Integer>> map1, Map<String, List<Integer>> map2) {
        if (!map1.keySet().equals(map2.keySet())) {
            return false;  // Different keys
        }

        for (String key : map1.keySet()) {
            List<Integer> list1 = new ArrayList<>(map1.get(key));
            List<Integer> list2 = new ArrayList<>(map2.get(key));
            Collections.sort(list1);
            Collections.sort(list2);
            if (!list1.equals(list2)) {
                return false;  // Lists are different
            }
        }
        return true;  // Maps are equal
    }

    static String generateProtein(int proteinSize) {
        Random r = new Random();
        byte[] data = new byte[proteinSize];
        for (int i = 0; i < proteinSize; i++) {
            data[i] = ALPHABET[r.nextInt(ALPHABET.length)];
        }
        return new String(data);
    }

    static List<String> generateLibrary(int librarySize) {
        List<String> library = new ArrayList<String>(librarySize);
        for (int i = 0; i < librarySize; i++) {
            String peptide = generateProtein(PEPTIDE_SIZE);
            library.add(peptide);
        }
        return library;
    }


}