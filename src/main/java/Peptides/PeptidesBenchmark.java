package Peptides;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class PeptidesBenchmark {

    private static final int PROTEIN_SIZE = 10_000;
    private static final int LIBRARY_SIZE = 100_000;
    private static final int PEPTIDE_SIZE = 8;
    static final byte[] ALPHABET = new byte[26];

    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c - 'A'] = c;
        }
    }

    private String protein;
    private List<String> library;
    private PeptidesV1 peptidesV1;
    private PeptidesV2 peptidesV2;
    private PeptidesV3 peptidesV3;
    private PeptidesV4 peptidesV4;

    @Setup(Level.Trial)
    public void setup() {
        System.out.println("Generating data...");
        protein = generateProtein(PROTEIN_SIZE);
        library = generateLibrary(LIBRARY_SIZE);

        peptidesV1 = new PeptidesV1(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        peptidesV2 = new PeptidesV2(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        peptidesV3 = new PeptidesV3(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
        peptidesV4 = new PeptidesV4(PeptidesV1.DEFAULT_PEPTIDE_SIZE, protein, library);
    }

    @Benchmark
    public Map<String, List<Integer>> benchmarkPeptidesV1() {
        return peptidesV1.searchLibrary();
    }

    @Benchmark
    public Map<String, List<Integer>> benchmarkPeptidesV2() {
        return peptidesV2.searchProtein();
    }

    @Benchmark
    public void benchmarkPeptidesV3() {
        peptidesV3.createKMersDictionaryV3();
    }

    @Benchmark
    public void benchmarkPeptidesV4() {
        peptidesV4.createKMersDictionaryV4();
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

    private static List<String> generateLibrary(int librarySize) {
        List<String> library = new ArrayList<>(librarySize);
        for (int i = 0; i < librarySize; i++) {
            String peptide = generateProtein(PEPTIDE_SIZE);
            library.add(peptide);
        }
        return library;
    }


    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
