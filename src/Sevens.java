


public class Sevens {
	protected static boolean IS_TEST = true;
	
	public static volatile boolean isStarting=false;
	public static final String version="1.1";

	public static void main(String[] args) {
		IO.loadCards();
		new GUI();
		while(true){
			while(!isStarting){
			}
			isStarting=false;
			GUI.enableStart(false);
			int players=0;
			while(players==0){
				players=Dialogs.showSetupDialog();
			}
			Table.setupTable(players);
			Table.removeSevens();
			//game loop
			while(true){
				GUI.setConsole("");
				if(Table.isWinner())
					break;
				Table.changePlayer();
				Player current=Table.getCurrentPlayer();
				if(!current.isOut){
					current.playCard();
					if(current.isOut){
						Table.getField().placeAll(current.myHand);
						Table.putInDeck(current.myHand);
						current.myHand.clear();
					}
				}
			}
			GUI.updateTable();
			GUI.clear();
			for(int i=0;i<Table.getNumPlayers();i++)
				Table.putInDeck(Table.getPlayer(i).myHand);
			GUI.enableStart(true);
			Dialogs.showWinDialog(Table.getWinner().name);
		}
	}
}
