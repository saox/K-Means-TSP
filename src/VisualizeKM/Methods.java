package VisualizeKM;

/*
 * Common method(s) used throughout the algorithm
 */

public class Methods {

	public static int EUC(Vertex a, Vertex b){
		float xDiff = a.x-b.x;
		double  xSqr  = Math.pow(xDiff, 2);

		double yDiff = a.y-b.y;
		double ySqr = Math.pow(yDiff, 2);

		int output   = (int)Math.sqrt(xSqr + ySqr);

		return output;  
	}

}
