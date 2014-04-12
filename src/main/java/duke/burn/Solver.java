package duke.burn;

import duke.solver.burn.Burn;
import duke.solver.burn.BurnSolver;
import duke.solver.tsp.SolverImpl;
import duke.solver.tsp.SolverTimeConcurrentProxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solver {
	/**
	 * The main class
	 *
	 * @throws InterruptedException
	 * @throws CloneNotSupportedException
	 */
	public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
		try {
			solve(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read the instance, solve it, and print the solution in the standard
	 * output
	 *
	 * @throws InterruptedException
	 * @throws CloneNotSupportedException
	 */
	public static PointHolder solveForGraph(String[] args, Burn<Point[]> customSolver, long min, int threads) throws IOException, InterruptedException, CloneNotSupportedException {
		String fileName = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}
		}
		if (fileName == null)
			return null;

		// read the lines out of the file
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(fileName));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			input.close();
		}

		// parse the data in the file
		String[] firstLine = lines.get(0).split("\\s+");
		int points = Integer.parseInt(firstLine[0]);

		Point[] arr = new Point[points];

		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;

		double minY = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;

		for (int i = 1; i < points + 1; i++) {
			String line = lines.get(i);
			String[] parts = line.split("\\s+");

			double x = Double.parseDouble(parts[0]);
			double y = Double.parseDouble(parts[1]);

			if (x < minX) {
				minX = x;
			}
			if (x > maxX) {
				maxX = x;
			}
			if (y < minY) {
				minY = y;
			}
			if (y > maxY) {
				maxY = y;
			}

			Point point = new Point(i - 1, x, y);
			arr[i - 1] = point;

		}
		Burn<Point[]> solver = proxySolve(arr, customSolver, min, threads);

		System.out.println(solver.value() + " " + (solver.isOptimal() ? 1 : 0));
		for (int i = 0; i < points; i++) {
			System.out.print(solver.result()[i].id + " ");
		}
		System.out.println("");
		PointHolder h = new PointHolder();
		h.points = solver.result();
		h.maxX = maxX;
		h.maxY = maxY;
		h.minX = minX;
		h.minY = minY;
		return h;
	}

	public static void solve(String[] args) throws IOException, InterruptedException, CloneNotSupportedException {
		String fileName = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}
		}
		if (fileName == null)
			return;

		// read the lines out of the file
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(fileName));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			input.close();
		}

		// parse the data in the file
		String[] firstLine = lines.get(0).split("\\s+");
		int points = Integer.parseInt(firstLine[0]);

		Point[] arr = new Point[points];

		for (int i = 1; i < points + 1; i++) {
			String line = lines.get(i);
			String[] parts = line.split("\\s+");

			double x = Double.parseDouble(parts[0]);
			double y = Double.parseDouble(parts[1]);

			Point point = new Point(i - 1, x, y);
			arr[i - 1] = point;

		}
		Burn<Point[]> solver = proxySolve(arr);
		System.out.println(solver.value() + " " + (solver.isOptimal() ? 1 : 0));
		for (int i = 0; i < points; i++) {
			System.out.print(solver.result()[i].id + " ");
		}
		System.out.println("");
	}

	private static Burn<Point[]> proxySolve(Point[] arr) {
		return proxySolve(arr, null, 250, 3);
	}

	private static Burn<Point[]> proxySolve(Point[] arr, Burn<Point[]> customSolver, long min, int threads) {
		List<Burn<Point[]>> pool = new LinkedList<Burn<Point[]>>();
		for (int i = 0; i < threads; i++) {
			pool.add(new SolverImpl());
		}
		if (customSolver == null)
			customSolver = new SolverTimeConcurrentProxy(pool, min);

		customSolver.solve(arr);
		return customSolver;
	}

}
