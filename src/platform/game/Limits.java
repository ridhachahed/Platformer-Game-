package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Vector;

public class Limits extends Actor {
	private Box box;

	/**
	 * @param box
	 * La taille des limites du stage
	 */
	public Limits(Box box) {
		this.box = box;
	}
	
	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (!(box.isColliding(other.getBox()))) {
				other.hurt(this, Damage.VOID, Double.POSITIVE_INFINITY, Vector.ZERO);
			}
	}

	@Override
	public int getPriority() {
		return 1000;
	}

}
