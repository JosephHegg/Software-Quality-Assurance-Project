package WorkingSpace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import WorkingSpace.Placement;
import WorkingSpace.Player;
import WorkingSpace.AttackPhase;
import WorkingSpace.Country;
import WorkingSpace.FortifyPhase;
import WorkingSpace.GUI;
import WorkingSpace.GameBoard;

public class GameBoardSetupTests {
	
	GameBoard board;
	
	@Before
	public void setUp(){
		GUI mockGUI = EasyMock.mock(GUI.class);
		board = new GameBoard(mockGUI, EasyMock.mock(ArrayList.class));
	}

	@Test
	public void testAddCountry() {
		board.countries = new HashMap<Integer, Country>();
		board.addCountry(0, new Country("Afghanistan", 0));
		board.addCountry(1, new Country("Alaska", 1));

		HashMap<Integer, Country> countries = board.countries;

		String s = "";
		for (int key : countries.keySet()) {
			s = s + " " + countries.get(key).getCountryID();
		}

		assertEquals(" 0 1", s);
	}

	@Test
	public void testAskForNumPlayers_1() {
		GUI gui = EasyMock.mock(GUI.class);
		EasyMock.expect(gui.askForNumberofPlayers()).andReturn(2);
		EasyMock.expect(gui.askForNumberofPlayers()).andReturn(6);
		board.gui = gui;

		// replay
		EasyMock.replay(gui);
		int num = board.getNumPlayers();
		assertEquals(2, num);
		
		num = board.getNumPlayers();
		assertEquals(6, num);
		
		
		
		// verify
		EasyMock.verify(gui);
		
	}
	
	@Test
	public void testAskForNumPlayers_lessThanMinPlayers() {
		GUI gui = EasyMock.mock(GUI.class);
		EasyMock.expect(gui.askForNumberofPlayers()).andReturn(1);
		EasyMock.expect(gui.askForNumberofPlayers()).andReturn(6);
		board.gui = gui;


		// replay
		EasyMock.replay(gui);
		int num = board.getNumPlayers();
		assertEquals(-1, num);
		
		num = board.getNumPlayers();
		assertEquals(6, num);
				
		// verify
		EasyMock.verify(gui);
		
	}
	
	@Test
	public void testAskForNumPlayers_moreThanMaxPlayers() {
		GUI gui = EasyMock.mock(GUI.class);
		EasyMock.expect(gui.askForNumberofPlayers()).andReturn(2);
		EasyMock.expect(gui.askForNumberofPlayers()).andReturn(8);
		board.gui = gui;


		// replay
		EasyMock.replay(gui);
		int num = board.getNumPlayers();
		assertEquals(2, num);
		
		num = board.getNumPlayers();
		assertEquals(-1, num);
				
		// verify
		EasyMock.verify(gui);
		
	}

	
	@Test
	public void testInitializeCountryMap(){
		GUI gui = EasyMock.mock(GUI.class);
		board.gui = gui;
		
		EasyMock.replay(gui);
		
		board.initializeCountryMap();
		
		EasyMock.verify(gui);
		
		assertEquals(42,board.countries.size());
	}
	
	@Test
	public void testShowGUI() {
		GUI gui = EasyMock.mock(GUI.class);
		board.gui = gui;
		
		gui.showGUI();
		
		EasyMock.replay(gui);
		
		board.showGUI();
		
		EasyMock.verify(gui);
	}
	
	@Test 
	public void playerDrawsCard(){
		Placement placement = EasyMock.mock(Placement.class);
		AttackPhase attackPhase = EasyMock.mock(AttackPhase.class);
		FortifyPhase fortify = EasyMock.mock(FortifyPhase.class);
		DrawPhase drawPhase = EasyMock.mock(DrawPhase.class);
		
		board.placement = placement;
		board.attackPhase = attackPhase;
		board.fortifyPhase = fortify;
		board.drawPhase = drawPhase;
		
		Player p1 = new Player(1);
		p1.addCountry(board.countries.get(1));
		p1.addCountry(board.countries.get(2));
		
		EasyMock.expect(drawPhase.checkValidHand()).andReturn(0);
		EasyMock.expect(placement.placementTurn(p1, 0)).andReturn(0); 
		
		EasyMock.expect(attackPhase.askForAttack()).andReturn(true);
		attackPhase.takeAttackAction(1);
		EasyMock.expect(attackPhase.askForAttack()).andReturn(false);
		
		EasyMock.expect(fortify.askForFortify()).andReturn(true);
		fortify.takeFortifyAction();
		
		EasyMock.expect(attackPhase.hasMadeValidAttack()).andReturn(true);
		drawPhase.drawCard();
		attackPhase.resetTurn();
		
		
		EasyMock.replay(placement, attackPhase, fortify, board.gui, drawPhase);
		
		board.takeTurn(p1);
		
		EasyMock.verify(placement, attackPhase, fortify, board.gui, drawPhase);
	}
	
	
	@Test
	public void testTakeTurn() {
		Placement placement = EasyMock.mock(Placement.class);
		AttackPhase attackPhase = EasyMock.mock(AttackPhase.class);
		FortifyPhase fortify = EasyMock.mock(FortifyPhase.class);
		
		board.placement = placement;
		board.attackPhase = attackPhase;
		board.fortifyPhase = fortify;
		
		Player p1 = new Player(1);
		p1.addCountry(board.countries.get(1));
		p1.addCountry(board.countries.get(2));
		
		EasyMock.expect(placement.placementTurn(p1, 0)).andReturn(0); 
		
		EasyMock.expect(attackPhase.askForAttack()).andReturn(true);
		attackPhase.takeAttackAction(1);
		EasyMock.expect(attackPhase.askForAttack()).andReturn(false);
		
		EasyMock.expect(fortify.askForFortify()).andReturn(true);
		fortify.takeFortifyAction();
		
		EasyMock.expect(attackPhase.hasMadeValidAttack()).andReturn(true);
		attackPhase.resetTurn();
		
		
		EasyMock.replay(placement, attackPhase, fortify, board.gui);
		
		board.takeTurn(p1);
		
		EasyMock.verify(placement, attackPhase, fortify, board.gui);
	}
	
