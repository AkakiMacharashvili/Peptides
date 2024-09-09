package Peptides;

import java.util.*;

public class PeptidesV4 {

    public static final int DEFAULT_PEPTIDE_SIZE = 8;

    private String protein;
    private final int peptideSize;
    public HashMap<Long, List<Integer>> kmers = new LinkedHashMap<>();
    List<Long> library = new ArrayList<>();
    private HashSet<String> libraryCollection;

    public PeptidesV4(int peptideSize, String protein, List<String> lib) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        generateLibrary(lib);

        libraryCollection = new HashSet<>(lib);

    }

    public void generateLibrary(List<String> library){
        for (String v : library) {
            this.library.add(StringToLong(v));
        }
        Collections.sort(this.library);
    }

    void createKMersDictionaryV4() {
        for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            long current = StringToLong(kmer);

            if (binarySearch(library, current) >= 0) {
                List<Integer> positions = kmers.computeIfAbsent(current, k -> new ArrayList<>());
                positions.add(i);
            }
        }
    }

    public int binarySearch(List<Long> list, long target) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            long midVal = list.get(mid);

            if (midVal < target) {
                left = mid + 1;
            } else if (midVal > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public String convertStringToBinary(String peptide){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < peptide.length(); i++){
            char c = peptide.charAt(i);
            int num = c - 'A';
            result.append(convertNumberIntoBinary(num));
        }
        return result.toString();
    }

    public String convertNumberIntoBinary(int num) {
        if (num < 0 || num > 31) {
            throw new IllegalArgumentException("Number must be in the range 0-31.");
        }
        String binaryString = Integer.toBinaryString(num);
        while (binaryString.length() < 5) {
            binaryString = "0" + binaryString;
        }

        return binaryString;
    }

    public Long convertBinaryStringToNumber(String binaryString) {
        return Long.parseLong(binaryString, 2);
    }

    public Long StringToLong(String peptide){
        String str = convertStringToBinary(peptide);
        return convertBinaryStringToNumber(str);
    }

    public static void main(String[] args) {
        String peptide = "RNLKDGHI";
        String protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
        List<String> library = List.of(peptide, "ORNLKDGH", "ABERNLKD");

        PeptidesV4 peptidesV4 = new PeptidesV4(DEFAULT_PEPTIDE_SIZE, protein, library);
        peptidesV4.createKMersDictionaryV4();

        System.out.println(peptidesV4.kmers);
    }
}
