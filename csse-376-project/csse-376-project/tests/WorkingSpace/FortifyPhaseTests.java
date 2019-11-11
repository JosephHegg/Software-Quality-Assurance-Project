package WorkingSpace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
  
import org.easymock.EasyMock;
import org.junit.Test;

import WorkingSpace.AttackPhase;
import WorkingSpace.Country;
import WorkingSpace.FortifyPhase;
import WorkingSpace.GUI;
import WorkingSpace.GameBoard;
import WorkingSpace.Player;

public class FortifyPhaseTests {

	@Test
	public void initializeFortifyPhaseForPlayerTest_goodIndexes() {
		FortifyPhase fortifyPhaseValidOne = new FortifyPhase(1, null, null);
		assertTrue(fortifyPhaseValidOne.getPlayerID() == 1);

		FortifyPhase fortifyPhaseValidTwo = new FortifyPhase(6, null, null);
		assertTrue(fortifyPhaseValidTwo.getPlayerID() == 6);
			
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void initializeFortifyPhaseForPlayerTest_badIndexes(){
		FortifyPhase phaseThree = new FortifyPhase(7, null, null);
		FortifyPhase phaseTwo = new FortifyPhase(0, null, null);

	}

	@Test
	public void sourceCountryOwnedTest() {
		FortifyPhase fp = new FortifyPhase(2, new HashMap<Integer, Country>(), null);

		Country sourceCountry = EasyMock.mock(Country.class);

		fp.countryFortifySource = 1;

		assertFalse(fp.checkSourceCountryOwned());

		fp.getCountryMap().put(1, sourceCountry);

		EasyMock.replay(sourceCountry);

		assertTrue(fp.checkSourceCountryOwned());

		EasyMock.verify(sourceCountry);

	}

	@Test
	public void destinationCountryOwnedTest() {
		FortifyPhase fp = new FortifyPhase(2, new HashMap<Integer, Country>(), null);

		Country destinationCountry = EasyMock.mock(Country.class);

		fp.countryFortifyDestination = 1;

		assertFalse(fp.checkDestinationCountryOwned());

		fp.getCountryMap().put(1, destinationCountry);

		EasyMock.replay(destinationCountry);

		assertTrue(fp.checkDestinationCountryOwned());

		EasyMock.verify(destinationCountry);

	}

	@Test
	public void filterCountryMapToValidOnesForFortifyTest() {
		FortifyPhase fortifyPhase = new FortifyPhase(1, new HashMap<Integer, Country>(), null);

		Player playerMock = EasyMock.mock(Player.class);

		Country countryMockInvalid = EasyMock.mock(Country.class);
		Country countryValid = new Country();

		EasyMock.expect(countryMockInvalid.getOwnerID()).andStubReturn(99);
		EasyMock.expect(playerMock.getPlayerID()).andStubReturn(1);

		countryValid.setOwnerID(1);

		fortifyPhase.getCountryMap().put(1, countryValid);
		fortifyPhase.getCountryMap().put(2, countryMockInvalid);

		EasyMock.replay(playerMock);
		EasyMock.replay(countryMockInvalid);

		assertTrue(fortifyPhase.getCountryMap().containsKey(1));
		assertTrue(fortifyPhase.getCountryMap().containsKey(2));

		fortifyPhase.filterValidCountriesForFortify();

		assertTrue(fortifyPhase.getCountryMap().containsKey(1));
		assertFalse(fortifyPhase.getCountryMap().containsKey(2));

		EasyMock.verify(playerMock);

		EasyMock.verify(countryMockInvalid);
	}

	@Test (expected = IllegalArgumentException.class)
	public void fortifyCountryTests() {

		FortifyPhase fortifyPhase = new FortifyPhase(1, new HashMap<Integer, Country>(), null);

		fortifyPhase.mockingInEffect = true;

		Player playerMock = EasyMock.mock(Player.class);
		Country sourceCountry = EasyMock.mock(Country.class);
		Country destinationCountry = EasyMock.mock(Country.class);

		sourceCountry.addArmies(5);
		destinationCountry.addArmies(2);

		EasyMock.expect(sourceCountry.getArmyCount()).andReturn(5);
		EasyMock.expect(destinationCountry.getArmyCount()).andReturn(2);

		EasyMock.expect(sourceCountry.getArmyCount()).andReturn(2);
		EasyMock.expect(destinationCountry.getArmyCount()).andReturn(5);

		EasyMock.expect(sourceCountry.getArmyCount()).andReturn(2);
		EasyMock.expect(destinationCountry.getArmyCount()).andReturn(5);

		EasyMock.expect(sourceCountry.getArmyCount()).andReturn(2);
		EasyMock.expect(destinationCountry.getArmyCount()).andReturn(5);

		EasyMock.expect(sourceCountry.getNeighboringCountries()).andReturn(null);
		EasyMock.expect(sourceCountry.getNeighboringCountries()).andReturn(new HashSet<Integer>());

		fortifyPhase.getCountryMap().put(1, sourceCountry);
		fortifyPhase.getCountryMap().put(2, destinationCountry);

		EasyMock.replay(playerMock);
		EasyMock.replay(sourceCountry);
		EasyMock.replay(destinationCountry);

		sourceCountry.addArmies(5);
		destinationCountry.addArmies(2);

		sourceCountry.getNeighboringCountries();
		sourceCountry.getNeighboringCountries();

		fortifyPhase.fortifyCountry(sourceCountry, destinationCountry, 3);

		assertEquals(sourceCountry.getArmyCount(), 2);
		assertEquals(destinationCountry.getArmyCount(), 5);

		fortifyPhase.fortifyCountry(sourceCountry, destinationCountry, 2);
		
		assertEquals(sourceCountry.getArmyCount(), 2);
		assertEquals(destinationCountry.getArmyCount(), 5);

		EasyMock.verify(playerMock);
		EasyMock.verify(sourceCountry);
		EasyMock.verify(destinationCountry);

	}

	@Test
	public void fortifyPhaseAdjacencyTest() {
		FortifyPhase fortifyPhase = new FortifyPhase(1, new HashMap<Integer, Country>(), null);

		fortifyPhase.mockingInEffect = false;

		Player playerOne = new Player(1);
		Country america = new Country("United States", 0);
		Country germany = new Country("Germany", 1);
		Country mexico = new Country("Mexico", 2);
		Country japan = new Country("Japan", 3);

		playerOne.addCountry(america);
		playerOne.addCountry(germany);
		playerOne.addCountry(mexico);

		fortifyPhase.getCountryMap().put(0, america);
		fortifyPhase.getCountryMap().put(1, germany);
		fortifyPhase.getCountryMap().put(2, mexico);
		fortifyPhase.getCountryMap().put(3, japan);

		assertFalse(fortifyPhase.checkPlayerCountryNeighborsForFortify(america, mexico));

		fortifyPhase.mockingInEffect = true;
		assertTrue(fortifyPhase.checkPlayerCountryNeighborsForFortify(america, mexico));
		fortifyPhase.mockingInEffect = false;

		america.getNeighboringCountries().add(2);
		assertFalse(fortifyPhase.checkPlayerCountryNeighborsForFortify(america, mexico));
		mexico.getNeighboringCountries().add(0);

		assertTrue(fortifyPhase.checkPlayerCountryNeighborsForFortify(america, mexico));

		fortifyPhase.filterValidCountriesForFortify();

		assertEquals(fortifyPhase.getCountryMap().size(), 3);

		america.setArmies(6);
		germany.setArmies(4);
		mexico.setArmies(3);
		japan.setArmies(2);

		assertTrue(fortifyPhase.checkPlayerCountryNeighborsForFortify(america, mexico));

		fortifyPhase.fortifyCountry(america, mexico, 3);
		assertEquals(america.getArmyCount(), 3);
		assertEquals(mexico.getArmyCount(), 6);

	}

	@Test
	public void fortifyPhaseAskForSourceAndDestinationCountry() {

		GUI gui = EasyMock.mock(GUI.class);

		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));

