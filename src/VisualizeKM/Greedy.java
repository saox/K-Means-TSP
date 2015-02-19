package VisualizeKM;
import java.util.ArrayList;
import java.util.Random;

/*
 * Simple Greedy algorithm Heuristic. The performance of the K-Means TSP is dependent on a "fast" clustering method.
 * Changing the Greedy to something else would yield slower running time, but better results.
 */

public class Greedy {

	Vertex[] cities;
	boolean[] used;
	Vertex[] tour;

	public Greedy(Vertex[] cities){
		this.cities = cities;
		used = new boolean[cities.length];
		tour = new Vertex[cities.length];
	}
	
	public Greedy() {
	}

	public Vertex[] randomTour(){
		int idx = new Random().nextInt(cities.length);
		tour[0] = cities[idx];
		used[idx] = true;
		int curIdx = idx;
		Vertex curCity = cities[curIdx];
		Vertex next = null;
		int distance = Integer.MAX_VALUE;
		int newDistance = Integer.MAX_VALUE;

		for(int i =1;i < cities.length;i++){
			distance = Integer.MAX_VALUE;
			for(int j = 0;j < cities.length;j++){
				if(!used[j]){
					newDistance = Methods.EUC(curCity, cities[j]);
					if(newDistance < distance){
						distance = newDistance;
						next = cities[j];
					}
				}
			}
			tour[i] = next;
			used[next.id] = true;
			curCity = next;
		}
		return tour;

	}


	public ArrayList<Vertex> tourAL(ArrayList<Vertex> cts){
		int curIdx = 0;
		
		used = new boolean[cts.size()];
		ArrayList<Vertex> tourAL = new ArrayList<Vertex>(cts.size());
		tourAL.add(cts.get(curIdx));
		used[curIdx] = true;
		Vertex curCity = cts.get(curIdx);
		Vertex next = null;
		int distance = Integer.MAX_VALUE;
		int newDistance = Integer.MAX_VALUE;

		for(int i =1;i < cts.size();i++){
			distance = Integer.MAX_VALUE;
			int jIndex = 0;
			for(int j = 0;j < cts.size();j++){
				if(!used[j]){
					newDistance = Methods.EUC(curCity, cts.get(j));
					if(newDistance < distance){
						distance = newDistance;
						next = cts.get(j);
						jIndex = j;
					}
				}

			}
			//				System.out.println("HERE");
			//				next.print();
			tourAL.add(next);
			used[jIndex] = true;
			curCity = next;
		}
		return tourAL;
	}




}
