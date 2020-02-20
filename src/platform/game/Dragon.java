package platform.game;

import platform.game.Actor.Damage;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;


public class Dragon extends Actor implements Signal {
	private Vector position;
	private Vector on;
	private Vector off;
	private double current;
	private boolean mouvement; // aller: true retour:false
	private Vector projectile;
	private boolean fight;
	private double chrono;
	private double health;
	private String name;
	private double angle;
	private boolean droite;

	/**
	 * @param position
	 * La position initiale du dragon
	 * @param on
	 * Jusqu'ou le dragon va osciller
	 * @param health
	 * Les points de vie du dragon
	 * @param name
	 * Le nom du Sprite a charger pour afficher le dragon
	 */
	public Dragon(Vector position, Vector on, double health, String name) {
		this.position = position;
		this.on = on;
		this.off = position;
		this.name = name;
		mouvement = true;
		fight = false;
		chrono = 0;
		this.health = health;
		droite = false;
		angle = 0;

		if (position == null || on == null) {
			throw new NullPointerException();
		}
		if (health < 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getPriority() {
		return 90;
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
		if (getSprite(name) == null) {
			super.draw(input, output);
		} else {
			if (on.equals(off)) {
				if (droite) {
					output.drawSprite(getSprite(name+"Symetrique"), getBox(), angle%(3*Math.PI/2));
				} else {
					output.drawSprite(getSprite(name), getBox(), angle%(3*Math.PI/2));
				}
			} else {
				output.drawSprite(getSprite(name), getBox());
			}
			// Affiche une barre de vie au dragon
			output.drawSprite(getSprite("bar.png"),
					new Box(new Vector(getBox().getCenter().getX(), getBox().getCenter().getY() + 2.5), health, 0.2));
		}

	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Box getBox() {
		Vector vectorInterpolation = off.mixed(on, current);
		return new Box(vectorInterpolation, 3, 5);

	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		Box area = new Box(getBox().getCenter(), 20, 10);
		if (other instanceof Player && area.isColliding(other.getBox())) {
			projectile = (other.getPosition().sub(position)).mul(1.5);
			angle = ((Player) other).getPosition().getX();
			fight = true;
			if (((Player) other).getPosition().getX() > this.getPosition().getX()) {
				droite = true;
			} else {
				droite = false;
			}
		}
	}

	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		this.interact(instigator);
		switch (type) {
		case PHYSICAL:
		case HADOKEN:
			health = health - amount;
			if (health == 0)
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
	 * Retourne si le dragon doit se deplacer ou non 
	 */
	public boolean mouvement() {
		if (getBox().getCenter().equals(on) || getBox().getCenter().equals(off)) {
			mouvement = !mouvement;
			return mouvement;
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
