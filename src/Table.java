import java.util.ArrayList;
import java.util.Random;


public class Table {
	private static int numPlayers=0;
	private static int currentPlayer=-1;
	private static Player[] players;
	private static ArrayList<Card> deck=new ArrayList<Card>();
	private static Field field;
	
	public static void setupTable(int numPlayers){
		GUI.reset();
		field=new Field();
		Table.numPlayers=numPlayers;
		players=new Player[numPlayers];
		Dialogs.showPlayerDialog();
		if(deck.size()==0)
			initDeck();
		shuffle();
		deal();
	}
	public static Player getPlayer(int i){
		return players[i];
	}
	public static void setPlayer(int i, Player p){
		players[i]=p;
	}
	public static Player getCurrentPlayer(){
		return getPlayer(currentPlayer);
	}
	public static void changePlayer(){
		currentPlayer=currentPlayer+1==numPlayers ? 0 : currentPlayer+1;
	}
	public static Field getField(){
		return field;
	}
	public static int getNumPlayers(){
		return numPlayers;
	}
	private static void initDeck(){
		for(int i=1;i<=13;i++){
			for(int j=1;j<=4;j++){
				deck.add(new Card(i,j));
			}
		}
	}
	private static void shuffle(){
		Random rand=new Random();
		for(int i=0;i<deck.size();i++){
			int j= rand.nextInt(deck.size()-1);
			Card tempCard=deck.get(i);
			deck.set(i, deck.get(j));
			deck.set(j, tempCard);
		}
	}
	private static void deal(){
		while(deck.size()!=0){
			for(Player p:players){
				if(deck.size()==0)
					break;
				p.myHand.add(deck.remove(0));
			}
		}
	}
	
	public static void removeSevens(){
		for(Player p:players){
			for(int i=0;i<p.myHand.getSize();i++){
				Card c=p.myHand.getCard(i);
				if(c.number==7){
					field.placeCard(c);
					p.myHand.remove(c);
					putInDeck(c);
					i=0;
				}
			}
		}
	}
	
	public static void putInDeck(Card c){
		deck.add(c);
	}
	public static void putInDeck(Hand h){
		for(int i=0;i<h.getSize();i++)
			deck.add(h.getCard(i));
	}
	public static void printDeck(){
		sortDeck();
		for(Card c:deck)
			System.out.println(c.getName());
	}
	
	public static boolean isWinner(){
		int numOut=0;
		for(Player p:players){
			if(p.isOut){
				numOut++;
				continue;
			}
			if(p.myHand.getSize()==0)
				return true;
		}
		return numOut==numPlayers-1;
		//TODO fix logic
	}
	
	public static Player getWinner(){
		for(Player p:players){
			if(p.isOut)
				continue;
			if(p.myHand.getSize()==0)
				return p;
		}
		for(Player p:players)
			if(!p.isOut)
				return p;
		return null;
	}
	
	private static void sortDeck(){
		//TODO remove
		for(int i=0;i<(deck.size()-1);i++){
		    for(int j=0;j<(deck.size()-1);j++){
		        if(deck.get(j).suit==deck.get(j+1).suit){
		            if(deck.get(j).number>deck.get(j+1).number)
		                swap(j,j+1);
		        }
		        else{
		            if(deck.get(j).suit>deck.get(j+1).suit)
		                swap(j,j+1);
		        }
		    }
		}
	}
	
	private static void swap(int i, int j){
		Card temp=deck.get(i);
		deck.set(i,deck.get(j));
		deck.set(j,temp);
	}
}
