package duke.google.jam_2013.qb;

import duke.google.jam.ConcurrentSolver;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by andrew on 05.04.14.
 */
public class Solver extends ConcurrentSolver<Solver.Context, String> {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        new Solver((2)).solve("C:\\Andrew\\develop\\google-jam\\2013\\qaRound\\c\\C-large-practice-1.in", "C:\\Andrew\\develop\\google-jam\\2013\\qaRound\\c\\res.out");
    }

    public Solver(int threads) {
        super(threads);
    }

    @Override
    protected Context processLine(String s) {
        String[] arr = s.split(" ");
        return new Context(Long.valueOf(arr[0]), Long.valueOf(arr[1]));
    }

    @Override
    public String solveItem(Context context) {
        long res = 0l;
        for (long i = context.start; i <= context.end; i++) {
            if (isPalindrom(String.valueOf(i)) && isFear(i)) {
                res++;
            }
        }
        return String.valueOf(res);
    }

    private boolean isFear(long i) {
        double sqrt = Math.sqrt((double) i);
        return isPalindrom(String.valueOf(sqrt));
    }

    private boolean isPalindrom(String i) {
        String[] split = i.split("\\.");
        if (split.length > 1 && Long.valueOf(split[1]).longValue() != 0)
            return false;
        char[] chars = split[0].toCharArray();
        if (chars.length == 1) {
            return true;
        }
        int start = 0;
        int end = chars.length - 1;
        while (start < end) {
            if (chars[start++] != chars[end--]) {
                return false;
            }
        }
        return true;
    }

    public static class Context {
        public Context(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public long start;
        public long end;
    }
}
