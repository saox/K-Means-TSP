/*
 * The 2-opt algorithm
 * It is used to improve an existing solution
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class KOPT {


	Vertex temp = new Vertex(1,1,1);
	public KOPT(){
	}
	
	private void swap(Vertex a, Vertex b){
		temp.setOther(a);
		a.setOther(b);
		b.setOther(temp);
	}
	
	

	public Vertex[] linear2OPT(Vertex[] copy){

		int bestScore = Main.tourCost(copy);
		boolean noChange = false;
//		System.out.println("INITIAL SCORE: " + bestScore);
		int curScore = 0;

		for(int i=0;i<copy.length;i++){
			for(int j=i+1;j<copy.length;j++){
				swap(copy[i], copy[j]);
				curScore = Main.tourCost(copy);
				if(curScore<bestScore){
					bestScore=curScore;
				}
				else{
					//Reverse changes
					swap(copy[i], copy[j]);		
				}
			}
		}
		return copy;
	}
	

	public Vertex[] random2OPT(Vertex[] copy, Random rnd, int rep){
		if(rep>5){
			return copy;
		}
		//Could go wrong
		int i = rnd.nextInt(copy.length);
		int j = rnd.nextInt(copy.length);
		
		int bestScore = Main.tourCost(copy);
		
		while(i==j){
			i = rnd.nextInt(copy.length);
		}
		swap(copy[i],copy[j]);	
		int curScore = Main.tourCost(copy);

		if(curScore<bestScore){
			return copy;
		}
		else{
			swap(copy[j],copy[i]);	
			rep++;
			return random2OPT(copy, rnd, rep);
		}
	}
	
	public Vertex[] iterate2OPTRandom(Vertex[] initial, int times){
		Vertex[] ans = null;
		Random rnd = new Random();
		for(int i=0;i<times;i++){
			ans = random2OPT(initial,rnd,0);
		}
		return ans;
	}
	
	
	public ArrayList<Vertex> linear2OPT(ArrayList<Vertex> copy){

		int bestScore = Main.tourCost(copy);
		boolean noChange = false;
//		System.out.println("INITIAL SCORE: " + bestScore);
		int curScore = 0;

		for(int i=0;i<copy.size();i++){
			for(int j=i+1;j<copy.size();j++){
				Collections.swap(copy, i, j);
				curScore = Main.tourCost(copy);
				if(curScore<bestScore){
					bestScore=curScore;
				}
				else{
					//Reverse changes
					Collections.swap(copy, j, i);	
				}
			}
		}
		return copy;
	}
	
	public ArrayList<Vertex> random2OPT(ArrayList<Vertex> copy , Random rnd, int rep){
		if(rep>5){
			return copy;
		}
		//Could go wrong
		int i = rnd.nextInt(copy.size()-1);
		int j = rnd.nextInt(copy.size()-1);
		
		int bestScore = Main.tourCost(copy);
		
		if(i==j){
			return copy;
		}
		Collections.swap(copy, i, j);
		int curScore = Main.tourCost(copy);

		if(curScore<bestScore){
			return copy;
		}
		else{
			Collections.swap(copy, j, i);
			rep++;
			return random2OPT(copy, rnd, rep);
		}
	}
	
	public ArrayList<Vertex> iterate2OPTRandom(ArrayList<Vertex> initial, int times){
		Random rnd = new Random();
		for(int i=0;i<times;i++){
			initial = random2OPT(initial,rnd,0);
		}
		return initial;
	}	
	
	
	
}
