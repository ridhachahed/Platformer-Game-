package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.Output;
import platform.util.Loader;

public class Hadoken extends Actor {
	private Vector position;
	private Vector velocity;
	private final static double SIZE = 1;
	private Actor owner;
	private boolean disparition = false;
	private double chrono;
	private boolean gauche;

	/**
	 * @param position
	 * La position au depart du Hadoken
	 * @param velocity
	 * La vitesse initiale du Hadoken
	 * @param owner
	 * Le lanceur/proprietaire de Hadoken
	 */
	public Hadoken(Vector position, Vector velocity, Actor owner) {
		this.position = position;
		this.velocity = velocity;
		this.owner = owner;
		this.chrono = 5;
		this.gauche=false;
		if (position == null || velocity == null || owner == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		position = position.add(velocity.mul(delta));
		chrono -= input.getDeltaTime();
		if(input.getMouseLocation().getX()<owner.getPosition().getX()){
			gauche=true;
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite("Hadoken.png") == null || disparition) {
			super.draw(input, output);
		}
		if(!gauche){
		output.drawSprite(getSprite("Hadoken.png"), getBox());
		}else{
			output.drawSprite(getSprite("Hadoken.png"), getBox(),Math.PI);
		}
	}

	@Override
	public int getPriority() {
		return 666;
	}

	@Override
	public Box getBox() {
		// position est l'attribut position de l'objet
		// SIZE une constante choisie pour la taille , par exemple 0.4
		return new Box(position, SIZE, SIZE);
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other.getBox() != null) {
			if (other.getBox().isColliding(getBox()) && other != owner) {
				if (other.hurt(this, Damage.HADOKEN, 1.0, getPosition())) {
					// la boule Hadoken disparait une fois qu'elle a inflige un
					// dommage.
					getWorld().unregister(this);
				} else if (other instanceof Block && chrono < 4.8) {
					getWorld().unregister(this);
				}
			}
		}
	}
}