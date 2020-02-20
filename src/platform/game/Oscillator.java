package platform.game;

import platform.util.Input;

public class Oscillator extends Actor implements Signal {
	private double variation;
	private double intervalle;

	/**
	 * @param intervalle
	 * Intervalle de temps entre deux oscillations
	 */
	public Oscillator(double intervalle) {
		if (intervalle < 0) {
			throw new IllegalArgumentException();
		}
		this.intervalle = intervalle;
		this.variation = intervalle;

	}

	@Override
	public void update(Input input) {
		variation -= input.getDeltaTime();
		if (variation < -intervalle) {
			variation = intervalle;
		}
	}

	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public boolean isActive() {
		if (variation > 0) {
			return true;
		} else {
			return false;
		}
	}

	
}
