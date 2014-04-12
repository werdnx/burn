package duke.google.jam_2014.q.b;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by andrew on 12.04.14.
 */
public class Solve {

    private static final double INIT_SPEED = 2.0;

    public static void main(String[] args) throws IOException {
        new Solve().solve("C:\\Andrew\\develop\\google-jam\\2014\\q\\b\\B-large.in", "C:\\Andrew\\develop\\google-jam\\2014\\q\\b\\res.out");
    }


    public void solve(String in, String out) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(in), Charset.defaultCharset());
        List<String> result = new LinkedList<String>();
        int size = Integer.parseInt(lines.get(0));
        int i = 1;
        for (String s : lines.subList(1, lines.size())) {
            result.add(solveItem(createHolder(s), i++));
        }
        Files.write(Paths.get(out), result, Charset.defaultCharset(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private Holder createHolder(String s) {
        String[] split = s.split(" ");
        return new Holder(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));

    }

    private String solveItem(Holder h, int i) {
        double spentTime = 0.0;
        double speed = INIT_SPEED;

        boolean finish = false;
        while (!finish) {
            double total1 = h.c / speed + h.x / (speed + h.f);
            double total2 = h.x / speed;
            if (total1 < total2) {
                spentTime += h.c / speed;
                speed += h.f;
            } else {
                spentTime += h.x / speed;
                finish = true;
            }
        }


        return "Case #" + i + ": " + String.format(Locale.ENGLISH, "%.7f", spentTime);
    }

    private static class Holder {
        public double c;
        public double f;
        public double x;

        private Holder(double c, double f, double x) {
            this.c = c;
            this.f = f;
            this.x = x;
        }
    }
}
