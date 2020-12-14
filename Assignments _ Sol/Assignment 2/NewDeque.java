/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 2:  Problem 2
 * Author: Andy Mirzaian
 **********************************************************/
package A2sol;

import java.util.Stack;

/**
 * Deque of generic element type E, implemented by two stacks as the only
 * instance variables.
 * 
 * @author andy
 */
public class NewDeque<E> {
	
	/* ------------- instance variables ----------------- */
	private Stack<E> frontStack;
	private Stack<E> backStack;
	
	/** constructor instantiates an empty deque in O(1) time. */
	public NewDeque() {
		frontStack = new Stack<E>(); // empty stack
		backStack = new Stack<E>(); // empty stack
	}
	
	/**
	 * utility method: move elements from stack S1 to S2 in O(n) time.
	 * 
	 * @param <E>
	 *            generic element type.
	 * @param S1
	 *            source Stack.
	 * @param S2
	 *            destination Stack.
	 */
	private static <E> void transfer(Stack<E> S1, Stack<E> S2) {
		while (! S1.empty()) S2.push(S1.pop());
	}
	
	/* --------------- access methods ------------------ */

	/**
	 * 
	 * @return number of elements in the deque in O(1) time.
	 */
	public int size() {
		return frontStack.size() + backStack.size();
	}

	/**
	 * 
	 * @return true if deque is empty in O(1) time.
	 */
	public boolean isEmpty(){
		return size() == 0;
	}

	/**
	 * Running Time: O(n).
	 * 
	 * @return first element of the deque if deque is non-empty, null otherwise.
	 */
	public E first() {
		if (isEmpty()) return null;
		if (frontStack.empty()) transfer(backStack, frontStack);
		return frontStack.peek();
	}

	/**
	 * Running Time: O(n).
	 * 
	 * @return last element of the deque if deque is non-empty, null otherwise.
	 */
	public E last(){
		if (isEmpty()) return null;
		if (backStack.empty()) transfer(frontStack, backStack);
		return backStack.peek();
	}
	
	/* -------------- update methods ------------------ */

	/**
	 * Adds element e to the front of deque in O(1) time.
	 * 
	 * @param e
	 *            element to be added.
	 */
	public void addFirst(E e) {
		frontStack.push(e);
	}

	/**
	 * Adds element e to the back of deque in O(1) time.
	 * 
	 * @param e
	 *            element to be added.
	 */
	public void addLast(E e){
		backStack.push(e);
	}

	/**
	 * Removes and returns first element from the deque in O(n) time.
	 * 
	 * @return first element, null if deque is empty.
	 */
	public E removeFirst(){
		if (isEmpty()) return null;
		if (frontStack.empty()) transfer(backStack, frontStack);
		return frontStack.pop();
	}

	/**
	 * Removes and returns last element from the deque in O(n) time.
	 * 
	 * @return last element, null if deque is empty.
	 */
	public E removeLast(){
		if (isEmpty()) return null;
		if (backStack.empty()) transfer(frontStack, backStack);
		return backStack.pop();
	}
	
	/** ---------- main method does some testing on NewDeque ------------- */
	public static void main(String[] args) {
		NewDeque<Integer> deque = new NewDeque<>();
		System.out.println("The deque has " + deque.size() + " elements.");
		System.out.println("It is empty: " + deque.isEmpty() + ".");
		for (int i = 0; i < 10; i++) {
			int e = 5 * i;
			deque.addFirst(e);
			System.out.println(e + "\t inserted at the front.");
		}
		System.out.println("The deque has " + deque.size() + " elements.");
		System.out.println("It is empty: " + deque.isEmpty() + ".");
		for (int i = 0; i < 5; i++) {
			Integer e = deque.removeLast();
			System.out.println(e + "\t removed from back.");
		}
		System.out.println("The deque has " + deque.size() + " elements.");
		System.out.println("It is empty: " + deque.isEmpty() + ".");
		for (int i = 0; i < 5; i++) {
			Integer e = deque.removeFirst();
			System.out.println(e + "\t removed from front.");
		}
		System.out.println("The deque has " + deque.size() + " elements.");
		System.out.println("It is empty: " + deque.isEmpty() + ".");
	}

}
