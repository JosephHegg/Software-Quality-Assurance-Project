package WorkingSpace;

import java.util.Locale;

public class DebugMain {
	public static void main(String[] args) {
		GUI gui = new GUI();
		GameComponent gc = new GameComponent(gui);
		gc.showGame();
		gc.setupPlayers(3);
		gc.numPlayers = 3;

		for (Country c : gc.gameBoard.countries.values()) {
			gui.changeCountryControl(2, 1, c.countryID);
			c.ownerID = 1;
			c.setArmies(2);
			gc.players.get(0).addCountry(c);
		}

		gui.changeCountryControl(2, 2, 1);
		gui.changeCountryControl(2, 3, 2);

		gc.gameBoard.countries.get(1).ownerID = 2;
		gc.gameBoard.countries.get(1).setArmies(2);

		gc.gameBoard.countries.get(2).ownerID = 3;
		gc.gameBoard.countries.get(2).setArmies(2);

		gc.players.get(1).addCountry(gc.gameBoard.countries.get(1));
		gc.players.get(2).addCountry(gc.gameBoard.countries.get(2));

		gc.startGame();
	}
}
