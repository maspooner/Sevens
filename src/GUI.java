import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

//TODO google JScrollPane with no layout
//TODO indicate which cards in hand can play
public class GUI{
	public static JFrame frame=new JFrame("Sevens");
	private static JPanel cardPanel=new JPanel();
	private static JLabel passesLabel=new JLabel();
	private static JButton passButton=new JButton("Pass");
	private static JButton playButton=new JButton("Play");
	private static JTable table;
	private static JScrollPane tableScroll=new JScrollPane();
	private static JTextArea console=new JTextArea();
	private static JMenuItem start=new JMenuItem("Start");
	private static JLabel[][] miniCards=new JLabel[13][4];
	
	private static int selectedCard=-1;
	
	private static final Dimension FRAME_SIZE=new Dimension(800,600);
	private static final Font FONT=new Font("sans serif", Font.BOLD, 18);
	private static final Font CONSOLE_FONT=new Font("serif", Font.BOLD, 38);
	
	public static volatile int buttonPressed=-1;
	public static volatile boolean isEnabled=false;
	
	GUI(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				init();
			}
		});
	}
	
	private static void init(){
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(FRAME_SIZE);
		frame.setIconImage(IO.getImage("icon"));
		frame.setJMenuBar(getMenuBar());
		
		frame.setContentPane(getContentPanel());
		frame.setVisible(true);
	}
	
	public static void enableStart(boolean enabled){
		start.setEnabled(enabled);
	}
	
	private static JMenuBar getMenuBar(){
		JMenuBar bar=new JMenuBar();
		//file
		JMenu file=new JMenu("File");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sevens.isStarting=true;
			}
		});
		JMenuItem about=new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dialogs.showAboutDialog();
			}
		});
		JMenuItem exit=new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		file.add(start);
		file.add(about);
		file.add(exit);
		
		bar.add(file);
		return bar;
	}
	
	private static ImagePanel getContentPanel(){
		ImagePanel content=new ImagePanel();
		content.setImage(IO.getImage("felt"));
		content.setLayout(null);
		
		//cardPanel
		cardPanel.setLayout(null);
		cardPanel.setBounds(0, 350, 800, 200);
		cardPanel.repaint();
		
		//mini cards
		drawMiniCards();
		for(int i=0;i<13;i++){
			for(int j=0;j<4;j++){
				content.add(miniCards[i][j]);
			}
		}
		//passes label
		passesLabel.setOpaque(true);
		passesLabel.setFont(FONT);
		passesLabel.setBackground(Color.LIGHT_GRAY);
		passesLabel.setBounds(190, 70, 200, 40);
		passesLabel.repaint();
		//pass button
		passButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(isEnabled)
					buttonPressed=0;
			}
		});
		passButton.setBounds(30, 70, 150, 40);
		passButton.repaint();
		//play button
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(isEnabled)
					buttonPressed=1;
			}
		});
		playButton.setBounds(400, 70, 150, 40);
		playButton.repaint();
		//table
		tableScroll.setBounds(590, 0, 205, 110);
		tableScroll.repaint();
		//console
		console.setEditable(false);
		console.setText("Sevens");
		console.setFont(CONSOLE_FONT);
		console.setBounds(0, 0, 580, 60);
		
		content.add(tableScroll);
		content.add(console);
		content.add(playButton);
		content.add(passButton);
		content.add(passesLabel);
		content.add(cardPanel);
		return content;
	}
	
	public static void updateUI(){
		int passes=Table.getCurrentPlayer().passes;
		passesLabel.setText("Passes remaining: "+passes);
		passesLabel.setBackground(passes==0?Color.RED:Color.LIGHT_GRAY);
		
		if(table==null)
			initTable();
		else
			updateTable();
	}
	
	public static void reset(){
		table=null;
		tableScroll.repaint();
		for(int i=1;i<=13;i++){
			for(int j=1;j<=4;j++){
				enableMiniCard(i, j, false);
			}
		}
	}
	
	public static void createCardButtons(){
		clear();
		for(int i=0;i<Table.getCurrentPlayer().myHand.getSize();i++){
			addCardButton(i);
		}
		cardPanel.repaint();
	}
	
	public static int getSelectedCard(){
		return selectedCard;
	}
	
	public static void clear(){
		//TODO use more, remove System methods?
		passesLabel.setText("");
		cardPanel.removeAll();
		System.runFinalization();
		System.gc();
		cardPanel.repaint();
		selectedCard=-1;
	}
	
	private static void addCardButton(int i){
		JButton button=new JButton();
		Card c=Table.getCurrentPlayer().myHand.getCard(i);
		button.setIcon(IO.getImageIcon(c));
//		if(Table.getField().isPlaceable(c))TODO
		button.addActionListener(selectAL);
		button.setActionCommand(Integer.toString(i));
		button.setBounds(i>=13?(i-13)*53+5:i*53+5, i>=13?50:0, 150, 210);
		button.repaint();
		cardPanel.add(button, 0);
	}
	
	private static void drawMiniCards(){
		for(int i=0;i<13;i++){
			for(int j=0;j<4;j++){
				miniCards[i][j]=new JLabel();
				JLabel label=miniCards[i][j];
				label.setBounds(i*55+40, j*55+120, 35, 50);
				label.repaint();
				label.setIcon(IO.getBlankIcon());
			}
		}
	}
	
	public static void enableMiniCard(int num, int suit, boolean enabled){
		int i=num-1;
		int j=suit-1;
		
		JLabel mini=miniCards[i][j];
		if(!enabled)
			mini.setIcon(IO.getBlankIcon());
		else{
			mini.setIcon(IO.getMiniIcon(i, j)); 
		}
		mini.repaint();
	}
	
	public static void updateTable(){
		for(int i=0;i<Table.getNumPlayers();i++){
			if(Table.getPlayer(i).isOut)
				table.getModel().setValueAt("OUT", i, 0);
			else
				table.getModel().setValueAt(Table.getPlayer(i).name, i, 0);
			table.getModel().setValueAt(Table.getPlayer(i).myHand.getSize(), i, 1);
			table.getModel().setValueAt(Table.getPlayer(i).passes, i, 2);
		}
		table.repaint();
	}
	
	private static void initTable(){
		String[] columnTitles=new String[]{"Name", "Cards","Passes"};
		Object[][] tableData=new Object[Table.getNumPlayers()][3];
		for(int i=0;i<Table.getNumPlayers();i++){
			tableData[i][0]=Table.getPlayer(i).name;
			tableData[i][1]=Table.getPlayer(i).myHand.getSize();
			tableData[i][2]=Table.getPlayer(i).passes;
		}
		table=new JTable(tableData, columnTitles);
		table.setRowHeight(20);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.repaint();
		tableScroll.setViewportView(table);
	}
	
	public static void setConsole(String s){
		console.setText(s);
	}
	
	private static ActionListener selectAL=new ActionListener(){
		public void actionPerformed(ActionEvent ae) {
			int i=Integer.parseInt(ae.getActionCommand());
			Hand currentHand=Table.getCurrentPlayer().myHand;
			boolean isAlreadySelected=selectedCard==i;
			//reset icon
			if(selectedCard!=-1)
				((JButton) cardPanel.getComponent(currentHand.getSize()-selectedCard-1)).setIcon(IO.getImageIcon(currentHand.getCard(selectedCard)));
			selectedCard=isAlreadySelected ? -1 : i;
			setConsole(isAlreadySelected?"No card selected":"Selected card: "+currentHand.getCard(selectedCard).getName());
			((JButton) cardPanel.getComponent(currentHand.getSize()-i-1)).setIcon(isAlreadySelected?IO.getImageIcon(currentHand.getCard(i)):IO.getInvertedIcon(currentHand.getCard(i)));
		}
	};
	
	@SuppressWarnings("serial")
	private static class ImagePanel extends JPanel{
		private Image image;
		public void setImage(Image i){
			image=i;
		}
		@Override
		protected void paintComponent(Graphics g) {
			if(image!=null)
				g.drawImage(image, 0, 0, null);
			else
				super.paintComponent(g);
		}
	}
}
