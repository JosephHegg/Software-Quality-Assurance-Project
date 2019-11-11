package WorkingSpace;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class Placement {
	private GUI gui;
	public int armiesPlacedInSetup = 0;
	public int NUM_COUNTRIES = 42;
	private HashMap<Integer, Country> countries;
	private ArrayList<Player> players;
	public int[][] continentsIDs = { { 12, 14, 25, 30, 34, 35, 39 }, // Europe
			{ 7, 8, 11, 20, 24, 33 }, // Africa
			{ 1, 2, 5, 10, 13, 26, 27, 29, 40 }, // NA
			{ 3, 4, 28, 37 }, // SA
			{ 9, 16, 23, 38 }, // Australia
			{ 0, 6, 15, 17, 18, 19, 21, 22, 31, 32, 36, 41 } }; // Asia
	private ResourceBundle bundle = ResourceBundle.getBundle("res.strings");

	public Placement(GUI gui, HashMap<Integer, Country> countries, ArrayList<Player> players) {
		this.gui = gui;
		this.countries = countries;
		this.players = players;
	}

	public int placementTurn(Player player, int armiesEarnedFromDrawPhase) {

		int numArmies = calculateStartingArmies(player);
		numArmies += armiesEarnedFromDrawPhase;

		for (int i = 0; i < numArmies; i++) {
			notifyNumberOfArmiesLeft(player.playerID, (numArmies - i));
			placeArmyOnOwnedCountry(player.playerID);
		}

		return numArmies;
	}

	private void notifyNumberOfArmiesLeft(int playerID, int numArmiesLeft) {
		this.gui.updatePlayerConsole(playerID,
				MessageFormat.format(bundle.getString("placementNumArmiesLeftString"), playerID, numArmiesLeft));
	}

	public int calculateStartingArmies(Player player) {
		HashSet<Country> playerOwnedCountries = player.getOwnedCountries();

		int numCountries = playerOwnedCountries.size();
		numCountries = validateNumberOfCountries(numCountries);

		int numArmies = numCountries / 3;

		numArmies += getNumArmiesForEuropeAndAfrica(player);

		return numArmies;
	}

	private int getNumArmiesForEuropeAndAfrica(Player player) {
		int numExtraArmies = 0;

		for (int i = 0; i < continentsIDs.length; i++)
			numExtraArmies += calcExtraArmies(continentsIDs[i], player);

		return numExtraArmies;
	}

	private int calcExtraArmies(int[] continentIDs, Player player) {
		Set<Country> countries = player.ownedCountries;

		int continentValue = calcContinentValue(continentIDs);

		boolean ownsContinent = true;
		for (Integer i : continentIDs) {
			boolean ownsCurrentCountry = false;
			for (Country c : countries) {
				if (c.countryID == i)
					ownsCurrentCountry = true;
			}
			if (!ownsCurrentCountry)
				return 0;
		}

		return continentValue;
	}

	public int calcContinentValue(int[] continentIDs) {
		int numCountries = continentIDs.length;

		switch (numCountries) {
		case 12:
			return 7;
		case 9:
			return 5;
		case 7:
			return 5;
		case 6:
			return 3;
		case 4:
			return 2;
		default:
			return 0;
		}
	}

	private int validateNumberOfCountries(int numCountries) {
		if (numCountries < 1)
			throw new IllegalArgumentException("Provided Player owns no countries, it shouldn't be in the game");
		else if (numCountries < 12)
			return 9;
		else if (numCountries > 42)
			throw new NotEnoughCountriesException("Player owns more than" + numCountries + "countries");
		else
			return numCountries;
	}

	public void placeArmyOnOwnedCountry(int ownerID) {
		int countryID = selectValidCountry(ownerID, false);

		Country selectedCountry = this.countries.get(countryID);
		gui.updateArmies(selectedCountry.getArmyCount() + 1, countryID);
		selectedCountry.addArmies(1);
	}

	public boolean setupArmies(int ownerID) {
		int countryID = selectValidCountry(ownerID, true);

		boolean armyPlaced = false;
		Country selectedCountry = this.countries.get(countryID);
		if (armiesPlacedInSetup < NUM_COUNTRIES) {
			gui.changeCountryControl(1, ownerID, countryID);

			if (selectedCountry.checkIfAbleToTakeOverCountry(ownerID)) {
				selectedCountry.ownerID = ownerID;
				this.players.get(ownerID - 1).ownedCountries.add(selectedCountry);
			}
			armyPlaced = true;
		} else {
			gui.updateArmies(selectedCountry.getArmyCount() + 1, countryID);
		}
		this.armiesPlacedInSetup++;

		selectedCountry.addArmies(1);

		return armyPlaced;
	}

	public int selectValidCountry(int playerID, boolean isSetup) {
		boolean selected = false;
		int countryID = 0;

		while (!selected) {
			countryID = this.gui.selectCountry();
			if (isSetup) {
				selected = canPlaceArmyInSetup(countryID, playerID);
			} else {
				selected = canPlaceArmy(countryID, playerID);
			}
		}
		return countryID;
	}

	public boolean canPlaceArmyInSetup(int countryID, int ownerID) {
		boolean isAllowed = false;
		Country selectedCountry = this.countries.get(countryID);

		if (this.armiesPlacedInSetup < NUM_COUNTRIES) {
			if (selectedCountry.getOwnerID() == 99)
				isAllowed = true;
		} else {
			isAllowed = canPlaceArmy(countryID, ownerID);
		}

		return isAllowed;
	}

	private boolean canPlaceArmy(int countryID, int ownerID) {
		Country country = this.countries.get(countryID);
		return country.getOwnerID() == ownerID;
	}

}
