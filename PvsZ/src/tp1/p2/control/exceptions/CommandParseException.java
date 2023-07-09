package tp1.p2.control.exceptions;

import tp1.p2.view.Messages;

public class CommandParseException extends GameException {

	public CommandParseException(String message) {
		super(message);
	}

	public CommandParseException(Throwable cause) {
		super(cause);
	}

	public CommandParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommandParseException(String message,String data1,String data2,Throwable cause) {
		super(message.formatted(data1,data2),cause);
	}

	

}