package WorkingSpace;

public class IllegalNumberOfArmiesToAttackException extends Exception {

	public IllegalNumberOfArmiesToAttackException(){
		super("Cannot attack with invalid number of armies", new Throwable());
	}
}
