/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 1: Polygon Hierarchy
 * Author: Andy Mirzaian
 **********************************************************/
package A1sol;

import static java.lang.Math.abs; // ====== Note the static imports ======
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.awt.geom.Point2D;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * The class SimplePolygon implements the Polygon interface.
 * 
 * It is intended to be further extended by ConvexPolygon.
 * 
 * @author Andy Mirzaian
 */
public class SimplePolygon implements Polygon {


	/********* protected fields ************************/

	protected int n; // number of vertices of the polygon
	protected Point2D.Double[] vertices; // vertex sequence around the polygon boundary

	/********* protected constructors ******************/

	/**
	 * constructor used in the static factory method getNewPoly()
	 * 
	 * Further explanation: This constructor (and overloaded versions) is not public in order
	 * to enforce controlled access via getNewPoly(). It is not private to allow access from
	 * the constructor of its outer subclass ConvexPolygon. Alternative solutions are: (1)
	 * make it package protected, (2) make it even private, but define the subclass
	 * ConvexPolygon as a nested-class so that the latter has access to even private members
	 * of its outer-class. We do not recommend option (2), since it's preferable to have
	 * ConvexPoygon as a stand alone class by itself, without having to refer to it via its
	 * outer-class.
	 * 
	 * @param size
	 *            number of edges (also vertices) of the polygon
	 */
	protected SimplePolygon(int size) {
		n = size;
		vertices = new Point2D.Double[n]; // n null vertices
	}

	/** default no-parameter constructor */
	protected SimplePolygon() {
		this(0);
	}

	/********* public getters & toString ***************/

	/**
	 * static factory method constructs and returns an unverified "simple" polygon,
	 * initialized according to user provided input data in O(n) time.
	 * 
	 * This polygon should later be verified by the isSimple() method to see whether it is
	 * indeed simple or not.
	 * 
	 * Input format should be "n x_0 y_0 x_1 y_1 ... x_(n-1) y_(n-1)", where x_i y_i are the
	 * xy-coordinates of the i-th vertex.
	 * 
	 * @return an unverified simple-polygon instance
	 */
	public static SimplePolygon getNewPoly() {
		/*
		 * I wanted you to know about the factory method, which is a useful design pattern, e.g.,
		 * when designing singleton or immutable types.
		 */
		Scanner in = new Scanner(System.in);
		PrintStream out = System.out;
		out.println("Input the number of vertices, followed by xy-coordinates of vertices:");
		int size = in.nextInt();
		SimplePolygon p = new SimplePolygon(size);
		for (int i = 0; i < size; i++) {
			// initialize the i-th vertex with its input xy-coordinates:
			p.vertices[i] = new Point2D.Double(in.nextDouble(), in.nextDouble());
		}
		in.close(); // possible memory leak if not closed
		return p;
	}

	/**
	 * 
	 * @return n, the number of edges (equivalently, vertices) of the polygon.
	 */
	public int getSize() {
		return n;
	}


	/**
	 * 
	 * @param i
	 *            index of the vertex.
	 * @return the i-th vertex of the polygon.
	 * @throws IndexOutOfBoundsException
	 *             if {@code i < 0 || i >= n }.
	 */
	public Point2D.Double getVertex(int i) throws IndexOutOfBoundsException {
		try {
			return vertices[i];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("n=" + n + ". getVertex(" + i + ")? No such vertex.");
		}
	}
	
	/**
	 * This method returns a string representation of the polygon in the form:
	 * "[ ( x_0, y_0 ), ( x_1, y_1 ), ........ , ( x_(n-1), y_(n-1) ) ]", where
	 * ( x_i, y_i ) is the xy-coordinates of the i-th vertex.
	 * 
	 * @return a String representation of the polygon in O(n) time.
	 */
	@Override
	public String toString() {
		/*
		 * IMPORTANT NOTE: To ensure O(n) time, we can use StringBuilder with repeated appends. If
		 * we use String with repeated concatenations, it will take O(n^2) time, and it will
		 * wastefully create O(n) distinct intermediate strings of total length O(n^2). This is
		 * because String is immutable. But the mutable StringBuilder is internally implemented as
		 * a dynamically expandable array, representing a character sequence. See the Java API. We
		 * prefer StringBuilder over the older version called StringBuffer.
		 */
		StringBuilder s = new StringBuilder("[ ");
		for (Point2D.Double v : vertices) // Note: this is a for-each loop
			s.append("(" + v.x + ", " + v.y + "), ");
		int j = s.lastIndexOf(","); // replace the last ", " with " ]"
		s.deleteCharAt(j);
		s.append("]");
		return s.toString(); // String representation of character sequence s
	}

