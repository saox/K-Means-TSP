/*
 * Author: Konrad Ilczuk
 * 
 * Date: December 2014
 * Project: Advanced Algoritmhs course at KTH (Royal Institute Of Technology)
 * 
 * Description: The project was about solving the Traveling Salesman Problem for inputs
 * ranging between 10 and 1000 cities. The most common approach taken by students was to
 * use the 2-opt algorithm that checks if a swap of two edges yields a better result(removing a crossing).
 * 
 * My approach was that instead of *correcting* crossings I want to create as few as possible costly ones by grouping the cities.
 * I implemented a modified version of the K-Means algorithm that clusters nearby cities, groups them, then groups the groups and
 * corrects the sequence of visiting after which it connects all groups by opening "portals" from the closest city of G1 to the closest of G2.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.swing.SwingUtilities;


public class Main {


	/*
	 * Variables:
	 * N - number of cities(# input)
	 * cities - input as objects with parameters: id, x, y and to which cluster they belong.
	 * solution - the answer
	 *  file - the input file, can be changed to read from console
	 *  
	 */
	public static int N;
	public static Vertex[] cities;
	public static Vertex[] solution;
	static File file = new File("medium3.txt");
	public static boolean verd = true;

	public static void main(String[] args){
		try {
			fastReadIN();
		} catch (IOException e) {
			e.printStackTrace();
		};
		
		// Solve the TSP
		
		TSPSolver solver = new TSPSolver();
		solution = solver.solve();
		//Diagnostics
		//			tourCost(solution);
		answer(solution);
		
		
		//Visualize the tour
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Visualize(cities,solution);
			}
		});
	}

	/*
	 * Fast method to read in. It uses BufferedReader which is faster than Scanner
	 * The input is :
	 * N - number of cities
	 * x,y - coordinates
	 */
	public static void fastReadIN() throws IOException{
		//	BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader bi = new BufferedReader(new FileReader(file));

		float maxX = 0;
		float maxY = 0;

		N = Integer.parseInt(bi.readLine());
		cities = new Vertex[N];
		float x,y;
		String line = null;
		for(int i=0;i<N;i++){
			line = bi.readLine();
			String[] str = new String[2];
			str = line.split("\\s");
			x = Float.parseFloat(str[0]);
			y = Float.parseFloat(str[1]);
			if(x>maxX){
				maxX=x;
			}
			if(y>maxY){
				maxY=y;
			}
			cities[i] = new Vertex(x,y,i);
		}

	}


	/*
	 * Below are methods that are used throughout the project
	 * 
	 * EUC - Eucledian Distance to determine the distance between two cities
	 * tourCost - Evaluate the cost of a tour
	 * answer - prints the final answer
	 */

	public static int EUC(Vertex a, Vertex b){
		float xDiff = a.x-b.x;
		double  xSqr  = Math.pow(xDiff, 2);

		double yDiff = a.y-b.y;
		double ySqr = Math.pow(yDiff, 2);

		int output   = (int)Math.sqrt(xSqr + ySqr);

		return output;  
	}

	public static int tourCost(Vertex[] tour){
		int cost =0;
		for(int i =0;i<tour.length-1;i++){
			cost += Main.EUC(tour[i], tour[i+1]);
		}

		cost+=Main.EUC(tour[tour.length-1], tour[0]);

		return cost;
	}

	public static int tourCost(ArrayList<Vertex> tour){
		int cost =0;
		for(int i =0;i<tour.size()-1;i++){
			cost += Main.EUC(tour.get(i), tour.get(i+1));
		}

		cost+=Main.EUC(tour.get(tour.size()-1), tour.get(0));

		return cost;
	}

	public static void answer(Vertex[] tour){
		for(Vertex a: tour){
			System.out.println(a.id);
		}
		//		System.out.println("COST: " + tourCost(tour));
	}

	public static void answer(ArrayList<Vertex> tour){
		for(Vertex a: tour){
			System.out.println(a.id);
		}
		//		System.out.println("COST: " + tourCost(tour));
	}


}
