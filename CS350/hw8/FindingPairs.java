import java.security.*;
import java.util.*;
import java.io.*;


public class FindingPairs {
    private static Hash useHash = new Hash();
    private static int maxVal = Integer.MAX_VALUE;
    private static ArrayList<Thread> occurences = new ArrayList<Thread>();

    public static void findPairs(String input_filepath1, String input_filepath2) throws Exception {
        ArrayList<String> strAnswer1 = new ArrayList<String>();
        ArrayList<String> strAnswer2 = new ArrayList<String>();
        FileReader fr1 = new FileReader(input_filepath1);
        FileReader fr2 = new FileReader(input_filepath2);
        int N = 1;

        try (BufferedReader br = new BufferedReader(fr1)) {
            String line;
            while ((line = br.readLine()) != null) {
               strAnswer1.add(line);
            }
        }
        
        try (BufferedReader br = new BufferedReader(fr2)) {
            String line;
            while ((line = br.readLine()) != null) {
               strAnswer2.add(line);
            }
        }
        
        int split = strAnswer1.size()/N;
        for(int i = 0; i < N; i++) {
            int start = i*split;
            int end = ((i*split)+split);
            occurences.add(new threadFunc(start, end, strAnswer1, strAnswer2));
        }
        
        
        for(int i = 0; i < N; i++) {
            occurences.get(i).start();
        }


        for(int i = 0; i < N; i++) {
            occurences.get(i).join();
        }

    }

    public static void main(String[] args) throws Exception {
        String inputArg1 = args[0];
        String inputArg2 = args[1];
        findPairs(inputArg1, inputArg2);

    }

}
