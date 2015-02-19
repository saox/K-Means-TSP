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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class KMeans {

	int k;
	int iterations;
	
	//Contains a ClusterID associated with a list of cities that belong to that cluster
	HashMap<Integer,ArrayList<Vertex>> clusters = new HashMap<Integer,ArrayList<Vertex>>();
	//Contains the ClusterID associated with a tour within that cluster
	HashMap<Integer,ArrayList<Vertex>> clustSolutions = new HashMap<Integer,ArrayList<Vertex>>();
	//A Portal is the edge that connects two clusters
	ArrayList<Portal> portal = new ArrayList<Portal>(k);
	ArrayList<Vertex> clusterSequence = new ArrayList<Vertex>(k);
	//For visualization
	public static ArrayList<Vertex> centroids;
	
	Greedy gs = new Greedy();
	KOPT s2 = new KOPT();
	
	public KMeans(int k, int iterations){
		//Set-up
		this.iterations = iterations;
		this.k = k;
		centroids = new ArrayList<Vertex>(k);
		for(int i =0;i<k;i++){
			clusters.put(i, new ArrayList<Vertex>(100));
			clustSolutions.put(i, new ArrayList<Vertex>(100));
		}
		//Do the normal K-Means
		standardProcedure();
		
		//Find a solution within each cluster
		solveEachCluster();
		//Improve solution
		centroids = gs.tourAL(centroids);
		clusterSequence =  s2.linear2OPT(centroids);
		clusterSequence =  s2.iterate2OPTRandom(centroids,1000);
		connectClasses();
	}

	public void standardProcedure(){
		//Initialize centroids
		for(int i=0;i<k;i++){
			Vertex a = new Vertex(Main.cities[i].x,Main.cities[i].y,i);
			a.setkClass(i);
			centroids.add(a);
		}
		//Assign cities to centroids while recomputing new centroids iteratively 
		for(int i = 0;i<iterations;i++){
			assign();
			newCentroids();
		}
	}
	
	//Calculate new centroids by averaging point within each class.
	public void newCentroids(){
		int[] classCount = new int[k];
		for(int i = 0; i < centroids.size();i++){
			float centroid_X = 0;
			float centroid_Y = 0;

			for(Vertex v: Main.cities){
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
	}

	//Assign cities to the new centroids
	public void assign(){
		int[] distances  = new int[centroids.size()];

		for(Vertex city:Main.cities){
			int index = -1;
			int max = Integer.MAX_VALUE;
			for(int i =0;i<centroids.size();i++){
				distances[i] = Main.EUC(city, centroids.get(i));
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

	
	//Solves the minor TSP-problem within each cluster
	public void solveEachCluster(){
		for(Vertex v: Main.cities){
			this.clusters.get(v.kClass).add(v);
		}
		
		//Get a class, solve that class.
		for(int i=0;i<k;i++){
			ArrayList<Vertex> listie = clusters.get(i);
			if(listie.size()>0){
				listie = gs.tourAL(listie);
				clustSolutions.put(i,s2.iterate2OPTRandom(listie, listie.size()/2));
			}
		}
	}

	//Connects clusters together to generate the final solution
	public void connectClasses(){
		//Finds the pair of cities(vertices) of two closest centroids that are closest to each other
		//and hence make a good candidate for a portal
		for(int i =0;i<clusterSequence.size();i++){
			int distance = Integer.MAX_VALUE;
			Vertex candidate1 = null;
			Vertex candidate2 = null;
			for(int j=i;j<clusterSequence.size();j++){
				Vertex centr1  = centroids.get(i);
				Vertex centr2  = centroids.get(j);
				int newDistance = Main.EUC(centr1, centr2);
				if(newDistance<distance && centr1.kClass!=centr2.kClass){
					distance = newDistance;
					candidate1 = centr1;
					candidate2 = centr2;
				}

			}
			if(candidate1!=null){
				//Closest candidates are found by now: candidate1 and candidate2
				Vertex finalC1= null;
				Vertex finalC2 = null;
				for(Vertex c1: clustSolutions.get(candidate1.id)){
					int distance2 = Integer.MAX_VALUE;
					for(Vertex c2: clustSolutions.get(candidate2.id)){
						int distance3 = Main.EUC(c1, c2);
						if(distance3 < distance2){
							distance2 = distance3;
							finalC1 = c1;
							finalC2 = c2;
						}
					}
				}
				//Diagnostic:
				//System.out.println("Connecting: " + finalC1.id + " " + finalC2.id);
				portal.add(new Portal(finalC1, finalC2));
			}
		}




	}


//Merge(append) the sequences of each cluster to form a final answer to the TSP 
	public Vertex[] kMeansAnswer(){
		int currentClass;
		int nextClass;
		int index =0;
		Vertex[] FinalSolution = new Vertex[Main.cities.length];
		ArrayList<Vertex> aCluster = null;
		ArrayList<Vertex> bCluster = null;
		for(int i=0;i<clusterSequence.size()-1;i++){
			currentClass = clusterSequence.get(i).kClass;
			nextClass = clusterSequence.get(i+1).kClass;
			aCluster = clustSolutions.get(clusterSequence.get(i).kClass);
			bCluster = clustSolutions.get(clusterSequence.get(i+1).kClass);
			Portal tempPortal = null;
			for(Portal p:portal){
				if(p.id1.kClass==currentClass && p.id2.kClass==nextClass){
					//Connect
					tempPortal =p;
					for(int j=0;j<aCluster.size()-1;j++){
						if(aCluster.get(j).id==p.id1.id){
							Collections.swap(aCluster, j, aCluster.size()-1);
						}
					}
					for(int w=1;w<bCluster.size();w++){
						if(bCluster.get(w).id==p.id2.id){
							Collections.swap(bCluster, w, 0);
						}					
					}					
				}
			}
			for(Vertex v: aCluster){
				FinalSolution[index]=v;
				index++;
			}
		}
		for(Vertex v: bCluster){
			FinalSolution[index]=v;
			index++;
		}
		return FinalSolution;
	}

}
