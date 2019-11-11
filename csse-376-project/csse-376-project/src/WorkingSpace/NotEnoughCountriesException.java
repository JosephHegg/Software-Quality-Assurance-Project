package WorkingSpace;

public class NotEnoughCountriesException extends RuntimeException {
	public NotEnoughCountriesException(String message){
		super(message, new Throwable());
	}
}
