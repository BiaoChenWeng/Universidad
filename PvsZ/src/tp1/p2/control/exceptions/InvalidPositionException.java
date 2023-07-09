package tp1.p2.control.exceptions;

import tp1.p2.view.Messages;

public class InvalidPositionException extends GameException {
	public InvalidPositionException(String col,String row) {
		super(Messages.INVALID_POSITION.formatted(col,row));
	}
	
	public InvalidPositionException(String col,String row,Throwable cause) {
		super(Messages.INVALID_POSITION.formatted(col,row),cause);
	}
	

}