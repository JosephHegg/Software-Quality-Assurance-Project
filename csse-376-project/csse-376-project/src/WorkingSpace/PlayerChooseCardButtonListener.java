package WorkingSpace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerChooseCardButtonListener implements ActionListener {

	String cardType;
	ArrayList<String> selectedCards;

	public PlayerChooseCardButtonListener(String cardType, ArrayList<String> selectedCards) {
		this.cardType = cardType;
		this.selectedCards = selectedCards;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.selectedCards.add(cardType);
	}

}