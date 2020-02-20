package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Sprite;
import platform.util.Vector;

public class BreakableBlock extends Block {
private double health;

	/**
	 * @param zone
	 * La zone occupee par le BreakableBlock
	 * @param sprite
	 * Le sprite du BreakableBlock
	 * @param health
	 * Le dommage necessaire pour casser le BreakableBlock
	 */
	public BreakableBlock(Box zone, Sprite sprite,double health) {
		super(zone, sprite);
		this.health=health;
		if (health<0){
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case FIRE:
			health=health-amount;
			if (health<=0)
				endLife();
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}
	
	public void endLife(){
		getWorld().unregister(this);
	}
	
	
}
