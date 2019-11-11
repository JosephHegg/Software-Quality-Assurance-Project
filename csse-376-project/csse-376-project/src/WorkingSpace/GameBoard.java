package WorkingSpace;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

public class GameBoard {
	final int NUM_COUNTRIES = 42;
	final int MAX_PLAYERS = 6;
	final int MIN_PLAYERS = 2;
	public HashMap<Integer, Country> countries;
	public GUI gui;
	public Placement placement;
	public AttackPhase attackPhase;
	public FortifyPhase fortifyPhase;
	public DrawPhase drawPhase;
	public Random random;

	public Deck deck;

	public int armiesPlacedInSetup = 0;

	public int currentPlayerTurnID = 1;
	private ResourceBundle bundle = ResourceBundle.getBundle("res.strings");

	public GameBoard(GUI gui, ArrayList<Player> players) {
		this.countries = new HashMap<Integer, Country>();
		this.gui = gui;
		this.deck = new Deck();
		this.placement = new Placement(gui, countries, players);
		this.attackPhase = new AttackPhase(countries, gui, players);
		this.fortifyPhase = new FortifyPhase(1, countries, gui);
		this.drawPhase = new DrawPhase(null, gui, deck);
		this.random = new Random();

		initializeCountryMap();
		addCountryNeighbors();
	}

	public void showGUI() {
		this.gui.showGUI();
	}

	public void addCountry(int id, Country newCountry) {
		this.countries.put(id, newCountry);
	}

	public boolean setUpArmies(int ownerID) {
		return this.placement.setupArmies(ownerID);
	}

	public int getNumPlayers() throws IndexOutOfBoundsException {
		int num = 0;
		num = this.gui.askForNumberofPlayers();

		if (num < MIN_PLAYERS || num > MAX_PLAYERS) {
			num = -1;
		}

		return num;
	}

	public void takeTurn(Player currentPlayer) {
		this.drawPhase.player = currentPlayer;

		int armiesEarnedFromDrawPhase = this.drawPhase.checkValidHand();

		this.placement.placementTurn(currentPlayer, armiesEarnedFromDrawPhase);

		while (this.attackPhase.askForAttack()) {
			this.attackPhase.takeAttackAction(currentPlayer.getPlayerID());
		}

		if (this.attackPhase.hasMadeValidAttack()) {
			this.drawPhase.drawCard();
			this.attackPhase.resetTurn();
		}

		this.fortifyPhase.playerID = currentPlayer.getPlayerID();
		if (currentPlayer.ownedCountries.size() > 1 && this.fortifyPhase.askForFortify())
			this.fortifyPhase.takeFortifyAction();
	}

	public void changePlayerConsole(int playerID) {
		String message = MessageFormat.format(bundle.getString("changePlayerConsoleMessage"), playerID);
		this.currentPlayerTurnID = playerID;

		this.gui.updatePlayerConsole(playerID, message);
	}

	public void declareInitialPlayer(int playerID) {
		String message = MessageFormat.format(bundle.getString("declareInitialPlayerMessage"), playerID);
		this.gui.updatePlayerConsole(playerID, message);
	}

	public int rollDie() {
		return this.random.nextInt(6) + 1;
	}

	public void initializeCountryMap() {
		addCountry(0, new Country("Afghanistan", 0));
		addCountry(1, new Country("Alaska", 1));
		addCountry(2, new Country("Alberta", 2));
		addCountry(3, new Country("Argentina", 3));
		addCountry(4, new Country("Brazil", 4));
		addCountry(5, new Country("Central America", 5));
		addCountry(6, new Country("China", 6));
		addCountry(7, new Country("Congo", 7));
		addCountry(8, new Country("East Africa", 8));
		addCountry(9, new Country("East Australia", 9));
		addCountry(10, new Country("Eastern US", 10));
		addCountry(11, new Country("Egypt", 11));
		addCountry(12, new Country("Great Britain", 12));
		addCountry(13, new Country("Greenland", 13));
		addCountry(14, new Country("Iceland", 14));
		addCountry(15, new Country("India", 15));
		addCountry(16, new Country("Indonesia", 16));
		addCountry(17, new Country("Irkutsk", 17));
		addCountry(18, new Country("Japan", 18));
		addCountry(19, new Country("Kamchatka", 19));
		addCountry(20, new Country("Madagascar", 20));
		addCountry(21, new Country("Middle East", 21));
		addCountry(22, new Country("Mongolia", 22));
		addCountry(23, new Country("New Guinea", 23));
		addCountry(24, new Country("North Africa", 24));
		addCountry(25, new Country("North Europe", 25));
		addCountry(26, new Country("NW Territory", 26));
		addCountry(27, new Country("Ontario", 27));
		addCountry(28, new Country("Peru", 28));
		addCountry(29, new Country("Quebec", 29));
		addCountry(30, new Country("Scandinavia", 30));
		addCountry(31, new Country("Siam", 31));
		addCountry(32, new Country("Siberia", 32));
		addCountry(33, new Country("South Africa", 33));
		addCountry(34, new Country("South Europe", 34));
		addCountry(35, new Country("Ukraine", 35));
		addCountry(36, new Country("Ural", 36));
		addCountry(37, new Country("Venezuela", 37));
		addCountry(38, new Country("West Australia", 38));
		addCountry(39, new Country("West Europe", 39));
		addCountry(40, new Country("Western US", 40));
		addCountry(41, new Country("Yakutsk", 41));
	}