	/************** utilities *********************/

	
	/**
	 * @param a
	 * @param b two input points.
	 * @return length of the line-segment (a,b) in O(1) time.
	 */
	public static double segmentLength(Point2D.Double a, Point2D.Double b) {
		return sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
	
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 *            three input points.
	 * @return twice the signed area of the oriented triangle (a,b,c). Runs in O(1) time.
	 */
	public static double delta(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
		return (b.x - a.x) * c.y + (c.x - b.x) * a.y + (a.x - c.x) * b.y;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 *            end points of line-segment (a,b).
	 * @param c
	 * @param d
	 *            end points of line-segment (c,d).
	 * @return true if closed line-segments (a,b) and (c,d) are disjoint. Runs in O(1) time.
	 */
	public static boolean disjointSegments(Point2D.Double a, Point2D.Double b,
			Point2D.Double c, Point2D.Double d) {
		/*
		 * My intension was for you to figure out the logic with the possible help of the delta
		 * function. If you can't figure out how to do this, you may use intersectsLine method of
		 * Line2D from the Java API only as a last resort to get partial marks.
		 * 
		 * Line segments (a,b) and (c,d) are disjoint iff one of the cases 1, 2, 3, or 4 as shown
		 * in the assignment holds. Case 1, 2, or 3 holds iff one of the line segments is
		 * completely on one side of the line extension defined by the other segment. That is,
		 * either both a and b are strictly on the same side of line (c,d), or both c and d are
		 * strictly on the same side of line (a,b). Case 4 holds if the two segments are strictly
		 * separable by a vertical or a horizontal line.
		 */
		boolean cases123 = delta(a, b, c) * delta(a, b, d) > 0
				|| delta(c, d, a) * delta(c, d, b) > 0;
		boolean case4 = max(a.x, b.x) < min(c.x, d.x) || max(c.x, d.x) < min(a.x, b.x)
				|| max(a.y, b.y) < min(c.y, d.y) || max(c.y, d.y) < min(a.y, b.y);
		return cases123 || case4;
	}

	/**
	 * @param i
	 * @param j
	 *            indices of two edges of the polygon.
	 * @return true if the i-th and j-th edges of the polygon are disjoint. Runs in O(1) time.
	 * @throws IndexOutOfBoundsException
	 *             if i or j are outside the index range [0..n-1].
	 */
	public boolean disjointEdges(int i, int j) throws IndexOutOfBoundsException {
		try {
			return disjointSegments(vertices[i], vertices[(i + 1) % n],
					vertices[j], vertices[(j + 1) % n]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("n=" + n + ". disjointEdges("
					+ i + ", " + j + ")? Invalid parameters.");
		}
	}

	/**
	 * Reports, in String format, an intersecting pair of cyclically non-consecutive edges if
	 * there is any, or "None found." otherwise. Runs in O(n^2) time.
	 * 
	 * @return "Found (i,j)" if some intersecting pair of cyclically non-consecutive edges e_i
	 *         & e_j is found, or "None found." otherwise.
	 */
	public String reportAnIntersection() {
		for (int i = 0; i < n - 2; i++) {
			for (int j = i + 2; j < n + min(0, i - 1); j++) { // Note the case i=0
				// Now e_i and e_j are a pair of cyclically non-consecutive edges of the polygon
				if (!disjointEdges(i, j))
					return "Found (" + i + " , " + j + ").";
			}
		}
		return "None found.";
	}

	/**
	 * Verifies whether the claimed "simple-polygon" is indeed simple. Runs in O(n^2) time.
	 * 
	 * Further explanation: There are more efficient algorithms to test polygon simplicity,
	 * but they require advanced techniques and are outside the scope of this introductory
	 * course.
	 * 
	 * @return true if the polygon is simple.
	 */
	public boolean isSimple() {
		return reportAnIntersection() == "None found.";
	}

	/************ perimeter & area ***************/

	/**
	 * 
	 * @return the sum of the edge lengths of the polygon. Runs in O(n) time.
	 */
	public double perimeter() {
		double polyPerimeter = 0;
		for (int i = 0; i < n; i++)
			polyPerimeter += segmentLength(vertices[i], vertices[(i + 1) % n]);
		return polyPerimeter;
	}

	/**
	 * Area of verified simple polygon. Runs in O(n) time.
	 * 
	 * @Pre-Condition: The instance is assumed to be an already verified simple polygon.
	 * @Post-Condition: Returns correct area of the interior of the simple polygon.
	 *                  Postcondition is not guaranteed if precondition is not satisfied.
	 * 
	 * @return area of the interior of the verified simple polygon.
	 * 
	 */
	public double areaOfVerifiedSimplePoly() {
		double deltaSum = 0;
		for (int i = 0; i < n; i++)
			// use (n+i-1)%n instead of (i-1)%n so that case i=0 works too.
			deltaSum += vertices[i].x * (vertices[(i + 1) % n].y - vertices[(n + i - 1) % n].y);
		return 0.5 * abs(deltaSum); // Note: abs of sum, not sum of abs
	}

	/**
	 * Area of unverified polygon. Runs in O(n^2) time.
	 * 
	 * See the Forum topic on whether to use a try-catch approach or not.
	 * 
	 * @return area of the polygon interior.
	 * 
	 * @throws NonSimplePolygonException
	 *             if the polygon is non-simple.
	 */
	public double area() throws NonSimplePolygonException {
		if (!isSimple()) // this line takes O(n^2) time
			throw new NonSimplePolygonException("Area of non-simple polygon is ill-defined.");
		return areaOfVerifiedSimplePoly(); // this line takes O(n) time
	}

}
