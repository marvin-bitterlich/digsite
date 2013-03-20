package gameData;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import recources.SingletonWorker;

public class InventoryPage {
	private Object[][] items;
	private int itemWidth;
	private int itemHeight;
	public InventoryPage(Object[][] invPage,int itemwidth,int itemheight) {
		this.items = invPage;
		this.itemWidth = itemwidth;
		this.itemHeight = itemheight;
	}

	public void draw(Graphics g,int highlighted, int mouseoverMarker) {
		g.setFont(SingletonWorker.gameProperties().gameFont());
		for (int i = 0; i < items.length; i++) {
			Object[] item = items[i];
			if(highlighted == i){
				Color tempc = g.getColor();
				g.setColor(Color.yellow);
				g.fillRoundRect((int) item[0]-5, (int) item[1]-5, itemWidth+10, itemHeight+10, 10, 10);
				g.setColor(tempc);
			}else if(mouseoverMarker == i){
				Color tempc = g.getColor();
				g.setColor(Color.cyan);
				g.fillRoundRect((int) item[0]-4, (int) item[1]-4, itemWidth+8, itemHeight+8, 10, 10);
				g.setColor(tempc);
			}
			if((int)item[4] != 0 && (int)item[5] > 0){
				g.drawImage((BufferedImage) item[2], (int) item[0], (int) item[1],
						null);
				String s = Integer.toString((int)item[5]);
				int w = ((int)item[0])+itemWidth-15-g.getFontMetrics().charsWidth(s.toCharArray(), 0, s.length());
				int h = ((int)item[1])+itemHeight-10;
				g.drawString(s, w , h);
			}else{
				Color tempc = g.getColor();
				g.setColor(Color.gray);
				g.fillRoundRect((int) item[0], (int) item[1], itemWidth, itemHeight, 10, 10);
				g.clearRect((int)item[0]+4, (int)item[1]+4, itemWidth-8, itemHeight-8);
				g.setColor(tempc);
			}
			

		}

	}

	public Object[][] getItems() {
		return items;
	}

	public void setItems(Object[][] items) {
		this.items = items;
	}
}
