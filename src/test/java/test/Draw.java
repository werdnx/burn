package test;

import duke.burn.Point;
import duke.burn.PointHolder;
import duke.burn.PointUtils;
import duke.burn.Solver;
import duke.solver.burn.Burn;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitrenko on 07.03.14.
 */
public class Draw {
	public static void main(String[] args) throws InterruptedException, IOException, CloneNotSupportedException {
		String fileName = "D:\\Temp\\tsp\\tsp\\data\\tsp_200_2";
		//String output = "26 29 14 80 96 16 4 91 69 13 28 62 64 76 34 50 2 89 61 98 67 78 95 73 81 10 75 56 31 27 58 86 65 0 12 93 15 97 33 60 1 36 45 46 30 94 82 49 23 85 6 63 59 41 68 48 42 53 9 18 52 22 8 90 38 70 72 19 25 40 43 44 99 11 32 21 35 54 92 5 20 87 88 77 37 47 7 83 39 74 66 57 71 24 55 3 51 84 17 79";
		//String optimal2 = "26 29 14 80 96 16 4 91 69 13 28 62 64 76 34 50 2 89 61 98 67 78 95 73 81 10 75 56 31 27 58 86 65 0 12 93 15 97 33 60 1 36 45 46 30 94 82 49 23 6 85 63 59 41 68 48 42 53 9 18 52 22 8 90 38 70 72 19 25 40 43 44 99 11 32 21 35 54 92 5 20 87 88 77 37 47 7 83 39 74 66 57 71 24 55 3 51 84 17 79";
		String output ="116 91 140 47 143 29 1 99 118 50 191 18 135 144 195 36 34 61 117 123 52 59 43 92 121 122 187 70 3 154 42 76 62 153 53 90 38 72 14 171 136 83 40 146 78 180 12 178 87 164 23 26 179 119 137 51 7 65 37 185 148 33 80 129 174 49 0 155 199 125 168 161 31 104 96 166 93 16 89 139 138 97 169 48 69 152 88 10 167 109 22 41 172 184 21 192 110 102 57 127 28 190 196 198 107 128 35 175 158 74 66 131 6 170 60 111 73 197 194 100 189 120 45 145 124 108 126 160 134 71 56 30 98 77 193 79 75 156 133 68 106 183 157 151 15 95 85 159 64 173 44 165 32 67 13 186 188 142 63 149 58 84 17 5 39 82 2 176 4 115 101 8 9 141 19 24 113 163 81 182 103 105 114 11 27 46 132 181 20 55 150 94 147 130 162 25 86 112 54 177";
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

		Point[] res = new Point[arr.length];
		int i = 0;
		for (String id : output.split(" ")) {
			res[i++] = findById(arr, Integer.valueOf(id));
		}
				/*SolverImpl s = new SolverImpl();
		res = s.localSearch(res);*/
		PointHolder h = new PointHolder();
		h.points = res;
		h.maxX = maxX;
		h.maxY = maxY;
		h.minX = minX;
		h.minY = minY;

		h.w = 1500;
		h.h = 900;
		Graph g = new Graph(h);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setSize(h.w + 50, h.h + 50);
		g.setVisible(true);
		System.out.println(PointUtils.routeSize(res));
	}

	public static Point findById(Point[] arr, int id) {
		for (Point point : arr) {
			if (point.id == id) {
				return point;
			}
		}
		return null;
	}

}
