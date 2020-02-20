package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;
import platform.game.Player;

public class Key extends Actor implements Signal {
	private Vector position;
	private boolean taken;
	private String name;
	private Signal signal;
	private final static double SIZE=0.5;

	/**
	 * @param position
	 * La position de la clef
	 * @param name
	 * Le nom du Sprite de la clef
	 */
	public Key(Vector position, String name) {
		this.position = position;
		taken = false;
		this.name = name;
		this.signal = new Constante(true);

		if (position == null || name == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * @param position
	 * La position de la clef
	 * @param name
	 * Le nom du Sprite de la clef
	 * @param signal
	 * Le signal necessaire a l'apparition de la clef
	 */
	public Key(Vector position, String name, Signal signal) {
		this.position = position;
		taken = false;
		this.name = name;
		this.signal = signal;
		if (position == null || name == null || signal == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite(name) == null) {
			super.draw(input, output);
		} else {
			if (signal.isActive()) {
				if (!taken) {
					output.drawSprite(getSprite(name), getBox(), input.getTime());
				} else {
					super.draw(input, output);
				}
			}
		}
	}

	@Override
	public int getPriority() {
		return 70;
	}

	@Override
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}
	
	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (signal.isActive() && !taken && other instanceof Player && (other.getBox().isColliding(getBox()))) {
			taken = true;
			getWorld().unregister(this);
		}

	}

	

	

	@Override
	public boolean isActive() {
		if (taken) {
			return true;
		} else {
			return false;
		}
	}

}
