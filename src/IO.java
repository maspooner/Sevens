import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class IO {
	
	private static ImageIcon[][] cardIcons=new ImageIcon[13][4];
	private static ImageIcon[][] reverseCardIcons=new ImageIcon[13][4];
	private static ImageIcon[][] miniCardIcons=new ImageIcon[13][4];
	private static ImageIcon blank;
	private static final String FILE_PATH="/cards/";
	private static final String MINI_PATH="/cards/mini/";
	
	private static BufferedImage loadImage(String fileName, boolean isMini){
		BufferedImage i= null;
		try {
			String imageFileName=(isMini?MINI_PATH:FILE_PATH)+fileName+".png";
			i=ImageIO.read(IO.class.getResourceAsStream(imageFileName));
			//TODO change when debugging
//			i=ImageIO.read(new File(imageFileName));
		}catch(Exception e){
			System.err.println("Couldn't read file.");
			e.printStackTrace();
		}
		return i;
	}
	
	public static BufferedImage getImage(String s){
		return loadImage(s, false);
	}
	public static ImageIcon getImageIcon(Card c){
		int i=c.number-1;
		int j=c.suit-1;
		return cardIcons[i][j];
	}
	
	public static void loadCards(){
		for(int i=0;i<13;i++){
			for(int j=0;j<4;j++){
				String temp=new Card(i+1,j+1).getFileName();
				cardIcons[i][j]=new ImageIcon(loadImage(temp, false));
				reverseCardIcons[i][j]=new ImageIcon(invertColors(loadImage(temp, false)));
				miniCardIcons[i][j]=new ImageIcon(loadImage(temp, true));
			}
		}
		blank=new ImageIcon(getImage("blank"));
	}
	
	private static BufferedImage invertColors(BufferedImage i){
		for(int x=0;x<i.getWidth();x++){
			for(int y=0;y<i.getHeight();y++){
				int rgb=i.getRGB(x, y);
				Color c=new Color(rgb, true);
				c=new Color(255-c.getRed(),255-c.getGreen(),255-c.getBlue());
				i.setRGB(x, y, c.getRGB());
			}
		}
		return i;
	}
	
	public static ImageIcon getInvertedIcon(Card c){
		int i=c.number-1;
		int j=c.suit-1;
		return reverseCardIcons[i][j];
	}
	
	public static ImageIcon getMiniIcon(int i, int j){
		return miniCardIcons[i][j];
	}
	public static ImageIcon getBlankIcon(){
		return blank;
	}
}
