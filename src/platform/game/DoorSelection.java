package platform.game;

import platform.game.level.Level;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

public class DoorSelection extends Exit {
private String levelNumber;

	/**
	 * @param vector
	 * La position de la DoorSelection
	 * @param level
	 * Le niveau vers lequel elle nous envoie
	 * @param signal
	 * Le signal qui permet d'acceder ou non au niveau
	 * @param levelNumber
	 * Le nom du sprite correspondant au level
	 */
	public DoorSelection(Vector position, Level level, Signal signal,String levelNumber){
		super(position, level,signal);
		this.levelNumber=levelNumber;
		setSize(3);
		
	}
	
	@Override
	public void update(Input input){
		if(this.getBox().isColliding(input.getMouseLocation()) && (input.getMouseButton(1).isPressed()) && getSignal().isActive()){
			getWorld().setNextLevel(getLevel());
			getWorld().nextLevel();
		}
	}
	
	public void draw(Input input, Output output) {
		if (getSprite("box.1.enabled") == null || getSprite("box.1.disabled") == null) {
			super.draw(input, output);
		} else {
			if (getSignal().isActive()) {
				output.drawSprite(getSprite("box.1.enabled"), getBox());
			} else {
				output.drawSprite(getSprite("box.1.disabled"), getBox());
			}
			output.drawSprite(getSprite(levelNumber), (new Box(getBox().getCenter(),1.5,1.5)));
		}
	}
	
}
