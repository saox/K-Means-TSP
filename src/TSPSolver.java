/*
 * The central class to which each TSP is delegatad and which choses the strategy which should be used.
 */

public class TSPSolver {

	//Number of iterations
	int iter = 10;
	Vertex[] solution;


	/*
	 * Other strategies removed - only KMeans
	 */
	public Vertex[] solve() {
		int k = Main.N/10;
		if(k==0){
			k=1;
		}
		/*
		 * Initialize KMeans with the parameters: 
		 *  - Number of Clusters
		 *  - Number of Iterations
		 */
		KMeans km = new KMeans(k,iter);
		solution = km.kMeansAnswer();

		//Improve the initial KMeans solution, yields on average 10% better path
		KOPT s2 = new KOPT();
		solution = s2.linear2OPT(solution);
		solution = s2.iterate2OPTRandom(solution, 1000);
		return solution;
	}


	/*
	 * Diagnostic method to check the cost of KMeans tour by testing different values of
	 * - Number of Clusters
	 * - Iterations
	 * (also known as "Elbow Function" in Machine Learning)
	 */
	public void compareKMeans(){
		for(int i=1;i<30;i++){
			for(int k=2;k<Main.N;k=k+Main.N/k){
				KMeans km = new KMeans(k,iter);
				km.standardProcedure();
				solution = km.kMeansAnswer();
				int ans = Main.tourCost(solution);
				System.out.println("Iterations: " + i);
				System.out.println("K: " + k);
				System.out.println("Cost: " + ans);
			}
		}
	}



}
