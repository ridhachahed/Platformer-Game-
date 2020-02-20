package platform.game.level;

import platform.game.*;
import platform.util.Box;
import platform.util.Vector;

public class TheEnd extends Level {
	@Override
	public void register(World world) {
		super.register(world);
		world.register(new Background(new Box(Vector.ZERO, 28,20), "Theend.png"));
	}
}