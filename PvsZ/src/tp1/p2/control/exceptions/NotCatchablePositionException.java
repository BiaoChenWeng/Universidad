package tp1.p2.control.exceptions;

import tp1.p2.view.Messages;

public class NotCatchablePositionException extends GameException {

	public NotCatchablePositionException(String col,String row) {
		super(Messages.NO_CATCHABLE_IN_POSITION.formatted(col,row));
	}
	
	public NotCatchablePositionException(String col,String row,Throwable cause) {
		super(Messages.NO_CATCHABLE_IN_POSITION.formatted(col,row),cause);
	}
}