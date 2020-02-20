package platform.game.level;
import platform.game.World;
import platform.game.And;
import platform.game.Background;
import platform.game.Block;
import platform.game.Blockyspike;
import platform.game.Heart;
import platform.game.Hiddenjumper;
import platform.util.Box;
import platform.util.Vector;
import platform.game.Jumper;
import platform.game.Key;
import platform.game.Lever;
import platform.game.Limits;
import platform.game.Mover;
import platform.game.Not;
import platform.game.Oscillator;
import platform.game.Overlay;
import platform.game.Player;
import platform.game.Slime;
import platform.game.Spike;
import platform.game.Torch;
import platform.game.Door;
import platform.game.Dragon;
import platform.game.Exit;
import platform.game.Fly;
public class Level2 extends Level {
	@Override
	public void register(World world) {
		
		super.register(world);
		
		// Register a new instance, to restart level automatically
		world.setNextLevel(this);
		
		Player player = new Player(new Vector(0, 3), new Vector(0, -1), 50);
		world.register(player);
		world.register(new Overlay(player));
		Key green = new Key(new Vector(-34, 54), "key.green");
		Torch torche0 = new Torch(new Vector(-37.0, 0), true);
		Torch torche1 = new Torch(new Vector(-1.0, 3.5), false);
		Torch torche2 = new Torch(new Vector(0.0, 3.5), false);
		Torch torche3 = new Torch(new Vector(1.0, 3.5), false);
		Torch torche4 = new Torch(new Vector(-44, 17), true);
		Torch torche5 = new Torch(new Vector(-36, 11.2), false);
		Key red = new Key(new Vector(0, 2.5), "key.red", new And(new And(torche1, torche2), new Not(torche3)));
		Dragon miniboss = new Dragon(new Vector(-41, 12), new Vector(-38, 12), 12, "Dragon.png");
		Dragon blue = new Dragon(new Vector(-41, 20), new Vector(-38, 20), 12, "dragon.white.blue.png");
		Dragon boss = new Dragon(new Vector(-44, 54), new Vector(-37, 54), 24, "dragon.red.png");
		Fly mouche1 = new Fly(new Vector(-45, 20), new Vector(-42, 20));
		Fly mouche2 = new Fly(new Vector(-46, 18), new Vector(-43, 18));
		Fly mouche3 = new Fly(new Vector(-45, 22), new Vector(-42, 18));
		Fly mouche4 = new Fly(new Vector(-44, 21), new Vector(-44, 18));
		
		Slime slime1= new Slime(new Vector(-55, 23.35), new Vector(-52,23.35), blue);
		Slime slime2= new Slime(new Vector(-54, 23.35), new Vector(-51,23.35), blue);
		Slime slime3= new Slime(new Vector(-53, 23.35), new Vector(-49.25,23.35), blue);
		Slime slime4= new Slime(new Vector(-52.5, 23.35), new Vector(-48.75,23.35), blue);
		world.register(slime1);
		world.register(slime2);
		world.register(slime3);
		world.register(slime4);
		
		Lever lever = (new Lever(new Vector(-42, 9.85), false, 180, miniboss));
		world.register(mouche1);
		world.register(mouche2);
		world.register(mouche3);
		world.register(mouche4);
		world.register(miniboss);
		world.register(boss);
		world.register(blue);
		world.register(red);
		world.register(torche0);
		world.register(torche1);
		world.register(torche2);
		world.register(torche3);
		world.register(torche4);
		world.register(torche5);
		world.register(green);
		world.register(new Background(new Box(new Vector(0, 0), 200, 250), "nuit.png"));
		world.register(new Limits(new Box(new Vector(-100, -25), new Vector(100, 90))));
		world.register(new Block(new Box(new Vector(-0.2, 0), 4, 2), world.getLoader().getSprite("stone.broken.2")));
		world.register(new Block(new Box(new Vector(5, 0), 2, 1), world.getLoader().getSprite("stone.broken.8")));
		world.register(new Exit(new Vector(5.0, 1.0), new Showdown(),
				new And(new And(new And(new And(torche1, torche2), torche3), green), red)));
		for (double i = 2; i < 14; i = i + 0.5) {
			world.register(new Blockyspike(new Vector(i, -0.7 + i), lever));
			world.register(new Block(new Box(new Vector(i, -1.2 + i), 0.51, 0.51),
					world.getLoader().getSprite("stone.broken.3")));
		}
		for (double i = 14; i < 27; i = i + 0.5) {
			world.register(new Blockyspike(new Vector(i, 12.8), lever));
			world.register(
					new Block(new Box(new Vector(i, 12.3), 0.51, 0.51), world.getLoader().getSprite("stone.broken.3")));
		}
		for (double i = 27; i < 38.5; i = i + 0.5) {
			world.register(new Blockyspike(new Vector(i, -i + 27 + 12.3), lever));
			world.register(new Block(new Box(new Vector(i, 38.8 - i), 0.51, 0.51),
					world.getLoader().getSprite("stone.broken.3")));
		}
		for (double i = -1.5; i > -27; i = i - 5) {
			world.register(new Hiddenjumper(new Vector(i, 1), new And(new And(torche1, torche3), new Not(torche2))));
		}
		for (int i = -32; i > -50; i--) {
			world.register(
					new Block(new Box(new Vector(i, -2), 1.01, 1), world.getLoader().getSprite("stone.broken.3")));
		}
		for (int i = 0; i < 50; i++) {
			world.register(
					new Block(new Box(new Vector(-34, i), 1, 1.01), world.getLoader().getSprite("stone.broken.3")));
		}
		world.register(new Door(new Box(new Vector(-34, -1), 1, 1), world.getLoader().getSprite("lock.red"), red));
		world.register(new Hiddenjumper(new Vector(-32, 0), green));
		
		world.register(new Mover(new Box(new Vector(-37,-1),2.02,1.01 ),world.getLoader().getSprite("stone.broken.5"), new Not (torche0), new Vector(-37,5)));
		for (int i = 0; i < 8; ++i) {
			world.register(new Block(new Box(new Vector(-40, i - 2), 1.01, 1.01),
					world.getLoader().getSprite("stone.broken.5")));
			world.register(new Block(new Box(new Vector(-43, i - 2), 1.01, 1.01),
					world.getLoader().getSprite("stone.broken.5")));
			}
		for (int i = 0; i < 10; ++i) {
			world.register(new Block(new Box(new Vector(-35 - i, 9), 1.01, 1.01),
					world.getLoader().getSprite("stone.broken.5")));
			world.register(new Block(new Box(new Vector(-49, i - 2), 1.01, 1.01),
					world.getLoader().getSprite("stone.broken.5")));
			world.register(new Block(new Box(new Vector(-49, i + 12), 1.01, 1.01),
					world.getLoader().getSprite("stone.broken.5")));
			world.register(new Block(new Box(new Vector(-49 - i, 22), 1.01, 1.01),
					world.getLoader().getSprite("stone.broken.5")));
			world.register(new Blockyspike(new Vector(-49.3 - i, 22.8), lever));
			world.register(new Blockyspike(new Vector(-48.8 - i, 22.8), lever));
			world.register(new Spike(new Vector(-44 - i / 2, -1.2)));
		}
		world.register(new Block(new Box(new Vector(-50, 7), 1.01, 1), world.getLoader().getSprite("stone.broken.5")));
		world.register(new Block(new Box(new Vector(-51, 7), 1.01, 1), world.getLoader().getSprite("stone.broken.5")));
		world.register(new Block(new Box(new Vector(-52, 7), 1.01, 1), world.getLoader().getSprite("stone.broken.5")));
		world.register(new Block(new Box(new Vector(-50, 12), 1.01, 1), world.getLoader().getSprite("stone.broken.5")));
		world.register(new Block(new Box(new Vector(-51, 12), 1.01, 1), world.getLoader().getSprite("stone.broken.5")));
		world.register(new Block(new Box(new Vector(-52, 12), 1.01, 1), world.getLoader().getSprite("stone.broken.5")));
		for (int i = 0; i < 5; ++i) {
			world.register(new Block(new Box(new Vector(-52, 8 + i), 1.01, 1.01),
					world.getLoader().getSprite("stone.broken.5")));
		}
		
		for (int i=0; i<11;++i){
			world.register(new Block(new Box(new Vector(-49 + i, 15), 1.01, 1),
					world.getLoader().getSprite("stone.broken.6")));
		}
		
		world.register(new Mover(new Box(new Vector(-36,10),2.02,1.01 ),world.getLoader().getSprite("stone.broken.5"), torche5, new Vector(-37,16)));	
		world.register(new Mover(new Box(new Vector(-44,16),2.02,1.01 ),world.getLoader().getSprite("stone.broken.5"), new And( new Not (torche4), blue), new Vector(-46,23)));		
		
		for (int i = 0; i < 14; ++i) {
			world.register(
					new Door(new Box(new Vector(-49 + i, 22), 1.01, 1), world.getLoader().getSprite("lock.blue"),
							new And(new And(new And(new And(blue, mouche4), mouche1), mouche2), mouche3)));
		}
		world.register(new Door(new Box(new Vector(-35, 22), 1.01, 1), world.getLoader().getSprite("lock.blue"),
				new And(new And(new And(new And(blue, mouche4), mouche1), mouche2), mouche3)));
		//world.register(new Jumper(new Vector(-35, 9.5)));
		world.register(new Spike(new Vector(-38.5, -1.2)));
		world.register(new Spike(new Vector(-39, -1.2)));
		world.register(new Spike(new Vector(-41, -1.2)));
		world.register(new Spike(new Vector(-41.5, -1.2)));
		world.register(new Spike(new Vector(-42, -1.2)));
		world.register(lever);
		world.register(new Jumper(new Vector(-60, 22)));
		world.register(new Jumper(new Vector(-62, 26)));
		world.register(new Jumper(new Vector(-60, 30)));
		world.register(new Jumper(new Vector(-62, 34)));
		world.register(new Jumper(new Vector(-60, 38)));
		world.register(new Jumper(new Vector(-62, 42)));
		world.register(new Jumper(new Vector(-60, 46)));
		for (int i = 0; i < 20; ++i) {
			world.register(new Block(new Box(new Vector(-58 + i, 50), 1.01, 1),
					world.getLoader().getSprite("stone.broken.6")));
		}
		world.register(new Hiddenjumper(new Vector(-34, 49.7), new And(lever, boss)));
		Oscillator oscillateur2 = new Oscillator(2);
		world.register(oscillateur2);
		Oscillator oscillateur4 = new Oscillator(4);
		world.register(oscillateur4);
		world.register(new Mover(new Box(new Vector(8, 0), 4, 1), world.getLoader().getSprite("stone.3"), oscillateur2,
				new Vector(12, 0)));
		world.register(new Mover(new Box(new Vector(16, 0), 4, 1), world.getLoader().getSprite("stone.3"), oscillateur4,
				new Vector(20, 0)));
		world.register(new Mover(new Box(new Vector(24, 0), 4, 1), world.getLoader().getSprite("stone.3"), oscillateur2,
				new Vector(28, 0)));
		world.register(new Mover(new Box(new Vector(32, -2), 4, 1), world.getLoader().getSprite("stone.3"),
				oscillateur2, new Vector(36, -2)));
		world.register(new Jumper(new Vector(40, -6)));
		world.register(new Block(new Box(new Vector(40 , -8), 12, 1),
				world.getLoader().getSprite("stone.broken.6")));
		
	}
}
