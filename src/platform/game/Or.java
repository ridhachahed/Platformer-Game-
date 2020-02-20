package platform.game;

public class Or implements Signal{
	private final Signal left;
	private final Signal right;

	/**
	 * @param left
	 * Un des deux signaux 
	 * @param right
	 * Le deuxieme signal
	 */
	public Or(Signal left,Signal right) {
		if (left == null || right==null)
			throw new NullPointerException();
		this.left=left;
		this.right=right;
	}

	@Override
	public boolean isActive() {
		return (left.isActive() || right.isActive());
	}
}

