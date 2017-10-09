import java.io.FileNotFoundException;
import java.util.Scanner;

/**
* @author  Izzy Wrubel
* @version 1.0
* @since   03-23-2016
*/

public class Driver {

	
	/**
	 * @param args
	 * @throws InvalidToken if there is any invalid token thrown in Tokenizer2. This will override printStackTrace.
	 */
	
	public static void main(String args[]) throws InvalidToken{
		Tokenizer2 t = new Tokenizer2();
		Scanner sc = new Scanner(System.in);
		String line = "";
		System.out.println("Enter your text here:");
		while(sc.hasNextLine()){
			
			line = sc.nextLine();
			try {
				t.load(line);
			}
			
			catch(InvalidToken e ){
				System.out.println( e.getMessage());
			}
			
			for (int i = 0; i < t.allTokens().size(); i++){
				System.out.print(t.allTokens().get(i));
			}
			System.out.println();
			
			if (!sc.hasNext() || (sc == null)){
				System.exit(1);
				
			}
			
			t.allTokens().clear();
			
		}
		
		sc.close();
		System.out.println("Program Terminated");
	}
}
