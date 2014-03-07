package duke.solver.tsp;

import duke.burn.Point;
import duke.solver.burn.Burn;
import duke.solver.burn.BurnSolver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dmitrenko on 06.03.14.
 */
public class SolverTimerProxy<Entity> implements Burn<Entity> {
	private Burn<Entity> impl;
	private long minutes;

	public SolverTimerProxy(Burn<Entity> impl, long minutes) {
		this.impl = impl;
		this.minutes = minutes;
	}

	@Override
	public double value() {
		return impl.value();
	}

	@Override
	public boolean isOptimal() {
		return impl.isOptimal();
	}


	@Override
	public Entity result() {
		return impl.result();
	}

	@Override
	public void solve(Entity arr) {
		ExecutorService srv = Executors.newFixedThreadPool(1);
		srv.submit(new AthreadSolver(arr));
		srv.shutdown();
		srv.shutdownNow();
		try {
			srv.awaitTermination(minutes, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			impl.stop();
			return;
		}
		impl.stop();
	}

	@Override
	public void stop() {
		impl.stop();
	}

	private class AthreadSolver implements Runnable {
		private Entity array;

		private AthreadSolver(Entity array) {
			this.array = array;
		}

		@Override
		public void run() {
			impl.solve(array);
		}
	}
}
