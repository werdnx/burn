package duke.solver.burn;

import duke.burn.Point;
import duke.burn.PointUtils;

import java.util.*;

/**
 * Created by Dmitrenko on 13.03.14.
 * min spanning tree
 */
public class GraphSolver implements Burn<Point[]> {
	//private Map<Point, PriorityQueue<Point>> pointsMap;
	private Map<Point, PriorityQueue<Point>> pointsMap;
	private Map<Point, Set<Point>> spanningMap;

	private Point[] result;
	private double routSize;

	@Override
	public double value() {
		return routSize;
	}

	@Override
	public boolean isOptimal() {
		return false;
	}

	@Override
	public Point[] result() {
		return result;
	}

	@Override
	public void solve(Point[] points) {
		pointsMap = new HashMap<Point, PriorityQueue<Point>>(points.length);
		spanningMap = new HashMap<Point, Set<Point>>(points.length);
		spanningMap.put(points[0], new HashSet<Point>());
		prepareMap(points);
		calculateMinSpanningTree();
		buildOptimalRoute();
	}

	private void buildOptimalRoute() {
		Set<Point> visited = new TreeSet<Point>(new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return o1.id - o2.id;
			}
		});
		int i = 0;
		result = new Point[spanningMap.size()];
		for (Map.Entry<Point, Set<Point>> entry : spanningMap.entrySet()) {

		}


	}

	private void calculateMinSpanningTree() {
		while (spanningMap.size() != pointsMap.size()) {
			TreeSet<Pair> routePairTree = new TreeSet<Pair>(new Comparator<Pair>() {
				@Override
				public int compare(Pair o1, Pair o2) {
					return Double.valueOf(PointUtils.path(o1.from, o1.to)).intValue() - Double.valueOf(PointUtils.path(o2.from, o2.to)).intValue();
				}
			});
			for (Map.Entry<Point, Set<Point>> entry : spanningMap.entrySet()) {
				Pair pair = new Pair();
				pair.from = entry.getKey();
				pair.to = getNearestPointExcept(entry.getKey(), spanningMap.keySet());
				if (pair.to != null)
					routePairTree.add(pair);
			}
			Pair minPair = routePairTree.first();

			Set<Point> pointSet = spanningMap.get(minPair.from);
			pointSet.add(minPair.to);
			spanningMap.put(minPair.to, new HashSet<Point>());
		}
	}

	private Point getNearestPointExcept(Point target, Set<Point> except) {
		PriorityQueue<Point> queue = pointsMap.get(target);
		Point result = queue.poll();
		while (except.contains(result)) {
			result = queue.poll();
		}
		queue.add(result);
		return result;
	}

	private void prepareMap(Point[] points) {
		for (final Point point : points) {
			PriorityQueue<Point> pointSet = new PriorityQueue<Point>(points.length - 1, new Comparator<Point>() {
				@Override
				public int compare(Point o1, Point o2) {
					return Double.valueOf(PointUtils.path(point, o1)).intValue() - Double.valueOf(PointUtils.path(point, o2)).intValue();
				}
			});
			for (Point point1 : points) {
				if (!point.equals(point1)) {
					pointSet.add(point1);
				}
			}
			pointsMap.put(point, pointSet);
		}
	}

	@Override
	public void stop() {

	}

	public static class Pair {
		public Point from;
		public Point to;
	}

	public Map<Point, Set<Point>> getSpanningMap() {
		return spanningMap;
	}

	public void setSpanningMap(Map<Point, Set<Point>> spanningMap) {
		this.spanningMap = spanningMap;
	}
}
