/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 2:  Problem 1
 * Author: Andy Mirzaian
 **********************************************************/
package A2sol;

/**
 * reads the number of lights in the room from the main argument line. Initializes all
 * lights ON. Then prints out the sequence of light switches, along with the state of all
 * lights after each light switch. Continues until all lights are OFF.
 * 
 * @author andy
 */
public class Lights {
	int numL; // number of lights in the room
	int[] L; // L[0] ignored, lights L[1..numL] used

	enum State { ON, OFF };
	
	/**
	 * Constructor initializes n lights ON in the room.
	 * 
	 * @param n
	 *            the number of lights in the room.
	 */
	public Lights(int n) {
		numL = n;
		L = new int[numL + 1];
		for (int i = 1; i <= numL; i++) L[i] = 1;    // ON = 1, OFF = 0.
	}
	
	/**
	 * Prints current state of all lights.
	 */
	public void displayCurrentState() {
		for (int i = 1; i <= numL; i++)
			System.out.print(L[i]);
	}
	
	/**
	 * switch light number i into state s and print new state of all lights.
	 * 
	 * @param i
	 *            the light being switched.
	 * @param s
	 *            the new state of light i.
	 */
	public void switchLight(int i, State s){
		L[i] = (s == State.OFF) ? 0 : 1;
		displayCurrentState();
		System.out.println("\t turn light " + i + " " + s + ".");
	}
	
	/**
	 * Pre-Condition: Lights 1..n are ON.
	 * 
	 * Post-Condition: Lights 1..n are OFF.
	 * 
	 * @param n
	 *            the number of lights to turn OFF.
	 */
	public void turnOff(int n){
		if (n <= 0) return;
		turnOff(n - 2);
		switchLight(n, State.OFF);
		turnOn(n - 2);
		turnOff(n - 1);
	}
	
	/**
	 * Pre-Condition: Lights 1..n are OFF.
	 * 
	 * Post-Condition: Lights 1..n are ON.
	 * 
	 * @param n
	 *            the number of lights to turn ON.
	 */
	public void turnOn(int n) {
		if (n <= 0) return;
		turnOn(n - 1);
		turnOff(n - 2);
		switchLight(n, State.ON);
		turnOn(n - 2);
	}
	
	/** main takes argument n and prints out the result for n lights. */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]); // # of lights given as argument
		Lights roomLights = new Lights(n);
		roomLights.displayCurrentState();
		System.out.println("\t " + n + " lights ON initially.");
		roomLights.turnOff(n);
	}
}
