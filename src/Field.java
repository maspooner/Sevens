
public class Field {
	private boolean[][] field=new boolean[13][4];
	
	public void placeCard(Card c){
		field[c.number-1][c.suit-1]=true;
		GUI.enableMiniCard(c.number, c.suit, true);
	}
	
	public void placeAll(Hand h){
		for(int i=0;i<h.getSize();i++){
			placeCard(h.getCard(i));
		}
	}
	
	public boolean isPlaceable(Card c){
		if(c.number==7)
			return true;
		int j=c.suit-1;
		if(c.number>7){
			for(int k=7;k<c.number;k++){
				if(field[k-1][j]==false)
					return false;
			}
			return true;
		}
		if(c.number<7){
			for(int k=7;k>c.number;k--){
				if(field[k-1][j]==false)
					return false;
			}
			return true;
		}
		return false;
	}
}
