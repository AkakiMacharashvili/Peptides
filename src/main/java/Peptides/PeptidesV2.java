package Peptides;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PeptidesV2 {

    public static final int DEFAULT_PEPTIDE_SIZE = 8;

    private String protein;
    private final int peptideSize;

    public final Map<String, List<Integer>> peptideMap = new LinkedHashMap<>();

    // Constructor
    public PeptidesV2(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        populatePeptideMap(library);
    }

    private void populatePeptideMap(List<String> library) {
        for (String peptide : library) {
            peptideMap.put(peptide, new ArrayList<>());
        }
    }

    public Map<String, List<Integer>> searchProtein() {
        for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
            String kmer = protein.substring(i, i + peptideSize);

            if (peptideMap.containsKey(kmer)) {
                peptideMap.get(kmer).add(i);
            }
        }

        peptideMap.values().removeIf(List::isEmpty);

        return peptideMap;
    }

    public List<Integer> getPositions(String peptide) {
        return peptideMap.getOrDefault(peptide, List.of());
    }

    public void displayKeys(){
        for(String v : peptideMap.keySet()){
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
