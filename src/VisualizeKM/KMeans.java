package VisualizeKM;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


/*
 * KMeans Clustering
 * 
 * This is the main class of this TSP solver.
 * 
 * The idea is:
 * 1) Group nearby cities into clusters.
 * 2) Find a good path within the clusters, thus minimizing the problem into a smaller one
 * 3) Determine a sequence of visiting clusters
 * 4) Connect the closest neighbouring cities of two clusters in the sequence
 */

public class KMeans {

	int k;
	int iterations = 10;
	HashMap<Integer,ArrayList<Vertex>> clusters = new HashMap<Integer,ArrayList<Vertex>>();
	HashMap<Integer,ArrayList<Vertex>> clustSolutions = new HashMap<Integer,ArrayList<Vertex>>();

	ArrayList<Portal> portal = new ArrayList<Portal>();
	ArrayList<Vertex> centroids;
	
	
	Greedy gs = new Greedy();
//	Swap2 s2 = new Swap2();
	
	public KMeans(int k, int iterations){
		this.iterations = iterations;
		this.k = k;
		centroids = new ArrayList<Vertex>(k);
	}
	

	public void visualInit(){
		//Initialize centroids
		for(int i=0;i<k;i++){
			//rnd.nextInt(Main.cities.length-1) gave error
			Vertex a = new Vertex(VisualKMeans.cities[i].x,VisualKMeans.cities[i].y,i);
			a.setkClass(i);
			centroids.add(a);
		}
	}	

	public void visualASSIGN(){
		int[] distances  = new int[centroids.size()];

		for(Vertex city:VisualKMeans.cities){
			int index = -1;
			int max = Integer.MAX_VALUE;
			for(int i =0;i<centroids.size();i++){
				distances[i] = Methods.EUC(city, centroids.get(i));
			}
			for(int x = 0;x<distances.length;x++){
				if(distances[x]<max){
					max = distances[x];
					index = x;
				}
			}
			city.setkClass(index);
		}
	}
	
	public Vertex[] visualCentroids(){
		
		
		int[] classCount = new int[k];
		for(int i = 0; i < centroids.size();i++){
			float centroid_X = 0;
			float centroid_Y = 0;

			for(Vertex v: VisualKMeans.cities){
				if(v.getKClass()==i){
					classCount[i]++;
					centroid_X+=v.x;
					centroid_Y+=v.y;

				}
			}
			if(centroid_X ==0 || centroid_Y ==0 || classCount[i]==0){
				//Don't do anything
			}
			else{
				centroid_X = centroid_X/classCount[i] ;
				centroid_Y = centroid_Y/classCount[i];
				centroids.get(i).x = centroid_X;
				centroids.get(i).y = centroid_Y;
			}
		}		
		Vertex[] ans = new Vertex[centroids.size()];
		for(int i=0;i<centroids.size();i++){
			ans[i]=centroids.get(i);
			ans[i].print();
		}
		return ans;
	}
	

}
