package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

public class Overlay extends Actor {
	private Player player;

	/**
	 * @param joueur
	 * Le joueur par rapport auquel le Overlay affiche les points de vie
	 */
	public Overlay(Player joueur) {
		this.player=joueur;
		if(player==null){
			throw new NullPointerException();
		}
	}

	@Override
	public void draw(Input input, Output output) {

		double health = 5.0 * player.getHealth() / player.getHealthMax();
		for (int i = 1; i <= 5; ++i) {
			String name;
			if (health >= i)
				name = "heart.full";
			else if (health >= i - 0.5)
				name = "heart.half";
			else
				name = "heart.empty";
	
			if (getSprite(name) == null) {
				super.draw(input, output);
			}
			// trouver le Sprite associe a name
			// dessiner ce Sprite au desssus de Player.
			
			Box translation= (player.getBox().add(new Vector(0, 0.5)) );
			Vector vectMin=translation.getCenter().add(new Vector((0.3*(-3.5+i)),0));
			Vector vectMax=(vectMin.add(new Vector(0.3,0.3)));
            		
			output.drawSprite(getSprite(name), new Box (vectMin,vectMax));
		}
	}
	
	@Override
	public int getPriority() {
		return 70;
	}
	@Override
	public Box getBox(){
		return player.getBox().add(new Vector(0, 0.5)) ;
	}
	
	@Override
	public void unregister() {
		super.unregister();
		if(player.getWorld()==null){
			unregister();
		}
	}
	
	

}
