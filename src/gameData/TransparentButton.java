package gameData;

import javax.swing.JButton;

public class TransparentButton extends JButton {
	private static final long serialVersionUID = 8225611310676742450L;

	public TransparentButton(String text) {
		super(text);
		setBorder(null);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
	}
}