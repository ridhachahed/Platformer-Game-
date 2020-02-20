package platform.game;

import platform.game.Actor.Damage;
import platform.game.level.Level;
import java.util.ArrayList;
import java.util.List;
import platform.util.Box;

import platform.util.Input;
import platform.util.Loader;
import platform.util.Output;
import platform.util.SortedCollection;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.View;

/**
 * Basic implementation of world, managing a complete collection of actors.
 */
public class Simulator implements World {
	SortedCollection<Actor> actors = new SortedCollection<Actor>();
	private ArrayList<Actor> registered;
	private ArrayList<Actor> unregistered;

	private Loader loader;
	private Vector currentCenter;
	private double currentRadius;
	private Vector expectedCenter;
	private double expectedRadius;
	private Level next;
	private boolean transition;

	/**
	 * Create a new simulator.
	 * 
	 * @param loader
	 *            associated loader, not null
	 * @param args
	 *            level arguments, not null
	 */
	public Simulator(Loader loader, String[] args) {
		if (loader == null) {
			throw new NullPointerException();
		}
		this.loader = loader;
		currentCenter = Vector.ZERO;
		currentRadius = 10.0;
		expectedCenter = Vector.ZERO;
		expectedRadius = 10.0;

		registered = new ArrayList<Actor>();
		unregistered = new ArrayList<Actor>();
		next = Level.createDefaultLevel();
		transition = false;
		register(next);

	}

	public void setView(Vector center, double radius) {
		if (center == null)
			throw new NullPointerException();
		if (radius <= 0.0)
			throw new IllegalArgumentException("radius must be positive");
		expectedCenter = center;
		expectedRadius = radius;
	}

	/**
	 * Simulate a single step of the simulation.
	 * 
	 * @param input
	 *            input object to use, not null
	 * @param output
	 *            output object to use, not null
	 */
	public void update(Input input, Output output) {

		double factor = 0.01;
		currentCenter = currentCenter.mul(1.0 - factor).add(expectedCenter.mul(factor));
		currentRadius = currentRadius * (1.0 - factor) + expectedRadius * factor;

		View view = new View(input, output);
		view.setTarget(currentCenter, currentRadius);
	

		for (Actor a : actors)
			a.preUpdate(view);

		for (Actor actor : actors)
			for (Actor other : actors)
				if (actor.getPriority() > other.getPriority())
					actor.interact(other);

		// Apply update
		for (Actor a : actors)
			a.update(view);

		// Draw everything
		for (Actor a : actors.descending())
			a.draw(view, view);

		for (Actor a : actors)
			a.postUpdate(view);

		// Add registered actors
		for (int i = 0; i < registered.size(); ++i) {
			Actor actor = registered.get(i);
			if (!actors.contains(actor)) {
				actors.add(actor);
			}
		}
		registered.clear();

		// Remove unregistered actors
		for (int i = 0; i < unregistered.size(); ++i) {
			Actor actor = unregistered.get(i);
			actor.unregister();
			actors.remove(actor);
		}
		unregistered.clear();

		// si un acteur a mis transition a true pour demander le passage
		// a un autre niveau :
		if (transition) {
			if (next == null) {
				next = Level.createDefaultLevel();
			}
			// si un acteur appelle setNextLevel , next ne sera pas null :
			Level level = next;
			transition = false;
			next = null;
			actors.clear();
			registered.clear();
			// tous les anciens acteurs sont desenregistres ,
			// y compris le Level precedent :
			unregistered.clear();
			register(level);
		}
	}

	@Override
	public Loader getLoader() {
		return loader;
	}

	@Override
	public void register(Actor actor) {
		registered.add(actor);
		actor.register(this);
	}

	@Override
	public void unregister(Actor actor) {
		unregistered.add(actor);
	}

	public void setNextLevel(Level level) {
		next = level;
	}

	@Override
	public void nextLevel() {
		transition = true;
	}

	@Override
	public int hurt(Box area, Actor instigator, Damage type, double amount, Vector location) {
		int victims = 0;
		for (Actor actor : actors)
			if (area.isColliding(actor.getBox()))
				if (actor.hurt(instigator, type, amount, location))
					++victims;
		return victims;
	}
	

}
