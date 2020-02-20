package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

public class Slime extends Actor implements Signal {
	private Vector position;
	private Vector on;
	private Vector off;
	private double current;
	private boolean mouvement; // aller: true retour:false
	private double health;
	private double chrono;
	private boolean fight;
	private Signal s;
	private final static double SIZE = 0.5;

	/**
	 * @param position
	 * La position de depart du Slime
	 * @param on
	 * La position maximale jusqu'ou oscille le Slime
	 */
	public Slime(Vector position, Vector on) {
		this.position = position;
		this.on = on;
		this.off = position;
		mouvement = true;
		health = 1;
		chrono = 0;
		s = new Constante(true);
		
		if (position == null || on == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * @param position
	 * La position de depart du Slime
	 * @param on
	 * La position maximale jusqu'ou oscille le Slime
	 * @param s
	 * Le signal necessaire a l'appartition de certains Slimes
	 */
	public Slime(Vector position, Vector on, Signal s) {
		this.position = position;
		this.on = on;
		this.off = position;
		mouvement = true;
		health = 1;
		chrono = 0;
		this.s = s;

		if (position == null || on == null || s==null) {
			throw new NullPointerException();
		}
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (mouvement()) {
			current += input.getDeltaTime();
			if (current > 1.0)
				current = 1.0;
		} else {
			current -= input.getDeltaTime();
			mouvement();
			if (current < 0.0)
				current = 0.0;
		}
		chrono = -input.getDeltaTime();
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite("slime.left.1") == null || getSprite("slime.left.2") == null || getSprite("slime.left.3") == null
				|| getSprite("slime.right.1") == null || getSprite("slime.right.2") == null
				|| getSprite("slime.right.3") == null) {
			super.draw(input, output);
		}
		super.draw(input, output);
		if (s.isActive()) {
			if (current < 1.0 / 6.0) {
				output.drawSprite(getSprite("slime.left.1"), getBox());
			} else if (current > 1.0 / 6.0 && current < (1.0 / 3.0)) {
				output.drawSprite(getSprite("slime.left.2"), getBox());
			} else if (current > (1.0 / 3.0) && current < 0.5) {
				output.drawSprite(getSprite("slime.left.3"), getBox());
			} else if (current > 0.5 && current < (2.0 / 3.0)) {
				output.drawSprite(getSprite("slime.right.3"), getBox());
			} else if (current > (2.0 / 3.0) && current < (5.0 / 6.0)) {
				output.drawSprite(getSprite("slime.right.2"), getBox());
			} else if (current > (5.0 / 6.0)) {
				output.drawSprite(getSprite("slime.right.1"), getBox());
			}
		}
       
	}

	@Override
	public int getPriority() {
		return 90;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Box getBox() {
		Vector vectorInterpolation = off.mixed(on, current);
		return new Box(vectorInterpolation, SIZE, SIZE);

	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (s.isActive()) {
			if (other instanceof Player && this.getBox().isColliding(other.getBox()) && chrono < 0) {
				other.hurt(this, Damage.PHYSICAL, 10, other.getPosition());
				Vector below = new Vector(position.getX(), position.getY());
				other.hurt(this, Damage.AIR, 4, below);
				mouvement = !mouvement;
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
			if (health<= 0)
				endLife();
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
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

	public void endLife() {
		getWorld().unregister(this);
	}

	public boolean mouvement() {
		if (getBox().getCenter().equals(on) || getBox().getCenter().equals(off)) {
			mouvement = !mouvement;
			return mouvement;
		} else {
			return mouvement;
		}
	}

}
