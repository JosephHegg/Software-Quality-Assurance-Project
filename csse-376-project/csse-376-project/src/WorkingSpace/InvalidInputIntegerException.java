package WorkingSpace;

public class InvalidInputIntegerException extends RuntimeException{

	public InvalidInputIntegerException(String message) {
		super(message, new Throwable());
	}
}
