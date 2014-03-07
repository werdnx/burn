package duke.burn;

/**
 * Created by Dmitrenko on 05.03.14.
 */
public class PointUtils {

	public static double path(Point p1, Point p2) {
		return Math.sqrt((Math.pow(p1.x - p2.x, 2) + (Math.pow(p1.y - p2.y, 2))));
	}

	public static double routeSize(Point[] arr) {
		double result = 0.0;
		Point current = arr[0];
		for (int i = 1; i < arr.length; i++, current = arr[i - 1]) {
			result += path(current, arr[i]);
		}
		result += path(arr[arr.length - 1], arr[0]);
		return result;
	}


}
