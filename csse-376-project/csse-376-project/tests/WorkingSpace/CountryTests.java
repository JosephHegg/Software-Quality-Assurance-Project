package WorkingSpace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

import WorkingSpace.Country;
import WorkingSpace.InvalidInputIntegerException;

public class CountryTests {

	@Test
	public void buildCountry() {
		Country country = new Country("America", 0);
		int result = country.hashCode();
		
		assertEquals(result , 0);
		assertEquals(country.getArmyCount(), 0); // Basic check to see if actually created.
	}

	@Test
	public void countryEquivalences(){
		Country firstCountry = new Country("America", 0);
		Country secondCountry = new Country("Canada", 1);
		
		Country boundaryCheckCountry = new Country("America", 1);
		
		assertFalse(firstCountry.equals(secondCountry));
		assertFalse(firstCountry.equals(boundaryCheckCountry));
		
		boundaryCheckCountry = new Country("America", 0);
		
		assertTrue(firstCountry.equals(boundaryCheckCountry));
		
		assertFalse(firstCountry.equals(1));
				
	}
	@Test
	public void addAndRemoveArmiesInCountry() {
		Country country = new Country();

		assertEquals(country.getArmyCount(), 0); // Initially 0 per country

		
		try{
			country.addArmies(-1); // This will print an output to console to
			fail();// re-enter a value >= 0.
		} catch(InvalidInputIntegerException e){
			assertEquals(country.getArmyCount(), 0);
		}

		country.addArmies(1);
		assertEquals(country.getArmyCount(), 1);

		country.addArmies(50); // Ridiculous upper bound, we would never have to
								// add this many at a time.
		assertEquals(country.getArmyCount(), 51);

		
		try{
			country.removeArmies(-1);
			fail();
		} catch(InvalidInputIntegerException e){
			assertEquals(country.getArmyCount(), 51);
		}

		country.removeArmies(0);
		assertEquals(country.getArmyCount(), 51);

		country.removeArmies(1);
		assertEquals(country.getArmyCount(), 50);

		country.removeArmies(50);
		assertEquals(country.getArmyCount(), 0); 

		try {
			country.removeArmies(1);
			fail();
		} catch(InvalidInputIntegerException e){
			assertEquals(country.getArmyCount(), 0);
		}

	}

	@Test
	public void playerAssumesControlOfCountry() {
		Country country = new Country();
		country.setArmies(2);

		int playerID = 0;

		/// START GAME TAKEOVER WHERE TAKING A COUNTRY IS DONE AUTOMATICALLY BY
		/// PLAYERS

		assertEquals(country.getOwnerID(), 99); // 99 Default for country that
												// has never been owned before.
		boolean canTakeOver = country.checkIfAbleToTakeOverCountry(playerID);
		assertTrue(canTakeOver);
		country.setArmies(0);
		
		country.setOwnerID(0);

		/// IN GAME TAKEOVER WHERE PLAYERS MUST FIGHT FOR CONTROL OF A COUNTRY

		int enemyID = 1;
		country.addArmies(2);
		canTakeOver = country.checkIfAbleToTakeOverCountry(enemyID);
		assertFalse(canTakeOver);
		

		country.removeArmies(2); // Simulation of fight between players.
		canTakeOver = country.checkIfAbleToTakeOverCountry(enemyID);
		country.setOwnerID(enemyID);
		assertTrue(canTakeOver);

	}
	
	@Test
	public void countryChecksForValidNeighborsByID(){
		Country unitedStates = new Country("US", 0);
		Country canada = new Country("Canada", 1);
		Country germany = new Country("Germany", 2);
		
		Set<Integer> neighborSet = unitedStates.getNeighboringCountries();
		assertEquals(neighborSet.size(), 0);
		
		neighborSet.add(canada.getCountryID());
		
		boolean value = unitedStates.checkIfValidNeighbors(canada);
		assertTrue(value);
		
		value = unitedStates.checkIfValidNeighbors(germany);
		assertFalse(value);	
	}
	
	@Test(expected = InvalidInputIntegerException.class)
	public void testAddArmiesNegativeInput() {
		Country testCountry = new Country("test", 0);
		
		testCountry.addArmies(-1);
	}
	
	@Test(expected = InvalidInputIntegerException.class)
	public void testRemoveArmiesNegativeInput() {
		Country testCountry = new Country("test", 0);
		
		testCountry.removeArmies(-1);
	}
	
	@Test(expected = InvalidInputIntegerException.class)
	public void testRemoveArmiesCausesValueLessThanZero() {
		Country testCountry = new Country("test", 0);
		testCountry.addArmies(10);
		
		testCountry.removeArmies(11);
	}
	
	@Test
	public void testAddZeroArmies() {
		Country testCountry = new Country("test", 0);
		testCountry.addArmies(0);
		assertEquals(0, testCountry.getArmyCount());
	}

}
