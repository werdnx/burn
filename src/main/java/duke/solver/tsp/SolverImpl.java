package duke.solver.tsp;

import duke.burn.Point;
import duke.burn.PointUtils;
import duke.solver.burn.BurnSolver;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Dmitrenko on 05.03.14.
 */
public class SolverImpl extends BurnSolver<Point[]> {
	private Random rnd = new Random();

	@Override
	public boolean isOptimal() {
		return false;
	}

	@Override
	public double stateEnergy(Point[] state) {
		return PointUtils.routeSize(state);
	}

	protected Point[] localSearch(Point[] arr) {
		Point[] result = arr;
		for (int i = 1; i < result.length - 2; i++)
			for (int j = i + 1; j < result.length - 1; j++) {
				if ((PointUtils.path(result[i], result[i - 1]) + PointUtils.path(result[j + 1], result[j])) >
						(PointUtils.path(result[i], result[j + 1]) + PointUtils.path(result[i - 1], result[j])))
				{
					result = reverse(result, i, j);
				}
			}
		return result;
	}

	private Point[] reverse(Point[] arr, int from, int to) {
		if (from > to) {
			int tmp = to;
			to = from;
			from = tmp;
		}
		while (from < to) {
			Point temp = arr[from];
			arr[from++] = arr[to];
			arr[to--] = temp;
		}
		return arr;
	}

	public Point[] getNewStateCandidate(Point[] arr) {
		Point[] newState = Arrays.copyOf(arr, arr.length);
		int k = rnd.nextInt(getRandomSwapNumber());
		if (k < 1)
			k = 1;

		Set<Integer> generatedNums = new TreeSet<Integer>();
		for (int i = 1; i <= k; i++) {
			int nextLeftInt = getNextRandom(arr.length, generatedNums);
			generatedNums.add(nextLeftInt);
			int nextRightInt = getNextRandom(arr.length, generatedNums);
			generatedNums.add(nextRightInt);

			newState = reverse(newState, nextLeftInt, nextRightInt);
		}
		return newState;
	}

	private int getNextRandom(int size, Set<Integer> generated) {
		int nextInt = rnd.nextInt(size);
		while (generated.contains(nextInt)) {
			nextInt = rnd.nextInt(size);
		}
		return nextInt;
	}
}
