package platform.game;

import platform.game.level.Level;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

public class Exit extends Actor {

	private double size;
	private Signal signal;
	private Vector position;
	private Level level;

	
	/**
	 * @param position
	 * La position de la porte de sortie
	 * @param level
	 * Le niveau vers lequel cette porte renvoie
	 * @param signal
	 * Le signal qu'il faut pour activer la porte
	 */
	public Exit(Vector position, Level level, Signal signal) {
		this.position = position;
		this.level = level;
		this.signal = signal;
		size = 1;
	}

	@Override
	public void draw(Input input, Output output) {
		if (getSprite("door.open") == null || getSprite("door.closed") == null) {
			super.draw(input, output);
		} else {
			if (signal.isActive()) {
				output.drawSprite(getSprite("door.open"), getBox());
			} else {
				output.drawSprite(getSprite("door.closed"), getBox());
			}
		}
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other instanceof Player && signal.isActive() && this.getBox().isColliding(other.getBox())) {
			getWorld().setNextLevel(level);
			getWorld().nextLevel();
		}
	}

	
	public Level getLevel() {
		return level;
	}
	
	public Signal getSignal() {
		return signal;
	}
	

	@Override
	public int getPriority() {
		return 60;
	}
	
	public double getSize(){
		return size;
	}
	
	public void setSize(double size){
		this.size=size;
	}

	@Override
	public Box getBox() {
		// position est l'attribut position de l'objet
		// SIZE une constante choisie pour la taille
		return new Box(position, getSize(), getSize());
	}

}
