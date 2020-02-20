package platform.game;

import java.awt.event.KeyEvent;

import platform.game.Actor.Damage;
import platform.game.level.Level;
import platform.util.*;

public class Player extends Actor {
	private Vector position;
	private Vector velocity;
	private final static double size = 0.5;
	private boolean colliding;
	private double health;
	private final static double HEALTHMAX=50;

	
	/**
	 * @param position
	 * La position de depart du joueur
	 * @param velocity
	 * La vitesse du joueur
	 * @param health
	 * Les points de vie du joueur
	 */
	public Player(Vector position, Vector velocity,double health) {
		
		this.position = position;
		this.velocity = velocity;
		if (health<HEALTHMAX){
			
			
			this.health=health;
			} else {
				this.health = HEALTHMAX;
			}
		
		
		if (position == null || velocity == null ) {
			throw new NullPointerException();
		}
		if (health<0){
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void preUpdate(Input input) {
		colliding = false;
	}
	
	@Override
	public void postUpdate(Input input) {
		getWorld().setView(getPosition(),8);
	}

	@Override
	public Box getBox() {
		// position est l'attribut position de l'objet
		// SIZE une constante choisie pour la taille , par exemple 0.4
		return new Box(position, size, size);

	}

	@Override
	public int getPriority() {
		return 42;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		if (colliding) {
			double scale = Math.pow(0.001, input.getDeltaTime());
			velocity = velocity.mul(scale);
		}

		double maxSpeed = 4.0;
		if (input.getKeyboardButton(KeyEvent.VK_RIGHT).isDown()) {
			if (velocity.getX() < maxSpeed) {
				double increase = 60.0 * input.getDeltaTime();
				double speed = velocity.getX() + increase;
				if (speed > maxSpeed)
					speed = maxSpeed;
				velocity = new Vector(speed, velocity.getY());
			}
		}
		if (input.getKeyboardButton(KeyEvent.VK_LEFT).isDown()) {
			if (velocity.getX() > -maxSpeed) {
				double increase = 60.0 * input.getDeltaTime();
				double speed = velocity.getX() - increase;
				if (speed < -maxSpeed)
					speed = -maxSpeed;
				velocity = new Vector(speed, velocity.getY());
			}
		}
		if (input.getKeyboardButton(KeyEvent.VK_UP).isPressed() && colliding) {
			velocity = new Vector(velocity.getX(), 7.0);
		}
		
		
		if (input.getKeyboardButton(KeyEvent.VK_SPACE).isPressed()){
			Vector v = velocity.add(velocity.resized(2.0));
				///*if(position.getX()>input.getMouseLocation().getX()){
				//v=v.opposite();
			//}*/
			Fireball bouleDeFeu=new Fireball(position, v,this);
			getWorld().register(bouleDeFeu);
		}
		
		if (input.getKeyboardButton(KeyEvent.VK_A).isPressed()){
			//choix de conception d'orienter le hadoken en fonction de la souris
			Vector v = input.getMouseLocation().sub(position);
				///*if(position.getX()>input.getMouseLocation().getX()){
				//v=v.opposite();
			//}*/
			Hadoken hadoken=new Hadoken(position, v,this);
			getWorld().register(hadoken);
		}

		if (input.getKeyboardButton(KeyEvent.VK_B).isPressed()) {
			getWorld().hurt(getBox(), this, Damage.AIR, 1.0, getPosition());
		}
		if (input.getKeyboardButton(KeyEvent.VK_E).isPressed()) {
			getWorld().hurt(getBox(), this, Damage.ACTIVATION, 5.0, getPosition());
		}
		velocity = velocity.add((getWorld().getGravity()).mul(delta));
		position = position.add(velocity.mul(delta));
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite("blocker.happy") == null) {
			super.draw(input, output);
		}else if(health>HEALTHMAX/2){
		output.drawSprite(getSprite("blocker.happy"), getBox());
		}else{
			output.drawSprite(getSprite("blocker.sad"), getBox());
		}
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other.isSolid()) {
			Vector delta = other.getBox().getCollision(getBox());
			if (delta != null) {
				colliding=true;
				position = position.add(delta);
				if (delta.getX() != 0.0)
					velocity = new Vector(0.0, velocity.getY());
				if (delta.getY() != 0.0)
					velocity = new Vector(velocity.getX(), 0.0);
			}
		}
	}
	
	@Override
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
		super.hurt(instigator, type, amount, location);
		switch (type) {
		case ACTIVATION:
			return true;
		case AIR:
			velocity = getPosition().sub(location).resized(amount);
			return true;
		case HEAL:
			if(health<HEALTHMAX){
				if((health+amount)>=HEALTHMAX){
					health=HEALTHMAX;
				}else{
					health+=amount;
				}
			}	
			return true;
		case VOID:
			health=0;
			endLife();
			return true;
		case PHYSICAL: case FIRE:
			health=health-amount;
			//pour simuler un recul apres l'attaque
			if (health<=0)
				endLife();
			return true;
		default:
			return super.hurt(instigator, type, amount, location);
		}
	}
	
	public double getHealth(){
		return health;
	}
	
	public double getHealthMax() {
		return HEALTHMAX;
	}
	
	public void endLife(){
		getWorld().unregister(this);
		getWorld().nextLevel();
	}

	public Vector getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector velocity){
		this.velocity=velocity;
	}
	
	

}
