
package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

public class Spike extends Actor {
	private Vector position;
	private final static double SIZE = 0.5;

	/**
	 * @param position
	 * La position du Spike
	 */
	public Spike(Vector position) {
		this.position = position;
		if (position == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public void update(Input input) {
		super.update(input);
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite("spikes") == null) {
			super.draw(input, output);
		} else {
			output.drawSprite(getSprite("spikes"), getBox());
		}
	}

	@Override
	public int getPriority() {
		return 45;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if ((other.isSolid())) {
			if (getBox().isColliding(other.getBox()) && other instanceof Player
					&& ((Player) other).getVelocity().getY() <= 0
					&& other.getBox().getMin().getY() > this.getPosition().getY()) {
				Vector below = new Vector(position.getX(), position.getY());
				other.hurt(this, Damage.AIR, 4, below);
				other.hurt(this, Damage.PHYSICAL, 10, other.getPosition());

			} else {
				other.interact(this);
			}
		}
	}

}
