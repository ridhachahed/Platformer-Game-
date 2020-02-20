package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.Output;
import platform.util.Loader;

public class Fireball extends Actor {
	private Vector position;
	private Vector velocity;
	private final static double SIZE = 0.4;
	private Actor owner;
	private double chrono;

	
	/**
	 * @param position
	 * La position de la boule de feu au depart
	 * @param velocity
	 * La vitesse initiale de la boule de feu
	 * @param owner
	 * Le proprietaire/lanceur de la boule de feu
	 */
	public Fireball(Vector position, Vector velocity, Actor owner) {
		this.position = position;
		this.velocity = velocity;
		this.owner = owner;
		this.chrono = 5;
		if (position == null || velocity == null) {
			throw new NullPointerException();
		}
	}

	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		velocity = velocity.add((getWorld().getGravity()).mul(delta));
		position = position.add(velocity.mul(delta));
		chrono -= input.getDeltaTime();

	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite("fireball") == null) {
			super.draw(input, output);
		}
		output.drawSprite(getSprite("fireball"), getBox(), input.getTime() * 30);
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
				if (other.hurt(this, Damage.FIRE, 5.0, getPosition())) {
					// la boule feu disparait une fois qu'elle a inflige un
					// dommage.
					getWorld().unregister(this);
				} else if (other.isSolid()) {
					Vector delta = other.getBox().getCollision(position);
					if (delta != null) {
						position = position.add(delta);
						velocity = velocity.mirrored(delta);
					}
				}
			} else if (chrono < 0) {
				getWorld().unregister(this);
			}
		}
	}
}
