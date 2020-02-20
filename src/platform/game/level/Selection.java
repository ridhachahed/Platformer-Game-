package platform.game.level;

import platform.game.And;
import platform.game.Background;
import platform.game.Constante;
import platform.game.Door;
import platform.game.DoorSelection;
import platform.game.Exit;
import platform.game.World;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;
import platform.util.View;

public class Selection extends Level {
	
	
	@Override
	public void register(World world) {
		super.register(world);

		// Register a new instance, to restart level automatically
		world.setNextLevel(this);
		
		world.register(new Background(new Box(Vector.ZERO, 40, 30),"starter" ));
		world.register(new DoorSelection(new Vector(-6, 0), new BasicLevel(),new Constante(true),"digit.1"));
		world.register(new DoorSelection(new Vector(0, 0), new Level2(),new Constante(true),"digit.2"));
		world.register(new DoorSelection(new Vector(6, 0), new Showdown(),new Constante(true),"digit.3"));
       
		
	}
}
