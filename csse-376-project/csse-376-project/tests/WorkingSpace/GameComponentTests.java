package WorkingSpace;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.naming.directory.InvalidAttributesException;

import org.easymock.EasyMock;
import org.junit.Test;

import WorkingSpace.Country;
import WorkingSpace.GUI;
import WorkingSpace.GameBoard;
import WorkingSpace.GameComponent;

public class GameComponentTests {

	@Test
	public void testNumberArmiesPlayerCount() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		int numArmies = gc.getArmiesPerPlayer(2);
		assertEquals(40, numArmies);
		numArmies = gc.getArmiesPerPlayer(3);
		assertEquals(35, numArmies);
		numArmies = gc.getArmiesPerPlayer(4);
		assertEquals(30, numArmies);
		numArmies = gc.getArmiesPerPlayer(5);
		assertEquals(25, numArmies);
		numArmies = gc.getArmiesPerPlayer(6);
		assertEquals(20, numArmies);
	}
	
	@Test
	public void testLoopOverPlayersAndSetupforGUIUpdate(){
		GUI mockGUI = EasyMock.mock(GUI.class);
		ArrayList<Player> mockList = EasyMock.mock(ArrayList.class);
		Player mockPlayer = EasyMock.mock(Player.class);
		GameBoard mockGB = EasyMock.mock(GameBoard.class);
		
		GameComponent gc = new GameComponent(mockGUI);
		gc.gameBoard = mockGB;
		gc.players = mockList;
		
		gc.current = 0;
		mockPlayer.playerID = 0;
		
		
		
		EasyMock.expect(mockList.get(0)).andReturn(mockPlayer);
		mockGUI.updateCurrentPlayer(mockPlayer);
		EasyMock.expect(mockList.get(0)).andReturn(mockPlayer);
		mockGB.changePlayerConsole(0);
		EasyMock.expect(mockList.get(0)).andReturn(mockPlayer);
		EasyMock.expect(mockGB.setUpArmies(0)).andReturn(false); //return value doesnt matter
		
		EasyMock.replay(mockGUI, mockList, mockPlayer, mockGB);
		gc.loopOverPlayersAndSetup(1, 1);
		
		EasyMock.verify(mockGUI, mockList, mockPlayer, mockGB);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNumberArmiesInvalidPlayerCount() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		int numArmies = gc.getArmiesPerPlayer(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNumberArmiesInvalidPlayerCount2() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		int numArmies = gc.getArmiesPerPlayer(7);
	}

	@Test
	public void getNumberOfPlayers() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		GameBoard mockedBoard = EasyMock.mock(GameBoard.class);
		EasyMock.expect(mockedBoard.getNumPlayers()).andReturn(2);
		mockedBoard.showGUI();

		gc.gameBoard = mockedBoard;

		EasyMock.replay(mockedBoard);

		gc.showGame();
		int numPlayers = gc.getNumPlayersFromBoard(); // Returns int in range
														// [2,6]

		EasyMock.verify(mockedBoard);
		assertEquals(2, numPlayers);
	}

	@Test
	public void getNumberOfPlayers2() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		GameBoard mockedBoard = EasyMock.mock(GameBoard.class);
		EasyMock.expect(mockedBoard.getNumPlayers()).andReturn(6);

		gc.gameBoard = mockedBoard;

		EasyMock.replay(mockedBoard);
		int numPlayers = gc.getNumPlayersFromBoard();

		EasyMock.verify(mockedBoard);
		assertEquals(6, numPlayers);
	}

	@Test
	public void testSetupPlayers_1() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		gc.setupPlayers(2);
		assertEquals(2, gc.players.size());
	}

	@Test
	public void testSetupPlayers_2() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		gc.setupPlayers(6);
		assertEquals(6, gc.players.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetupPlayersWithInvalidNumber_1() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		gc.setupPlayers(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetupPlayersWithInvalidNumber_2() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		gc.setupPlayers(7);
	}

	@Test
	public void testCleanUpPlayers_1left() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		gc.setupPlayers(2);
		gc.players.get(0).addCountry(new Country("testCountry", 1));
		gc.numPlayers = 2;

		assertEquals(2, gc.players.size());

		gc.cleanUpPlayers();

		assertEquals(2, gc.players.size());
		assertTrue(gc.players.get(1).isDead);
	}

	@Test
	public void testCleanUpBoundary() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		gc.setupPlayers(2);
		gc.players.get(0).addCountry(new Country("testCountry", 1));

		gc.numPlayers = 2;

		gc.players.get(1).isDead = true;

		assertEquals(2, gc.players.size());

		gc.cleanUpPlayers();

		assertEquals(2, gc.players.size());
		assertTrue(gc.players.get(1).isDead);
	}

	@Test
	public void testCleanUpPlayers_noneRemoved() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameComponent gc = new GameComponent(mockGUI);

		gc.setupPlayers(2);
		gc.players.get(0).addCountry(new Country("testCountry", 1));
		gc.players.get(1).addCountry(new Country("testCountry2", 2));
		gc.numPlayers = 2;

		assertEquals(2, gc.players.size());

		gc.cleanUpPlayers();

		assertEquals(2, gc.players.size());
	}

	@Test
	public void testLoopOverPlayerAndSetup_2Players_1Army() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = EasyMock.mock(GameBoard.class);
		GameComponent gc = new GameComponent(mockGUI);
		gc.gameBoard = gb;
		gc.setupPlayers(2);
		gc.numPlayers = 2;

		gb.changePlayerConsole(1);
		EasyMock.expect(gb.setUpArmies(1)).andReturn(true);
		gb.changePlayerConsole(2);
		EasyMock.expect(gb.setUpArmies(2)).andReturn(true);

		EasyMock.replay(gb);

		gc.loopOverPlayersAndSetup(2, 1);

		EasyMock.verify(gb);
	}

	@Test
	public void testLoopOverPlayerAndSetup_2Players_2Armies() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = EasyMock.mock(GameBoard.class);
		GameComponent gc = new GameComponent(mockGUI);
		gc.gameBoard = gb;
		gc.setupPlayers(2);
		gc.numPlayers = 2;

		gb.changePlayerConsole(1);
		EasyMock.expect(gb.setUpArmies(1)).andReturn(true);
		gb.changePlayerConsole(2);
		EasyMock.expect(gb.setUpArmies(2)).andReturn(true);
		gb.changePlayerConsole(1);
		EasyMock.expect(gb.setUpArmies(1)).andReturn(true);
		gb.changePlayerConsole(2);
		EasyMock.expect(gb.setUpArmies(2)).andReturn(true);

		EasyMock.replay(gb);

		gc.loopOverPlayersAndSetup(2, 2);

		EasyMock.verify(gb);
	}

	@Test
	public void testPlaceInitialArmies_1() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = EasyMock.mock(GameBoard.class);
		GameComponent gc = EasyMock.partialMockBuilder(GameComponent.class).addMockedMethod("loopOverPlayersAndSetup")
				.withConstructor(mockGUI).createMock();

		gc.gameBoard = gb;
		gc.setupPlayers(2);
		gc.numPlayers = 2;

		EasyMock.expect(gb.rollDie()).andReturn(5);
		EasyMock.expect(gb.rollDie()).andReturn(1);
		gb.declareInitialPlayer(0);
		gc.loopOverPlayersAndSetup(2, 40);

		EasyMock.replay(gb, gc);

		gc.placeInitialArmies(2);

		EasyMock.verify(gb, gc);
	}

	@Test
	public void testPlaceInitialArmies_2() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = EasyMock.mock(GameBoard.class);
		GameComponent gc = EasyMock.partialMockBuilder(GameComponent.class).addMockedMethod("loopOverPlayersAndSetup")
				.withConstructor(mockGUI).createMock();

		gc.gameBoard = gb;
		gc.setupPlayers(2);
		gc.numPlayers = 2;

		EasyMock.expect(gb.rollDie()).andReturn(5);
		EasyMock.expect(gb.rollDie()).andReturn(5);
		gb.declareInitialPlayer(0);
		gc.loopOverPlayersAndSetup(2, 40);

		EasyMock.replay(gb, gc);

		gc.placeInitialArmies(2);

		EasyMock.verify(gb, gc);
	}

	@Test
	public void testSetupGame() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = EasyMock.mock(GameBoard.class);
		GameComponent gc = EasyMock.partialMockBuilder(GameComponent.class).addMockedMethod("placeInitialArmies")
				.withConstructor(mockGUI).createMock();

		gc.gameBoard = gb;

		EasyMock.expect(gb.getNumPlayers()).andReturn(2);
		gc.placeInitialArmies(2);

		EasyMock.replay(gc, gb);

		gc.setupGame();

		EasyMock.verify(gc, gb);

		assertEquals(2, gc.players.size());
	}

	@Test
	public void testStartGame_1() {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = EasyMock.mock(GameBoard.class);
		GameComponent gc = new GameComponent(mockGUI);

		gc.gameBoard = gb;
		gb.gui = mockGUI;
		gc.numPlayers = 2;
		gc.setupPlayers(2);
		gc.players.get(0).addCountry(new Country("testCountry", 1));

		mockGUI.updateCurrentPlayer(gc.players.get(0));
		gb.changePlayerConsole(1);
		gb.takeTurn(gc.players.get(0));
		
		mockGUI.showMessage("Game Over!");

		EasyMock.replay(gb, mockGUI);

		gc.startGame();

		EasyMock.verify(gb, mockGUI);
	}

	@Test
	public void testStartGame_2() throws InvalidAttributesException {
		GUI mockGUI = EasyMock.mock(GUI.class);
		GameBoard gb = EasyMock.mock(GameBoard.class);
		GameComponent gc = new GameComponent(mockGUI);

		Country america = new Country("America", 2);

		gc.gameBoard = gb;
		gb.gui = mockGUI;
		gc.numPlayers = 3;
		gc.setupPlayers(3);
		gc.players.get(0).addCountry(new Country("testCountry", 1));
		gc.players.get(2).addCountry(america);

		mockGUI.updateCurrentPlayer(gc.players.get(0));
		gb.changePlayerConsole(1);
		gb.takeTurn(gc.players.get(0));

		EasyMock.replay(gb, mockGUI);

		gc.unitTestVerification = true;
		
		int preStartCurrent = 2;
		
		gc.startGame();
		
		assertEquals(preStartCurrent, gc.current);
		

		EasyMock.verify(gb, mockGUI);
	}
}