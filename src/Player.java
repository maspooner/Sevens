

public abstract class Player {
	protected String name;
	protected Hand myHand;
	protected int passes;
	protected boolean isOut;
	
	Player(String name){
		this.name=name;
		myHand=new Hand();
		passes=3;
		isOut=false;
	}
	
	public abstract void playCard();
}
