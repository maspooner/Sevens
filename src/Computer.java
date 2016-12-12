import java.util.ArrayList;
import java.util.Random;


public class Computer extends Player{

	Computer(String name) {
		super(name);
	}

	@Override
	public void playCard() {
		// TODO more complex AI later
		ArrayList<Integer> matches=new ArrayList<Integer>();
		for(int i=0;i<myHand.getSize();i++){
			if(Table.getField().isPlaceable(myHand.getCard(i))){
				matches.add(i);
			}
		}
		if(matches.size()==0){
			if(passes==0){
				isOut=true;
				System.out.println(name+" IS OUT\n\n\n\n");
			}
			else
				passes--;
		}
		else{
			int cardIndex=new Random().nextInt(matches.size());
			Card c=myHand.remove(matches.get(cardIndex));
			Table.getField().placeCard(c);
			System.out.println(name+" placed "+c.getName());
			Table.putInDeck(c);
		}
	}
}
