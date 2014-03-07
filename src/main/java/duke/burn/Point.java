package duke.burn;

/**
 * Created by Dmitrenko on 05.03.14.
 */
public class Point {
	public int id;
	public double x;
	public double y;

	public Point(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point{" +
				"id=" + id +
				", x=" + x +
				", y=" + y +
				'}';
	}
}
