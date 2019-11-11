package WorkingSpace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Test;

import WorkingSpace.Country;
import WorkingSpace.GUI;
import WorkingSpace.NotEnoughCountriesException;
import WorkingSpace.Placement;
import WorkingSpace.Player;

public class PlacementTests {
	
	@Test
	public void testStartingArmiesMulipleAverageCases(){
		Player player = new Player(1);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), EasyMock.mock(HashMap.class), EasyMock.mock(ArrayList.class));
		
		int id = 0;
		
		for(int i = 0; i < 11; i++)
			player.addCountry(new Country("TEST", id++));
		
		assertEquals(3, placement.calculateStartingArmies(player));
		
		for(int i = 0; i < 9; i++)
			player.addCountry(new Country("TEST", id++));
		assertEquals(6, placement.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesNoOwnedCoutnries(){
		Player mockPlayer = EasyMock.mock(Player.class);
		HashSet<Country> mockSet = EasyMock.mock(HashSet.class);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), EasyMock.mock(HashMap.class), EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(mockSet.size()).andReturn(0);
		EasyMock.expect(mockPlayer.getOwnedCountries()).andReturn(mockSet);
		
		EasyMock.replay(mockSet, mockPlayer);
		
		try {
			placement.calculateStartingArmies(mockPlayer);
			fail(); //Should throw exception
		} catch(IllegalArgumentException e) {
			EasyMock.verify(mockSet, mockPlayer);
		}
	}
	private void addEuropeanCountries(Player player) {
		Set<Country> countries = player.ownedCountries;
		countries.add(new Country("West Europe", 39));
		countries.add(new Country("Iceland", 14));
		countries.add(new Country("Scandinavia", 30));
		countries.add(new Country("Great Britain", 12));
		countries.add(new Country("Ukraine", 35));
		countries.add(new Country("South Europe", 34));
		countries.add(new Country("North Europe", 25));
	}
	
	private void addAsianCountries(Player player) {
		Set<Country> countries = player.ownedCountries;
		countries.add(new Country("Yakutsk", 41));
		countries.add(new Country("Ural", 36));
		countries.add(new Country("Siberia", 32));
		countries.add(new Country("Afghanistan", 0));
		countries.add(new Country("Irkutsk", 17));
		countries.add(new Country("Japan", 18));
		countries.add(new Country("Kamchatka", 19));
		countries.add(new Country("Middle East", 21));
		countries.add(new Country("Mongolia", 22));
		countries.add(new Country("Siam", 31));
		countries.add(new Country("India", 15));
		countries.add(new Country("China", 6));
	}
	
	private void addAustralianCountries(Player player) {
		Set<Country> countries = player.ownedCountries;
		countries.add(new Country("Indonesia", 16));
		countries.add(new Country("New Guinea", 23));
		countries.add(new Country("East Australia", 9));
		countries.add(new Country("West Australia", 38));
	}
	
	private void addSouthAmericanCountries(Player player) {
		Set<Country> countries = player.ownedCountries;
		countries.add(new Country("Venezuela", 37));
		countries.add(new Country("Brazil", 4));
		countries.add(new Country("Peru", 28));
		countries.add(new Country("Argentina", 3));
	}
	
	private void addNorthAmericanCountries(Player player) {
		Set<Country> countries = player.ownedCountries;
		countries.add(new Country("Alaska", 1));
		countries.add(new Country("Alberta", 2));
		countries.add(new Country("Central America", 5));
		countries.add(new Country("Eastern US", 10));
		countries.add(new Country("Greenland", 13));
		countries.add(new Country("NW Territory", 26));
		countries.add(new Country("Ontario", 27));
		countries.add(new Country("Quebec", 29));
		countries.add(new Country("Western US", 40));
	}
	
	private void addAfricanCountries(Player player) {
		Set<Country> countries = player.ownedCountries;
		countries.add( new Country("Congo", 7));
		countries.add(new Country("Egypt", 11));
		countries.add(new Country("Madagascar", 20));
		countries.add(new Country("North Africa", 24));
		countries.add(new Country("South Africa", 33));
		countries.add(new Country("East Africa", 8));
	}
	
	
	
	@Test
	public void testStartingArmiesWithContinentOwned(){
		Player player = new Player(1);
		addEuropeanCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
		
		assertEquals(8, p.calculateStartingArmies(player));	
	}
	
	@Test
	public void testStartingArmiesWithEuropeAndAfricaContinentOwned() {
		Player player = new Player(1);
		addEuropeanCountries(player);
		addAfricanCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
		
		assertEquals(12, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesWithAllButAsiaOwned() {
		Player player = new Player(1);
		addEuropeanCountries(player);
		addAustralianCountries(player);
		addAfricanCountries(player);
		addNorthAmericanCountries(player);
		addSouthAmericanCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
		
		assertEquals(27, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesWithNorthAndSouthAmericaOwned() {
		Player player = new Player(1);
		addNorthAmericanCountries(player);
		addSouthAmericanCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
		
		assertEquals(11, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesNorthAmericaOwned() {
		Player player = new Player(1);
		addNorthAmericanCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
	
		assertEquals(8, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesSouthAmericaOwned() {
		Player player = new Player(1);
		addSouthAmericanCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
	
		assertEquals(5, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesAsiaOwned(){
		Player player = new Player(1);
		addAsianCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
	
		assertEquals(11, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesAfricaOwned(){
		Player player = new Player(1);
		addAfricanCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
	
		assertEquals(6, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesAustraliaOwned() {
		Player player = new Player(1);
		addAustralianCountries(player);
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
	
		assertEquals(5, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testRedeemCardsForExtraArmies(){
		Player player = new Player(1);
		
		addSouthAmericanCountries(player);
		
		Country country = EasyMock.niceMock(Country.class);	
		
		HashMap<Integer, Country> map = new HashMap<Integer, Country>();
		map.put(0, country);
		
		GUI gui = EasyMock.niceMock(GUI.class);
		Placement p = new Placement(gui, map, EasyMock.mock(ArrayList.class));
	
		
		EasyMock.expect(gui.selectCountry()).andStubReturn(0);
		
		
		EasyMock.expect(country.getOwnerID()).andStubReturn(1);
		EasyMock.expect(country.getArmyCount()).andStubReturn(1);
		country.addArmies(1);
		
		EasyMock.replay(gui, country);
		
		
		int extraArmies = 4;
		
		int returnedArmies = p.placementTurn(player, extraArmies);
		
		assertEquals(9, returnedArmies);
		
		EasyMock.verify(gui, country);
	}
	@Test
	public void testStartingArmiesTwoContinentsWith20CountriesOwned() {
		Player player = new Player(1);
		addAfricanCountries(player);
		addAsianCountries(player);
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		
		HashMap<Integer, Country> mockMap = EasyMock.mock(HashMap.class);
		Placement p = new Placement(EasyMock.mock(GUI.class), mockMap, EasyMock.mock(ArrayList.class));
	
		assertEquals(16, p.calculateStartingArmies(player));
	}
	
	@Test
	public void testOneFullPlacementTurn() {
		Player player = new Player(1);
		
		for(Country c : player.ownedCountries)
			c.setOwnerID(1);
		
		HashMap<Integer, Country> countries = new HashMap<Integer, Country>();
		Country we = new Country("West Europe", 39);
		Country ice = new Country("Iceland", 14);
		Country scand = new Country("Scandinavia", 30);
		Country gb = new Country("Great Britain", 12); 
		Country ukraine = new Country("Ukraine", 35);
		Country se = new Country("South Europe", 34);
		Country ne = new Country("North Europe", 25);
		
		
		countries.put(39, we);
		countries.put(14, ice);
		countries.put(30, scand);
		countries.put(12, gb);
		countries.put(35, ukraine);
		countries.put(34, se);
		countries.put(25, ne);
		
		player.addCountry(we);
		player.addCountry(ice);
		player.addCountry(scand);
		player.addCountry(gb);
		player.addCountry(ukraine);
		player.addCountry(se);
		player.addCountry(ne);
		
		for(Country c : player.ownedCountries){
			c.setOwnerID(1);
		}
		
		GUI gui = EasyMock.mock(GUI.class);
		Placement p = new Placement(gui, countries, EasyMock.mock(ArrayList.class));
		
		EasyMock.expect(gui.selectCountry()).andStubReturn(25);
		gui.updatePlayerConsole(1, "Player 1 \nyou have 8 to place");
		EasyMock.expectLastCall();
		gui.updateArmies(1, 25);
		EasyMock.expectLastCall();
		
		gui.updatePlayerConsole(1, "Player 1 \nyou have 7 to place");
		EasyMock.expectLastCall();
		gui.updateArmies(2, 25);
		EasyMock.expectLastCall();
		
		gui.updatePlayerConsole(1, "Player 1 \nyou have 6 to place");
		EasyMock.expectLastCall();
		gui.updateArmies(3, 25);
		EasyMock.expectLastCall();
		
		gui.updatePlayerConsole(1, "Player 1 \nyou have 5 to place");
		EasyMock.expectLastCall();
		gui.updateArmies(4, 25);
		EasyMock.expectLastCall();
		
		gui.updatePlayerConsole(1, "Player 1 \nyou have 4 to place");
		EasyMock.expectLastCall();
		gui.updateArmies(5, 25);
		EasyMock.expectLastCall();
		
		gui.updatePlayerConsole(1, "Player 1 \nyou have 3 to place");
		EasyMock.expectLastCall();
		gui.updateArmies(6, 25);
		EasyMock.expectLastCall();
		
		gui.updatePlayerConsole(1, "Player 1 \nyou have 2 to place");
		EasyMock.expectLastCall();
		gui.updateArmies(7, 25);
		EasyMock.expectLastCall();
		
		gui.updatePlayerConsole(1, "Player 1 \nyou have 1 to place");
		EasyMock.expectLastCall();
		gui.updateArmies(8, 25);
		EasyMock.expectLastCall();
		
		EasyMock.replay(gui);
		assertEquals(8, p.calculateStartingArmies(player));
		
		
		p.placementTurn(player, 0);
		int countNumArmies = 0;
		for(Country c : player.ownedCountries){
			countNumArmies += c.getArmyCount();
		}
		
		
		assertEquals(8, countNumArmies);
		
		EasyMock.verify(gui);
	}
	
	@Test
	public void testStartingArmiesSingleCountryOwnedNoContinents(){
		Player player = new Player(1);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), EasyMock.mock(HashMap.class), EasyMock.mock(ArrayList.class));
		
		player.ownedCountries.add(new Country("West Europe", 39));
		
		assertEquals(3, placement.calculateStartingArmies(player));
		
	}
	
	@Test
	public void testStartingArmiesElevenCountriesOwnedNoContinents(){
		Player player = new Player(1);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), EasyMock.mock(HashMap.class), EasyMock.mock(ArrayList.class));
		
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		player.addCountry(new Country("TEST", 99));
		assertEquals(3, placement.calculateStartingArmies(player));
	}
	
	@Test
	public void testStartingArmiesTwelveCountriesOwnedNoContinents(){
		Player player = new Player(1);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), EasyMock.mock(HashMap.class), EasyMock.mock(ArrayList.class));
		
		for(int i = 0; i < 12; i++){
			player.addCountry(new Country("TEST", i));
		}
		
		assertEquals(4, placement.calculateStartingArmies(player));
	}
	
	@Test(expected = NotEnoughCountriesException.class)
	public void testStartingArmiesMoreCountriesThanOnBoard(){
		Player player = new Player(1);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), EasyMock.mock(HashMap.class), EasyMock.mock(ArrayList.class));
		
		for(int i = 0; i < 43; i++)
			player.addCountry(new Country("TEST", i));
			
		
		placement.calculateStartingArmies(player); //Error should occur here
	}
	
	@Test
	public void testStartingArmiesAllCountriesOwned(){
		Player player = new Player(1);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), EasyMock.mock(HashMap.class), EasyMock.mock(ArrayList.class));
		
		addEuropeanCountries(player);
		addAfricanCountries(player);
		addNorthAmericanCountries(player);
		addSouthAmericanCountries(player);
		addAsianCountries(player);
		addAustralianCountries(player);
		
		assertEquals(38, placement.calculateStartingArmies(player));
	}
	
	@Test
	public void testCanPlaceArmiesInSetupWithZeroArmiesPlaced() {
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), mockedCountryMap, EasyMock.mock(ArrayList.class));
		boolean answer = false;
		
		Country mockedCountry = EasyMock.mock(Country.class);
		
		EasyMock.expect(mockedCountry.getOwnerID()).andReturn(1);
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		
		EasyMock.replay(mockedCountry, mockedCountryMap);
		answer = placement.canPlaceArmyInSetup(1, 2);
		assertEquals(false, answer);
		
		EasyMock.verify(mockedCountry, mockedCountryMap);
	}
	
	@Test
	public void testCanPlaceArmiesUnownedCountry() {
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), mockedCountryMap, EasyMock.mock(ArrayList.class));
		boolean answer = false;
		
		Country mockedCountry = EasyMock.mock(Country.class);
		
		EasyMock.expect(mockedCountry.getOwnerID()).andReturn(99);
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		
		EasyMock.replay(mockedCountry, mockedCountryMap);
		answer = placement.canPlaceArmyInSetup(1, 1);
		assertEquals(true, answer);
		
		EasyMock.verify(mockedCountry, mockedCountryMap);
	}
	
	@Test
	public void testCanPlaceArmiesAlmostAllCountriesPicked() {
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), mockedCountryMap, EasyMock.mock(ArrayList.class));
		boolean answer = false;
		
		Country mockedCountry = EasyMock.mock(Country.class);
		
		placement.armiesPlacedInSetup = 41;
		EasyMock.expect(mockedCountry.getOwnerID()).andReturn(99);
		EasyMock.expect(mockedCountryMap.get(2)).andReturn(mockedCountry);
		
		EasyMock.replay(mockedCountry, mockedCountryMap);
		answer = placement.canPlaceArmyInSetup(2, 4);
		assertEquals(true, answer);
		
		EasyMock.verify(mockedCountry, mockedCountryMap);
	}
	
	@Test
	public void testCanPlaceArmiesAllCountriesPicked() {
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), mockedCountryMap, EasyMock.mock(ArrayList.class));
		boolean answer = false;
		
		Country mockedCountry = EasyMock.mock(Country.class);
		
		placement.armiesPlacedInSetup = 42;
		
		EasyMock.expect(mockedCountryMap.get(3)).andReturn(mockedCountry);
		EasyMock.expect(mockedCountry.getOwnerID()).andReturn(4);
		EasyMock.expect(mockedCountryMap.get(3)).andReturn(mockedCountry);
		
		EasyMock.replay(mockedCountry, mockedCountryMap);
		answer = placement.canPlaceArmyInSetup(3, 4);
		assertEquals(true, answer);
		
		EasyMock.verify(mockedCountry, mockedCountryMap);
	}
	
	@Test
	public void testCanPlaceArmiesAllCountriesPickedButCountryNotOwned() {
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		Placement placement  = new Placement(EasyMock.mock(GUI.class), mockedCountryMap, EasyMock.mock(ArrayList.class));
		boolean answer = false;
		
		Country mockedCountry = EasyMock.mock(Country.class);
		
		placement.armiesPlacedInSetup = 42;
		
		EasyMock.expect(mockedCountryMap.get(3)).andReturn(mockedCountry);
		EasyMock.expect(mockedCountry.getOwnerID()).andReturn(5);
		EasyMock.expect(mockedCountryMap.get(3)).andReturn(mockedCountry);
		
		EasyMock.replay(mockedCountry, mockedCountryMap);
		answer = placement.canPlaceArmyInSetup(3, 4);
		assertEquals(false, answer);
		
		EasyMock.verify(mockedCountry, mockedCountryMap);
	}
	
	@Test
	public void testSetUpArmies() {
		GUI mockedGUI = EasyMock.niceMock(GUI.class);
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		
		Placement placement = new Placement(mockedGUI, mockedCountryMap, EasyMock.mock(ArrayList.class));
		placement.armiesPlacedInSetup = 41;
		
		Country mockedCountry = EasyMock.niceMock(Country.class);
		
		EasyMock.expect(mockedGUI.selectCountry()).andReturn(1);
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		EasyMock.expect(mockedCountry.getOwnerID()).andReturn(99);			//CHANGE HERE
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		
		EasyMock.replay(mockedGUI, mockedCountry, mockedCountryMap);
		placement.setupArmies(1);
		
		assertEquals(42, placement.armiesPlacedInSetup);
		EasyMock.verify(mockedGUI, mockedCountry, mockedCountryMap);	
	}
	
	@Test
	public void testSetUpArmiesEdgeCase() {
		GUI mockedGUI = EasyMock.niceMock(GUI.class);
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		
		Placement placement = new Placement(mockedGUI, mockedCountryMap, EasyMock.mock(ArrayList.class));
		placement.armiesPlacedInSetup = 42;
		
		Country mockedCountry = EasyMock.niceMock(Country.class);
		
		EasyMock.expect(mockedGUI.selectCountry()).andReturn(1);
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		EasyMock.expect(mockedCountry.getOwnerID()).andReturn(1);
		
		EasyMock.replay(mockedGUI, mockedCountry, mockedCountryMap);
		placement.setupArmies(1);
		
		assertEquals(43, placement.armiesPlacedInSetup);
		EasyMock.verify(mockedGUI, mockedCountry, mockedCountryMap);	
	}	
	
	@Test
	public void testPlaceArmyOnOwnedCountry(){
		GUI mockedGUI = EasyMock.niceMock(GUI.class);
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		
		Placement placement = new Placement(mockedGUI, mockedCountryMap, EasyMock.mock(ArrayList.class));
		
		Country mockedCountry = EasyMock.niceMock(Country.class);
		
		EasyMock.expect(mockedGUI.selectCountry()).andReturn(1);
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		EasyMock.expect(mockedCountry.getOwnerID()).andReturn(1);
		EasyMock.expect(mockedCountryMap.get(1)).andReturn(mockedCountry);
		EasyMock.expect(mockedCountry.getArmyCount()).andReturn(1); //Value doesn't matter
		
		EasyMock.replay(mockedGUI, mockedCountryMap, mockedCountry);
		
		placement.placeArmyOnOwnedCountry(1);
		
		EasyMock.verify(mockedGUI, mockedCountryMap, mockedCountry);
	}
	
	@Test
	public void testSetupArmiesMethodArmiesPlacedBoundary(){
		GUI mockedGUI = EasyMock.niceMock(GUI.class);
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		ArrayList<Player> mockedPlayers = EasyMock.niceMock(ArrayList.class);
		
		Placement p = EasyMock.partialMockBuilder(Placement.class)
				.addMockedMethod("selectValidCountry").withConstructor(mockedGUI, mockedCountryMap, 					mockedPlayers).createMock();
		
		Country country = new Country("TEST", 99);
		
		p.armiesPlacedInSetup = 41;
		p.NUM_COUNTRIES = 42;
		
		int ownerID = 1;
		int countryID = 0;
		
		EasyMock.expect(p.selectValidCountry(ownerID, true)).andReturn(countryID);
		EasyMock.expect(mockedCountryMap.get(countryID)).andReturn(country);
		mockedGUI.changeCountryControl(1, ownerID, countryID);
		EasyMock.expect(mockedPlayers.get(0)).andReturn(new Player(1));
		
		EasyMock.replay(mockedGUI, mockedCountryMap, mockedPlayers);
		
		boolean armyPlaced = p.setupArmies(ownerID);
		
		assertEquals(1, country.getArmyCount());
		assertTrue(armyPlaced);
		
		EasyMock.verify(mockedGUI, mockedCountryMap, mockedPlayers);
	}
	
	@Test
	public void testSetupArmiesMethodArmiesPlacedBoundaryUpper(){
		GUI mockedGUI = EasyMock.niceMock(GUI.class);
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		ArrayList<Player> mockedPlayers = EasyMock.niceMock(ArrayList.class);
		
		Placement p = EasyMock.partialMockBuilder(Placement.class)
				.addMockedMethod("selectValidCountry").withConstructor(mockedGUI, mockedCountryMap, 					mockedPlayers).createMock();
		
		Country country = new Country("TEST", 99);
		
		p.armiesPlacedInSetup = 42;
		p.NUM_COUNTRIES = 42;
		
		int ownerID = 1;
		int countryID = 0;
		
		EasyMock.expect(p.selectValidCountry(ownerID, true)).andReturn(countryID);
		EasyMock.expect(mockedCountryMap.get(countryID)).andReturn(country);
		
		mockedGUI.updateArmies(1, 0);
		
		EasyMock.replay(mockedGUI, mockedCountryMap, mockedPlayers);
		
		boolean armyPlaced = p.setupArmies(ownerID);
		
		assertEquals(1, country.getArmyCount());
		assertFalse(armyPlaced);
		
		EasyMock.verify(mockedGUI, mockedCountryMap, mockedPlayers);
	}
	
	@Test
	public void testCalcContinentValueDifferentContinentIDs() {
		GUI mockedGUI = EasyMock.niceMock(GUI.class);
		HashMap<Integer, Country> mockedCountryMap = EasyMock.mock(HashMap.class);
		ArrayList<Player> mockedPlayers = EasyMock.niceMock(ArrayList.class);
		
		Placement p = new Placement(mockedGUI, mockedCountryMap, mockedPlayers);
		
		p.continentsIDs = new int[][]{{1,2}};
		
		int val = p.calcContinentValue(p.continentsIDs[0]);
		
		assertEquals(0, val);
	}
	
}
