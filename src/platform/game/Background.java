package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;

public class Background extends Actor {
private Box box;
private String name;


	/**
	 * @param box
	 * La surface couverte par le BackGround
	 * @param name
	 * Le nom du sprite correspondant
	 */
	public Background(Box box,String name) {
		this.box = box;
		this.name=name;
		
	}
	@Override
	public void draw(Input input, Output output) {
		if (getSprite(name) == null ) {
			super.draw(input, output);
		}
		output.drawSprite(getSprite(name), getBox());
	}

	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public Box getBox() {
		return box;
	}

}
