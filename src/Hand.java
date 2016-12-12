import java.util.ArrayList;


public class Hand {
	private ArrayList<Card> myCards=new ArrayList<Card>();
	
	public void clear(){
		myCards.clear();
	}
	public void add(Card c){
		myCards.add(c);
	}
	public Card remove(int i){
		return myCards.remove(i);
	}
	public void remove(Card c){
		myCards.remove(c);
	}
	public Card getCard(int i){
		return myCards.get(i);
	}
	
	public int getSize(){
		return myCards.size();
	}
	
	public void sort(){
		for(int i=0;i<(myCards.size()-1);i++){
		    for(int j=0;j<(myCards.size()-1);j++){
		        if(myCards.get(j).suit==myCards.get(j+1).suit){
		            if(myCards.get(j).number>myCards.get(j+1).number)
		                swap(j,j+1);
		        }
		        else{
		            if(myCards.get(j).suit>myCards.get(j+1).suit)
		                swap(j,j+1);
		        }
		    }
		}
	}
	
	public void swap(int i, int j){
		Card temp=myCards.get(i);
		myCards.set(i,myCards.get(j));
		myCards.set(j,temp);
	}
}
