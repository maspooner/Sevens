import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Dialogs {
	//TODO add help
	private static final String aboutText="Sevens v"+Sevens.version+"\n\nProject Timeline: July 24, 2013-now\n\nMade by:\n   coder:   Matt Spooner\n   graphic artist:   Emily Spooner";
	
	public static int showSetupDialog(){
		Object[] options=new Object[]{2,3,4,5,6,7,8};
		Integer choice=(Integer) JOptionPane.showInputDialog(GUI.frame, "How many players?", "Game Setup", JOptionPane.PLAIN_MESSAGE, null, options, 3);
		return choice!=null ? choice : 0;
	}
	
	public static void showAboutDialog(){
		JOptionPane.showMessageDialog(GUI.frame, aboutText, "About Sevens v"+Sevens.version, JOptionPane.INFORMATION_MESSAGE, null);
	}
	
	public static void showPlayerDialog(){
		final JDialog d=new JDialog(GUI.frame, "Player Setup");
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setModal(true);
		d.setResizable(false);
		d.setLocation(GUI.frame.getLocation());
		
		JPanel panel=new JPanel();
		panel.setLayout(null);
		JTextField jft;
		JCheckBox jcb;
		int height=110;
		for(int i=0;i<Table.getNumPlayers();i++){
			jft=new JTextField("Player "+(i+1));
			jcb=new JCheckBox("Computer");
			jft.setBounds(50, i*55, 200, 50);
			jft.repaint();
			panel.add(jft);
			jcb.setBounds(300, i*55, 200, 50);
			jcb.repaint();
			panel.add(jcb);
			height+=50;
		}
		JButton okay=new JButton("Done");
		okay.setBounds(150, height-70, 150, 40);
		okay.repaint();
		okay.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
			}
		});
		panel.add(okay);
		
		d.getContentPane().add(panel);
		d.setSize(new Dimension(450,height));
		d.setVisible(true);
		
		int iter=-1;
		String name="";
		for(int i=0;i<panel.getComponentCount();i++){
			Component comp=panel.getComponent(i);
			if(i!=panel.getComponentCount()-1){
				if(i%2==0){
					JTextField foo=(JTextField) comp;
					name=foo.getText();
				}
				else{
					iter++;
					JCheckBox bar=(JCheckBox) comp;
					if(bar.isSelected()){
						Table.setPlayer(iter, new Computer("Computer "+(iter+1)));
					}
					else{
						Table.setPlayer(iter, new Human(name.equals("") ? "Player "+(iter+1) : name));
					}
				}
			}
		}
		d.setVisible(false);
	}
	
	public static void showTurnDialog(String name){
		JOptionPane.showMessageDialog(GUI.frame, "It\'s "+name+"\'s turn", "Turn Change", JOptionPane.INFORMATION_MESSAGE, null);
	}
	public static void showWinDialog(String name){
		JOptionPane.showMessageDialog(GUI.frame, name+" wins!", "Gave Over", JOptionPane.INFORMATION_MESSAGE, null);
	}
}
