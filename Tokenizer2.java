import java.util.ArrayList;
import java.util.List;

/**
* @author  Izzy Wrubel
* @version 1.0
* @since   03-23-2016
*/

/**
*Takes a valid user input and converts the input to a token. 
*It stores the valid input in an ArrayList of all tokens.
*If the input is not valid, the InvalidToken class will be called and the input will be cleared and
*Not be added to the ArrayList. 
*
*The following criteria is checked for in Tokenizer2:
*	digit: 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
*	letter: 'a'-'z' OR 'A'-'Z'
*	quotes: "
*	operators (one each):
*	+, -, *, /, %
*	[, ], (, ), comma
*	integer: digit (digit)*
*	double: (integer).(digit)*
*	string: (quotes)(any symbol)*(quotes)
*	identifier: letter(letter|digit)*
*/

public class Tokenizer2  {
	private ArrayList<Token<?>> allTokens; //ArrayList of all the valid tokens

	/**
	 * Constructor that initializes a token ArrayList
	 */
	
	public Tokenizer2(){
		allTokens = new ArrayList<Token<?>>(); 
	}

	/**
	*Given a user input, str, this method will run through the string and classify it according to the if statements.
	*The input, according to load(), will either be an operator of some kind, a number, a string, or an identifier.
	*createNumber, createString, and createIdentifier will then tokenize them in their specific methods.
	* @param str String str is the user input that will be used for tokenization.
	*/	
	
	public void load(String str) throws InvalidToken {
		for (int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(c >= '0' && c <='9'){
				try{
					i = createNumber(str, i);
				}
				catch (InvalidToken e){
					allTokens.clear();
					throw e;
				}

			}
			else if(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z'){
				try{
					i = createIdentifier(str, i);
				}
				catch (InvalidToken e){
					allTokens.clear();
					throw e;
				}
			}
			else if (c == '"'){
				try {
					i = createString(str, i);
				}
				catch(InvalidToken e) { 
					allTokens.clear();
					throw e;

				}
			}
			else if (c == '+'){ //addition operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.ADDOP;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == '-'){//subtraction operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.SUBOP;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == '*'){//multiplication operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.MULTOP;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == '('){//left parenthesis operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.LPAREN;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == ')'){//right parenthesis operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.RPAREN;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == '/'){ //division operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.DIVOP;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == '%'){ //mod operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.MODOP;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == '['){ //left bracket operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.LBRACKET;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == ']'){ // right bracket operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.RBRACKET;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == ','){ //comma operator
				Token<String> t = new Token<String>();
				t.type = Token.TOKEN_TYPE.COP;
				t.data = null;
				allTokens.add(t);
			}
			else if (c == ' '){
			}
			else
				throw new InvalidToken("Invalid Token: Unrecognizable value");
			}
		}

	private int createNumber(String str, int start) throws InvalidToken{		
		boolean isDouble = false;
		int endingindex = start;
		String f = "";

		for (int x = start; x < str.length(); x++){
			char s = str.charAt(x);
			if (s == '.'){
				if(isDouble){
					throw new InvalidToken("Invalid Double: May not have more than one decimal");
				}
				isDouble = true;
			}

			if (s == ' '){
				f = str.substring(start, x);
				endingindex = x;
				break;
			}

			if(isDouble == false && (s < '0' || s > '9')){
				InvalidToken t = new InvalidToken("Invalid Token: Not an integer");
				throw t;
			}

			if(isDouble == true && ((s < '0' || s > '9') && s != '.')){
				InvalidToken t = new InvalidToken("Invalid Token: Not a double");
				throw t;
			}

			if (x == str.length()-1){
				f = str.substring(start, x+1);
				endingindex = x;
				break;
			}
		}

		if (isDouble == true){
			double num = Double.parseDouble(f);
			Token<Double> t = new Token<Double>();
			t.type = Token.TOKEN_TYPE.DOUBLE;
			t.data = num;
			allTokens.add(t);
		}

		else{
			try{
				Integer num = Integer.parseInt(f);
				Token<Integer> t = new Token<Integer>();
				t.type = Token.TOKEN_TYPE.INTEGER;
				t.data = num;
				allTokens.add(t);
			}
			catch (NumberFormatException e){
				InvalidToken t = new InvalidToken("Invalid Token: Number is too large");
				throw t;
			}
		}
		return endingindex;
	}

	private int createString(String str, int start) throws InvalidToken{
		int endingindex = start;
		String f = "";
		boolean endString = false;

		for (int i = start + 1; i < str.length(); i++){
			Character s = str.charAt(i);
			if ((s.compareTo('"') == 0)){
				f = str.substring(start+1, i);
				endingindex = i+1;
				endString = true;
				break;
			}

			if (!endString && i == str.length()-1){
				InvalidToken t = new InvalidToken("Invalid Token: Unclosed string");
				throw t;
			}	
		}

		Token<String> t = new Token<String>();
		t.type = Token.TOKEN_TYPE.STRING;
		t.data = f;
		allTokens.add(t);
		return endingindex;
	}

	private int createIdentifier(String str, int start) throws InvalidToken{
		int endingindex = start;
		String f = "";
		for (int i = start; i < str.length(); i++){
			char s = str.charAt(i);
			char end = str.charAt(str.length()-1);

			if((s < '0' || s > '9' && s < 'A' || s > 'Z' && s < 'a' || s > 'z') && s != ' '){
				InvalidToken t = new InvalidToken("Invalid Token: Identifier may only contain digits and letters");
				throw t;
			}

			if (s == ' '){ 
				f = str.substring(start, i);
				endingindex = i;
				break;
			}
			
			if (i == str.length()-1){
				f = str.substring(start, i+1);
				endingindex = i;
				break;
			}
			
			if (end == '"'){
				InvalidToken t = new InvalidToken("Invalid Token: Unclosed string");
				throw t;
			}
		}
		
		Token<String> t = new Token<String>();
		t.type = Token.TOKEN_TYPE.IDENTIFIER;
		t.data = f;
		allTokens.add(t);
		return endingindex;
	}

	/**
	 * Return the oldest token in the AllTokens ArrayList
	 * @return the oldest token
	 */
	
	public Token<?> nextToken(){
		Token<?> rtn = allTokens.get(allTokens.size()-1);
		return rtn;
	}
	
	/**
	 * @return all tokens in the ArrayList
	 */
	
	public ArrayList<Token<?>> allTokens(){
		return allTokens;
	}
}











