package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

public class Heart extends Actor {
	private Vector position;
	private double cooldown;
	private final static double SIZE = 0.5;

	
	/**
	 * @param position
	 * La position  du coeur
	 */
	public Heart(Vector position) {
		this.position = position;
		cooldown = 0;
		if (position == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public void update(Input input) {
		super.update(input);
		cooldown -= input.getDeltaTime();
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite("heart.full") == null) {
			super.draw(input, output);
		} else {
			if (cooldown > 0) {
				super.draw(input, output);
			} else {
				output.drawSprite(getSprite("heart.full"), getBox());
			}
		}
	}

	@Override
	public int getPriority() {
		return 45;
	}

	@Override
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other instanceof Player && getBox().isColliding(other.getBox()) && cooldown < 0) {
			other.hurt(this, Damage.HEAL, 10, other.getPosition());
			cooldown = 10;
		}
	}

}
