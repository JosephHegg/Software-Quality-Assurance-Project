package WorkingSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class AttackPhase {

	HashMap<Integer, Country> countries;
	GUI gui;
	int countryToAttackFrom;
	int countryToAttack;
	int currentPlayer;
	int attackingArmies;
	int defendingArmies;
	Random random;
	private ArrayList<Player> players;
	private ResourceBundle bundle = ResourceBundle.getBundle("res.strings");
	boolean hasMadeValidAttack = false;

	AttackPhase(HashMap<Integer, Country> countries, GUI gui, ArrayList<Player> players) {
		this.countries = countries;
		this.gui = gui;
		this.random = new Random();
		this.players = players;
	}

	boolean askForAttack() {
		return this.gui.askToAttack();
	}

	void selectCountriesForAttack() {
		this.countryToAttackFrom = this.gui.selectCountryToAttackFrom();

		this.countryToAttack = this.gui.selectCountryToAttack();
	}

	boolean checkAttackFromIsOwned() {
		int actualOwner = this.countries.get(this.countryToAttackFrom).getOwnerID();

		return this.currentPlayer == actualOwner;
	}

	boolean checkToAttackIsNotOwned() {
		int ownerOfCountry = this.countries.get(this.countryToAttack).getOwnerID();

		return this.currentPlayer != ownerOfCountry;
	}

	boolean checkToAttackIsAdjacent() {
		boolean isAdjacent = false;

		Country toAttack = this.countries.get(this.countryToAttack);

		for (Integer i : toAttack.neighboringCountryIDs) {
			if (i == this.countryToAttackFrom) {
				isAdjacent = true;
			}
		}

		return isAdjacent;
	}

	int getNumOfArmiesAttacking() throws IllegalNumberOfArmiesToAttackException {
		int armies = this.gui.askForNumArmiesToAttack();

		if (armies <= 0 || armies >= this.countries.get(this.countryToAttackFrom).currentArmies) {
			throw new IllegalNumberOfArmiesToAttackException();
		}

		this.attackingArmies = armies;
		this.defendingArmies = this.countries.get(this.countryToAttack).getArmyCount();
		return armies;
	}

	void makeAttack() {
		ArrayList<Integer> attackRolls = makeAttackRoll();
		ArrayList<Integer> defenceRolls = makeDefenseRoll();

		int numDiceToCheck = Math.min(defenceRolls.size(), attackRolls.size());

		for (int i = 0; i < numDiceToCheck; i++) {
			if (attackRolls.get(attackRolls.size() - (i + 1)) > defenceRolls.get(defenceRolls.size() - (i + 1))) {
				this.countries.get(this.countryToAttack).removeArmies(1);
				defendingArmies--;
			} else {
				this.countries.get(this.countryToAttackFrom).removeArmies(1);
				attackingArmies--;
			}
		}
	}

	private ArrayList<Integer> makeDefenseRoll() {
		ArrayList<Integer> defenceRolls = new ArrayList<>();

		int numOfDice = this.gui.askforNumDefenderDice();

		for (int i = 0; i < this.defendingArmies && i < numOfDice; i++) {
			defenceRolls.add(this.random.nextInt(6) + 1);
		}

		Collections.sort(defenceRolls);

		return defenceRolls;
	}

	private ArrayList<Integer> makeAttackRoll() {
		ArrayList<Integer> attackRolls = new ArrayList<>();

		int numOfDice = this.gui.askforNumAttackerDice();

		for (int i = 0; i < this.attackingArmies && i < numOfDice; i++) {
			attackRolls.add(this.random.nextInt(6) + 1);
		}

		Collections.sort(attackRolls);

		return attackRolls;
	}

	void changeCountryControl(int defendingOwnerID) {

		Country attackedCountry = this.countries.get(this.countryToAttack);
		this.players.get(defendingOwnerID - 1).ownedCountries.remove(attackedCountry);
		this.players.get(this.currentPlayer - 1).ownedCountries.add(attackedCountry);
	}

	void takeAttackAction(int playerNum) {
		this.currentPlayer = playerNum;

		while (!checkAttackFromIsOwned() || !checkToAttackIsAdjacent() || !checkToAttackIsNotOwned()) {
			selectCountriesForAttack();
		}

		boolean succeededInAttack = false;
		while (!succeededInAttack) {
			try {
				getNumOfArmiesAttacking();
				succeededInAttack = true;
			} catch (IllegalNumberOfArmiesToAttackException e) {
				this.gui.showMessage(bundle.getString("illegalNumArmiesToAttack"));
			}
		}

		int armies = this.attackingArmies;

		do {
			makeAttack();

			this.gui.updateArmies(this.countries.get(this.countryToAttack).getArmyCount(), this.countryToAttack);
			this.gui.updateArmies(this.countries.get(this.countryToAttackFrom).getArmyCount(),
					this.countryToAttackFrom);

		} while (this.attackingArmies > 0 && this.defendingArmies > 0 && this.gui.askToContinueAttack());

		if (this.defendingArmies == 0) {
			this.hasMadeValidAttack = true;
			int defendingOwnerID = this.countries.get(this.countryToAttack).ownerID;

			Country attackedCountry = this.countries.get(this.countryToAttack);
			attackedCountry.ownerID = this.currentPlayer;
			attackedCountry.currentArmies = this.attackingArmies;

			changeCountryControl(defendingOwnerID);

			this.countries.get(this.countryToAttackFrom).removeArmies(this.attackingArmies);

			this.gui.changeCountryControl(this.attackingArmies, currentPlayer, this.countryToAttack);

			this.gui.updateArmies(this.countries.get(this.countryToAttackFrom).getArmyCount(),
					this.countryToAttackFrom);
		}

		this.countryToAttack = 0;
		this.countryToAttackFrom = 0;
	}

	boolean hasMadeValidAttack() {
		return this.hasMadeValidAttack;
	}

	void resetTurn() {
		this.hasMadeValidAttack = false;
	}

}
