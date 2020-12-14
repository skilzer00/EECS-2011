/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 1: Polygon Hierarchy
 * Author: Andy Mirzaian
 **********************************************************/
package A1sol;

/**
 * The class ConvexPolygon extends SimplePolygon.
 * 
 * @author Andy Mirzaian
 */
public class ConvexPolygon extends SimplePolygon {
	
	
	/********* protected constructors ******************/

	/**
	 * constructor used in the static factory method getNewPoly()
	 * 
	 * KEEP CONSTRUCTORS PROTECTED. Constructors are not inherited. If we do not explicitly
	 * define a constructor, then Java will add the default public zero-parameter version of
	 * the constructor.
	 * 
	 * As we said, we intentionally want to prevent the public to have any direct access to
	 * (any overloaded version of) the constructor. We want to force them to use our public
	 * static factory method getNewPoly().
	 * 
	 * Since we did not yet define the ConvexPolygon constructor in the template that I gave
	 * you, you see a public constructor in the Javadoc API. It was Java who put it there. To
	 * prevent the constructor becoming public, in your completed code you have to explicitly
	 * define the constructor and make it protected.
	 * 
	 * @param size
	 *            number of edges (also vertices) of the polygon
	 */
	protected ConvexPolygon(int size) {
		super(size);
	}

	/** default no-parameter constructor */
	protected ConvexPolygon() {
		this(0);
	}

	/**
	 * shallow-copy parameter polygon p as a new instance of unverified convex polygon.
	 * 
	 * This protected constructor is intended to be called from the public static
	 * ConvexPolygon.getNewPoly() factory method.
	 * 
	 * @param p
	 *            an unverified simple polygon
	 */
	protected ConvexPolygon(SimplePolygon p) {
		n = p.n;
		vertices = p.vertices;
	}

	/********* new non-inherited methods ***************/

	/**
	 * static factory method constructs and returns an unverified "convex" polygon.
	 * 
	 * Notice the return type of this method is different in the two classes: It is
	 * SimplePolygon in one but ConvexPolygon in the other. So, in the ConvexPolygon class you
	 * cannot directly use the version from its parent class which returns a SimplePolygon.
	 * You have to replace it by a new instance of ConvexPolygon.
	 * 
	 * @return an unverified convex-polygon instance
	 */
	 public static ConvexPolygon getNewPoly() {
		return new ConvexPolygon(SimplePolygon.getNewPoly());
	}


	/**
	 * @Pre-Condition: the instance polygon is assumed to be simple.
	 * @Post-Condition: returns true if that instance is convex.
	 * 
	 *                  Postcondition is not guaranteed if pre-condition is violated. For
	 *                  instance, it will return true on the non-simple (hence, non-convex)
	 *                  pentagon [(-1,-1), (1,0), (-1,0), (1,-1), (0,1)].
	 * 
	 * @return true if the instance simple polygon is convex. Runs in O(n) time.
	 */
	public boolean isConvex() {
		/*
		 * IMPORTENT NOTE: DO NOT use the sign of delta(v_0, v_1, v_2) to fix the turn type to
		 * which other turns are compared. It could be No TURN type if that delta is 0. This can
		 * happen if the first two edges are aligned. Also, by definition, any triangle is simple
		 * and convex, even if it has zero area!
		 */
		boolean leftTurnNotFound = true;
		boolean rightTurnNotFound = true;
		for (int i = 0; i < n; i++) {
			double d = delta(vertices[i], vertices[(i+1)%n], vertices[(i+2)%n]);
			if (d > 0)
				leftTurnNotFound = false;
			if (d < 0)
				rightTurnNotFound = false;
		}
		return leftTurnNotFound || rightTurnNotFound;
	}
}
