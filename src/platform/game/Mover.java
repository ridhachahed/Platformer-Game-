package platform.game;

import platform.util.Box;
import platform.util.Sprite;
import platform.util.Vector;

import java.util.function.Function;

import platform.util.*;

public class Mover extends Block {
	private Vector on;
	private Vector off;
	private Signal signal;
	private double current;

	/**
	 * @param zone
	 * La box associee au mover
	 * @param sprite
	 * Le Sprite a charger pour faire afficher le mover
	 * @param signal
	 * Le signal indiquant le fait que le mover doive se deplacer
	 * @param on
	 * La position maximale jusqu'ou oscille le mover 
	 */
	public Mover(Box zone, Sprite sprite, Signal signal, Vector on) {
		super(zone, sprite);
		this.signal = signal;
		this.on = on;
		this.off = zone.getCenter();
		if (on == null || signal == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (signal.isActive()) {
			current += input.getDeltaTime();
			if (current > 1.0)
				current = 1.0;
		} else {
			current -= input.getDeltaTime();
			if (current < 0.0)
				current = 0.0;
		}
	}

	@Override
	public Box getBox() {
		Vector vectorInterpolation = off.mixed(on, current);
		return new Box(vectorInterpolation, super.getBox().getWidth(), super.getBox().getHeight());
	}

}
