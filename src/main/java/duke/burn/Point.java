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
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Point point = (Point) o;

		if (id != point.id) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return id;
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