		EasyMock.expect(gui.selectSourceCountryForFortify()).andReturn(1);
		EasyMock.expect(gui.selectDestinationCountryForFortify()).andReturn(2);

		EasyMock.replay(gui);
		FortifyPhase fortifyP = new FortifyPhase(1, gb.countries, gui);

		fortifyP.selectCountriesToFortify();

		assertEquals(1, fortifyP.countryFortifySource);
		assertEquals(2, fortifyP.countryFortifyDestination);
		EasyMock.verify(gui);
	}

	@Test
	public void fortifyPhaseCheckInputConditions_1() {

		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, null, gui);

		String result;

		gui.showMessage("Your source country is not owned by you! Please try again.");

		EasyMock.replay(gui);

		result = fp.checkConditionsOfInputEntry(true, false, false);
		assertEquals(result, "Bad Source");

		EasyMock.verify(gui);
	}

	@Test
	public void fortifyPhaseCheckInputConditions_2() {

		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, null, gui);

		String result;

		gui.showMessage("Your destination country is not owned by you! Please try again.");

		EasyMock.replay(gui);

		result = fp.checkConditionsOfInputEntry(false, true, false);

		assertEquals(result, "Bad Destination");

		EasyMock.verify(gui);
	}

	@Test
	public void fortifyPhaseCheckInputConditions_3() {

		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, null, gui);

		String result;

		gui.showMessage("Both countries are not owned by you! Please try again.");

		EasyMock.replay(gui);

		result = fp.checkConditionsOfInputEntry(true, true, false);

		assertEquals(result, "Bad Source And Destination");

		EasyMock.verify(gui);
	}

	@Test
	public void fortifyPhaseCheckInputConditions_4() {

		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, null, gui);

		String result;

		gui.showMessage("The countries you have selected are not neighbors. Please try again.");

		EasyMock.replay(gui);

		result = fp.checkConditionsOfInputEntry(false, false, false);

		assertEquals(result, "Invalid Neighbors");

		EasyMock.verify(gui);
	}
  
	@Test
	public void fortifyPhaseCheckInputConditions_5() {

		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, null, gui);

		String result;

		gui.showMessage("Incorrect inputs, please try again.");

		EasyMock.replay(gui);

		result = fp.checkConditionsOfInputEntry(false, true, true);

		assertEquals(result, "None Of Above");

		EasyMock.verify(gui);
	}

	@Test
	public void fortifyPhaseCheckInputConditions_6() {

		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, null, gui);

		String result;

		gui.showMessage("Incorrect inputs, please try again.");

		EasyMock.replay(gui);

		result = fp.checkConditionsOfInputEntry(true, false, true);

		assertEquals(result, "None Of Above");

		EasyMock.verify(gui);
	}  

	@Test
	public void testTakeFortifyAction_ValidEntry() {
		GUI gui = EasyMock.mock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, new HashMap<Integer, Country>(), gui);

		Player player = new Player(1);

		Country firstValidCountry = new Country("America", 0);
		Country secondValidCountry = new Country("Mexico", 1);

		firstValidCountry.setArmies(5);
		secondValidCountry.setArmies(5);

		firstValidCountry.getNeighboringCountries().add(1);
		secondValidCountry.getNeighboringCountries().add(0);

		player.addCountry(firstValidCountry);
		player.addCountry(secondValidCountry);

		fp.getCountryMap().put(0, firstValidCountry);
		fp.getCountryMap().put(1, secondValidCountry);

		EasyMock.expect(gui.askToFortify()).andReturn(true);
		EasyMock.expect(gui.selectSourceCountryForFortify()).andReturn(0);
		EasyMock.expect(gui.selectDestinationCountryForFortify()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToTransfer()).andReturn(5);
		EasyMock.expect(gui.askForNumArmiesToTransfer()).andReturn(7);
		EasyMock.expect(gui.askForNumArmiesToTransfer()).andReturn(1);
		
		gui.showMessage("You have entered too many armies to transfer, please try again.");
		gui.showMessage("You have entered too many armies to transfer, please try again.");
		
		gui.updateArmies(4, 0);
		gui.updateArmies(6, 1);  

		EasyMock.replay(gui);

		assertEquals(fp.askForFortify(), true);
		
		fp.takeFortifyAction();
		
		assertEquals(firstValidCountry.getArmyCount(), 4);
		assertEquals(secondValidCountry.getArmyCount(), 6);

		EasyMock.verify(gui);

	}

	@Test
	public void testTakeFortifyAction_BadNeighbors() {
		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, new HashMap<Integer, Country>(), gui);

		Player player = new Player(1);

		Country firstValidCountry = new Country("America", 0);
		Country secondValidCountry = new Country("Mexico", 1);
		Country thirdValidCountry = new Country("Germany", 2);

		firstValidCountry.setArmies(5);
		secondValidCountry.setArmies(5);

		firstValidCountry.getNeighboringCountries().add(1);
		secondValidCountry.getNeighboringCountries().add(0);

		player.addCountry(firstValidCountry);
		player.addCountry(secondValidCountry);
		player.addCountry(thirdValidCountry);

		fp.getCountryMap().put(0, firstValidCountry);
		fp.getCountryMap().put(1, secondValidCountry);
		fp.getCountryMap().put(2, thirdValidCountry);

		EasyMock.expect(gui.selectSourceCountryForFortify()).andReturn(0);
		EasyMock.expect(gui.selectDestinationCountryForFortify()).andReturn(2);
		EasyMock.expect(gui.selectSourceCountryForFortify()).andReturn(0);
		EasyMock.expect(gui.selectDestinationCountryForFortify()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToTransfer()).andReturn(0);

		gui.updateArmies(5, 0);
		gui.updateArmies(5, 1);

		EasyMock.replay(gui);

		fp.takeFortifyAction();

		EasyMock.verify(gui);

	}

	@Test
	public void testTakeFortifyAction_BadSource() {
		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, new HashMap<Integer, Country>(), gui);

		Player player = new Player(1);

		Country firstValidCountry = new Country("America", 0);
		Country secondValidCountry = new Country("Mexico", 1);
		Country thirdValidCountry = new Country("Germany", 2);

		Country firstInvalidCountry = new Country("Peru", 3);

		firstValidCountry.setArmies(5);
		secondValidCountry.setArmies(5);

		firstValidCountry.getNeighboringCountries().add(1);
		secondValidCountry.getNeighboringCountries().add(0);

		player.addCountry(firstValidCountry);
		player.addCountry(secondValidCountry);
		player.addCountry(thirdValidCountry);

		fp.getCountryMap().put(0, firstValidCountry);
		fp.getCountryMap().put(1, secondValidCountry);
		fp.getCountryMap().put(2, thirdValidCountry);

		fp.getCountryMap().put(3, firstInvalidCountry);

		EasyMock.expect(gui.selectSourceCountryForFortify()).andReturn(3);
		EasyMock.expect(gui.selectDestinationCountryForFortify()).andReturn(2);
		EasyMock.expect(gui.selectSourceCountryForFortify()).andReturn(0);
		EasyMock.expect(gui.selectDestinationCountryForFortify()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToTransfer()).andReturn(0);

		gui.updateArmies(5, 0);
		gui.updateArmies(5, 0);

		EasyMock.replay(gui);

		fp.takeFortifyAction();

		EasyMock.verify(gui);

	}

	@Test
	public void testTakeFortifyAction_BadDestination() {
		GUI gui = EasyMock.niceMock(GUI.class);

		FortifyPhase fp = new FortifyPhase(1, new HashMap<Integer, Country>(), gui);

		Player player = new Player(1);

		Country firstValidCountry = new Country("America", 0);
		Country secondValidCountry = new Country("Mexico", 1);
		Country thirdValidCountry = new Country("Germany", 2);

		Country firstInvalidCountry = new Country("Peru", 3);

		firstValidCountry.setArmies(5);
		secondValidCountry.setArmies(5);

		firstValidCountry.getNeighboringCountries().add(1);
		secondValidCountry.getNeighboringCountries().add(0);

		player.addCountry(firstValidCountry);
		player.addCountry(secondValidCountry);
		player.addCountry(thirdValidCountry);

		fp.getCountryMap().put(0, firstValidCountry);
		fp.getCountryMap().put(1, secondValidCountry);
		fp.getCountryMap().put(2, thirdValidCountry);

		fp.getCountryMap().put(3, firstInvalidCountry);

		EasyMock.expect(gui.selectSourceCountryForFortify()).andReturn(2);
		EasyMock.expect(gui.selectDestinationCountryForFortify()).andReturn(3);
		EasyMock.expect(gui.selectSourceCountryForFortify()).andReturn(0);
		EasyMock.expect(gui.selectDestinationCountryForFortify()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToTransfer()).andReturn(0);

		gui.updateArmies(5, 0);
		gui.updateArmies(5, 0);

		EasyMock.replay(gui);

		fp.takeFortifyAction();

		EasyMock.verify(gui);

	}

}
