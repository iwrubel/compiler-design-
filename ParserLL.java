import java.util.ArrayList;
import java.util.List;

/**
 * @author Isabel Wrubel
 * Only one public method, all other methods are private
 * Criteria:
 * expression is defined as --> term | expression + term | expression - term
 * term is defined as --> factor | term * factor | term / factor 
 * factor is defined as --> integer | double | string | ( expression ) 
 * 
 * Top down approach without backtracking
 */
public class ParserLL {
	
	/**
	 * ParserLL uses a LL1 approach in order to evaluate an expression of tokens
	 * Parses through an array list of tokens and respectfully evaluates
	 */

	public ParserLL(){

	}

	/**
	 * Top down approach -- starts with parseExpression
	 * @param tokens
	 * @return end.data.toString() -- the received and evaluated tokens
	 * @throws ParserException
	 */
	public String parse(ArrayList<Token<?>> tokens) throws ParserException
	{
		Token<String> endToken = new Token<String>();
		endToken.type = Token.TOKEN_TYPE.END_TOKEN;
		tokens.add( endToken);

		Token<?> end = parseExpression(tokens);
		return end.data.toString();
	}


	private Token<?> parseFactor(ArrayList<Token<?>> list) throws ParserException
	{		
		Token<?> factor = list.remove(0);
		
		if (factor.type == Token.TOKEN_TYPE.LPAREN) {
			Token<?> val = parseExpression(list);
			Token<?> t = list.get(0);
			if (t.type == Token.TOKEN_TYPE.RPAREN) {
				list.remove(0);
				return val;
			}
			else {
				throw new ParserException("Parse Error: Unequal Parenthesis");
			}
		}
		
		else if ((factor.type != Token.TOKEN_TYPE.INTEGER) && (factor.type != Token.TOKEN_TYPE.STRING) && (factor.type != Token.TOKEN_TYPE.DOUBLE)){
			throw new ParserException("Parser: Not a complete expression");
		}
		
		
		
		return factor;
	}


