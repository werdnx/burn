package duke.solver.burn;

import duke.burn.Point;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dmitrenko on 07.03.14.
 */
public interface Burn<State> {

	double value();

	public boolean isOptimal();

	public State result();

	void solve(State state);

	public void stop();
}
