package Peptides;

import java.util.*;

public class PeptidesV3 {

    public static final int DEFAULT_PEPTIDE_SIZE = 8;

    private String protein;
    private final int peptideSize;
    public HashMap<Long, List<Integer>> kmers = new LinkedHashMap<>();
    private List<Long> library = new ArrayList<>();

    //just constructor...
    public PeptidesV3(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        generateLibrary(library);
    }

    public void generateLibrary(List<String> library){
        for(String v : library){
            this.library.add(StringToLong(v));
        }
    }

    void createKMersDictionaryV3() {
        for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            long current = StringToLong(kmer);
            if(library.contains(current)){
                List<Integer> positions = kmers.computeIfAbsent(current, k -> new ArrayList<>());
                positions.add(i);
            }
        }
    }

    public String  convertStringToBinary(String peptide){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < peptide.length(); i++){
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
//
    public Long StringToLong(String peptide){
        if(peptide.length() > 8) System.out.println("gamarjoba...");
        String str = convertStringToBinary(peptide);
        return convertBinaryStringToNumber(str);
    }
}
