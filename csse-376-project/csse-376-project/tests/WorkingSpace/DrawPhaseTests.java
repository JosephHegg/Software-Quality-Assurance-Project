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

public class DrawPhaseTests {

	final String CAVALRY = "Cavalry";
	final String ARTILLERY = "Artillery";
	final String INFANTRY = "Infantry";
	final String WILD = "Wild";

	@Test
	public void playerDrawSingleCardTest() {
		Player player = new Player(1);
		Deck deck = EasyMock.mock(Deck.class);
		DrawPhase dp = new DrawPhase(player, null, deck);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.replay(deck);

		dp.drawCard();
		int value = dp.checkValidHand();

		assertEquals(1, dp.getPlayerID());
		assertEquals(1, dp.getPlayerHandSizeOfCardType(CAVALRY));
		assertEquals(0, value);

		EasyMock.verify(deck);

	}

	@Test
	public void playerHasValidHandNoWild() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cards = new ArrayList<>();

		for (int i = 0; i < 3; i++)
			cards.add(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);
		gui.disposeCardFrame();

		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);

	}

	@Test
	public void playerHasValidHandYesWild_1() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cards = new ArrayList<>();

		for (int i = 0; i < 2; i++)
			cards.add(CAVALRY);

		cards.add(WILD);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(WILD);
		deck.discardCard(WILD);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);
		gui.disposeCardFrame();

		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);
		deck.discardCard(WILD);

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);

	}

	@Test
	public void playerHasValidHandYesWild_2() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cards = new ArrayList<>();

		cards.add(WILD);
		cards.add(INFANTRY);
		cards.add(INFANTRY);

		EasyMock.expect(deck.drawCard()).andReturn(INFANTRY);
		deck.discardCard(INFANTRY);

		EasyMock.expect(deck.drawCard()).andReturn(INFANTRY);
		deck.discardCard(INFANTRY);

		EasyMock.expect(deck.drawCard()).andReturn(WILD);
		deck.discardCard(WILD);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);
		gui.disposeCardFrame();

		deck.discardCard(INFANTRY);
		deck.discardCard(INFANTRY);
		deck.discardCard(WILD);

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);

	}

	@Test
	public void playerHasValidOneOfEachKind() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cards = new ArrayList<>();

		cards.add(INFANTRY);
		cards.add(ARTILLERY);
		cards.add(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(ARTILLERY);
		deck.discardCard(ARTILLERY);

		EasyMock.expect(deck.drawCard()).andReturn(INFANTRY);
		deck.discardCard(INFANTRY);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);
		gui.disposeCardFrame();

		deck.discardCard(CAVALRY);
		deck.discardCard(INFANTRY);
		deck.discardCard(ARTILLERY);

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);
	}

	@Test
	public void playerHasValidTwoUniqueOneWild() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cards = new ArrayList<>();

		cards.add(INFANTRY);
		cards.add(ARTILLERY);
		cards.add(WILD);

		EasyMock.expect(deck.drawCard()).andReturn(WILD);
		deck.discardCard(WILD);

		EasyMock.expect(deck.drawCard()).andReturn(ARTILLERY);
		deck.discardCard(ARTILLERY);

		EasyMock.expect(deck.drawCard()).andReturn(INFANTRY);
		deck.discardCard(INFANTRY);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);
		gui.disposeCardFrame();

		deck.discardCard(WILD);
		deck.discardCard(INFANTRY);
		deck.discardCard(ARTILLERY);

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);
	}

	@Test
	public void playerHasValidOneUniqueAndTwoWilds() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cards = new ArrayList<>();

		cards.add(INFANTRY);
		cards.add(WILD);
		cards.add(WILD);

		EasyMock.expect(deck.drawCard()).andReturn(WILD);
		deck.discardCard(WILD);

		EasyMock.expect(deck.drawCard()).andReturn(WILD);
		deck.discardCard(WILD);

		EasyMock.expect(deck.drawCard()).andReturn(INFANTRY);
		deck.discardCard(INFANTRY);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);
		gui.disposeCardFrame();

		deck.discardCard(WILD);
		deck.discardCard(INFANTRY);
		deck.discardCard(WILD);

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);
	}

	@Test
	public void playerHasFullHandMustRedeemCards() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cardsOne = new ArrayList<>();

		for (int i = 0; i < 3; i++)
			cardsOne.add(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(INFANTRY);
		deck.discardCard(INFANTRY);

		EasyMock.expect(deck.drawCard()).andReturn(INFANTRY);
		deck.discardCard(INFANTRY);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(false);
		EasyMock.expect(gui.askToRedeemCards()).andReturn(false);

		gui.demandToRedeemCards();

		EasyMock.expect(gui.showCardsToChoose()).andReturn(cardsOne);
		gui.disposeCardFrame();

		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);

	}

	@Test
	public void playerRedeemCardsVoluntary() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cards = new ArrayList<>();

		for (int i = 0; i < 3; i++)
			cards.add(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);

		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);

		gui.disposeCardFrame();

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);

	}

	@Test 
	public void playerRedeemCardsStandAloneFirstTry(){
		ArrayList<String> cards = new ArrayList<>();
		
		cards.add(CAVALRY);
		cards.add(CAVALRY);
		cards.add(CAVALRY);
		
		GUI gui = EasyMock.mock(GUI.class);
		Player player = EasyMock.mock(Player.class);
		
		DrawPhase dp = new DrawPhase(player, gui, null);
		
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);
		gui.disposeCardFrame();
		
		player.removeSelectedCardsFromHand(cards, null);
		
		EasyMock.replay(gui, player);
		
		assertTrue(dp.playerRedeemCards());
		
		EasyMock.verify(gui, player);
	}
	
	@Test 
	public void playerRedeemCardsStandAloneSecondTry(){
		ArrayList<String> cardsOne = new ArrayList<>();
		
		cardsOne.add(CAVALRY);
		cardsOne.add(ARTILLERY);
		cardsOne.add(CAVALRY);
		
		ArrayList<String> cardsTwo = new ArrayList<>();
		
		cardsTwo.add(CAVALRY);
		cardsTwo.add(CAVALRY);
		cardsTwo.add(CAVALRY);
		
		GUI gui = EasyMock.mock(GUI.class);
		Player player = EasyMock.mock(Player.class);
		
		DrawPhase dp = new DrawPhase(player, gui, null);
		
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cardsOne);
		gui.disposeCardFrame();
		
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cardsTwo);
		gui.disposeCardFrame();
		
		player.removeSelectedCardsFromHand(cardsTwo, null);
		
		EasyMock.replay(gui, player);
		
		assertTrue(dp.playerRedeemCards());
		
		EasyMock.verify(gui, player);
	}
	
	
	@Test
	public void playerRedeemCardsInvalidSelection() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cardsOne = new ArrayList<>();
		ArrayList<String> cardsTwo = new ArrayList<>();

		cardsOne.add(CAVALRY);
		cardsOne.add(INFANTRY);
		cardsOne.add(CAVALRY);

		cardsTwo.add(CAVALRY);
		cardsTwo.add(CAVALRY);
		cardsTwo.add(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(INFANTRY);
		deck.discardCard(INFANTRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
		deck.discardCard(CAVALRY);

		EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
		EasyMock.expect(gui.showCardsToChoose()).andReturn(cardsOne);
		gui.disposeCardFrame();

		EasyMock.expect(gui.showCardsToChoose()).andReturn(cardsTwo);
		gui.disposeCardFrame();

		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);
		deck.discardCard(CAVALRY);

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		dp.drawCard();
		dp.checkValidHand();

		EasyMock.verify(deck, gui);

	}

	@Test
	public void playerRedeemManySetsOfCards() {
		Deck deck = EasyMock.mock(Deck.class);
		GUI gui = EasyMock.mock(GUI.class);
		Player player = new Player(1);

		ArrayList<String> cards = new ArrayList<>();

		for (int i = 0; i < 3; i++)
			cards.add(CAVALRY);

		for (int i = 0; i < 21; i++) {
			EasyMock.expect(deck.drawCard()).andReturn(CAVALRY);
			deck.discardCard(CAVALRY);
		}

		for (int i = 0; i < 21; i++) {
			deck.discardCard(CAVALRY);
		}

		for (int i = 0; i < 7; i++) {
			EasyMock.expect(gui.askToRedeemCards()).andReturn(true);
			EasyMock.expect(gui.showCardsToChoose()).andReturn(cards);

			gui.disposeCardFrame();
		}

		DrawPhase dp = new DrawPhase(player, gui, deck);

		EasyMock.replay(deck, gui);
		
		ArrayList<Integer> returnValues = new ArrayList<Integer>();
		
		for (int i = 0; i < 21; i++) {
			dp.drawCard();
			int value = dp.checkValidHand();
			
			if((i+1) % 3 == 0){
				returnValues.add(value);
			}
		}
		
		
		assertEquals(Integer.valueOf(4), returnValues.get(0));
		assertEquals(Integer.valueOf(6), returnValues.get(1));
		assertEquals(Integer.valueOf(8), returnValues.get(2));
		assertEquals(Integer.valueOf(10), returnValues.get(3));
		assertEquals(Integer.valueOf(12), returnValues.get(4));
		assertEquals(Integer.valueOf(15), returnValues.get(5));
		assertEquals(Integer.valueOf(20), returnValues.get(6));
		

		EasyMock.verify(deck, gui);
	}

}
