// SQA Project for Spring Quarter 2019
// Student Contributors: Andrew White | Joseph Hegg | Tyler Bath


package WorkingSpace;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

	public static void main(String[] args) {
		GameComponent gc = new GameComponent(new GUI());
		gc.showGame();
		gc.setupGame();
		gc.startGame();

	}

}


