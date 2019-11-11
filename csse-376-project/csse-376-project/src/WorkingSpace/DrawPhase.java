package WorkingSpace;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

public class DrawPhase {
	Player player;
	Deck deck;

	int numSetsTurnedIn = 0;

	GUI gui;

	public DrawPhase(Player player, GUI gui, Deck deck) throws NullPointerException {

		this.player = player;
		this.gui = gui;
		this.deck = deck;

	}

	public int getPlayerID() {
		return this.player.playerID;
	}

	public void drawCard() {
		String resultCard = deck.drawCard();

		deck.discardCard(resultCard);

		player.giveCard(resultCard);
	}

	public int checkValidHand() {

		boolean playerTurnIn = false;

		int armiesToReturn = 0;

		if (player.checkIfPossessingValidHand()) {
			if (player.getCardAmountInHand() < 5) {
				playerTurnIn = gui.askToRedeemCards();
			} else {
				gui.demandToRedeemCards();
				playerTurnIn = true;
			}
		}

		if (playerTurnIn) {
			playerRedeemCards();
			armiesToReturn = getArmiesForCards();

		}

		return armiesToReturn;

	}

	public boolean playerRedeemCards() {
		ArrayList<String> selectedCards = gui.showCardsToChoose();
		while (selectedCards.size() < 3) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		gui.disposeCardFrame();

		if (hasSelectedValidSet(selectedCards)) {
			player.removeSelectedCardsFromHand(selectedCards, deck);
			return true;
		} else {
			return playerRedeemCards();
		}

	}

	public boolean hasSelectedValidSet(ArrayList<String> selectedCards) {

		String first = selectedCards.get(0);
		String second = selectedCards.get(1);
		String third = selectedCards.get(2);

		if (first.equals(second)) {
			if (first.equals(third)) {
				return true;
			}
		}

		if (!first.equals(third) && !second.equals(third) && !first.equals(second)) {
			return true;
		}

		if (selectedCards.contains("Wild")) {
			return true;
		}

		return false;
	}

	public int getArmiesForCards() {
		this.numSetsTurnedIn++;
		switch (this.numSetsTurnedIn) {
		case 1:
			return 4;
		case 2:
			return 6;
		case 3:
			return 8;
		case 4:
			return 10;
		case 5:
			return 12;
		default:
			return 15 + ((this.numSetsTurnedIn - 6) * 5);
		}

	}

	public int getPlayerHandSizeOfCardType(String cardValue) {
		return player.getHandMap().get(cardValue);
	}
}
