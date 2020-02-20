package platform.game;

import platform.game.Actor.Damage;
import platform.util.*;

public class Blockyspike extends Spike {
	private Signal s;
	private Sprite sprite;

	/**
	 * @param position
	 * Le vecteur position du Blockyspike
	 * @param s
	 * Le signal qui permet permet de transformer le Spike en Block
	 */
	public Blockyspike(Vector position, Signal s) {
		super(position);
		this.s = s;
		if (position == null || s == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (!s.isActive()) {
			if (getSprite("spikes") == null) {
				super.draw(input, output);
			} else {
				output.drawSprite(getSprite("spikes"), getBox());
			}
		} else {
			output.drawSprite(getSprite("stone.broken.4"), getBox());
		}
	}

	@Override
	public void interact(Actor other) {
		if (!s.isActive()) {
			super.interact(other);
		} else {
			if ((other.isSolid())) {
				if (getBox().isColliding(other.getBox())) {
					other.interact(this);
				}
			}
		}
	}
}