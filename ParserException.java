/**
 * @author Isabel Wrubel
 */

public class ParserException extends Exception {
	
	/**
	 * 
	 * @param message Message that contains the reason for ParserException to be thrown
	 * Calls a super method that will throw a ParserException; overthrows PrintStackTrace
	 */
	public ParserException(String message){
		super(message);
	}
}
