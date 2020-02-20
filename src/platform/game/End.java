package platform.game;

import platform.game.Actor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;

public class End extends Actor {
	private double duration;

	
	public End(double duration){
		this.duration=duration;
	}
	
	@Override
	public void update(Input input) {
		super.update(input);
		}
	
	@Override
	public void draw(Input input, Output output) { 
		Sprite sprite = getSprite("pixel.black"); 
		double transparency = Math.max(0.0, input.getDeltaTime()- duration + 1.0); 
		output.drawSprite(sprite, output.getBox(), 0.0, transparency);
		}
	
	@Override
	public int getPriority() {
		return 990;
	}
	
}


