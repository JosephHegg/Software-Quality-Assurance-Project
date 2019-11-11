package WorkingSpace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowCardsButtonListener implements ActionListener {

	GUI gui;

	public ShowCardsButtonListener(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.showCardsToChoose();
	}

}