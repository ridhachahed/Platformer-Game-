package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.Output;
import platform.util.Loader;

public class Jumper extends Actor {
	private Vector position;
	private double cooldown;

	/**
	 * @param position
	 * La position du Jumper
	 */
	public Jumper(Vector position) {
		this.position = position;
		this.cooldown = 0;
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
		if (getSprite("jumper.extended") == null || getSprite("jumper.normal") == null) {
			super.draw(input, output);
		} else {
			if (cooldown > 0) {
				output.drawSprite(getSprite("jumper.extended"), getBox());
			} else {
				output.drawSprite(getSprite("jumper.normal"), getBox());
			}
		}
	}

	@Override
	public int getPriority() {
		return 43;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Box getBox() {
		return new Box(new Vector(position.getX(), position.getY()),
				new Vector(position.getX() + 0.5, position.getY() + 1));
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other instanceof Player && cooldown <= 0 && getBox().isColliding(other.getBox())) {
			Vector below = new Vector(position.getX(), position.getY() - 1.0);
			other.hurt(this, Damage.AIR, 10.0, below);
			cooldown = 0.5;
		}
	}

}