	public void addCountryNeighbors() {
		Set<Integer> neighbors = new HashSet<>();

		int[] afgahnistan = { 36, 35, 21, 15, 6 };
		neighbors = getNewNeighborSet(afgahnistan);
		this.countries.get(0).neighboringCountryIDs = neighbors;

		int[] alaska = { 2, 26, 19 };
		neighbors = getNewNeighborSet(alaska);
		this.countries.get(1).neighboringCountryIDs = neighbors;

		int[] alberta = { 1, 26, 27, 40 };
		neighbors = getNewNeighborSet(alberta);
		this.countries.get(2).neighboringCountryIDs = neighbors;

		int[] argentina = { 4, 28 };
		neighbors = getNewNeighborSet(argentina);
		this.countries.get(3).neighboringCountryIDs = neighbors;

		int[] brazil = { 28, 3, 37, 24 };
		neighbors = getNewNeighborSet(brazil);
		this.countries.get(4).neighboringCountryIDs = neighbors;

		int[] centralAmerica = { 40, 10, 37 };
		neighbors = getNewNeighborSet(centralAmerica);
		this.countries.get(5).neighboringCountryIDs = neighbors;

		int[] china = { 15, 31, 22, 32, 36, 0 };
		neighbors = getNewNeighborSet(china);
		this.countries.get(6).neighboringCountryIDs = neighbors;

		int[] congo = { 24, 8, 33 };
		neighbors = getNewNeighborSet(congo);
		this.countries.get(7).neighboringCountryIDs = neighbors;

		int[] eastAfrica = { 7, 33, 24, 11, 20, 21 };
		neighbors = getNewNeighborSet(eastAfrica);
		this.countries.get(8).neighboringCountryIDs = neighbors;

		int[] eastAustralia = { 38, 23 };
		neighbors = getNewNeighborSet(eastAustralia);
		this.countries.get(9).neighboringCountryIDs = neighbors;

		int[] easternUS = { 5, 40, 27, 29 };
		neighbors = getNewNeighborSet(easternUS);
		this.countries.get(10).neighboringCountryIDs = neighbors;

		int[] egypt = { 24, 8, 34, 21 };
		neighbors = getNewNeighborSet(egypt);
		this.countries.get(11).neighboringCountryIDs = neighbors;

		int[] greatBritain = { 39, 25, 30, 14 };
		neighbors = getNewNeighborSet(greatBritain);
		this.countries.get(12).neighboringCountryIDs = neighbors;

		int[] greenland = { 14, 29, 27, 26 };
		neighbors = getNewNeighborSet(greenland);
		this.countries.get(13).neighboringCountryIDs = neighbors;

		int[] iceland = { 13, 12, 30 };
		neighbors = getNewNeighborSet(iceland);
		this.countries.get(14).neighboringCountryIDs = neighbors;

		int[] india = { 31, 6, 0, 21 };
		neighbors = getNewNeighborSet(india);
		this.countries.get(15).neighboringCountryIDs = neighbors;

		int[] indonesia = { 38, 31, 23 };
		neighbors = getNewNeighborSet(indonesia);
		this.countries.get(16).neighboringCountryIDs = neighbors;

		int[] irkutsk = { 22, 32, 41, 19 };
		neighbors = getNewNeighborSet(irkutsk);
		this.countries.get(17).neighboringCountryIDs = neighbors;

		int[] japan = { 22, 19 };
		neighbors = getNewNeighborSet(japan);
		this.countries.get(18).neighboringCountryIDs = neighbors;

		int[] kamchatka = { 18, 1, 41, 17, 22 };
		neighbors = getNewNeighborSet(kamchatka);
		this.countries.get(19).neighboringCountryIDs = neighbors;

		int[] madagascar = { 33, 8 };
		neighbors = getNewNeighborSet(madagascar);
		this.countries.get(20).neighboringCountryIDs = neighbors;

		int[] middleEast = { 8, 11, 34, 35, 0, 15 };
		neighbors = getNewNeighborSet(middleEast);
		this.countries.get(21).neighboringCountryIDs = neighbors;

		int[] mongolia = { 6, 32, 17, 19, 18 };
		neighbors = getNewNeighborSet(mongolia);
		this.countries.get(22).neighboringCountryIDs = neighbors;

		int[] newGuinea = { 9, 38, 16 };
		neighbors = getNewNeighborSet(newGuinea);
		this.countries.get(23).neighboringCountryIDs = neighbors;

		int[] northAfrica = { 11, 7, 8, 34, 39, 4 };
		neighbors = getNewNeighborSet(northAfrica);
		this.countries.get(24).neighboringCountryIDs = neighbors;

		int[] northEurope = { 30, 12, 39, 34, 35 };
		neighbors = getNewNeighborSet(northEurope);
		this.countries.get(25).neighboringCountryIDs = neighbors;

		int[] nwTerritory = { 1, 13, 27, 2 };
		neighbors = getNewNeighborSet(nwTerritory);
		this.countries.get(26).neighboringCountryIDs = neighbors;

		int[] ontario = { 2, 26, 40, 10, 29, 13 };
		neighbors = getNewNeighborSet(ontario);
		this.countries.get(27).neighboringCountryIDs = neighbors;

		int[] peru = { 4, 37, 3 };
		neighbors = getNewNeighborSet(peru);
		this.countries.get(28).neighboringCountryIDs = neighbors;

		int[] quebec = { 13, 10, 27 };
		neighbors = getNewNeighborSet(quebec);
		this.countries.get(29).neighboringCountryIDs = neighbors;

		int[] scandinavia = { 35, 25, 14, 12 };
		neighbors = getNewNeighborSet(scandinavia);
		this.countries.get(30).neighboringCountryIDs = neighbors;

		int[] siam = { 6, 15, 16 };
		neighbors = getNewNeighborSet(siam);
		this.countries.get(31).neighboringCountryIDs = neighbors;

		int[] siberia = { 41, 17, 22, 6, 36 };
		neighbors = getNewNeighborSet(siberia);
		this.countries.get(32).neighboringCountryIDs = neighbors;

		int[] southAfrica = { 20, 7, 8 };
		neighbors = getNewNeighborSet(southAfrica);
		this.countries.get(33).neighboringCountryIDs = neighbors;

		int[] southEurope = { 11, 24, 39, 25, 35, 21 };
		neighbors = getNewNeighborSet(southEurope);
		this.countries.get(34).neighboringCountryIDs = neighbors;

		int[] ukraine = { 30, 34, 25, 21, 0, 36 };
		neighbors = getNewNeighborSet(ukraine);
		this.countries.get(35).neighboringCountryIDs = neighbors;

		int[] ural = { 35, 0, 6, 32 };
		neighbors = getNewNeighborSet(ural);
		this.countries.get(36).neighboringCountryIDs = neighbors;

		int[] venezuela = { 5, 4, 28 };
		neighbors = getNewNeighborSet(venezuela);
		this.countries.get(37).neighboringCountryIDs = neighbors;

		int[] westAustralia = { 9, 16, 23 };
		neighbors = getNewNeighborSet(westAustralia);
		this.countries.get(38).neighboringCountryIDs = neighbors;

		int[] westEurope = { 34, 25, 12, 24 };
		neighbors = getNewNeighborSet(westEurope);
		this.countries.get(39).neighboringCountryIDs = neighbors;

		int[] westernUS = { 5, 10, 2, 27 };
		neighbors = getNewNeighborSet(westernUS);
		this.countries.get(40).neighboringCountryIDs = neighbors;

		int[] yakutsk = { 19, 17, 32 };
		neighbors = getNewNeighborSet(yakutsk);
		this.countries.get(41).neighboringCountryIDs = neighbors;
	}

	private Set<Integer> getNewNeighborSet(int[] ids) {
		Set<Integer> n = new HashSet<>();
		for (Integer i : ids) {
			n.add(i);
		}
		return n;
	}
}
