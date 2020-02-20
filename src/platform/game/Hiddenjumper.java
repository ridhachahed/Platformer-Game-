package platform.game;

import platform.game.Actor.Damage;
import platform.game.Jumper;
import platform.game.Signal;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

public class Hiddenjumper extends Jumper implements Signal {
	private Signal s = new Constante(true);
	private double cooldown;

	private Sprite sprite;

	
	/**
	 * @param position
	 * La position du Jumper cache
	 * @param s
	 * Le signal necessaire pour l'apparition du Hiddenjumper
	 */
	public Hiddenjumper(Vector position, Signal s) {
		super(position);
		if (s==null){
			throw new NullPointerException();
		}
		this.s = s;
		
	}
	
	@Override
	public void update(Input input) {
		super.update(input);
		cooldown -= input.getDeltaTime();
	}
	
	@Override
	public void draw(Input input, Output output) {

		if (s.isActive()) {

			if (cooldown > 0) {
				output.drawSprite(getSprite("jumper.extended"), getBox());
			} else {
				output.drawSprite(getSprite("jumper.normal"), getBox());
			}
		}
	}

	@Override
	public void interact(Actor other) {

		if (s.isActive()) {
			if (cooldown <= 0 && getBox().isColliding(other.getBox())) {
				Vector below = new Vector(getPosition().getX(), getPosition().getY() - 1.0);
				if (other.hurt(this, Damage.AIR, 10.0, below))
					cooldown = 0.5;
			}
		}
	}

	@Override
	public boolean isActive() {
		return s.isActive();
	}

}
