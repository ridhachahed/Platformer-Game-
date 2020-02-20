package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Output;

/**
 * Simple solid actor that does nothing.
 */
public class Block extends Actor {
	private Box zone;
	private Sprite sprite;

	/**
	 * @param zone
	 * La zone couverte par le block
	 * @param sprite
	 * Le sprite correspondant au block
	 */
	public Block(Box zone, Sprite sprite) {
		this.zone = zone;
		this.sprite = sprite;
	}

	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
	};

	@Override
	public void update(Input input) {
		super.update(input);
	};

	@Override
	public void postUpdate(Input input) {
		super.postUpdate(input);
	};

	@Override
	public void draw(Input input, Output output) {
		if (sprite == null) {
			super.draw(input, output);
		}
		output.drawSprite(sprite, getBox());
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Box getBox() {
		return zone;
	}

	public Sprite getSprite() {
		return sprite;
	}

}
