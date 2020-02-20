package platform.game.level;

import platform.game.World;
import platform.game.And;
import platform.game.Background;
import platform.game.Block;
import platform.game.BreakableBlock;
import platform.game.Door;
import platform.game.Dragon;
import platform.game.Exit;
import platform.game.Fireball;
import platform.game.Fly;
import platform.game.Heart;
import platform.game.Jumper;
import platform.game.Slime;
import platform.game.Key;
import platform.game.Lever;
import platform.game.Limits;
import platform.game.Mover;
import platform.game.Not;
import platform.game.Or;
import platform.game.Oscillator;
import platform.game.Overlay;
import platform.game.Player;
import platform.game.Signal;
import platform.game.Spike;
import platform.game.Torch;
import platform.util.Box;
import platform.util.Vector;

public class BasicLevel extends Level {

	@Override
	public void register(World world) {
		super.register(world);

		// Register a new instance, to restart level automatically
		world.setNextLevel(this);

		// Create blocks
		world.register(new Block(new Box(new Vector(0, 0), 4, 2), world.getLoader().getSprite("stone.2")));
		world.register(new Block(new Box(new Vector(-4, 3.5), 4, 1), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(4, 0), 4, 2), world.getLoader().getSprite("stone.2")));
		world.register(new Block(new Box(new Vector(8, 0), 4, 2), world.getLoader().getSprite("stone.2")));
		world.register(new Block(new Box(new Vector(-4, -4), 4, 1), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(0, -4), 4, 1), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(4, -4), 4, 1), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(8, -4), 4, 1), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(-10, 0), 4, 1), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(9.5, -2.25), 1, 2.5), world.getLoader().getSprite("stone.7")));
		
		world.register(new Spike(new Vector(-3,4.2)));
		world.register(new Spike(new Vector(-2.5,4.2)));
		world.register(new Jumper(new Vector(-4,4)));
		world.register(new Spike(new Vector(-4.5,4.2)));
		world.register(new Spike(new Vector(-5,4.2)));
		world.register(new Spike(new Vector(-1.5,1.2)));
		world.register(new Spike(new Vector(-4.5,-3.3)));
		world.register(new Spike(new Vector(-5,-3.3)));
		world.register(new Spike(new Vector(-4,-3.3)));
		world.register(new Spike(new Vector(-5.5,-3.3)));
		world.register(new Spike(new Vector(-3.5,-3.3)));
		world.register(new Spike(new Vector(-3,-3.3)));
		world.register(new Background(new Box(Vector.ZERO, 40, 30),"background" )) ;
		
		Lever levier=new Lever(new Vector (7,-3.15), false,Double.POSITIVE_INFINITY);
		world.register(new Mover(new Box(new Vector(5, 3), 1, 4), world.getLoader().getSprite("stone.7"),levier,new Vector(5, 5)));
		
		Player joueur =new Player(new Vector(2,3), new Vector(0,-1),50);
		Dragon dragon=new Dragon (new Vector (8,11), new Vector(8,11),20, "Dragon");
		world.register(dragon);
		Key blue = new Key(new Vector(8,6.5),"key.blue",dragon) ;
		Key red= new Key(new Vector(-10,1),"key.red");
		
		world.register(joueur);
		world.register(new Jumper(new Vector(-1,1)));
		world.register(new Jumper(new Vector(4,1)));
		world.register(new Limits(new Box(Vector.ZERO, 40, 30) )) ;
		world.register(new Overlay(joueur));
		world.register(new Heart(new Vector(1,2)));
		world.register(new Spike(new Vector(1,1.2)));
		world.register(new Torch(new Vector(2,2),true));
		world.register(levier);
		world.register(new Door(new Box(new Vector(2, -3), 1, 1), world.getLoader().getSprite("lock.yellow"),new  And(blue,red) )) ;
		world.register(blue);
		world.register(red);
		world.register(new Exit(new Vector (7,1.5), new Level2(),levier));
		world.register(new BreakableBlock(new Box(new Vector(1, -3), 1, 1), world.getLoader().getSprite("box.double"), 30));
		world.register(new BreakableBlock(new Box(new Vector(3, -3), 1, 1), world.getLoader().getSprite("box.double"), 30));
		world.register(new Block(new Box(new Vector(2, -1.75), 3, 1.5), world.getLoader().getSprite("stone.3")));
		world.register(new Fly (new Vector (0,5), new Vector(0.5,6.2)));
		world.register(new Block(new Box(new Vector(7.7, 5.5), 4.5,1), world.getLoader().getSprite("stone.3")));
		world.register(new Block(new Box(new Vector(9.5, 3), 1, 4), world.getLoader().getSprite("stone.7")));
		Torch torche1=new Torch(new Vector(-11,1),false);
		world.register(torche1);
		Oscillator oscillateur2=new Oscillator(2);
		world.register(oscillateur2);
		world.register(new Mover(new Box(new Vector(-12, -4), 4, 1), world.getLoader().getSprite("stone.3"),new And(torche1,oscillateur2),new Vector(-6, -2)));
		Torch torche2=new Torch(new Vector(6,-2.5),false);
		world.register(torche2);
		Torch torche3=new Torch(new Vector(8,-2.5),false);
		world.register(torche3);
		Torch torche4=new Torch(new Vector(7,-2),true);
		world.register(torche4);
		world.register(new Mover(new Box(new Vector(4, -1.5), 1, 1), world.getLoader().getSprite("stone.broken.1"), (new And(levier, new Not(new And (torche2,torche3)))),new Vector(4, -3)));
		world.register(new Slime(new Vector(7,1.2), (new Vector(8,1.2))));
		world.register(new Slime(new Vector(5,-3.3), (new Vector(7,-3.3))));
		
	}


}
