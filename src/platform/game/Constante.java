package platform.game;

public class Constante implements Signal {
	private final  boolean constante;
	
	/**
	 * @param constante
	 * Le singal sera toujours vrai,ou toujours faux selon le boolean
	 */
	public Constante(boolean constante){
		this.constante=constante;
	}
	
	@Override
	public boolean isActive() {
		return constante;
	}
}
