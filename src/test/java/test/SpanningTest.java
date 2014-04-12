package test;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import duke.burn.Point;
import duke.burn.PointHolder;
import duke.burn.Solver;
import duke.solver.burn.GraphSolver;

import javax.swing.*;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dmitrenko on 14.03.14.
 */
public class SpanningTest {
	public static void main(String[] args) {
		GraphSolver solver = new GraphSolver();
		try {
			PointHolder holder = Solver.solveForGraph(new String[] { "-file=D:\\Temp\\tsp\\tsp\\data\\tsp_51_1" }, solver, 2, 3);
			holder.w = 450;
			holder.h = 450;
			TreeGraph g = new TreeGraph(solver.getSpanningMap(), holder);
			g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			g.setSize(holder.w + 50, holder.h + 50);
			g.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class TreeGraph extends JFrame {
		private static final long serialVersionUID = -2707712944901661771L;

		public TreeGraph(Map<Point, Set<Point>> map, PointHolder holder) {

			super("Tsp graph");
			mxGraph graph = new mxGraph();
			Object parent = graph.getDefaultParent();
			graph.getModel().beginUpdate();
			try {
				Object prev = null;
				Object first = null;
				for (Map.Entry<Point, Set<Point>> entry : map.entrySet()) {
					Object from = graph.insertVertex(parent, null, String.valueOf(entry.getKey().id), entry.getKey().x * (holder.w / holder.maxX), entry.getKey().y * (holder.h / holder.maxY), 15,
							15, "shape=ellipse;perimeter=ellipsePerimeter");
					for (Point point : entry.getValue()) {
						Object to = graph.insertVertex(parent, null, String.valueOf(point.id), point.x * (holder.w / holder.maxX), point.y * (holder.h / holder.maxY), 15,
								15, "shape=ellipse;perimeter=ellipsePerimeter");
						graph.insertEdge(parent, null, null, from, to);
					}

				}
			} finally

			{
				graph.getModel().endUpdate();
			}

			mxGraphComponent graphComponent = new mxGraphComponent(graph);

			getContentPane().add(graphComponent);
		}
	}
}
