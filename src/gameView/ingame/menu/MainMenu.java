package gameView.ingame.menu;


import java.awt.Graphics;
import java.awt.event.MouseEvent;

import singleton.SingletonWorker;


public class MainMenu extends UIItem {

	private int textwidth,textheight;

	public MainMenu(int startX, int startY, int endX, int endY, String src) {
		super(startX, startY, endX, endY, src);
		// TODO Auto-generated method stub
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.drawString("Exit", 250 , 250);
		textwidth = g.getFontMetrics().charsWidth("Exit".toCharArray(), 0, "Exit".length());
		textheight = g.getFontMetrics().getHeight()*4;
	}

	
	
	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseKlicked(MouseEvent e) {
		if(e.getX() > (250) && e.getY() > (250-textheight/2) && e.getX() < (250 + textwidth) && e.getY() < (250)){
			SingletonWorker.logger().info("Shutting down");
			System.exit(0);
		}
			
	}

	@Override
	public void process(long duration) {
		// TODO Auto-generated method stub
		
	}

}
