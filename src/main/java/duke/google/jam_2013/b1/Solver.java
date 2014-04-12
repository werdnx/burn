package duke.google.jam_2013.b1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Dmitrenko on 19.03.14.
 */
public class Solver {
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		new Solver().solve("D:\\Temp\\jam\\2013-1b\\A-large-practice.in", "D:\\Temp\\jam\\2013-1b\\result.out");
	}

	public void solve(String in, String out) throws IOException, InterruptedException, ExecutionException {
		List<String> result = new LinkedList<String>();
		List<String> lines = Files.readAllLines(Paths.get(in), Charset.defaultCharset());

		ExecutorService srv = Executors.newFixedThreadPool(4);

		int size = Integer.valueOf(lines.get(0));
		int counter = 0;
		int myMoteSize = 0;
		int motes[] = null;

		List<ASolver> solvers = new ArrayList<ASolver>(size);
		for (String s : lines.subList(1, lines.size())) {
			if (counter % 2 == 0) {
				myMoteSize = Integer.valueOf(s.split(" ")[0]);
				motes = new int[Integer.valueOf(s.split(" ")[1])];
			} else {
				String[] sMotes = s.split(" ");
				for (int i = 0; i < motes.length; i++) {
					motes[i] = Integer.valueOf(sMotes[i]);
				}
				solvers.add(new ASolver(myMoteSize, motes));
			}
			counter++;
		}
		List<Future<Integer>> futures = srv.invokeAll(solvers);
		srv.shutdown();
		for (int i = 1; i <= futures.size(); i++) {
			Integer min = futures.get(i - 1).get();
			result.add("Case #" + i + ": " + min);
		}

		Files.write(Paths.get(out), result, Charset.defaultCharset(), StandardOpenOption.TRUNCATE_EXISTING);
	}

	private static class ASolver implements Callable<Integer> {

		public int mySize;
		public int[] motes;

		private ASolver(int mySize, int[] motes) {
			this.mySize = mySize;
			this.motes = motes;
		}

		@Override
		public Integer call() throws Exception {
			int result = motes.length;

			int addSteps = 0;
			if (mySize > 1) {
				Arrays.sort(motes);
				for (int i = 0; i < motes.length; i++) {
					while (mySize <= motes[i]) {
						mySize += mySize - 1;
						addSteps++;
					}
					mySize += motes[i];
					if (result > addSteps + motes.length - (i + 1)) {
						result = addSteps + motes.length - (i + 1);
					}
				}
			}
			return result;
		}
	}
}
