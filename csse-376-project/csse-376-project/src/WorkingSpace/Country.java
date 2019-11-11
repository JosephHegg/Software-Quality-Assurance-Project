package WorkingSpace;

import java.util.HashSet;
import java.util.Set;

public class Country {

	String countryName;
	int countryID, ownerID = 99, currentArmies = 0;
	Set<Integer> neighboringCountryIDs;

	public Country(String countryName, int countryID) {
		this.countryName = countryName;
		this.countryID = countryID;
		this.neighboringCountryIDs = new HashSet<Integer>();
	}

	public Country() {
		this.countryName = "America";
		this.countryID = 0;
	}

	public int getArmyCount() {
		return this.currentArmies;
	}

	public void setArmies(int newArmyCount) {
		this.currentArmies = newArmyCount;
	}

	public int getOwnerID() {
		return this.ownerID;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Country) {
			return this.countryName.equals(((Country) other).countryName)
					&& this.countryID == ((Country) other).countryID;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = this.countryID;

		return result;

	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getCountryID() {
		return this.countryID;
	}

	public Set<Integer> getNeighboringCountries() {
		return neighboringCountryIDs;

	}

	public void addArmies(int i) {
		if (i < 0) {
			throw new InvalidInputIntegerException("");
		} else {
			this.currentArmies += i;
		}

	}

	public void removeArmies(int i) {
		if (i < 0) {
			throw new InvalidInputIntegerException("at removeArmies, input cannot be negative");
		}

		if (this.currentArmies - i < 0) {
			throw new InvalidInputIntegerException("at removeArmies, input too large for this country");
		} else {
			this.currentArmies -= i;
		}

	}

	public boolean checkIfAbleToTakeOverCountry(int enemyID) {

		return this.currentArmies == 0 || this.ownerID == 99;

	}

	public boolean checkIfValidNeighbors(Country otherCountry) {

		if (this.neighboringCountryIDs.contains(otherCountry.getCountryID())) {
			return true;
		}

		return false;
	}

}
