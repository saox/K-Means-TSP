/*
 * This class visualizes the TSP tour. 
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.*;



public class Visualize extends JFrame {
	private Vertex[] cities;   
	private Vertex[] solution;
	private HashMap<Integer,Color> colorMap = new HashMap<Integer,Color>();

	ArrayList<Color> colorArray;
	private DrawCanvas canvas;
	int scaleFactorX=1;
	int scaleFactorY=1;

	//Window parameters, scaling factors
	int sizeX = 1100;
	int sizeY = 700;
	int divFactorX = sizeX;
	int divFactorY = sizeY;


	/** Constructor to set up the GUI components */
	public Visualize(Vertex[] cities, Vertex[] solution) {
		canvas = new DrawCanvas();    // Construct the drawing canvas
		canvas.setPreferredSize(new Dimension(sizeX, sizeY));
		this.cities = cities;
		this.solution = solution;
		this.colorArray = new ArrayList<Color>();


		int maxX=0;
		int maxY =0;
		for(Vertex v:cities){
			if(maxX<v.x)
				maxX = (int)v.x;
			if(maxY<v.y){
				maxY=(int)v.y;
			}
		}

		//Scaling Factors
		if(maxX>sizeX){
			scaleFactorX = (maxX)/(divFactorX)*2;
		}
		if(maxY>sizeY){
			scaleFactorY = (maxY)/(divFactorY)*2;
		}

		for (int i = 0; i < cities.length/5; i++){
			colorArray.add(Color.getHSBColor((float) i / (cities.length/5), 1, 1));
			colorMap.put(i, colorArray.get(i));
		}

		Container cp = getContentPane();
		cp.add(canvas);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);   
		this.pack();              
		this.setTitle("TSP Tour Visualized");  
		this.setVisible(true);    
	}

	private class DrawCanvas extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);     
			setBackground(Color.BLACK); 

			//Paint the cities with the color indicating which class they belong to

			for(Vertex v: cities){
				g.setColor(colorMap.get(v.kClass));
				g.fillOval((int)v.x/scaleFactorX,(int)v.y/scaleFactorY,5,5);
			}

			// Connect the cities

			g.setColor(Color.WHITE);
			for(int i=0;i<solution.length-1;i++){
					g.setColor(colorMap.get(solution[i].kClass));
				g.drawLine((int)solution[i].x/scaleFactorX, (int)solution[i].y/scaleFactorY, (int)solution[i+1].x/scaleFactorX, (int)solution[i+1].y/scaleFactorY);
			}
			for(Vertex centr: KMeans.centroids){
				g.setColor(colorMap.get(centr.kClass));
				g.drawOval((int)centr.x/scaleFactorX, (int)centr.y/scaleFactorY, 25, 25);
			}

			// Printing texts
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
			g.drawString("Cost: ", 10, 20);
			g.drawString(String.valueOf(Main.tourCost(solution)), 60, 20);
		}
	}


}