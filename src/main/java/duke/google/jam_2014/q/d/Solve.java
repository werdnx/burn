package duke.google.jam_2014.q.d;

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
 * Created by andrew on 13.04.14.
 */
public class Solve {

    public static void main(String[] args) throws IOException {
        new Solve().solve("C:\\Andrew\\develop\\google-jam\\2014\\q\\d\\D-large.in", "C:\\Andrew\\develop\\google-jam\\2014\\q\\d\\res.out");
    }

    public void solve(String in, String out) throws IOException {
        List<String> result = new LinkedList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(in)))) {
            int size = Integer.parseInt(br.readLine());
            for (int i = 1; i <= size; i++) {
                br.readLine();
                Holder item = new Holder();
                item.naomiSet = parseSet(br.readLine());
                item.kenSet = parseSet(br.readLine());
                result.add(solveItem(item, i));
            }
        }
        Files.write(Paths.get(out), result, Charset.defaultCharset(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private TreeSet<Double> parseSet(String s) {
        TreeSet<Double> res = new TreeSet<>();
        String[] split = s.split(" ");
        for (String s1 : split) {
            res.add(Double.parseDouble(s1));
        }
        return res;
    }

    private String solveItem(Holder item, int i) {
        return "Case #" + i + ": " + solveDeceitfulWar(item) + " " + solveWar(item);
    }

    private int solveDeceitfulWar(Holder item) {
        int res = 0;
        TreeSet<Double> k = (TreeSet<Double>) item.kenSet.clone();
        for (Double d : item.naomiSet) {
            Double floor = k.floor(d);
            if (floor != null) {
                res++;
                k.remove(floor);
            }
        }
        return res;
    }

    private int solveWar(Holder item) {
        int res = 0;
        TreeSet<Double> n = (TreeSet<Double>) item.naomiSet.clone();
        TreeSet<Double> k = (TreeSet<Double>) item.kenSet.clone();

        for (Double d : n) {
            Double higher = k.higher(d);
            if (higher == null) {
                res++;
            } else {
                k.remove(higher);
            }
        }

        return res;
    }

    private static class Holder {
        public TreeSet<Double> naomiSet;
        public TreeSet<Double> kenSet;
    }
}
