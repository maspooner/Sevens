import java.awt.Toolkit;


public class Human extends Player{

	Human(String name) {
		super(name);
	}

	@Override
	public void playCard(){
		Dialogs.showTurnDialog(name);
		myHand.sort();
		
		GUI.isEnabled=true;
		GUI.setConsole("Select a card");
		GUI.createCardButtons();
		GUI.updateUI();
		
		while(true){
			while(GUI.buttonPressed==-1){
			}
			if(GUI.buttonPressed==0){
				if(passes==0)
					isOut=true;
				else
					passes--;
			}
			else{
				int i=GUI.getSelectedCard();
				if(i==-1){
					GUI.setConsole("Select a card.");
					Toolkit.getDefaultToolkit().beep();
					GUI.buttonPressed=-1;
					continue;
				}
				if(!Table.getField().isPlaceable(myHand.getCard(i))){
					GUI.setConsole("This card is not playable.");
					Toolkit.getDefaultToolkit().beep();
					GUI.buttonPressed=-1;
					continue;
				}
				Card c=myHand.remove(i);
				Table.getField().placeCard(c);
				Table.putInDeck(c);
			}
			GUI.buttonPressed=-1;
			GUI.clear();
			GUI.isEnabled=false;
			break;
		}
	}
}