	private Token<?> parseTerm(ArrayList<Token<?>> list) throws ParserException{

		Token<?> left = parseFactor(list);

		Token<?> op = list.get(0);

		while ((op.type == Token.TOKEN_TYPE.MULTOP) || (op.type == Token.TOKEN_TYPE.DIVOP)){
			op = list.remove(0);
			Token<?> right = parseTerm(list);

			if (left.type == Token.TOKEN_TYPE.INTEGER){
				if (right.type == Token.TOKEN_TYPE.INTEGER){
					if (op.type == Token.TOKEN_TYPE.MULTOP){
						Token<Integer> end = new Token<Integer>();
						end.data = ((Integer)(left.data))*((Integer)(right.data));
						end.type = Token.TOKEN_TYPE.INTEGER;
						left = end;
					}
					else{
						if (right.data.equals(0)){
							throw new ParserException("Parser: Cannot divide by 0.");
						}
						else{
							Token<Integer> end = new Token<Integer>();
							end.data = ((Integer)(left.data))/((Integer)(right.data));
							end.type = Token.TOKEN_TYPE.INTEGER;
							left = end;
						}
					}
					if (right.type == Token.TOKEN_TYPE.DOUBLE){
						if (op.type == Token.TOKEN_TYPE.MULTOP){
							Token<Double> end = new Token<Double>();
							end.data = ((Integer)(left.data))*((Double)(right.data));
							end.type = Token.TOKEN_TYPE.DOUBLE;
							left = end;
						}
						else{
							if (right.data.equals(0.0)){
								throw new ParserException("Parser: Cannot divide by 0.0.");
							}
							else{
								Token<Double> end = new Token<Double>();
								end.data = ((Integer)(left.data))/((Double)(right.data));
								end.type = Token.TOKEN_TYPE.DOUBLE;
								left = end;
							}
						}
					}
					if (right.type == Token.TOKEN_TYPE.STRING){
						if (op.type == Token.TOKEN_TYPE.MULTOP){
							Token<String> end = new Token<String>();
							int count = (int) left.data;
							String concatenate = "";
							for (int i = 0; i < count; i++){
								concatenate += right.data.toString();
							}
							end.data = concatenate;
							end.type = Token.TOKEN_TYPE.STRING;
							left = end;
						}
						else{
							throw new ParserException("Parser: Cannot divide strings");
						}
					}
				}


				else if (left.type == Token.TOKEN_TYPE.DOUBLE){
					if (right.type == Token.TOKEN_TYPE.DOUBLE){
						if (op.type == Token.TOKEN_TYPE.MULTOP){
							Token<Double> end = new Token<Double>();
							end.data = ((Double)(left.data))*((Double)(right.data));
							end.type = Token.TOKEN_TYPE.DOUBLE;
							left = end;
						}
						else{
							Token<Double> end = new Token<Double>();
							end.data = ((Double)(left.data))/((Double)(right.data));
							end.type = Token.TOKEN_TYPE.DOUBLE;
							left = end;
						}
						if (right.type == Token.TOKEN_TYPE.INTEGER){
							if (op.type == Token.TOKEN_TYPE.MULTOP){
								Token<Double> end = new Token<Double>();
								end.data = ((Double)(left.data))*((Integer)(right.data));
								end.type = Token.TOKEN_TYPE.DOUBLE;
								left = end;
							}
							else{
								Token<Double> end = new Token<Double>();
								end.data = ((Double)(left.data))/((Integer)(right.data));
								end.type = Token.TOKEN_TYPE.DOUBLE;
								left = end;
							}
						}
						if (right.type == Token.TOKEN_TYPE.STRING){
							if (op.type == Token.TOKEN_TYPE.MULTOP){
								Token<String> end = new Token<String>();
								int count = (int) right.data;
								String concatenate = "";
								for (int i = 0; i < count; i++){
									concatenate += left.data.toString();

								}
								end.data = concatenate;
								end.type = Token.TOKEN_TYPE.STRING;
								left = end;
							}
							else{
								throw new ParserException("Parser: Cannot divide strings");
							}
						}
					}
				}
				
				else if (left.type == Token.TOKEN_TYPE.STRING){
					if (right.type == Token.TOKEN_TYPE.STRING){
						throw new ParserException("Parser: Cannot divide or multiply strings with strings");
					}	
					if (right.type == Token.TOKEN_TYPE.INTEGER){
						if (op.type == Token.TOKEN_TYPE.MULTOP){
							Token<String> end = new Token<String>();
							int count = (int) right.data;
							String concatenate = "";
							for (int i = 0; i < count; i++){
								concatenate += left.data.toString();

							}
							end.data = concatenate;
							end.type = Token.TOKEN_TYPE.STRING;
							left = end;
						}
						else{
							throw new ParserException("Parser: Cannot divide strings");
						}
					}
					if (right.type == Token.TOKEN_TYPE.DOUBLE){
						throw new ParserException("Parser: Cannot divide strings");
					}
				}
				op = list.get(0);
			}
		}
		return left;
	}



