package WorkingSpace;

import static org.junit.Assert.*;

import javax.naming.directory.InvalidAttributesException;

import org.junit.Test;

import WorkingSpace.Country;
import WorkingSpace.DeckAndDiscardEmptyException;
import WorkingSpace.Player;

public class PlayerTests {

	@Test
	public void buildPlayer() {
		Player playerOne = new Player(1);
		assertEquals(playerOne.getPlayerID(), 1); // Basic check to see if
													// player is actually
													// created.
	}
  
	@Test
	public void armyInteractionsWithPlayer() {
		Player playerOne = new Player(1);

		playerOne.giveArmies(3);
		assertEquals(playerOne.getArmyCount(), 3);

		playerOne.removeArmies(3);
		assertEquals(playerOne.getArmyCount(), 0);

		playerOne.removeArmies(1);
		assertEquals(playerOne.getArmyCount(), 0); // BVA Check for when army
													// count goes below 0.

		playerOne.removeArmies(0);
		assertEquals(playerOne.getArmyCount(), 0); // BVA Check for an entry of
													// negative army count.

	}
	
	@Test 
	public void testRemoveNegativeArmies()
	{
		Player playerOne = new Player(1);

		playerOne.giveArmies(3);
		assertEquals(playerOne.getArmyCount(), 3);

		try {
			playerOne.removeArmies(-1);
		} catch (IllegalArgumentException e) {
		}
		assertEquals(playerOne.getArmyCount(), 3);
	}

	@Test
	public void testRemoveMoreArmiesThanPresent() {
		Player playerOne = new Player(1);

		playerOne.giveArmies(3);
		assertEquals(playerOne.getArmyCount(), 3);

		playerOne.removeArmies(4);
		assertEquals(playerOne.getArmyCount(), 0);

		playerOne.giveArmies(3);
		assertEquals(playerOne.getArmyCount(), 3);

		playerOne.removeArmies(3);
		assertEquals(playerOne.getArmyCount(), 0);

		playerOne.giveArmies(3);
		assertEquals(playerOne.getArmyCount(), 3);

		playerOne.removeArmies(2);
		assertEquals(playerOne.getArmyCount(), 1);
	}

	
	
	@Test
	public void playerCardSmallTest(){

		Player playerOne = new Player(1);

		playerOne.initializeHandMap();
		
		playerOne.giveCard("Infantry");
		playerOne.giveCard("Wild");
		
		assertFalse(playerOne.checkIfPossessingValidHand());
		
		
	}

}
