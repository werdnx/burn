package duke.google.jam_2013.a1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Dmitrenko on 18.03.14.
 */
public class Bullseye {
	final static String WORK_DIR = "D:\\Temp\\jam\\2013-1a\\";

	void solve(Scanner sc, PrintWriter pw) {
		BigInteger r = new BigInteger(sc.next());
		BigInteger t = new BigInteger(sc.next());
		BigInteger left = BigInteger.ZERO;
		BigInteger right = BigInteger.valueOf(2000000000);
		while (right.subtract(left).compareTo(BigInteger.ONE) > 0) {
			BigInteger mid = (left.add(right)).divide(BigInteger.valueOf(2));
			BigInteger need = mid.multiply(r.multiply(BigInteger.valueOf(2)).subtract(BigInteger.valueOf(3)));
			need = need.add(BigInteger.valueOf(2).multiply(mid).multiply(mid.add(BigInteger.ONE)));
			if (need.compareTo(t) <= 0) left = mid; else right = mid;
		}
		pw.println(left);
	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new FileReader(WORK_DIR + "A-large-practice.in"));
		PrintWriter pw = new PrintWriter(new FileWriter(WORK_DIR + "output.txt"));
		int caseCnt = sc.nextInt();
		for (int caseNum=0; caseNum<caseCnt; caseNum++) {
			System.out.println("Processing test case " + (caseNum + 1));
			pw.print("Case #" + (caseNum+1) + ": ");
			new Bullseye().solve(sc, pw);
		}
		pw.flush();
		pw.close();
		sc.close();
	}
}