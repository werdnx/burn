package duke.google.jam_2013.a1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dmitrenko on 17.03.14.
 * Problem
 * <p/>
 * Maria has been hired by the Ghastly Chemicals Junkies (GCJ) company to help them manufacture bullseyes. A bullseye consists of a number of concentric rings (rings that are centered at the same point), and it usually represents an archery target. GCJ is interested in manufacturing black-and-white bullseyes.
 * <p/>
 * <p/>
 * <p/>
 * Maria starts with t millilitres of black paint, which she will use to draw rings of thickness 1cm (one centimetre). A ring of thickness 1cm is the space between two concentric circles whose radii differ by 1cm.
 * <p/>
 * Maria draws the first black ring around a white circle of radius r cm. Then she repeats the following process for as long as she has enough paint to do so:
 * <p/>
 * Maria imagines a white ring of thickness 1cm around the last black ring.
 * Then she draws a new black ring of thickness 1cm around that white ring.
 * Note that each "white ring" is simply the space between two black rings.
 * The area of a disk with radius 1cm is π cm2. One millilitre of paint is required to cover area π cm2. What is the maximum number of black rings that Maria can draw? Please note that:
 * <p/>
 * Maria only draws complete rings. If the remaining paint is not enough to draw a complete black ring, she stops painting immediately.
 * There will always be enough paint to draw at least one black ring.
 * Input
 * <p/>
 * The first line of the input gives the number of test cases, T. T test cases follow. Each test case consists of a line containing two space separated integers: r and t.
 * <p/>
 * Output
 * <p/>
 * For each test case, output one line containing "Case #x: y", where x is the case number (starting from 1) and y is the maximum number of black rings that Maria can draw.
 * <p/>
 * Limits
 * <p/>
 * Small dataset
 * <p/>
 * 1 ≤ T ≤ 1000.
 * 1 ≤ r, t ≤ 1000.
 * <p/>
 * Large dataset
 * <p/>
 * 1 ≤ T ≤ 6000.
 * 1 ≤ r ≤ 1018.
 * 1 ≤ t ≤ 2 × 1018.
 */
public class Problem {
	public static void main(String args[]) throws IOException {
		new Problem().solve("D:\\Temp\\jam\\2013-1a\\test.txt", "D:\\Temp\\jam\\2013-1a\\result.out");
	}

	/**
	 * cm
	 */
	protected int diff = 1;
	private double leftPaint;
	private double nextRadius;

	public void solve(String in, String out) throws IOException {
		List<String> list = Files.readAllLines(Paths.get(in), Charset.defaultCharset());
		List<String> result = new LinkedList<String>();
		int counter = 1;
		for (String s : list.subList(1, list.size())) {
			result.add(processItem(s, counter++));
		}
		Files.write(Paths.get(out), result, Charset.defaultCharset(), StandardOpenOption.TRUNCATE_EXISTING);

	}

	public String processItem(String s, int i) {
		StringBuilder sb = new StringBuilder();
		sb.append("Case #").append(i).append(": ");
		double r = Long.valueOf(s.split(" ")[0]);
		double t = Long.valueOf(s.split(" ")[1]);
		long circles = 0l;

		nextRadius = r;
		leftPaint = t;

		while (canDrawOneMore()) {
			leftPaint = leftPaint - nextCirclePaint();
			nextRadius = nextRadius + 2 * diff;
			circles++;
		}

		return sb.append(circles).toString();
	}

	private boolean canDrawOneMore() {
		return leftPaint > nextCirclePaint();
	}

	private double nextCirclePaint() {
		return (Math.PI * Math.pow(nextRadius + diff, 2) - Math.PI * Math.pow(nextRadius, 2)) / Math.PI;
	}

}
