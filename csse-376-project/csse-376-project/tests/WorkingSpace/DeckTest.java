package WorkingSpace;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Test;

import WorkingSpace.Deck;
import WorkingSpace.DeckAndDiscardEmptyException;
import WorkingSpace.GUI;

public class DeckTest {

	final int NUMBER_CARDS_IN_DECK = 44;

	private Set<String> getCardTypes() {
		Set<String> cardTypes = new HashSet<String>();
		cardTypes.add("Infantry");
		cardTypes.add("Artillery");
		cardTypes.add("Cavalry");
		cardTypes.add("Wild");
		return cardTypes;
	}

	@Test
	public void testCardsAreMade() {
		Deck d = new Deck();
		Set<String> cardTypes = getCardTypes();
		String card = d.drawCard(); // Deck should make cards when constructed

		if (!cardTypes.contains(card))
			fail();
	} 

	@Test
	public void testDrawAllCards() {
		Deck d = new Deck();
		Set<String> cardTypes = getCardTypes();
		HashMap<String, Integer> numberOfEachType = new HashMap<String, Integer>();
		for (String type : cardTypes)
			numberOfEachType.put(type, 0);

		for (int i = 0; i < NUMBER_CARDS_IN_DECK; i++) { // draw all cards and
															// count number of
															// each
			String card = d.drawCard();
			if (!cardTypes.contains(card))
				fail();

			numberOfEachType.put(card, numberOfEachType.get(card) + 1);
		}

		Integer correct = 14;
		for (String type : cardTypes) {
			if (type.equals("Wild")) {
				correct = 2;
			} else {
				correct = 14;
			}
			assertEquals(correct, numberOfEachType.get(type));
		}
	}

	@Test(expected = DeckAndDiscardEmptyException.class)
	public void testDrawFromEmptyDeck() {
		Deck d = new Deck();
		for (int i = 0; i < NUMBER_CARDS_IN_DECK; i++)
			d.drawCard();

		d.drawCard(); // Should throw the right type of exception

	}

	@Test
	public void testDiscardCard() {
		Deck d = new Deck();

		for (int i = 0; i < 42; i++) // Discard all cards
			d.discardCard(d.drawCard());

		Set<String> cardTypes = getCardTypes();
		HashMap<String, Integer> numberOfEachType = new HashMap<String, Integer>();
		for (String type : cardTypes)
			numberOfEachType.put(type, 0);

		for (int i = 0; i < 44; i++) { // draw all cards and count number of
										// each
			String card = d.drawCard();
			numberOfEachType.put(card, numberOfEachType.get(card) + 1);
		}

		Integer correct = 14;
		for (String type : cardTypes) {
			if (type.equals("Wild")) {
				correct = 2;
			} else {
				correct = 14;
			}
			assertEquals(correct, numberOfEachType.get(type));

		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testDiscardCardWithInvalidType() {
		Deck d = new Deck();
		d.discardCard("INVALID");
	}

	@Test
	public void testReshuffle() {
		Deck d = new Deck();

		for (int i = 0; i < NUMBER_CARDS_IN_DECK; i++) {
			// Discard all cards
			d.discardCard(d.drawCard());
		}

		d.drawCard();

	}

	@Test
	public void testPickrandomInfantry() {
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(4)).andReturn(0);

		EasyMock.replay(rand);
		Deck d = new Deck();
		d.r = rand;
		String returnVal = d.pickRandomType();

		EasyMock.verify(rand);
		assertEquals("Infantry", returnVal);
	}

	@Test
	public void testPickrandomCavalry() {
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(4)).andReturn(1);

		EasyMock.replay(rand);
		Deck d = new Deck();
		d.r = rand;
		String returnVal = d.pickRandomType();

		EasyMock.verify(rand);
		assertEquals("Cavalry", returnVal);
	}

	@Test
	public void testPickrandomArtillery() {
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(4)).andReturn(2);

		EasyMock.replay(rand);
		Deck d = new Deck();
		d.r = rand;
		String returnVal = d.pickRandomType();

		EasyMock.verify(rand);
		assertEquals("Artillery", returnVal);
	}

	@Test
	public void testPickrandomWild() {
		Random rand = EasyMock.mock(Random.class);
		EasyMock.expect(rand.nextInt(4)).andReturn(3);

		EasyMock.replay(rand);
		Deck d = new Deck();
		d.r = rand;
		String returnVal = d.pickRandomType();

		EasyMock.verify(rand);
		assertEquals("Wild", returnVal);
	}
}
