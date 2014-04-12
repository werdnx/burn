package duke.google.jam_2014.q.c;

import org.omg.CORBA.INITIALIZE;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by andrew on 12.04.14.
 */
public class Solve {
    private static final String IMPOSSIPLE = "Impossible";
    private static final char START = 'c';
    private static final char EMPTY = '.';
    private static final char INIT = '\u0000';
    private static final char MINE = '*';


    public static void main(String[] args) throws IOException {
        new Solve().solve("C:\\Andrew\\develop\\google-jam\\2014\\q\\c\\C-small-attempt1.in", "C:\\Andrew\\develop\\google-jam\\2014\\q\\c\\res.out");
    }

    public void solve(String in, String out) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(in), Charset.defaultCharset());
        StringBuilder res = new StringBuilder();
        //int size = Integer.parseInt(lines.get(0));
        int i = 1;
        for (String s : lines.subList(1, lines.size())) {
            solveItem(i++, res, createHolder(s));
        }
        Files.write(Paths.get(out), res.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private Holder createHolder(String s) {
        Holder res = new Holder();
        String[] split = s.split(" ");
        res.rows = Integer.parseInt(split[0]);
        res.columns = Integer.parseInt(split[1]);
        res.mines = Integer.parseInt(split[2]);
        res.field = new char[res.rows][res.columns];
        return res;
    }

    private void solveItem(int i, StringBuilder sb, Holder h) {
        String res = null;
        if (h.rows == 1 || h.columns == 1) {
            h.field[h.rows - 1][h.columns - 1] = START;
        } else if (h.mines < h.rows * h.columns - 1 && h.mines > h.rows * h.columns - 4) {
            res = IMPOSSIPLE;
        } else if (h.mines == h.rows * h.columns - 1) {
            h.field[h.rows - 1][h.columns - 1] = START;
        } else {
            /*h.field[h.rows - 1][h.columns - 1] = START;
            h.field[h.rows - 1][h.columns - 2] = EMPTY;
            h.field[h.rows - 2][h.columns - 2] = EMPTY;
            h.field[h.rows - 2][h.columns - 1] = EMPTY;*/
            res = prepareSquare(h);
        }
        if (res == null) {
            int restMines = h.mines;
            StringBuilder sField = new StringBuilder();
            for (int r = 0; r < h.rows; r++) {
                for (int c = 0; c < h.columns; c++) {
                    if (h.field[r][c] == INIT) {
                        if (restMines > 0) {
                            h.field[r][c] = MINE;
                            restMines--;
                        } else {
                            h.field[r][c] = EMPTY;
                        }
                    }
                    sField.append(h.field[r][c]);
                }
                if (r != h.rows - 1)
                    sField.append("\n");
            }
            res = sField.toString();
        }

        sb.append("Case #").append(i).append(":\n").append(res).append("\n");
    }

    private String prepareSquare(Holder h) {
        String res = null;
        double emptySize = h.rows * h.columns - h.mines;
        int width = 0;
        int height = 0;
        if (emptySize % 2 != 0) {
            //check square
            double sq = Math.sqrt(emptySize);
            if ((sq == Math.floor(sq))) {
                if (sq <= h.rows && sq <= h.columns) {
                    width = (int) sq;
                    height = width;
                } else {
                    if (tryMixTriangle(h, emptySize))
                        return null;
                    else
                        res = IMPOSSIPLE;
                }
            } else {
                res = IMPOSSIPLE;
            }
        } else {
            boolean finish = false;
            double i = 2.0;
            while (!finish) {
                double v = emptySize / i;
                if (v == Math.floor(v) && (int) i <= h.columns && (int) v <= h.rows) {
                    width = (int) i;
                    height = (int) v;
                    finish = true;
                } else {
                    i += 1.0;
                    if ((int) i > h.columns) {
                        finish = true;
                        res = IMPOSSIPLE;
                        if (tryMixTriangle(h, emptySize))
                            return null;
                    }
                }
            }

        }
        if (res == null) {
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    h.field[r][c] = EMPTY;
                }
            }
            h.field[0][0] = START;
        }
        return res;
    }

    private boolean tryMixTriangle(Holder h, double emptySize) {
        boolean res = true;

        int i = 2;
        int j = 2;
        boolean finish = false;
        while (!finish) {
            if (h.columns * h.rows - (h.rows - i) * (h.columns - j) >= emptySize) {
                int rest = (int) emptySize + i * j;
                fillit(h, rest, i, j);
                finish = true;
            } else {
                if (i < h.rows - 1) {
                    i++;
                } else if (j < h.columns - 1) {
                    j++;
                } else {
                    finish = true;
                    res = tryReversMixTriangle(h, emptySize);
                }
            }
        }

        return res;
    }

    private boolean tryReversMixTriangle(Holder h, double emptySize) {
        boolean res = true;

        int i = 2;
        int j = 2;
        boolean finish = false;
        while (!finish) {
            if (h.columns * h.rows - (h.rows - i) * (h.columns - j) >= emptySize) {
                int rest = (int) emptySize + i * j;
                fillit(h, rest, i, j);
                finish = true;
            } else {
                if (j < h.columns - 1) {
                    j++;
                } else if (i < h.rows - 1) {
                    i++;
                } else {
                    finish = true;
                    res = false;
                }
            }
        }

        return res;
    }

    private void fillit(Holder h, int rest, int i, int j) {
        if (i < j) {
            for (int r = 0; r < i; r++) {
                for (int c = 0; c < h.columns; c++) {
                    if (rest > 0) {
                        h.field[r][c] = EMPTY;
                        rest--;
                    }

                }
            }
            for (int r = 0; r < h.rows; r++) {
                for (int c = 0; c < j; c++) {
                    if (rest > 0) {
                        h.field[r][c] = EMPTY;
                        rest--;
                    }

                }
            }
        } else {
            for (int r = 0; r < h.rows; r++) {
                for (int c = 0; c < j; c++) {
                    if (rest > 0) {
                        h.field[r][c] = EMPTY;
                        rest--;
                    }

                }
            }
            for (int r = 0; r < i; r++) {
                for (int c = 0; c < h.columns; c++) {
                    if (rest > 0) {
                        h.field[r][c] = EMPTY;
                        rest--;
                    }

                }
            }

        }
        h.field[0][0] = START;
    }


    private static class Holder {
        public int rows;
        public int columns;
        public int mines;
        public char[][] field;
    }
}
