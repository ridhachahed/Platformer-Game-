package platform.game;


public class And implements Signal{
	
	private final Signal left;
	private final Signal right;
	
	public And(Signal left, Signal right){
		if((left == null )||(right==null)){
			throw new NullPointerException();
		
		}
		this.left=left;
		this.right=right;
		
		
	}
	
	@Override
	public boolean isActive(){
	
		return left.isActive() && right.isActive();
	}
	

}
