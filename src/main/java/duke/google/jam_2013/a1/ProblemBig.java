package duke.google.jam_2013.a1;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.math3.fraction.Fraction;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Dmitrenko on 17.03.14.
 */
public class ProblemBig extends Problem {
	public static void main(String args[]) throws IOException {
		//new ProblemBig().solve("D:\\Temp\\jam\\2013-1a\\A-small-practice.in", "D:\\Temp\\jam\\2013-1a\\result.out");
		new ProblemBig().solve("D:\\Temp\\jam\\2013-1a\\A-large-practice.in", "D:\\Temp\\jam\\2013-1a\\result.out");
		//new ProblemBig().solve("D:\\Temp\\jam\\2013-1a\\test.txt", "D:\\Temp\\jam\\2013-1a\\result.out");
	}

	@Override
	public String processItem(String s, int i) {
		StringBuilder sb = new StringBuilder();
		sb.append("Case #").append(i).append(": ");
		double r = Long.valueOf(s.split(" ")[0]);
		double t = Long.valueOf(s.split(" ")[1]);

		BigFraction a1 = new BigFraction(0);
		a1 = a1.add(new BigFraction(Math.pow(diff, 2)));
		a1 = a1.subtract(new BigFraction(r).multiply(new BigFraction(2).multiply(new BigFraction(diff))));

		BigFraction a11 = new BigFraction(2);
		a11 = a11.multiply(new BigFraction(r)).multiply(new BigFraction(diff)).subtract(new BigFraction(diff * diff));
		a11 = a11.multiply(a11).add(new BigFraction(8).multiply(new BigFraction(t).multiply(new BigFraction(diff))));
		a1 = a1.add(new BigFraction(a11.pow(0.5)));

		a1 = a1.divide(new BigFraction(4.0).multiply(new BigFraction(diff).multiply(new BigFraction(diff))));
		long circles = a1.longValue();
		/*BigDecimal a1 = new BigDecimal(0.0);
		a1 = a1.add(new BigDecimal(Math.pow(diff, 2)));
		a1 = a1.subtract(new BigDecimal(r).multiply(new BigDecimal(2.0).multiply(new BigDecimal(diff))));

		BigDecimal a11 = new BigDecimal(2.0);
		a11 = a11.multiply(new BigDecimal(r)).multiply(new BigDecimal(diff)).subtract(new BigDecimal(diff * diff));
		a11 = a11.multiply(a11).add(new BigDecimal(8.0).multiply(new BigDecimal(t).multiply(new BigDecimal(diff))));
		a1 = a1.add(sqrt(a11));

		long circles = a1.divide(new BigDecimal(4.0).multiply(new BigDecimal(diff).multiply(new BigDecimal(diff))), RoundingMode.HALF_DOWN).longValue();*/
		//(long) ((Math.pow(diff, 2) - 2 * r * diff + Math.sqrt(Math.pow(2 * r * diff - diff * diff, 2) + 8 * diff * t)) / (4 * diff * diff));
		circles = checkAndCorrect(circles, t, r);
		return sb.append(circles).toString();
	}

	private long checkAndCorrect(long n, double t, double r) {
		long res = checkDown(n, t, r);
		res = checkUp(res, t, r);
		return res;
	}

	private long checkDown(long n, double t, double r) {
		BigFraction f = calculateFunction(n, r);
		long result = f.longValue();
		long nn = n;
		while (result < t) {
			nn++;
			f = calculateFunction(nn, r);
			result = f.longValue();
		}
		return nn;
	}

	private long checkUp(long n, double t, double r) {
		BigFraction f = calculateFunction(n, r);
		long result = f.longValue();
		long nn = n;
		while (result > t) {
			nn--;
			f = calculateFunction(nn, r);
			result = f.longValue();
		}
		return nn;
	}

	private BigFraction calculateFunction(long n, double r) {
		BigFraction result = new BigFraction(2).multiply(new BigFraction(diff * diff)).multiply(new BigFraction(n)).multiply(new BigFraction(n)).subtract(new BigFraction(diff * diff).multiply(new BigFraction(n))).add(new BigFraction(2).multiply(new BigFraction(r)).multiply(new BigFraction(diff)).multiply(new BigFraction(n)));
		return result;
	}

}
