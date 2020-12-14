/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 1: Polygon Hierarchy
 * Author: Andy Mirzaian 
 **********************************************************/
package A1sol;

import java.io.PrintStream;

/**
 * PolygonTester tests various features of the polygon hierarchy and provides an input-output of the test case. The
 * results are logged in the accompanying TestIO.txt file.
 * 
 * @author Andy Mirzaian
 */
public class PolygonTester {

	public static void testPoly(SimplePolygon p) {
		PrintStream out = System.out;

		// -------- Test toString: -----------------------------------
		out.println("The polygon being tested is: \n " + p.toString());

		// -------- Test getSize & getVertex: ------------------------
		try {
			out.println("vertex[0] is " + p.getVertex(0) + ".");
			out.println(p.getVertex(p.getSize()));
		} catch (IndexOutOfBoundsException e) {
			out.println(e);
		}

		// -------- Test disjointEdges: ------------------------------
		try {
			out.println("disjontEdges(0,2) is " + p.disjointEdges(0, 2) + ".");
			out.println(p.disjointEdges(p.getSize(), -1));
		} catch (IndexOutOfBoundsException e) {
			out.println(e);
		}

		// -------- Test reportAnIntersection: -----------------------
		out.println("Any intersecting pair of non-adjacent edges found? "
				+ p.reportAnIntersection());

		// -------- Test simplicity, and if necessary, convexity: ----
		if (p.isSimple()) {
			out.println("This polygon is SIMPLE.");
			if (p instanceof ConvexPolygon) {
				if (((ConvexPolygon) p).isConvex()) // explicit cast
					out.println("This polygon is CONVEX.");
				else
					out.println("This polygon is NOT CONVEX.");
			}	
		}
		else
			out.println("This polygon is NON-SIMPLE.");

		// --------- Test perimeter: --------------------------------
		out.printf("Perimeter = %.2f . \n", p.perimeter());

		// --------- Test area: -------------------------------------
		try {
			out.printf("Area = %.2f . \n", p.area());
		} catch (NonSimplePolygonException e) {
			out.println(e);
		}
	} // ------------ end testPoly --------------------------------

	public static void main(String[] args) {
		// args[0] is "simple" or "convex"
		System.out.println("Testing the input as a " + args[0] + " polygon.");
		if (args[0].equals("simple"))
			testPoly(SimplePolygon.getNewPoly());
		else
			testPoly(ConvexPolygon.getNewPoly());
	
	}
}
