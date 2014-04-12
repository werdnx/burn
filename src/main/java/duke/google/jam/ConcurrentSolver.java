package duke.google.jam;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Dmitrenko on 19.03.14.
 */
public abstract class ConcurrentSolver<Ctx, Result> {
	private int threads;

	public ConcurrentSolver(int threads) {
		this.threads = threads;
	}

	public void solve(String in, String out) throws IOException, InterruptedException, ExecutionException {
		List<String> result = new LinkedList<String>();
		List<String> lines = Files.readAllLines(Paths.get(in), Charset.defaultCharset());

		ExecutorService srv = Executors.newFixedThreadPool(threads);

		int size = Integer.valueOf(lines.get(0));
		int counter = 0;
		int myMoteSize = 0;
		int motes[] = null;

		List<ASolver> solvers = new ArrayList<ASolver>(size);
		processLines(lines.subList(1, lines.size()), solvers);

		List<Future<Result>> futures = srv.invokeAll(solvers);
		srv.shutdown();
		for (int i = 1; i <= futures.size(); i++) {
			Result res = futures.get(i - 1).get();
			result.add("Case #" + i + ": " + res);
		}

		Files.write(Paths.get(out), result, Charset.defaultCharset(), StandardOpenOption.TRUNCATE_EXISTING);
	}

	public void processLines(List<String> lines, List<ASolver> solvers) {
		for (String s : lines) {
			solvers.add(new ASolver(processLine(s)));
		}
	}

	protected abstract Ctx processLine(String s);

	private class ASolver implements Callable<Result> {
		private Ctx ctx;

		private ASolver(Ctx ctx) {
			this.ctx = ctx;
		}

		@Override
		public Result call() throws Exception {
			return solveItem(ctx);
		}


	}

	public abstract Result solveItem(Ctx ctx);
}
