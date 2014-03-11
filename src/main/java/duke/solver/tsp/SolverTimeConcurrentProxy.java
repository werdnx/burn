package duke.solver.tsp;

import duke.burn.Point;
import duke.solver.burn.Burn;
import duke.solver.burn.BurnSolver;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dmitrenko on 06.03.14.
 */
public class SolverTimeConcurrentProxy<Entity> implements Burn<Entity> {
	private List<Burn<Entity>> poolImpl;
	private List<AthreadSolver> proxySolverList;
	private long minutes;
	private Entity best;
	private double bestValue = Double.MAX_VALUE;

	public SolverTimeConcurrentProxy(List<Burn<Entity>> poolImpl, long minutes) {
		this.poolImpl = poolImpl;
		this.minutes = minutes;
	}

	@Override
	public double value() {
		return bestValue;
	}

	@Override
	public boolean isOptimal() {
		return false;
	}


	@Override
	public Entity result() {
		return best;
	}

	@Override
	public void solve(Entity arr) {
		proxySolverList = new LinkedList<AthreadSolver>();
		ExecutorService srv = Executors.newCachedThreadPool();
		for (Burn<Entity> item : poolImpl) {
			AthreadSolver athreadSolver = new AthreadSolver(item, arr);
			proxySolverList.add(athreadSolver);
			srv.submit(athreadSolver);
		}
		srv.shutdown();
		srv.shutdownNow();
		try {
			srv.awaitTermination(minutes, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			stopAsync();
			return;
		}
		stopAsync();
		findBest();
	}

	private void findBest() {
		for (AthreadSolver athreadSolver : proxySolverList) {
			if (athreadSolver.value() < bestValue) {
				bestValue = athreadSolver.value();
				best = athreadSolver.result();
			}
		}
	}

	private void stopAsync() {
		for (AthreadSolver athreadSolver : proxySolverList) {
			athreadSolver.stop();
		}
	}

	@Override
	public void stop() {
		stopAsync();
	}

	private class AthreadSolver implements Runnable {
		private Burn<Entity> solver;
		private Entity array;

		private AthreadSolver(Burn<Entity> solver, Entity array) {
			this.solver = solver;
			this.array = array;
		}

		@Override
		public void run() {
			try {
				solver.solve(array);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public double value() {
			return solver.value();
		}

		public boolean isOptimal() {
			return solver.isOptimal();
		}

		public Entity result() {
			return solver.result();
		}

		public void solve(Entity entity) {
			solver.solve(entity);
		}

		public void stop() {
			solver.stop();
		}
	}
}
