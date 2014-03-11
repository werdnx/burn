package duke.solver.tsp;

import duke.burn.Point;
import duke.burn.PointUtils;
import duke.solver.burn.BurnSolver;

import java.util.*;

/**
 * Created by Dmitrenko on 05.03.14.
 */
public class SolverImpl extends BurnSolver<Point[]> {
	private Random rnd = new Random();
	private Set<Integer> idSet = new TreeSet<Integer>();

	public SolverImpl() {
	}

	@Override
	public void solve(Point[] arr) {
		super.solve(arr);
	}

	@Override
	public boolean isOptimal() {
		return false;
	}

	@Override
	public double stateEnergy(Point[] state) {
		return PointUtils.routeSize(state);
	}

	public Point[] localSearch(Point[] arr) {
		Point[] result = arr;
		boolean better = true;

		while (better) {
			better = false;
			for (int i = 1; i < result.length; i++)
				for (int j = i + 1; j < result.length; j++) {
					Point prevPoint = getPrevPoint(result, i);
					Point nextPoint = getNextPoint(result, j);

					if ((PointUtils.path(result[i], prevPoint) + PointUtils.path(nextPoint, result[j])) >
							(PointUtils.path(result[i], nextPoint) + PointUtils.path(prevPoint, result[j])))
					{
						result = reverse(result, i, j);
						better = true;
					} else {
						Point prev = getPrevPoint(result, i);
						Point next = getNextPoint(result, i);

						Point nearest1 = result[j];
						Point nearest2 = getNextPoint(result, j);

						if (isAllDifferent(prev, next, nearest1, nearest2)) {
							if ((path(prev, result[i]) + path(result[i], next) + path(nearest1, nearest2))
									> (path(nearest1, result[i]) + path(result[i], nearest2) + path(prev, next)))
							{
								moveUntil(result, i, nearest2);
								better = true;
							}
						}
					}
				}
		}
		//result = doNearestImprovement(result);
		return result;
	}

	//3 opt ?
	private Point[] doNearestImprovement(Point[] result) {
		boolean better = true;
		while (better) {
			better = false;
			for (int i = 0; i < result.length; i++) {
				for (int j = i + 1; j < result.length; j++) {
					if (!result[i].equals(result[j])) {
						Point prev = getPrevPoint(result, i);
						Point next = getNextPoint(result, i);

						Point nearest1 = result[j];
						Point nearest2 = getNextPoint(result, j);

						if (isAllDifferent(prev, next, nearest1, nearest2)) {
							if ((path(prev, result[i]) + path(result[i], next) + path(nearest1, nearest2))
									> (path(nearest1, result[i]) + path(result[i], nearest2) + path(prev, next)))
							{
								moveUntil(result, i, nearest2);
								better = true;
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * move element with index from bofore element with index until
	 *
	 * @param from
	 * @param until
	 */
	private void moveUntil(Point[] arr, int from, Point until) {
		int i = from;
		Point cur = arr[i];
		while (!cur.equals(until)) {
			exchange(arr, i, i + 1);
			i = getNextCount(arr, i);
			cur = arr[getNextCount(arr, i)];
		}
	}

	private void exchange(Point[] arr, int i, int j) {
		if (j >= arr.length) {
			j = 0;
		}
		Point temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	private int getNextCount(Point[] result, int i) {
		if (i < result.length - 1)
			return i + 1;
		else
			return 0;
	}

	private double path(Point p1, Point p2) {
		return PointUtils.path(p1, p2);
	}

	private boolean isAllDifferent(Point... points) {
		for (Point point : points) {
			if (idSet.contains(point.id)) {
				idSet.clear();
				return false;
			} else {
				idSet.add(point.id);
			}
		}

		idSet.clear();
		return true;
	}

	private Point getNextPoint(Point[] result, int i) {
		if (i < result.length - 1)
			return result[i + 1];
		else
			return result[0];
	}

	private Point getPrevPoint(Point[] result, int i) {
		if (i > 0) {
			return result[i - 1];
		} else
			return result[result.length - 1];
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