	private Token<?> parseExpression(ArrayList<Token<?>> tokens) throws ParserException{

		Token<?> left = parseTerm(tokens);

		Token<?> op = tokens.get(0);

		while ((op.type == Token.TOKEN_TYPE.SUBOP) || (op.type == Token.TOKEN_TYPE.ADDOP)){
			op = tokens.remove(0);
			Token<?> right = parseExpression(tokens);


			if (left.type == Token.TOKEN_TYPE.INTEGER){
				if (right.type == Token.TOKEN_TYPE.INTEGER){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<Integer> end = new Token<Integer>();
						end.data = ((Integer)(left.data))+((Integer)(right.data));
						end.type = Token.TOKEN_TYPE.INTEGER;
						left = end;
					}
					else{
						Token<Integer> end = new Token<Integer>();
						end.data = ((Integer)(left.data))-((Integer)(right.data));
						end.type = Token.TOKEN_TYPE.INTEGER;
						left = end;
					}
				}
				if (right.type == Token.TOKEN_TYPE.DOUBLE){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<Double> end = new Token<Double>();
						end.data = ((Integer)(left.data))+((Double)(right.data));
						end.type = Token.TOKEN_TYPE.DOUBLE;
						left = end;
					}
					else{
						Token<Double> end = new Token<Double>();
						end.data = ((Integer)(left.data))-((Double)(right.data));
						end.type = Token.TOKEN_TYPE.DOUBLE;
						left = end;
					}
				}
				if (right.type == Token.TOKEN_TYPE.STRING){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<String> end = new Token<String>();
						String combo = "";
						combo = left.data.toString().concat(right.data.toString());
						end.data = combo;
						end.type = Token.TOKEN_TYPE.STRING;
						left = end;
					}

					else{
						throw new ParserException("Parser: Cannot employ subtraction with strings and integers");
					}
				}
			}

			else if (left.type == Token.TOKEN_TYPE.DOUBLE){
				if (right.type == Token.TOKEN_TYPE.INTEGER){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<Double> end = new Token<Double>();
						end.data = ((Double)(left.data))+((Integer)(right.data));
						end.type = Token.TOKEN_TYPE.DOUBLE;
						left = end;
					}

					else{
						Token<Double> end = new Token<Double>();
						end.data = ((Double)(left.data))-((Integer)(right.data));
						end.type = Token.TOKEN_TYPE.DOUBLE;
						left = end;
					}
				}
				if (right.type == Token.TOKEN_TYPE.DOUBLE){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<Double> end = new Token<Double>();
						end.data = ((Double)(left.data))+((Double)(right.data));
						end.type = Token.TOKEN_TYPE.DOUBLE;
						left = end;
					}

					else{
						Token<Double> end = new Token<Double>();
						end.data = ((Double)(left.data))-((Double)(right.data));
						end.type = Token.TOKEN_TYPE.DOUBLE;
						left = end;
					}
				}
				if (right.type == Token.TOKEN_TYPE.STRING){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<String> end = new Token<String>();
						String combo = "";
						combo = (left.data.toString()).concat(right.data.toString());
						end.data = combo;
						end.type = Token.TOKEN_TYPE.STRING;
						left = end;
					}
					else {
						throw new ParserException("Parser: Cannot employ subtraction with strings");
					}
				}
			}
			else if (left.type == Token.TOKEN_TYPE.STRING){
				if (right.type == Token.TOKEN_TYPE.INTEGER){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<String> end = new Token<String>();
						String combo = "";
						combo = left.data.toString().concat(right.data.toString());
						end.data = combo;
						end.type = Token.TOKEN_TYPE.STRING;
						left = end;
					}
					else{
						throw new ParserException("Parser: Cannot employ subtraction with strings and integers");
					}
				}
				if (right.type == Token.TOKEN_TYPE.STRING){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<String> end = new Token<String>();
						String combo = "";
						combo = (left.data.toString()).concat(right.data.toString());
						end.data = combo;
						end.type = Token.TOKEN_TYPE.STRING;
						left = end;
					}
					else{
						throw new ParserException("Parser: Cannot employ subtraction with strings");
					}
				}
				if (right.type == Token.TOKEN_TYPE.DOUBLE){
					if (op.type == Token.TOKEN_TYPE.ADDOP){
						Token<String> end = new Token<String>();
						String combo = "";
						combo = (left.data.toString()).concat(right.data.toString());
						end.data = combo;
						end.type = Token.TOKEN_TYPE.STRING;
						left = end;
					}
					else{
						throw new ParserException("Parser: Cannot employ subtraction with strings");
					}
				}
			}
			op = tokens.get(0);
		}
		return left;
	}
}






