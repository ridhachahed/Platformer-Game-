package platform.game.level;

import platform.game.*;
import platform.util.Box;
import platform.util.Vector;

public class Showdown extends Level {
	@Override
	public void register(World world) {
		super.register(world);
		// Register a new instance, to restart level automatically
		world.setNextLevel(this);

		Player player = new Player(new Vector(0, 3), new Vector(0, -1), 50);
		world.register(player);
		world.register(new Overlay(player));

		for (int i = 0; i < 20; ++i) {
			world.register(
					new Block(new Box(new Vector(i - 10, 0), 1, 1), world.getLoader().getSprite("stone.broken.3")));
		}

		world.register(new Limits(new Box(Vector.ZERO, 50, 30)));
		world.register(new Background(new Box(Vector.ZERO, 50, 30), "Finalbackground.png"));

		Dragon finalboss = new Dragon(new Vector(-8, 3), new Vector(-5, 3), 75, "Finalboss.png");
		world.register(finalboss);

		world.register(new Exit(new Vector(8, 1.0), new TheEnd(), finalboss));
	}
}