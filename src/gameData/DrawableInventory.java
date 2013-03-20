package gameData;

import gameView.GameWindow;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utilities.ImageUtil;

public class DrawableInventory extends UIItem {

	private int rows, cells, itemWidth, itemHeight, currentPage = 0;
	private double relativeDistanceX, relativeDistanceY;
	private RelativeBoxPosition relativeFirstBox;
	private ArrayList<InventoryPage> pageList;
	private ClickPosition backBtn;
	private ClickPosition fwdBtn;

	private ClickPosition[] equipmentBoxes = new ClickPosition[9];
	private int selectedEntry = -1;

	public DrawableInventory(int startX, int startY, int endX, int endY, String src,
			int rows, int cells, RelativeBoxPosition relativeFirstBox,
			double relativeDistanceX, double relativeDistanceY,
			RelativeBoxPosition relativeBackBtn,
			RelativeBoxPosition relativeFwdBtn,
			RelativeBoxPosition[] equipmentBoxes) {
		super(startX, startY, endX, endY, src);
		this.rows = rows;
		this.cells = cells;
		this.relativeDistanceX = relativeDistanceX;
		this.relativeDistanceY = relativeDistanceY;
		this.backBtn = new ClickPosition(relativeBackBtn);
		this.fwdBtn = new ClickPosition(relativeFwdBtn);
		this.relativeFirstBox = relativeFirstBox;

		int currentItemCategory = 0;
		for (RelativeBoxPosition relBox : equipmentBoxes) {
			this.equipmentBoxes[currentItemCategory] = new ClickPosition(relBox);
			currentItemCategory++;
		}

		itemWidth = (int) (GameWindow.getWindowWidth()
				* relativeFirstBox.getRelativeEndX() - GameWindow
				.getWindowWidth() * relativeFirstBox.getRelativeStartX());
		itemHeight = (int) (GameWindow.getWindowHeight()
				* relativeFirstBox.getRelativeEndY() - GameWindow
				.getWindowHeight() * relativeFirstBox.getRelativeStartY());
	}

