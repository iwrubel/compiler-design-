
/**
* @author  Izzy Wrubel
* @version 1.0
* @since   03-23-2016
*/

public class InvalidToken extends Exception{
	
	/**
	 * @param message Message that contains what is incorrect in the input that caused a throw exception.
	 */
	
	public InvalidToken(String message){
		super(message);
	}
}
