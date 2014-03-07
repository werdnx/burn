package test;

import duke.burn.Point;
import duke.burn.PointHolder;
import duke.burn.Solver;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dmitrenko on 05.03.14.
 */
public class BurnTspTest {

	public static void main(String[] args) throws InterruptedException, IOException, CloneNotSupportedException {
		PointHolder holder = Solver.solveForGraph(new String[] { "-file=D:\\Temp\\tsp\\tsp\\data\\tsp_51_1" });
		holder.w = 1500;
		holder.h = 900;
		Graph g = new Graph(holder);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setSize(holder.w + 50, holder.h + 50);
		g.setVisible(true);
	}

}
