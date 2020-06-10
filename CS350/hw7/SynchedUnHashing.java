import java.security.*;
import java.util.*;
import java.io.*;


public class SynchedUnHashing {
    private static Hash useHash = new Hash();
    private static int maxVal = Integer.MAX_VALUE;
    private static ArrayList<Thread> occurences = new ArrayList<Thread>();

    public static void synchedUnhash(String input_filepath, int N) throws Exception {
        ArrayList<String> strAnswer = new ArrayList<String>();
        FileReader fr = new FileReader(input_filepath);

        try (BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
               strAnswer.add(line);
            }
        }
        int split = strAnswer.size()/N;
        for(int i = 0; i < N; i++) {
            int start = i*split;
            int end = ((i*split)+split);
            occurences.add(new threadFunc(start, end, strAnswer));
        }

        for(int i = 0; i < N; i++) {
            occurences.get(i).start();
        }


        for(int i = 0; i < N; i++) {
            occurences.get(i).join();
        }

    }

    public static void main(String[] args) throws Exception {
        String inputArg = args[0];
        int maxN = Integer.parseInt(args[1]);
        synchedUnhash(inputArg, maxN);

    }

}
