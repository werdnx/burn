package duke.google.jam_2013.c1;

import duke.google.jam.ConcurrentSolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Dmitrenko on 19.03.14.
 * In English, there are 26 letters that are either vowels or consonants. In this problem, we consider a, e, i, o, and u to be vowels, and the other 21 letters to be consonants.
 * <p/>
 * A tribe living in the Greatest Colorful Jungle has a tradition of naming their members using English letters. But it is not easy to come up with a good name for a new member because it reflects the member's social status within the tribe. It is believed that the less common the name he or she is given, the more socially privileged he or she is.
 * <p/>
 * The leader of the tribe is a professional linguist. He notices that hard-to-pronounce names are uncommon, and the reason is that they have too many consecutive consonants. Therefore, he announces that the social status of a member in the tribe is determined by its n-value, which is the number of substrings with at least n consecutive consonants in the name. For example, when n = 3, the name "quartz" has the n-value of 4 because the substrings quartz, uartz, artz, and rtz have at least 3 consecutive consonants each. A greater n-value means a greater social status in the tribe. Two substrings are considered different if they begin or end at a different point (even if they consist of the same letters), for instance "tsetse" contains 11 substrings with two consecutive consonants, even though some of them (like "tsetse" and "tsetse") contain the same letters.
 * <p/>
 * All members in the tribe must have their names and n given by the leader. Although the leader is a linguist and able to ensure that the given names are meaningful, he is not good at calculating the n-values. Please help the leader determine the n-value of each name. Note that different names may have different values of n associated with them.
 * <p/>
 * Input
 * <p/>
 * The first line of the input gives the number of test cases, T. T test cases follow. The first line of each test case gives the name of a member as a string of length L, and an integer n. Each name consists of one or more lower-case English letters.
 * <p/>
 * Output
 * <p/>
 * For each test case, output one line containing "Case #x: y", where x is the case number (starting from 1) and y is the n-value of the member's name.
 */
public class Tribe extends ConcurrentSolver<Tribe.Context, Long> {
	public Tribe(int threads) {
		super(threads);
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		System.out.println("Start " + new Date());
		new Tribe(4).solve("D:\\Temp\\jam\\2013-1c\\A-large-practice.in", "D:\\Temp\\jam\\2013-1c\\result.out");
		System.out.println("End " + new Date());
	}

	private static Set<Character> vowels = new HashSet<Character>();

	static {
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('o');
		vowels.add('u');
		vowels.add('A');
		vowels.add('E');
		vowels.add('I');
		vowels.add('O');
		vowels.add('U');
	}

	@Override
	protected Context processLine(String s) {
		String[] split = s.split(" ");
		return new Context(Integer.valueOf(split[1]), split[0]);
	}

	@Override
	public Long solveItem(Context context) {
		long result = 0;
		boolean ok = true;
		int start = 0;

		int seq = 0;
		System.out.println("Start process line of size " + context.s.length());
		char[] chars = context.s.toCharArray();
		while (ok) {
			ok = false;
			for (int i = start; i < chars.length; i++) {
				if (seq + (chars.length - i) < context.n) {
					break;
				}
				if (chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u') {
					seq = 0;
				} else {
					seq++;
				}
				if (seq == context.n) {
					result += chars.length - i;
					start++;
					ok = true;
					seq = 0;
					break;
				}
			}
		}

		System.out.println("End process line of size " + context.s.length());
		return result;
	}


	public static class Context {
		public Context(int n, String s) {
			this.n = n;
			this.s = s;
		}

		public int n;
		public String s;
	}
}
