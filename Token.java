
/**
* @author  Izzy Wrubel
* @version 1.0
* @since   03-23-2016
*/


/**
 * @param <T> the type of TOKEN_TYPE
 */
public class Token<T> {
	
	/**
	 * Creates acceptable sub-types of tokens
	 */
	public enum TOKEN_TYPE{
		ADDOP,
		SUBOP,
		MULTOP,
		DIVOP,
		MODOP,
		COP,
		LPAREN,
		RPAREN,
		LBRACKET,
		RBRACKET,
		QUOTES,
		INTEGER,
		DOUBLE,
		STRING,
		IDENTIFIER,
		END_TOKEN
	}
	
public TOKEN_TYPE type; 
public T data;
	

	/**
	 * Creates consistent print given type T data and TOKEN_TYPE type.
	 * @return the converted string that is now in proper token form.
	 */
	public String toString(){
		String converted = "<" + type;
		if(data != null){
			converted += ":" + data.toString();
		}
		
		converted += ">";
		return converted;
		}
}

