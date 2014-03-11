package test;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import duke.burn.Point;
import duke.burn.PointHolder;

import javax.swing.*;

/**
 * Created by Dmitrenko on 06.03.14.
 */
public class Graph extends JFrame {
	private static final long serialVersionUID = -2707712944901661771L;

	public Graph(PointHolder holder) {
		super("Tsp graph");
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		try {
			Object prev = null;
			Object first = null;
			for (int i = 0; i < holder.points.length; i++) {

				Object v = graph.insertVertex(parent, null, String.valueOf(holder.points[i].id), holder.points[i].x * (holder.w / holder.maxX), holder.points[i].y * (holder.h / holder.maxY), 15,
						15, "shape=ellipse;perimeter=ellipsePerimeter");
				if (i == 0) {
					first = v;
				}
				if (prev != null) {
					graph.insertEdge(parent, null, null, prev, v);
				}
				prev = v;
			}
			graph.insertEdge(parent, null, null, prev, first);
		} finally {
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

}
