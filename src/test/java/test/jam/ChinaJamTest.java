package test.jam;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Dmitrenko on 14.03.14.
 */
public class ChinaJamTest {
	private static String[] dict = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
	private static String[] repeatDict = { "", "", "double", "triple", "quadruple", "quintuple", "sextuple", "septuple", "octuple", "nonuple", "decuple" };

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("d:\\Temp\\jam\\A-large-practice.in"), Charset.defaultCharset());
		List<String> result = new LinkedList<String>();

		int count = 1;
		for (String s : lines.subList(1, lines.size())) {
			String[] split = s.split(" ");
			String[] template = split[1].split("-");
			int[] intTemplate = new int[template.length];
			int i = 0;
			for (String t : template) {
				intTemplate[i++] = Integer.valueOf(t);
			}

			result.add(createPhrase(split[0], intTemplate, count));
			count++;
		}
		Files.write(Paths.get("d:\\Temp\\jam\\result.out"), result, Charset.defaultCharset(), StandardOpenOption.TRUNCATE_EXISTING);

	}

	public static String createPhrase(String source, int[] template, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append("Case #").append(count).append(": ");
		char[] chars = source.toCharArray();
		int pos = 0;
		for (int t : template) {
			int localCount = 0;
			int prevNumber = Integer.MAX_VALUE;

			for (int i = 0; i < t; i++) {
				int number = Integer.valueOf(String.valueOf(chars[pos + i]));
				if (number == prevNumber) {
					localCount++;
				} else if (localCount != 0 && number != prevNumber) {
					printNumbers(sb, localCount, prevNumber);
					localCount = 1;
					prevNumber = number;
				} else {
					localCount = 1;
					prevNumber = number;
				}
				if (i == t - 1) {
					printNumbers(sb, localCount, number);
				}
			}

			pos += t;
		}

		return sb.toString();
	}

	private static void printNumbers(StringBuilder sb, int count, int num) {
		if (count > 1 && count < 11) {
			sb.append(repeatDict[count]).append(" ").append(dict[num]).append(" ");
		} else if (count <= 1) {
			sb.append(dict[num]).append(" ");
		} else if (count > 10) {
			for (int i = 0; i < count; i++) {
				sb.append(dict[num]).append(" ");
			}
		}
	}


}
