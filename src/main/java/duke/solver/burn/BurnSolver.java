package duke.solver.burn;

import duke.burn.PointUtils;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Dmitrenko on 07.03.14.
 */
public abstract class BurnSolver<Entity> implements Burn<Entity> {
	private boolean debug;
	private double initialTemperature = 10;
	private double endTemperature = 0.000000000000001;
	private int randomSwapNumber = 3;
	private Random rnd = new Random();

	private double currentEnergy;
	private Entity currentState;

	private double globalBestEnergy;
	private Entity globalBest;
	private int step;

	private volatile boolean stop;

	public abstract boolean isOptimal();

	public abstract double stateEnergy(Entity state);

	public double value() {
		return globalBestEnergy;
	}

	public Entity result() {
		return globalBest;
	}

	@Override
	public void solve(Entity arr) {
		currentEnergy = stateEnergy(arr);
		globalBest = arr;
		globalBestEnergy = currentEnergy;
		currentState = arr;
		step = 1;
		double temperature = initialTemperature;
		while (temperature > endTemperature && !stop) {
			Entity candidateState = getNewStateCandidate(currentState);
			candidateState = localSearch(candidateState);
			double candidateEnergy = stateEnergy(candidateState);

			if (candidateEnergy < currentEnergy || makeTransit(getTransitionProbability(candidateEnergy - currentEnergy, temperature))) {
				currentEnergy = candidateEnergy;
				currentState = candidateState;
				if (debug)
					System.out.println("new path " + currentEnergy + " temperature = " + temperature);
			}
			if (candidateEnergy < globalBestEnergy) {
				globalBestEnergy = candidateEnergy;
				globalBest = candidateState;
			}
			temperature = decreaseTemperature(step);
			step++;
		}
		//test
		globalBest = localSearch(globalBest);
		globalBestEnergy = stateEnergy(globalBest);
		//test
	}

	@Override
	public void stop() {
		stop = true;
	}

	public abstract Entity localSearch(Entity arr);

	public abstract Entity getNewStateCandidate(Entity arr);

	protected double decreaseTemperature(int step) {
		return initialTemperature * 0.1 / step;
	}

	protected boolean makeTransit(double probability) {
		double nextDouble = rnd.nextDouble();
		return nextDouble <= probability;
	}

	protected double getTransitionProbability(double delta, double temperature) {
		return 1 / (1 + Math.exp(-delta / temperature));
	}

	public double getInitialTemperature() {
		return initialTemperature;
	}

	public void setInitialTemperature(double initialTemperature) {
		this.initialTemperature = initialTemperature;
	}

	public double getEndTemperature() {
		return endTemperature;
	}

	public void setEndTemperature(double endTemperature) {
		this.endTemperature = endTemperature;
	}

	public int getRandomSwapNumber() {
		return randomSwapNumber;
	}

	public void setRandomSwapNumber(int randomSwapNumber) {
		this.randomSwapNumber = randomSwapNumber;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public double getGlobalBestEnergy() {
		return globalBestEnergy;
	}

	public void setGlobalBestEnergy(double globalBestEnergy) {
		this.globalBestEnergy = globalBestEnergy;
	}

	public Entity getGlobalBest() {
		return globalBest;
	}

	public void setGlobalBest(Entity globalBest) {
		this.globalBest = globalBest;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
}
