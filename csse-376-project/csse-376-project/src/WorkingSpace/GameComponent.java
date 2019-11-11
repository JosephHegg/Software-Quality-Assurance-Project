package WorkingSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameComponent {
	public ArrayList<Player> players;
	private HashMap<Integer, Integer> numPlayersToArmies = new HashMap<Integer, Integer>();
	public GameBoard gameBoard;
	public int current;
	public int numPlayers;
	private int alivePlayers;
	public GUI gui;

	public boolean unitTestVerification = false;
	private ResourceBundle bundle = ResourceBundle.getBundle("res.strings");

	public GameComponent(GUI gui) {
		this.players = new ArrayList<>();
		this.gameBoard = new GameBoard(gui, players);
		this.gui = gui;
		setupArmyMap();
	}

	public void showGame() {
		this.gameBoard.showGUI();
	}

	public void setupGame() {
		int numPlayers = getNumPlayersFromBoard();
		this.numPlayers = numPlayers;
		setupPlayers(numPlayers);
		placeInitialArmies(numPlayers);
	}

	public void placeInitialArmies(int numPlayers) {
		int armiesPerPlayer = getArmiesPerPlayer(numPlayers);
		this.current = getFirstPlayer(numPlayers) - 1;
		this.gameBoard.declareInitialPlayer(this.current);

		loopOverPlayersAndSetup(numPlayers, armiesPerPlayer);
	}

	public void loopOverPlayersAndSetup(int numPlayers, int armiesPerPlayer) {
		for (int i = 0; i < armiesPerPlayer; i++) {
			int j = 0;
			while (j < numPlayers) {

				this.gui.updateCurrentPlayer(this.players.get(this.current));

				this.gameBoard.changePlayerConsole(players.get(this.current).playerID);
				this.gameBoard.setUpArmies(players.get(this.current).playerID);

				this.current = (this.current + 1) % numPlayers;
				j++;
			}
		}
	}

	private int getFirstPlayer(int numPlayers) {
		int winner = 0;
		int highRoll = -1;

		for (Player player : this.players) {
			int roll = this.gameBoard.rollDie();
			if (highRoll < roll) {
				highRoll = roll;
				winner = player.playerID;
			}
		}

		return winner;
	}

	public void startGame() {
		boolean gameOver = false;
		this.alivePlayers = this.numPlayers;

		while (!gameOver) {
			this.gui.updateCurrentPlayer(this.players.get(this.current));
			this.gameBoard.changePlayerConsole(players.get(this.current).playerID);

			this.gameBoard.takeTurn(players.get(this.current));

			cleanUpPlayers();
			if (this.alivePlayers <= 1) {
				gameOver = true;
				this.gameBoard.gui.showMessage(bundle.getString("gameOverMessage"));

			} else {
				this.current = (this.current + 1) % this.numPlayers;
				while (players.get(this.current).isDead) {
					this.current = (this.current + 1) % this.numPlayers;
				}

			}

			if (this.unitTestVerification) {
				return;
			}

		}
	}

	public void cleanUpPlayers() {
		for (int i = 0; i < this.numPlayers; i++) {
			if (this.players.get(i).ownedCountries.size() == 0 && !this.players.get(i).isDead) {
				this.alivePlayers--;
				this.players.get(i).isDead = true;
			}
		}
	}

	private void setupArmyMap() {
		numPlayersToArmies.put(2, 40);
		numPlayersToArmies.put(3, 35);
		numPlayersToArmies.put(4, 30);
		numPlayersToArmies.put(5, 25);
		numPlayersToArmies.put(6, 20);
	}

	public void setupPlayers(int numPlayers) {
		if (numPlayers < 2 || numPlayers > 6)
			throw new IllegalArgumentException();

		for (int i = 1; i <= numPlayers; i++) {
			this.players.add(new Player(i));
		}
	}

	public int getArmiesPerPlayer(int numPlayers) {
		if (numPlayers < 2 || numPlayers > 6)
			throw new IllegalArgumentException();
		return numPlayersToArmies.get(numPlayers);
	}

	public int getNumPlayersFromBoard() {
		return this.gameBoard.getNumPlayers();
	}

}
