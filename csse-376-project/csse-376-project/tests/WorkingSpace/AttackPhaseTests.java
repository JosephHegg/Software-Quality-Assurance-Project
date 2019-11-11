package WorkingSpace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Test;

import WorkingSpace.AttackPhase;
import WorkingSpace.GUI;
import WorkingSpace.GameBoard;
import WorkingSpace.IllegalNumberOfArmiesToAttackException;
import WorkingSpace.Player;

public class AttackPhaseTests {

	@Test
	public void testAskForAttack_1() {
		
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		EasyMock.expect(gui.askToAttack()).andReturn(true);
		
		EasyMock.replay(gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui,EasyMock.mock(ArrayList.class));
		
		
		assertTrue(ap.askForAttack());
		EasyMock.verify(gui);
		
	}
	
	@Test
	public void testAskForAttack_2() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui,EasyMock.mock(ArrayList.class));
		EasyMock.expect(gui.askToAttack()).andReturn(false);
		
		EasyMock.replay(gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui,EasyMock.mock(ArrayList.class));
		
		
		assertFalse(ap.askForAttack());
		EasyMock.verify(gui);
		
	}
	
	@Test
	public void testPicking2CountriesForAttack_1() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui,EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(1);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(2);
		
		EasyMock.replay(gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		
		ap.selectCountriesForAttack();
		
		assertEquals(1, ap.countryToAttackFrom);
		assertEquals(2, ap.countryToAttack);
		EasyMock.verify(gui);
	}
	
	@Test
	public void testPicking2CountriesForAttack_2() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(1);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(4);
		
		EasyMock.replay(gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		
		ap.selectCountriesForAttack();
		
		assertEquals(1, ap.countryToAttackFrom);
		assertEquals(4, ap.countryToAttack);
		EasyMock.verify(gui);
	}
	
	@Test
	public void testCheckAttackFromIsOwned_1() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		int playernum = 1;
		ap.currentPlayer = playernum;
		
		ap.countries.get(1).setOwnerID(playernum);
		ap.countryToAttackFrom = 1;
		
		boolean result = ap.checkAttackFromIsOwned();
		
		assertTrue(result);
	}
	
	@Test
	public void testCheckAttackFromIsOwned_2() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		int playernum = 2;
		ap.currentPlayer = playernum;
		
		//set control to a different player
		ap.countries.get(1).checkIfAbleToTakeOverCountry(1);
		ap.countryToAttackFrom = 1;
		
		boolean result = ap.checkAttackFromIsOwned();
		
		assertFalse(result);
	}
	
	@Test
	public void testCheckToAttackIsNotOwned_1() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		int playernum = 1;
		ap.currentPlayer = playernum;
		
		//set control to a different player
		ap.countries.get(1).checkIfAbleToTakeOverCountry(2);
		ap.countryToAttack = 1;
		
		boolean result = ap.checkToAttackIsNotOwned();
		
		assertTrue(result);
	}
	
	@Test
	public void testCheckToAttackIsNotOwned_2() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI,EasyMock.mock(ArrayList.class));
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		int playernum = 1;
		ap.currentPlayer = playernum;
		
		ap.countries.get(1).setOwnerID(playernum);
		ap.countryToAttack = 1;
		
		boolean result = ap.checkToAttackIsNotOwned();
		
		assertFalse(result);
	}
	
	@Test
	public void testCheckToAttackIsAdjacent_1() {
		GUI gui = EasyMock.mock(GUI.class);
		
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		
		int playernum = 1;
		ap.currentPlayer = playernum;
		
		//set control to a different player
		ap.countries.get(1).checkIfAbleToTakeOverCountry(2);
		
		//alaska
		ap.countryToAttack = 1;
		//alberta
		ap.countryToAttackFrom = 2;
		
		boolean result = ap.checkToAttackIsAdjacent();
		
		assertTrue(result);
	}
	
	@Test
	public void testCheckToAttackIsAdjacent_2() {
		GUI gui = EasyMock.mock(GUI.class);
		
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		
		int playernum = 1;
		ap.currentPlayer = playernum;
		
		//set control to a different player
		ap.countries.get(1).checkIfAbleToTakeOverCountry(2);
		
		//alaska
		ap.countryToAttack = 1;
		//alberta
		ap.countryToAttackFrom = 0;
		
		boolean result = ap.checkToAttackIsAdjacent();
		
		assertFalse(result);
	}
	
	@Test
	public void testGetArmiesToAttackWith_1() throws IllegalNumberOfArmiesToAttackException {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(10);
		
		EasyMock.replay(gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		int attackingCountry = 1;
		
		ap.countryToAttackFrom = attackingCountry;
		ap.countries.get(attackingCountry).addArmies(11);
		int result = ap.getNumOfArmiesAttacking();
		
		EasyMock.verify(gui);
		assertEquals(10, result);
	}
	
	@Test 
	public void testGetArmiesToAttackWith_2(){
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(10);
		
		EasyMock.replay(gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		int attackingCountry = 1;
		
		ap.countryToAttackFrom = attackingCountry;
		ap.countries.get(attackingCountry).addArmies(10);;
		int result;
		
		try {
			result = ap.getNumOfArmiesAttacking();
		} catch (IllegalNumberOfArmiesToAttackException e) {
			EasyMock.verify(gui);
			return;
		}
		
		fail("Did not throw an IllegalNumberOfArmiesToAttackException");
	}
	
	@Test 
	public void testGetArmiesToAttackWith_3(){
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(0);
		
		EasyMock.replay(gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		int attackingCountry = 1;
		
		ap.countryToAttackFrom = attackingCountry;
		ap.countries.get(attackingCountry).addArmies(10);;
		int result;
		
		try {
			result = ap.getNumOfArmiesAttacking();
		} catch (IllegalNumberOfArmiesToAttackException e) {
			EasyMock.verify(gui);
			return;
		}
		
		fail("Did not throw an IllegalNumberOfArmiesToAttackException");
	}
	
	@Test
	public void testMakeAttack_1() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(mockGUI.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(mockGUI.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.replay(rand, mockGUI);
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		ap.countries.get(1).addArmies(11);
		ap.countries.get(2).addArmies(4);
		ap.countryToAttack = 2;
		ap.countryToAttackFrom = 1;
		
		ap.random = rand;
		ap.attackingArmies = 10;
		ap.defendingArmies = 4;
		ap.makeAttack();
		
		EasyMock.verify(rand);
		assertEquals(2, ap.countries.get(2).getArmyCount());
		assertEquals(10, ap.attackingArmies);
		assertEquals(2, ap.defendingArmies);
	}
	
	@Test
	public void testMakeAttack_2() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(mockGUI.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(mockGUI.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.replay(rand,mockGUI);
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		ap.countries.get(1).addArmies(11);
		ap.countries.get(2).addArmies(4);
		ap.countryToAttack = 2;
		ap.countryToAttackFrom = 1;
		
		ap.random = rand;
		ap.attackingArmies = 10;
		ap.defendingArmies = 4;
		ap.makeAttack();
		
		EasyMock.verify(rand);
		assertEquals(8, ap.attackingArmies);
		assertEquals(4, ap.defendingArmies);
	}
	
	@Test
	public void testMakeAttack_3() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(1);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		
		ap.countries.get(1).addArmies(2);
		ap.countries.get(2).addArmies(4);
		ap.countryToAttack = 2;
		ap.countryToAttackFrom = 1;
		
		ap.random = rand;
		ap.attackingArmies = 1;
		ap.defendingArmies = 4;
		ap.makeAttack();
		
		EasyMock.verify(rand);
		assertEquals(0, ap.attackingArmies);
		assertEquals(4, ap.defendingArmies);
	}
	
	@Test
	public void testMakeAttack_4() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(mockGUI.askforNumAttackerDice()).andReturn(1);
		EasyMock.expect(mockGUI.askforNumDefenderDice()).andReturn(1);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.replay(rand,mockGUI);
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		ap.countries.get(1).addArmies(2);
		ap.countries.get(2).addArmies(1);
		ap.countryToAttack = 2;
		ap.countryToAttackFrom = 1;
		
		ap.random = rand;
		ap.attackingArmies = 1;
		ap.defendingArmies = 1;
		ap.makeAttack();
		
		EasyMock.verify(rand);
		assertEquals(1, ap.countries.get(1).getArmyCount());
		assertEquals(0, ap.attackingArmies);
		assertEquals(1, ap.defendingArmies);
	}
	
	@Test
	public void testMakeAttack_5() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(mockGUI.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(mockGUI.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		
		EasyMock.replay(rand,mockGUI);
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		ap.countries.get(1).addArmies(6);
		ap.countries.get(2).addArmies(4);
		ap.countryToAttack = 2;
		ap.countryToAttackFrom = 1;
		
		ap.random = rand;
		ap.attackingArmies = 5;
		ap.defendingArmies = 4;
		ap.makeAttack();
		
		EasyMock.verify(rand);
		assertEquals(5, ap.attackingArmies);
		assertEquals(2, ap.defendingArmies);
	}
	
	@Test
	public void testMakeAttack_moreAttackDiceSpecifiedThanArmies() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(mockGUI.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(mockGUI.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		
		EasyMock.replay(rand,mockGUI);
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		ap.countries.get(1).addArmies(6);
		ap.countries.get(2).addArmies(4);
		ap.countryToAttack = 2;
		ap.countryToAttackFrom = 1;
		
		ap.random = rand;
		ap.attackingArmies = 2;
		ap.defendingArmies = 4;
		ap.makeAttack();
		
		EasyMock.verify(rand);
		assertEquals(2, ap.attackingArmies);
		assertEquals(2, ap.defendingArmies);
	}
	
	@Test
	public void testMakeAttack_moreDefenceDiceSpecifiedThanArmies() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(mockGUI.askforNumAttackerDice()).andReturn(2);
		EasyMock.expect(mockGUI.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		
		EasyMock.replay(rand,mockGUI);
		AttackPhase ap = new AttackPhase(gb.countries, gb.gui, EasyMock.mock(ArrayList.class));
		
		ap.countries.get(1).addArmies(6);
		ap.countries.get(2).addArmies(1);
		ap.countryToAttack = 2;
		ap.countryToAttackFrom = 1;
		
		ap.random = rand;
		ap.attackingArmies = 2;
		ap.defendingArmies = 1;
		ap.makeAttack();
		
		EasyMock.verify(rand);
		assertEquals(2, ap.attackingArmies);
		assertEquals(0, ap.defendingArmies);
	}
	
	@Test
	public void testTakeAttackAction_1() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		
		ArrayList<Player> mockedPlayers = EasyMock.mock(ArrayList.class);
		
		GameBoard gb = new GameBoard(mockGUI, mockedPlayers);
		
		GUI gui = EasyMock.mock(GUI.class);
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(3);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		gui.updateArmies(4, 2);
		gui.updateArmies(0, 1);
		
		//armies,player,country
		gui.changeCountryControl(3, 2, 1);
		gui.updateArmies(1, 2);
		
		AttackPhase ap = EasyMock.partialMockBuilder(AttackPhase.class).addMockedMethod("changeCountryControl")
				.withConstructor(gb.countries, gui, mockedPlayers).createMock();
		ap.changeCountryControl(1);
		EasyMock.replay(rand,gui,ap);
		
		ap.random = rand;
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(4);
		
		ap.takeAttackAction(2);

		EasyMock.verify(rand,gui, ap);
		assertEquals(2,ap.countries.get(1).getOwnerID());
		
		assertEquals(3, ap.countries.get(1).getArmyCount());
		assertEquals(1, ap.countries.get(2).getArmyCount());
	}
	
	@Test
	public void testTakeAttackAction_2() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));

		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(2);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(2);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		gui.updateArmies(1, 2);
		gui.updateArmies(2, 1);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		ap.random = rand;
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(3);
		
		ap.takeAttackAction(2);
		
		EasyMock.verify(rand,gui);
		assertEquals(1,ap.countries.get(1).getOwnerID());
		
		assertEquals(2, ap.countries.get(1).getArmyCount());
		assertEquals(1, ap.countries.get(2).getArmyCount());
	}
	
	@Test
	public void testTakeAttackAction_3() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(0);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(2);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(2);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		gui.updateArmies(1, 2);
		gui.updateArmies(2, 1);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		ap.random = rand;
		
		ap.countries.get(0).setOwnerID(2);
		ap.countries.get(0).setArmies(3);
		
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(3);
		
		ap.takeAttackAction(2);

		
		EasyMock.verify(rand,gui);
		assertEquals(1,ap.countries.get(1).getOwnerID());
		
		assertEquals(2, ap.countries.get(1).getArmyCount());
		assertEquals(1, ap.countries.get(2).getArmyCount());
	}
	
	@Test
	public void testTakeAttackAction_incorrectNumofArmiesSelected() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(0);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(0);
		gui.showMessage("Incorrect Number of Armies");
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(2);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(2);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		gui.updateArmies(1, 2);
		gui.updateArmies(2, 1);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		ap.random = rand;
		
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(3);
		
		ap.takeAttackAction(2);

		
		EasyMock.verify(rand,gui);
		assertEquals(1,ap.countries.get(1).getOwnerID());
		
		assertEquals(2, ap.countries.get(1).getArmyCount());
		assertEquals(1, ap.countries.get(2).getArmyCount());
	}
	
	@Test
	public void testTakeAttackAction_toattackIsOwnedByPlayer() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(26);
		
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(2);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(2);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		gui.updateArmies(1, 2);
		gui.updateArmies(3, 26);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		ap.random = rand;
		
		ap.countries.get(1).setOwnerID(2);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(3);
		
		ap.countries.get(26).setOwnerID(1);
		ap.countries.get(26).addArmies(3);
		
		ap.takeAttackAction(2);

		
		EasyMock.verify(rand,gui);
		assertEquals(2,ap.countries.get(2).getOwnerID());
		
		assertEquals(2, ap.countries.get(1).getArmyCount());
		assertEquals(1, ap.countries.get(2).getArmyCount());
	}
	
	@Test
	public void testTakeAttackAction_attackfromIsNotOwnedByPlayer() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(1);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(26);
		
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(2);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(2);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		gui.updateArmies(1, 1);
		gui.updateArmies(3, 26);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		ap.random = rand;
		
		ap.countries.get(1).setOwnerID(2);
		ap.countries.get(1).addArmies(3);
		
		ap.countries.get(2).setOwnerID(1);
		ap.countries.get(2).addArmies(3);
		
		ap.countries.get(26).setOwnerID(1);
		ap.countries.get(26).addArmies(3);
		
		ap.takeAttackAction(2);

		
		EasyMock.verify(rand,gui);
		assertEquals(1,ap.countries.get(2).getOwnerID());
		
		assertEquals(1, ap.countries.get(1).getArmyCount());
		assertEquals(3, ap.countries.get(2).getArmyCount());
	}
	
	@Test
	public void testTakeAttackAction_continuingAnAttack() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));

		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(4);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		//attack rolls
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		//defence rolls
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		//armies , country
		gui.updateArmies(3, 2);
		gui.updateArmies(2, 1);
		
		EasyMock.expect(gui.askToContinueAttack()).andReturn(true);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(2);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		//attack rolls
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		//defence rolls
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		//armies , country
		gui.updateArmies(1, 2);
		gui.updateArmies(2, 1);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		ap.random = rand;
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(5);
		
		ap.takeAttackAction(2);
		
		EasyMock.verify(rand,gui);
		assertEquals(1,ap.countries.get(1).getOwnerID());
		
		assertEquals(2, ap.countries.get(1).getArmyCount());
		assertEquals(1, ap.countries.get(2).getArmyCount());
	}
	
	@Test
	public void testTakeAttackAction_declineToContinuingAnAttack() {
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));

		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(4);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		//attack rolls
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		//defence rolls
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		//armies , country
		gui.updateArmies(3, 2);
		gui.updateArmies(2, 1);
		
		EasyMock.expect(gui.askToContinueAttack()).andReturn(false);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		ap.random = rand;
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(5);
		
		ap.takeAttackAction(2);
		
		EasyMock.verify(rand,gui);
		assertEquals(1,ap.countries.get(1).getOwnerID());
		
		assertEquals(2, ap.countries.get(1).getArmyCount());
		assertEquals(3, ap.countries.get(2).getArmyCount());
	}
	
	@Test
	public void testChangeCountryControl_1(){
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));

		Player p1 = new Player(1);
		Player p2 = new Player(2);
		
		ArrayList<Player> players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		
		gb.countries.get(1).setOwnerID(2);
		p2.addCountry(gb.countries.get(1));
		
		AttackPhase ap = new AttackPhase(gb.countries, gui, players);
		
		ap.currentPlayer = 1;
		ap.countryToAttack = 1;
		
		ap.changeCountryControl(2);
		
		assertEquals(1, p1.ownedCountries.size());
		assertEquals(0, p2.ownedCountries.size());
		
	}
	
	@Test
	public void testChangeCountryControl_2(){
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));

		Player p1 = new Player(1);
		Player p2 = new Player(2);
		
		ArrayList<Player> players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		
		gb.countries.get(1).setOwnerID(2);
		p2.addCountry(gb.countries.get(1));
		
		gb.countries.get(2).setOwnerID(2);
		p2.addCountry(gb.countries.get(2));
		
		AttackPhase ap = new AttackPhase(gb.countries, gui, players);
		
		ap.currentPlayer = 1;
		ap.countryToAttack = 1;
		
		ap.changeCountryControl(2);
		
		assertEquals(1, p1.ownedCountries.size());
		assertEquals(1, p2.ownedCountries.size());
		
	}
	
	@Test
	public void testHasMadeValidAttack(){
		GUI mockGUI = EasyMock.mock(GUI.class);
		
		ArrayList<Player> mockedPlayers = EasyMock.mock(ArrayList.class);
		
		GameBoard gb = new GameBoard(mockGUI, mockedPlayers);
		
		GUI gui = EasyMock.mock(GUI.class);
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(3);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		gui.updateArmies(4, 2);
		gui.updateArmies(0, 1);
		
		//armies,player,country
		gui.changeCountryControl(3, 2, 1);
		gui.updateArmies(1, 2);
		
		AttackPhase ap = EasyMock.partialMockBuilder(AttackPhase.class).addMockedMethod("changeCountryControl")
				.withConstructor(gb.countries, gui, mockedPlayers).createMock();
		ap.changeCountryControl(1);
		EasyMock.replay(rand,gui,ap);
		
		ap.random = rand;
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(4);
		
		ap.takeAttackAction(2);

		EasyMock.verify(rand,gui, ap);
		
		assertTrue(ap.hasMadeValidAttack());
	}
	
	@Test
	public void testHasNotMadeValidAttack(){
		GUI gui = EasyMock.mock(GUI.class);
		GameBoard gb = new GameBoard(gui, EasyMock.mock(ArrayList.class));

		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(2);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(2);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		

		gui.updateArmies(1, 2);
		gui.updateArmies(2, 1);
		
		EasyMock.replay(rand,gui);
		AttackPhase ap = new AttackPhase(gb.countries, gui, EasyMock.mock(ArrayList.class));
		ap.random = rand;
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(3);
		
		ap.takeAttackAction(2);
		
		EasyMock.verify(rand,gui);
		
		assertFalse(ap.hasMadeValidAttack());
	}
	
	@Test
	public void testRestTurnAfterValidAttack(){
		GUI mockGUI = EasyMock.mock(GUI.class);
		
		ArrayList<Player> mockedPlayers = EasyMock.mock(ArrayList.class);
		
		GameBoard gb = new GameBoard(mockGUI, mockedPlayers);
		
		GUI gui = EasyMock.mock(GUI.class);
		EasyMock.expect(gui.selectCountryToAttackFrom()).andReturn(2);
		EasyMock.expect(gui.selectCountryToAttack()).andReturn(1);
		EasyMock.expect(gui.askForNumArmiesToAttack()).andReturn(3);
		
		EasyMock.expect(gui.askforNumAttackerDice()).andReturn(3);
		EasyMock.expect(gui.askforNumDefenderDice()).andReturn(2);
		
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		EasyMock.expect(rand.nextInt(6)).andReturn(1);
		
		gui.updateArmies(4, 2);
		gui.updateArmies(0, 1);
		
		//armies,player,country
		gui.changeCountryControl(3, 2, 1);
		gui.updateArmies(1, 2);
		
		AttackPhase ap = EasyMock.partialMockBuilder(AttackPhase.class).addMockedMethod("changeCountryControl")
				.withConstructor(gb.countries, gui, mockedPlayers).createMock();
		ap.changeCountryControl(1);
		EasyMock.replay(rand,gui,ap);
		
		ap.random = rand;
		ap.countries.get(1).setOwnerID(1);
		ap.countries.get(1).addArmies(2);
		
		ap.countries.get(2).setOwnerID(2);
		ap.countries.get(2).addArmies(4);
		
		ap.takeAttackAction(2);

		EasyMock.verify(rand,gui, ap);
		
		assertTrue(ap.hasMadeValidAttack());
		
		ap.resetTurn();
		
		assertFalse(ap.hasMadeValidAttack());
	}
}
