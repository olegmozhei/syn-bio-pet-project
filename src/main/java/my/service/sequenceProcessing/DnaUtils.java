package my.service.sequenceProcessing;

import org.json.JSONArray;
import org.json.JSONObject;

public class DnaUtils {
    //R = A or G	K = G or T	S = G or C
    //Y = C or T	M = A or C	W = A or T
    //B = not A (C, G or T)	H = not G (A, C or T)	N = any nucleotide
    //D = not C (A, G or T)	V = not T (A, C or G)

    public boolean compareLengthEqualStrings(String substring, String source){
        if (substring.length()!= source.length()) throw new RuntimeException("Can't compare strings with different length");
        substring = substring.toUpperCase();
        source = source.toUpperCase();
        if (source.replaceAll("[ACGT]", "").length() > 0) throw new RuntimeException("Can't process source DNA with ambiguous code");
        for (int i = 0; i < substring.length(); i++) {
            if (!isCharsMatches(substring.charAt(i), source.charAt(i))) return false;
        }
        return true;
    }

    public JSONArray findMotifInString(String motif, String sequence){
        JSONArray result = new JSONArray();
        for (int i = 0; i <= sequence.length() - motif.length(); i++) {
            String subsequence = sequence.substring(i, i + motif.length());
            if (compareLengthEqualStrings(motif, subsequence)) result.put(i + 1);
        }
        return result;
    }

    public String reverseComplement(String source){
        StringBuilder result = new StringBuilder();
        for (int i = source.length() - 1; i >= 0; i--) {
            char c = source.charAt(i);
            if (c == 'A') result.append('T');
            if (c == 'T') result.append('A');
            if (c == 'G') result.append('C');
            if (c == 'C') result.append('G');
        }
        return result.toString();
    }

    public boolean isCharsMatches(char a, char b){
        //R = A or G	K = G or T	S = G or C
        //Y = C or T	M = A or C	W = A or T
        //B = not A (C, G or T)	H = not G (A, C or T)	N = any nucleotide
        //D = not C (A, G or T)	V = not T (A, C or G)
        if (a == b) return true;
        if (a == 'R' && (b == 'A' || b == 'G')) return true;
        if (a == 'Y' && (b == 'C' || b == 'T')) return true;
        if (a == 'K' && (b == 'G' || b == 'T')) return true;
        if (a == 'M' && (b == 'A' || b == 'C')) return true;
        if (a == 'S' && (b == 'G' || b == 'C')) return true;
        if (a == 'W' && (b == 'A' || b == 'T')) return true;
        if (a == 'B' && (b == 'C' || b == 'G' || b == 'T')) return true;
        if (a == 'D' && (b == 'A' || b == 'G' || b == 'T')) return true;
        if (a == 'H' && (b == 'A' || b == 'C' || b == 'T')) return true;
        if (a == 'V' && (b == 'A' || b == 'C' || b == 'G')) return true;
        if (a == 'N') return true;
        return false;
    }
}
