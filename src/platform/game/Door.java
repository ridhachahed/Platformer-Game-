package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;

public class Door extends Block implements Signal {
	private Signal signal;

	/**
	 * @param zone
	 * La zone occupee par la Door
	 * @param sprite
	 * Le sprite de la Door
	 * @param signal
	 * Le signal qui permet d ouvrir la door
	 */
	public Door(Box zone, Sprite sprite, Signal signal) {
		super(zone, sprite);
		this.signal = signal;
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite() == null) {
			super.draw(input, output);
		} else {
			if (!signal.isActive()) {
				output.drawSprite(getSprite(), getBox());
			}
		}
	}

	@Override
	public boolean isSolid() {
		if (signal.isActive()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Box getBox() {
		if (signal.isActive()) {
			return null;
		} else {
			return super.getBox();
		}
	}

	@Override
	public boolean isActive() {
		if (signal.isActive()) {
			return true;
		} else {
			return false;
		}
	}

}