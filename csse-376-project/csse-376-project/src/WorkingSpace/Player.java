package WorkingSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.naming.directory.InvalidAttributesException;

public class Player {

	int playerID, currentArmyCount;
	HashMap<String, Integer> cardHandMap;
	public Set<Country> ownedCountries;
	public boolean isDead;

	public Player(int playerID) {
		this.playerID = playerID;
		this.currentArmyCount = 0;
		this.ownedCountries = new HashSet<Country>();
		this.isDead = false;
		this.cardHandMap = new HashMap<String, Integer>();
		initializeHandMap();
	}

	public void giveArmies(int armiesToGive) {

		this.currentArmyCount += armiesToGive;
	}

	public void removeArmies(int armiesToTake) {
		if (armiesToTake < 0) {
			throw new IllegalArgumentException();

		} else if (this.currentArmyCount - armiesToTake < 1) {
			this.currentArmyCount = 0;
		} else {
			this.currentArmyCount -= armiesToTake;
		}

	}

	public void giveCard(String cardType) {
		int cardTypeCount = this.cardHandMap.get(cardType);
		this.cardHandMap.put(cardType, ++cardTypeCount);

	}

	public void initializeHandMap() {
		String[] cardTypes = { "Infantry", "Cavalry", "Artillery", "Wild" };

		for (String cardName : cardTypes) {
			this.cardHandMap.put(cardName, 0);
		}

	}

	public HashMap<String, Integer> getHandMap() {
		return this.cardHandMap;
	}

	public int getCardAmountInHand() {
		int value = 0;

		for (String key : cardHandMap.keySet()) {
			value += cardHandMap.get(key);
		}

		return value;
	}

	public int getArmyCount() {
		return currentArmyCount;
	}

	public int getPlayerID() {
		return playerID;
	}

	public HashSet<Country> getOwnedCountries() {
		return (HashSet<Country>) this.ownedCountries;
	}

	public int getTotalCardCountInHand() {
		int sum = 0;

		for (String key : this.cardHandMap.keySet()) {
			sum += this.cardHandMap.get(key);
		}

		return sum;
	}

	public boolean checkIfPossessingValidHand() {

		int numberOfNonExistingCardTypes = 0;

		for (String key : this.cardHandMap.keySet()) {
			if (this.cardHandMap.get(key) == 3) {
				return true;
			} else if (this.cardHandMap.get(key) == 2 && !key.equals("Wild")) {
				if (this.cardHandMap.get("Wild") >= 1) {
					return true;
				}
			}

			if (this.cardHandMap.get(key) < 1) {
				numberOfNonExistingCardTypes++;
			}

			if (this.cardHandMap.get(key) == 2 && key.equals("Wild")) {
				return true;
			}

		}

		if (numberOfNonExistingCardTypes >= 2) {
			return false;
		}

		return true;
	}

	public void addCountry(Country countryToAdd) {
		this.ownedCountries.add(countryToAdd);
		countryToAdd.ownerID = this.playerID;

	}

	public void removeSelectedCardsFromHand(ArrayList<String> cardsToRemoveFromHand, Deck deck) {
		for (String currentCard : cardsToRemoveFromHand) {
			int oldValue = this.cardHandMap.get(currentCard);
			this.cardHandMap.put(currentCard, oldValue - 1);
			deck.discardCard(currentCard);
		}
	}
}
