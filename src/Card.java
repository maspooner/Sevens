
public class Card {
	int number, suit;
	String numStr, suitStr;//TODO remove?
	
	Card(int number, int suit){
		this.number=number;
		this.suit=suit;
		switch(number){
			case 1: numStr="Ace"; break;
			case 2: numStr="Two"; break;
			case 3: numStr="Three"; break;
			case 4: numStr="Four"; break;
			case 5: numStr="Five"; break;
			case 6: numStr="Six"; break;
			case 7: numStr="Seven"; break;
			case 8: numStr="Eight"; break;
			case 9: numStr="Nine"; break;
			case 10: numStr="Ten"; break;
			case 11: numStr="Jack"; break;
			case 12: numStr="Queen"; break;
			case 13: numStr="King"; break;
		}
		switch(suit){
			case 1: suitStr="Spades"; break;
			case 2: suitStr="Clubs"; break;
			case 3: suitStr="Diamonds"; break;
			case 4: suitStr="Hearts"; break;
		}
	}
	
	public String getName(){
		return numStr+" of "+suitStr;
	}
	
	public String getFileName(){
		return number+suitStr;
	}
}
