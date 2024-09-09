package Peptides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PeptidesV1 {

    public static final int DEFAULT_PEPTIDE_SIZE = 8;

    private String protein;
    private final int peptideSize;

    public HashMap<String, List<Integer>> kmers = new LinkedHashMap<>();

    public final List<String> library;

    //just constructor...
    public PeptidesV1(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.library = library;
        createKMersDictionary();
    }

    // check if some substring is in the kmers list...
    void createKMersDictionary() {
        for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            List<Integer> positions = kmers.computeIfAbsent(kmer, k -> new ArrayList<>());
            positions.add(i);
        }
    }

    //return list of specific peptide...
    public List<Integer> search(String peptide) {
        return kmers.getOrDefault(peptide, List.of());
    }

    // generate all the possible string that exists in library...
    public Map<String, List<Integer>> searchLibrary() {
        LinkedHashMap<String, List<Integer>> existingPeptides = new LinkedHashMap<>();
        for (String peptide : library) {
            List<Integer> positions = kmers.get(peptide);
            if (null == positions)
                continue;
            existingPeptides.put(peptide, positions);
        }
        return existingPeptides;
    }

    public void displayKeys(){
        for(String v : kmers.keySet()){
            System.out.print(v + " ");
        }
        System.out.println();
    }

}