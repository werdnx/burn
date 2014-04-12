package duke.google.jam_2013.a1;

import org.apache.commons.math3.fraction.BigFraction;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Dmitrenko on 17.03.14.
 */
public class ProblemMid extends Problem {
	public static void main(String args[]) throws IOException {
		//new ProblemBig().solve("D:\\Temp\\jam\\2013-1a\\A-small-practice.in", "D:\\Temp\\jam\\2013-1a\\result.out");
		new ProblemMid().solve("D:\\Temp\\jam\\2013-1a\\A-large-practice.in", "D:\\Temp\\jam\\2013-1a\\result.out");
		//new ProblemBig().solve("D:\\Temp\\jam\\2013-1a\\test.txt", "D:\\Temp\\jam\\2013-1a\\result.out");
	}

	@Override
	public String processItem(String s, int i) {
		System.out.println("Processing test case " + i);
		StringBuilder sb = new StringBuilder();
		sb.append("Case #").append(i).append(": ");
		long r = Long.valueOf(s.split(" ")[0]);
		long t = Long.valueOf(s.split(" ")[1]);

		BigInteger left = BigInteger.ZERO;
		BigInteger mid = BigInteger.ZERO;
		BigInteger right = new BigInteger("2000000000");
		while (right.subtract(left).compareTo(BigInteger.ONE) > 0) {
			mid = (right.add(left)).divide(BigInteger.valueOf(2));
			BigInteger f = calculateFunction(mid.longValue(), r);
			if (f.compareTo(BigInteger.valueOf(t)) <= 0) {
				left = mid;
			} else {
				right = mid;
			}
		}
		return sb.append(left).toString();
	}


	private BigInteger calculateFunction(long n, long r) {
		BigInteger result = BigInteger.valueOf(2).multiply(BigInteger.valueOf(diff * diff)).multiply(BigInteger.valueOf(n)).multiply(BigInteger.valueOf(n)).subtract(BigInteger.valueOf(diff * diff).multiply(BigInteger.valueOf(n))).add(BigInteger.valueOf(2).multiply(BigInteger.valueOf(r)).multiply(BigInteger.valueOf(diff)).multiply(BigInteger.valueOf(n)));
		return result;
	}

}
