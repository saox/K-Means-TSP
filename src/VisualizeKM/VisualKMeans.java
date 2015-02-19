package VisualizeKM;
import java.util.Scanner;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

/*
 * 
 * Author: Konrad Ilczuk
 * 
 * Date: December 2014
 * 
 * Project: Advanced Algoritmhs course at KTH (Royal Institute Of Technology)
 * 
 * This class was used to visualize the performance of K-Means Clustering.
 * It was used to give a visual representation of the clusters.
 * 
 * 
 */

public class VisualKMeans {

	public static int N;
	public static Vertex[] cities;
	public static Vertex[] solution;

	
	public static void main(String[] args){
		int iterations=10;
		/*
		 * Read in the data,
		 * Define the number of iterations
		 * Run the Visualization
		 */
		readIn();
		
		KMeans km = new KMeans(N/4,iterations);
		km.visualInit();
		
		
		for(int i =0;i<iterations;i++){
			km.visualASSIGN();
			Vertex[] centr = km.visualCentroids();
			km.visualASSIGN();
			
			//Draws first and last iteration of the clustering
			if(i==0 || i==iterations-1){
				drawKMeans dkm = new drawKMeans(cities,centr);
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void readIn(){
		Scanner keyboard = new Scanner(System.in);

		float maxX = 0;
		float maxY = 0;

		N = keyboard.nextInt();
		cities = new Vertex[N];
		float x,y;

		for(int i=0;i<N;i++){
			x = keyboard.nextFloat();
			y = keyboard.nextFloat();
			if(x>maxX){
				maxX=x;
			}
			if(y>maxY){
				maxY=y;
			}
			cities[i] = new Vertex(x,y,i);
		}
	}






}
