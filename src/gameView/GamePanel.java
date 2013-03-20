package gameView;

import gameData.GameData;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4348065561147440438L;




	public GamePanel(GameData gd) {
		super();
		gd.setGamePanel(this);

	}

	@Override
	protected void paintComponent(Graphics g) {
		System.out.println("paint");

	}

}
