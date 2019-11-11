package WorkingSpace;

public class DeckAndDiscardEmptyException extends RuntimeException {

	public DeckAndDiscardEmptyException() {
		super("All Cards are currently out", new Throwable());
	}

}
