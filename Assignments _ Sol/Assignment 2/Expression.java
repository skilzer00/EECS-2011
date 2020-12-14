/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 2:  Problem 3
 * Author: Andy Mirzaian
 **********************************************************/
package A2sol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Converts and outputs an input RPIE to its equivalent FPIE and UPPE. Outputs
 * "Input is not a valid RPIE." if input is an invalid RPIE.
 * 
 * @author andy
 */
public class Expression {

	/**
	 * Converts an input RPIE to its equivalent FPIE. Input must be a string of operands, +,
	 * -, *, /, and right parenthesis ')'. No syntactical checking is done on operands.
	 * Pre-Condition: input is a valid RPIE. Post-Condition: output is an equivalent FPIE of
	 * the input. Running Time: O(n), where n is the length of the input expression.
	 * 
	 * @return an equivalent FPIE if input is a valid RPIE.
	 * @throws IllegalArgumentException
	 *             if it finds the input is an invalid RPIE.
	 */
	public static String RPIEtoFPIE(String exp) throws IllegalArgumentException {

		/* first, place exp into inStk, starting with a dummy '$' operator */
		Stack<Character> inStk = new Stack<>();
		inStk.push('$'); // dummy operator to flush out the remainder of exp
		for (char inputChar : exp.toCharArray())
			inStk.push(inputChar);

		/* now iteratively pop a character c from inStk and process it */
		char c;
		boolean operandLastProcessed = false; // last processed was an operand
		Stack<Character> processedStk = new Stack<>(); // processed part here
		Stack<Character> symbolStk = new Stack<>(); // unmatched ops & ')'s
		while (!inStk.isEmpty()) {
			c = inStk.pop(); // ======= now start processing c =======
			switch (c) {
			case ' ':
				break;
			case ')':
				if (operandLastProcessed) throw new IllegalArgumentException();
				symbolStk.push(c);
				break;
			case '+':
			case '-':
			case '*':
			case '/':
			case '$':
				if (!operandLastProcessed) throw new IllegalArgumentException();
				while (!symbolStk.isEmpty() && symbolStk.peek() != ')') {
					// top of symbolStk is an operator too
					symbolStk.pop(); // match the top operator
					if (symbolStk.pop() != ')')  // match with next ')'
						throw new IllegalArgumentException();
					processedStk.push('('); // add a matching '('
				}   // end inner loop
				symbolStk.push(c);
				operandLastProcessed = false;
				break;
			default:           // any other character is treated as part of an operand
				operandLastProcessed = true;
			}
			processedStk.push(c);
			// ========= end of processing c =============
		} // end outer loop

		symbolStk.pop();            // remove the dummy '$'
		processedStk.pop();         // remove the dummy '$'
		if (!symbolStk.isEmpty())   // unmatched operator or ')' exist
			throw new IllegalArgumentException();

		/* now prepare the output string */
		StringBuilder outEXP = new StringBuilder("");
		while (!processedStk.isEmpty()) outEXP.append(processedStk.pop());
		return outEXP.toString();
	} // =================== end of RPIEtoFPIE method =========================

	/**
	 * Converts a RPIE to its equivalent UPPE. Pre-Condition: input is a valid RPIE.
	 * Post-Condition: output is an equivalent UPPE of the input. Running Time: O(n), where n
	 * is the length of the input expression.
	 * 
	 * @return an equivalent UPPE of the input RPIE.
	 */
	public static String RPIEtoUPPE(String exp) {
		Stack<Character> opStk = new Stack<>();
		StringBuilder outEXP = new StringBuilder("");
		for (char c : exp.toCharArray()) {
			switch (c) {
			case ' ':
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				outEXP.append(' ');
				opStk.push(c);
				break;
			case ')':
				outEXP.append(" " + opStk.pop());
				break;
			default:           // any other character is treated as part of an operand
				outEXP.append(c);
			}
		}
		return outEXP.toString();
	} // ===================== end of RPIEtoUPPE method =========================

	/**
	 * main method does some testing by reading a RPIE from the standard input and prints it
	 * and its equivalent FPIE and UPPE. Running Time: O(n), where n is the length of the
	 * input expression.
	 * 
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.flush();

		System.out.println("Enter a valid RPIE: ");
		try {
			String expRPIE = stdin.readLine();
			System.out.println("Input  RPIE: \t " + expRPIE);
			String expFPIE = RPIEtoFPIE(expRPIE);
			System.out.println("Output FPIE: \t " + expFPIE);
			String expUPPE = RPIEtoUPPE(expRPIE);
			System.out.println("Output UPPE: \t " + expUPPE);
		} catch (Exception e) {
			System.out.println("Input is not a valid RPIE.");
		}
	}
}
