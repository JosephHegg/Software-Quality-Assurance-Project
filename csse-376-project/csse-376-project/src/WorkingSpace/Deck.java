package WorkingSpace;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	final int NUM_OF_EACH_CARD_TYPES = 14;
	private ArrayList<String> cards = new ArrayList<String>();
	private ArrayList<String> discardPile = new ArrayList<String>();
	private ArrayList<String> cardTypes = new ArrayList<String>();
	public Random r = new Random();

	public Deck() {
		initializeDeck();
		cardTypes.add("Infantry");
		cardTypes.add("Artillery");
		cardTypes.add("Cavalry");
		cardTypes.add("Wild");
	}

	private void initializeDeck() {
		for (int i = 0; i < NUM_OF_EACH_CARD_TYPES; i++) {
			cards.add("Infantry");
			cards.add("Artillery");
			cards.add("Cavalry");
		}

		cards.add("Wild");
		cards.add("Wild");
	}

	public String pickRandomType() {
		int randInt = r.nextInt(4);

		if (randInt == 0) {
			return "Infantry";
		} else if (randInt == 1) {
			return "Cavalry";
		} else if (randInt == 2) {
			return "Artillery";
		} else {
			return "Wild";
		}
	}

	public String drawCard() {
		if (cards.isEmpty()) {
			if (discardPile.isEmpty())
				throw new DeckAndDiscardEmptyException();

			shuffleInDiscardedCards();
		}
		String chosenType = pickRandomType();
		while (!cards.contains(chosenType))
			chosenType = pickRandomType();

		cards.remove(chosenType);

		return chosenType;
	}

	public void shuffleInDiscardedCards() {
		for (String card : discardPile)
			cards.add(0, card);
	}

	public void discardCard(String type) {
		if (!cardTypes.contains(type))
			throw new IllegalArgumentException();

		discardPile.add(type);
	}
}