	@Override
	public void process(long duration) {
		if(GameWindow.getGameData().getGameSessionData().getActivePlayer().getInventory().hasChanged()){
			this.pageList = this.createInventoryPages();
			if (this.currentPage >= this.pageList.size()) {
				this.currentPage = 0;
			} else if (this.currentPage < 0) {
				this.currentPage = this.pageList.size() - 1;
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		int mousex = GameWindow.getGameData().getGameSessionData().getActivePlayer().getMouseX();
		int mousey = GameWindow.getGameData().getGameSessionData().getActivePlayer().getMouseY();

		//		if (backBtn.isInRange(mousex, mousey)) {
		//			currentPage--;
		//			return;
		//		} else if (fwdBtn.isInRange(mousex, mousey)) {
		//			currentPage++;
		//			return;
		//		}

		//		for (int category = 0; category < equipmentBoxes.length; category++) {
		//			ClickPosition clickBox = equipmentBoxes[category];
		//			if (clickBox.isInRange(mousex, mousey)) {
		//				
		//			}
		//		}
		int mouseoverMarker = -1;
		for (int invPageEntry = 0; invPageEntry < pageList.get(currentPage).getItems().length; invPageEntry++) {
			Object[] item = pageList.get(currentPage).getItems()[invPageEntry];
			ClickPosition currentItemBox = (ClickPosition) (item[3]);
			if (currentItemBox.isInRange(mousex, mousey)) {
				mouseoverMarker = invPageEntry;
			}
		}


		int entry = selectedEntry % 30;
		int page = selectedEntry / 30;
		if(page == currentPage){
			this.pageList.get(this.currentPage).draw(g,entry,mouseoverMarker);
		}else{
			this.pageList.get(this.currentPage).draw(g,-1,mouseoverMarker);
		}
		this.drawEquipedItems(g);
	}

	private void drawEquipedItems(Graphics g) {

		// TODO in draw oder GameControllerThread

		int currentItemCategory = 0;
		for (int item : GameWindow.getGameData().getGameSessionData()
				.getActivePlayer().getEquiped()) {
			if (item != Integer.MAX_VALUE) {
				Image itemImage = ItemManager.getItem(item).getSprite()
						.getImage();

				int width = (int) (equipmentBoxes[currentItemCategory]
						.getEndX() - equipmentBoxes[currentItemCategory]
								.getStartX());
				int height = (int) (equipmentBoxes[currentItemCategory]
						.getEndY() - equipmentBoxes[currentItemCategory]
								.getStartY());

				int startX = (int) (equipmentBoxes[currentItemCategory]
						.getStartX());
				int startY = (int) (equipmentBoxes[currentItemCategory]
						.getStartY());

				// System.out.println("\r\n\r\nitemCategory: "
				// + currentItemCategory);
				// System.out
				// .println("relativeXValue: "
				// + (equipmentBoxes[currentItemCategory]
				// .getRelativeEndX() - equipmentBoxes[currentItemCategory]
				// .getRelativeStartX()));
				// System.out.println("startX: " + startX);
				// System.out.println("startY: " + startY);
				// System.out.println("item: " + item);

				itemImage = ImageUtil.resizeImage((BufferedImage) itemImage,
						width, height);

				g.drawImage(itemImage, startX, startY, null);

			}
			currentItemCategory++;

		}
	}

	public ArrayList<InventoryPage> createInventoryPages() {
		int row = 0;
		int cell = 0;
		int index = 0;

		ArrayList<InventoryPage> pageList = new ArrayList<InventoryPage>();

		Object[][] invPage;
		if (GameWindow.getGameData().getGameSessionData().getActivePlayer()
				.getInventory().getSize() >= (rows + 1) * (cells + 1)) {
			invPage = new Object[(rows + 1) * (cells + 1)][6];
		} else {
			invPage = new Object[GameWindow.getGameData().getGameSessionData()
			                     .getActivePlayer().getInventory().getSize()][6];
		}

		for (DrawableItemStack is : GameWindow.getGameData().getGameSessionData()
				.getActivePlayer().getInventory().getInventoryList()) {

			int startX = (int) (GameWindow.getWindowWidth()
					* relativeFirstBox.getRelativeStartX()
					+ GameWindow.getWindowWidth() * relativeDistanceX * cell + itemWidth
					* cell);
			int startY = (int) (GameWindow.getWindowHeight()
					* relativeFirstBox.getRelativeStartY()
					+ GameWindow.getWindowHeight() * relativeDistanceY * row + itemHeight
					* row);

			int endX = startX + itemWidth;
			int endY = startY + itemHeight;

			invPage[index][0] = startX;
			invPage[index][1] = startY;

			invPage[index][2] = ImageUtil.resizeImage(
					(BufferedImage) is.getSprite()
					.getImage(), itemWidth, itemHeight);

			invPage[index][3] = new ClickPosition(startX, startY, endX, endY);

			invPage[index][4] = is.getItemId();
			invPage[index][5] = is.getAmount();

			if (row == this.rows && cell == this.cells) {
				if (invPage.length != 0){
					pageList.add(new InventoryPage(invPage,itemWidth,itemHeight));
				}

				if (GameWindow.getGameData().getGameSessionData()
						.getActivePlayer().getInventory().getSize()
						- ((pageList.size() * ((rows + 1) * (cells + 1)))) >= (rows + 1)
						* (cells + 1)) {

					if (GameWindow.getGameData().getGameSessionData()
							.getActivePlayer().getInventory().getSize()
							- ((pageList.size() * ((rows + 1) * (cells + 1)))) > 0) {

						// System.out.println("Seite: " + currentPage);
						// System.out.println("Gesamt: "
						// + GameWindow.getGameData().getGameSessionData()
						// .getActivePlayer().getInventory()
						// .size());
						// System.out
						// .println("Bisherige: "
						// + (pageList.size() * ((rows + 1) * (cells + 1))));
						// System.out
						// .println("Rest: "
						// + (GameWindow.getGameData()
						// .getGameSessionData()
						// .getActivePlayer()
						// .getInventory().size() - ((pageList
						// .size() * ((rows + 1) * (cells + 1))))));

						invPage = new Object[(rows + 1) * (cells + 1)][6];
					}

				} else {

					invPage = new Object[GameWindow.getGameData()
					                     .getGameSessionData().getActivePlayer()
					                     .getInventory().getSize()
					                     - ((pageList.size() * ((rows + 1) * (cells + 1))))][6];

				}
				row = 0;
				cell = 0;
				index = 0;
			} else if (cell == this.cells) {
				cell = 0;
				row++;
				index++;
			} else {
				cell++;
				index++;
			}
		}
		pageList.add(new InventoryPage(invPage,itemWidth,itemHeight));

		return pageList;
	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseKlicked(MouseEvent e) {
		if (backBtn.isInRange(e.getX(), e.getY())) {
			currentPage--;
			return;
		} else if (fwdBtn.isInRange(e.getX(), e.getY())) {
			currentPage++;
			return;
		}

		// System.out.println("Mausklick bei: " + e.getX() + ":x y:" +
		// e.getY());

		for (int category = 0; category < equipmentBoxes.length; category++) {
			ClickPosition clickBox = equipmentBoxes[category];
			if (clickBox.isInRange(e.getX(), e.getY())) {

				unEquipItem(category);

			}
		}
		boolean rightclick = false;
		if(e.getButton() == MouseEvent.BUTTON3){
			rightclick = true;
		}
		for (int invPageEntry = 0; invPageEntry < pageList.get(currentPage).getItems().length; invPageEntry++) {
			Object[] item = pageList.get(currentPage).getItems()[invPageEntry];
			ClickPosition currentItemBox = (ClickPosition) (item[3]);
			if (currentItemBox.isInRange(e.getX(), e.getY())) {
				if(rightclick){
					if((int)item[4]==11 || (int)item[4]==12){
						int x = GameProperties.getPlayerBlockX();
						int y = GameProperties.getPlayerBlockY();
						GameWindow.getGameData().getNetworkHandlerThread().placeLadder(x,y,(int)item[4]);
					}else{
						GameWindow.getGameData().getNetworkHandlerThread().craftItem((int)item[4]);
					}
				}else{
					if(selectedEntry < 0){
						selectedEntry = currentPage*30 + invPageEntry;
					}else{
						change(selectedEntry,currentPage*30 + invPageEntry);
						selectedEntry = -1;
					}
				}

			}
		}
	}

	@SuppressWarnings("unused")
	private void equipItem(int item) {
		int itemCategory = ItemManager.getItem(item).getCategory();
		if (itemCategory != Item.ITEM_CATEGORY_OTHERS) {

			GameWindow.getGameData().getGameSessionData().getActivePlayer()
			.getEquiped()[itemCategory] = item;

		}
	}

	private void unEquipItem(int category) {
		GameWindow.getGameData().getGameSessionData().getActivePlayer()
		.getEquiped()[category] = Integer.MAX_VALUE;
	}

	private void change(int item1,int item2){
		GameWindow.getGameData().getNetworkHandlerThread().changeItemsInInventory(item1,item2);
	}

}