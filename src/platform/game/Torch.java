package platform.game;

import java.awt.event.KeyEvent;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

public class Torch extends Actor implements Signal {
	private Box box;
	private boolean lit;
	private double variation;
	private final static double SIZE = 0.8;

	
	/**
	 * @param centre
	 * La position du centre de la torche
	 * @param lit
	 * True si la torche est allumee, False si la torche est eteinte
	 */
	public Torch(Vector centre, boolean lit) {
		if(centre==null){
			throw new NullPointerException();
		}
		this.box = new Box(centre, SIZE, SIZE);
		this.lit = lit;
		variation = 0;
		
	}

	@Override
	public void update(Input input) {
		variation -= input.getDeltaTime();
		if (variation < 0.0) {
			variation = 0.6;
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite("torch.lit.1") == null || getSprite("torch") == null || getSprite("torch.lit.2") == null) {
			super.draw(input, output);
		} else {
			if (lit) {
				String name = "torch.lit.1";
				if (variation < 0.3) {
					name = "torch.lit.2";
				}
				output.drawSprite(getSprite(name), getBox());
			} else {
				output.drawSprite(getSprite("torch"), getBox());
			}
		}
	}

	@Override
	public int getPriority() {
		return 34;
	}

	@Override
	public Box getBox() {
		return box;
	}

	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case AIR:
			lit = false;
			return true;
		case FIRE:
			lit = true;
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}

	@Override
	public boolean isActive() {
		return lit;
	}
}
