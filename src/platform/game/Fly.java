package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author Walid
 *
 */
public class Fly extends Actor implements Signal {
	private Vector position;
	private Vector on;
	private Vector off;
	private double current;
	private boolean mouvement; // aller: true retour:false
	private Vector projectile;
	private boolean fight;
	private double chrono;
	private double health;
	private Signal s = new Constante(true);

	/**
	 * @param position
	 * La position de depart de la mouche
	 * @param on
	 * La position maximale jusqu'ou oscille la mouche
	 */
	public Fly(Vector position, Vector on) {
		this.position = position;
		this.on = on;
		this.off = position;
		mouvement = true;
		fight = false;
		chrono = 0;
		health = 1;
	}

	/**
	 * @param position
	 * La position de depart de la mouche
	 * @param on
	 * La position maximale jusqu'ou oscille la mouche
	 * @param s
	 * Le signal qu'il faut pour faire apparaitre la mouche
	 */
	public Fly(Vector position, Vector on, Signal s) {
		this.position = position;
		this.on = on;
		this.off = position;
		mouvement = true;
		fight = false;
		chrono = 0;
		health = 1;
		this.s = s;
	}

	@Override
	public int getPriority() {
		return 90;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (s.isActive())
			if (mouvement()) {
				current += input.getDeltaTime();
				if (current > 1.0)
					current = 1.0;
			} else {
				current -= input.getDeltaTime();
				if (current < 0.0)
					current = 0.0;
			}
		chrono -= input.getDeltaTime();
		if (fight && chrono < 0) {
			Fireball bouleDeFeu = new Fireball(position, projectile, this);
			getWorld().register(bouleDeFeu);
			fight = false;
			chrono = 1;
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (s.isActive()) {
			if (getSprite("fly.left.2") == null) {
				super.draw(input, output);
			}
			if (current < 0.25) {
				output.drawSprite(getSprite("fly.left.2"), getBox());
			} else if (current > 0.25 && current < 0.5) {
				output.drawSprite(getSprite("fly.left.1"), getBox());
			} else if (current > 0.5 && current < 0.75) {
				output.drawSprite(getSprite("fly.right.1"), getBox());
			} else if (current > 0.75) {
				output.drawSprite(getSprite("fly.right.2"), getBox());
			}

		}
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Box getBox() {
		Vector vectorInterpolation = off.mixed(on, current);
		return new Box(vectorInterpolation, 0.5, 0.5);

	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		Box area = new Box(getBox().getCenter(), 5, 5);
		if (s.isActive()) {
			if (other instanceof Player && area.isColliding(other.getBox())) {
				projectile = other.getPosition().sub(position);
				fight = true;
			}
		}
	}

	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case PHYSICAL:
		case FIRE:
			health = health - amount;
			if (health <= 0)
				endLife();
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}

	public void endLife() {
		getWorld().unregister(this);
	}

	/**
	 * @return
	 * retourne un boolean indiquant si la mouche est en mouvement ou non
	 */
	public boolean mouvement() {
		if (s.isActive()) {
			if (getBox().getCenter().equals(on) || getBox().getCenter().equals(off)) {
				mouvement = !mouvement;
				return mouvement;
			} else {
				return mouvement;
			}
		} else {
			return mouvement;
		}
	}

	@Override
	public boolean isActive() {
		if (health <= 0) {
			return true;
		} else {
			return false;
		}
	}

}