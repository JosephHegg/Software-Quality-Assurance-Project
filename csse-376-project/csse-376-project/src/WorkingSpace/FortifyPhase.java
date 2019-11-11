package WorkingSpace;

import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class FortifyPhase {

	Player player;

	int playerID;
	public int countryFortifySource;
	public int countryFortifyDestination;

	HashMap<Integer, Country> countryMap;
	HashMap<Integer, Country> actualMap;
	public boolean mockingInEffect = false;
	private ResourceBundle bundle = ResourceBundle.getBundle("res.strings");

	GUI gui;

	public FortifyPhase(int playerID, HashMap<Integer, Country> countryMap, GUI gui) throws NullPointerException {
		if (playerID < 1) {
			throw new IndexOutOfBoundsException();
		} else if (playerID > 6) {
			throw new IndexOutOfBoundsException();
		}
		this.playerID = playerID;
		this.actualMap = countryMap;
		this.countryMap = countryMap;
		this.gui = gui;

	}

	public int getPlayerID() {
		return this.playerID;
	}

	public HashMap<Integer, Country> getCountryMap() {
		return this.countryMap;

	}

	public void filterValidCountriesForFortify() {
		HashMap<Integer, Country> filteredCountryMap = new HashMap<Integer, Country>();

		for (int key : actualMap.keySet()) {
			if (actualMap.get(key).ownerID == this.playerID) {
				filteredCountryMap.put(key, actualMap.get(key));
			}
		}

		this.countryMap = filteredCountryMap;

	}

	public void fortifyCountry(Country sourceCountry, Country destinationCountry, int armiesToMoveToDestination) {
		int sourceArmies = sourceCountry.getArmyCount();
		int destinationArmies = destinationCountry.getArmyCount();

		if (sourceArmies - armiesToMoveToDestination <= 0) {
			throw new IllegalArgumentException();
		}

		sourceCountry.currentArmies = sourceArmies - armiesToMoveToDestination;
		destinationCountry.currentArmies = destinationArmies + armiesToMoveToDestination;

	}

	public void selectCountriesToFortify() {
		this.countryFortifySource = gui.selectSourceCountryForFortify();
		this.countryFortifyDestination = gui.selectDestinationCountryForFortify();
	}

	public boolean checkSourceCountryOwned() {
		if (this.countryMap.containsKey(countryFortifySource)) {
			return true;
		}
		return false;
	}

	public boolean checkDestinationCountryOwned() {
		if (this.countryMap.containsKey(countryFortifyDestination)) {
			return true;
		}
		return false;
	}

	public boolean checkPlayerCountryNeighborsForFortify(Country sourceCountry, Country destinationCountry) {

		if (mockingInEffect) {
			return true;
		}

		if (sourceCountry.getNeighboringCountries().contains(destinationCountry.countryID)) {
			if (destinationCountry.getNeighboringCountries().contains(sourceCountry.countryID)) {
				return true;
			}
		}
		return false;
	}

	public void takeFortifyAction() {

		filterValidCountriesForFortify();

		selectCountriesToFortify();

		Country countrySource = this.actualMap.get(this.countryFortifySource);
		Country countryDestination = this.actualMap.get(this.countryFortifyDestination);

		boolean validNeighbors = checkPlayerCountryNeighborsForFortify(countrySource, countryDestination);

		boolean validSource = false, validDestination = false;

		while ((validSource = !checkSourceCountryOwned()) || (validDestination = !checkDestinationCountryOwned())
				|| (validNeighbors = !validNeighbors)) {

			checkConditionsOfInputEntry(validSource, validDestination, validNeighbors);

			selectCountriesToFortify();

			countrySource = this.countryMap.get(this.countryFortifySource);
			countryDestination = this.countryMap.get(this.countryFortifyDestination);
		}

		boolean hasNotEnteredCorrectValue = true;

		int numArmiesToTransfer = 0;

		while (hasNotEnteredCorrectValue) {
			numArmiesToTransfer = gui.askForNumArmiesToTransfer();
			if (countrySource.getArmyCount() > numArmiesToTransfer) {
				hasNotEnteredCorrectValue = false;
			}

			if (hasNotEnteredCorrectValue) {
				this.gui.showMessage(bundle.getString("fortifyHasNotEnteredCorrectValue"));
			}
		}

		fortifyCountry(countrySource, countryDestination, numArmiesToTransfer);

		this.gui.updateArmies(countrySource.getArmyCount(), countrySource.countryID);

		this.gui.updateArmies(countryDestination.getArmyCount(), countryDestination.countryID);

	}

	public String checkConditionsOfInputEntry(boolean validSource, boolean validDestination, boolean validNeighbors) {

		String type = "";

		if (validSource && validDestination == false && validNeighbors == false) {
			type = "Bad Source";
			gui.showMessage(bundle.getString("fortifySourceCountryNotOwned"));
		} else if (validSource == false && validDestination && validNeighbors == false) {
			type = "Bad Destination";
			gui.showMessage(bundle.getString("fortifyDestinationCountryNotOwned"));
		} else if (validSource && validDestination) {
			type = "Bad Source And Destination";
			gui.showMessage(bundle.getString("fortifyBothCountriesNotOwned"));
		} else if (validSource == false && validDestination == false && validNeighbors == false) {
			type = "Invalid Neighbors";
			gui.showMessage(bundle.getString("fortifyCountriesAreNotNeighbors"));
		} else {
			type = "None Of Above";
			gui.showMessage(bundle.getString("fortifyIncorrectInputs"));
		}

		return type;
	}

	public boolean askForFortify() {
		return gui.askToFortify();
	}
}
