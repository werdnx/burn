package duke.google.jam_2014.q.a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by andrew on 12.04.14.
 */
public class Solve {
    private static int ARR_SIZE = 4;
    private static String BAD = "Bad magician!";
    private static String CHEET = "Volunteer cheated!";

    public static void main(String[] args) throws IOException {
        new Solve().solve("C:\\Andrew\\develop\\google-jam\\2014\\q\\a\\A-small-attempt0.in", "C:\\Andrew\\develop\\google-jam\\2014\\q\\a\\res.out");
    }

    public void solve(String in, String out) throws IOException {
        List<String> result = new LinkedList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(in)))) {
            int size = Integer.parseInt(br.readLine());
            for (int i = 1; i <= size; i++) {
                MagicBoxItem item = new MagicBoxItem();
                item.answer1 = Integer.parseInt(br.readLine()) - 1;
                item.box1 = createBox(br);
                item.answer2 = Integer.parseInt(br.readLine()) - 1;
                item.box2 = createBox(br);
                result.add(solveItem(item, i));
            }
        }
        Files.write(Paths.get(out), result, Charset.defaultCharset(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String solveItem(MagicBoxItem item, int i) {
        String res = "Case #" + i + ": ";
        String answer = null;

        int occ = 0;
        int number = 0;
        for (Integer integer : item.box1.get(item.answer1)) {
            if (item.box2.get(item.answer2).contains(integer)) {
                occ++;
                number = integer;
            }
        }
        switch (occ) {
            case 0:
                answer = CHEET;
                break;
            case 1:
                answer = String.valueOf(number);
                break;
            default:
                answer = BAD;
        }

        return res + answer;
    }

    private List<Set<Integer>> createBox(BufferedReader br) throws IOException {
        List<Set<Integer>> box = new ArrayList<Set<Integer>>(ARR_SIZE);
        for (int i = 0; i < ARR_SIZE; i++) {
            String[] s = br.readLine().split(" ");
            box.add(sArrToIntSet(s));
        }
        return box;
    }

    private Set<Integer> sArrToIntSet(String[] s) {
        Set<Integer> res = new HashSet<Integer>();
        for (String s1 : s) {
            res.add(Integer.parseInt(s1));
        }
        return res;
    }

    private static class MagicBoxItem {
        public Integer answer1;
        public List<Set<Integer>> box1;
        public Integer answer2;
        public List<Set<Integer>> box2;
    }
}
