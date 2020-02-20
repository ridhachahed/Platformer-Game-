package platform.game;

import platform.util.*;

public class Lever extends Actor implements Signal {

	private Vector position;
	private boolean value;
	private double duration;
	private double time;
	private Signal s;
	private final static double SIZE = 1;

	/**
	 * @param position
	 * La position du levier
	 * @param value
	 * Si le levier est actif ou non
	 * @param duration
	 * Le temps en secondes d'activite du levier une fois a true
	 */
	public Lever(Vector position, boolean value, double duration) {
		this.position = position;
		this.value = value;
		this.duration = duration;
		time = duration;
		s = new Constante(true);

		if (position == null) {
			throw new NullPointerException();
		}
		if (duration < 0) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @param position
	 * La position du levier
	 * @param value
	 * Si le levier est actif ou non
	 * @param duration
	 * Le temps en secondes d'activite du levier une fois a true
	 
	 * @param s
	 * Le signal necessaire a l'apparition du levier
	 */
	public Lever(Vector position, boolean value, double duration, Signal s) {

		this.position = position;
		this.value = value;
		this.duration = duration;
		this.s = s;
		time = duration;
		if (position == null || s == null) {
			throw new NullPointerException();
		}
		if (duration < 0) {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public void update(Input input) {
		if (value) {
			time -= input.getDeltaTime();
			if (time < 0) {
				value = !value;
				time = duration;
			}
		}
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output);
		if (s.isActive()) {
			if (value) {
				output.drawSprite(getSprite("lever.left"), getBox());
			} else {
				output.drawSprite(getSprite("lever.right"), getBox());
			}
		}
	}

	@Override
	public int getPriority() {
		return 24;
	}

	@Override
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case ACTIVATION:
			if (amount > 0) {
				value = !value;
				return true;
			}
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}

	@Override
	public boolean isActive() {
		return value;
	}

}