	@Test
	public void testTakeTurn_declineFortifyPhase() {
		Placement placement = EasyMock.mock(Placement.class);
		AttackPhase attackPhase = EasyMock.mock(AttackPhase.class);
		FortifyPhase fortify = EasyMock.mock(FortifyPhase.class);
		
		board.placement = placement;
		board.attackPhase = attackPhase;
		board.fortifyPhase = fortify;
		
		Player p1 = new Player(1);
		p1.addCountry(board.countries.get(1));
		p1.addCountry(board.countries.get(2));
		
		EasyMock.expect(placement.placementTurn(p1, 0)).andReturn(0);
		
		EasyMock.expect(attackPhase.askForAttack()).andReturn(true);
		attackPhase.takeAttackAction(1);
		EasyMock.expect(attackPhase.askForAttack()).andReturn(false);
		
		EasyMock.expect(fortify.askForFortify()).andReturn(false);
		
		EasyMock.expect(attackPhase.hasMadeValidAttack()).andReturn(true);
		attackPhase.resetTurn();
		
		
		EasyMock.replay(placement, attackPhase, fortify, board.gui);
		
		board.takeTurn(p1);
		
		EasyMock.verify(placement, attackPhase, fortify, board.gui);
	}
	
	@Test
	public void testTakeTurn_oneCountryOwned() {
		Placement placement = EasyMock.mock(Placement.class);
		AttackPhase attackPhase = EasyMock.mock(AttackPhase.class);
		FortifyPhase fortify = EasyMock.mock(FortifyPhase.class);
		
		board.placement = placement;
		board.attackPhase = attackPhase;
		board.fortifyPhase = fortify;
		
		Player p1 = new Player(1);
		p1.addCountry(board.countries.get(1));
		
		EasyMock.expect(placement.placementTurn(p1, 0)).andReturn(0);
		
		EasyMock.expect(attackPhase.askForAttack()).andReturn(true);
		attackPhase.takeAttackAction(1);
		EasyMock.expect(attackPhase.askForAttack()).andReturn(false);
		EasyMock.expect(attackPhase.hasMadeValidAttack()).andReturn(true);
		attackPhase.resetTurn();
		
		EasyMock.replay(placement, attackPhase, fortify, board.gui);
		
		board.takeTurn(p1);
		
		EasyMock.verify(placement, attackPhase, fortify, board.gui);
	}
	
	@Test
	public void testSetupArmies_true()
	{
		Placement placement = EasyMock.mock(Placement.class);
		board.placement = placement;
		
		EasyMock.expect(placement.setupArmies(1)).andReturn(true);
		
		EasyMock.replay(placement);
		
		boolean returnVal = board.setUpArmies(1);
		
		EasyMock.verify(placement);
		assertTrue(returnVal);
	}
	
	@Test
	public void testDeclareInitialPlayer(){
		board.gui.updatePlayerConsole(1, "Player 1 rolled the highest and gets to go first!");
		
		EasyMock.replay(board.gui);
		
		board.declareInitialPlayer(1);
		
		EasyMock.verify(board.gui);
	}
	
	@Test
	public void testChangePlayerConsole(){
		board.gui.updatePlayerConsole(1, "Player 1's turn");
		
		EasyMock.replay(board.gui);
		
		board.changePlayerConsole(1);
		
		EasyMock.verify(board.gui);
	}
	
	@Test
	public void testRollDie(){
		Random mockedRandom = EasyMock.mock(Random.class);
		board.random = mockedRandom;
		
		EasyMock.expect(mockedRandom.nextInt(6)).andReturn(1);
		
		EasyMock.replay(mockedRandom);
		
		int num = board.rollDie();
		
		EasyMock.verify(mockedRandom);
		assertEquals(2,num);
	}
	
}
