package VisualizeKM;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


import javax.swing.*;

/*
 * This class draws the objects and sets proportions by which they should be displayed.
 */

public class drawKMeans extends JFrame {
	   private Vertex[] cities;   
	   private Vertex[] centr;
	   private HashMap<Integer,Color> colorMap = new HashMap<Integer,Color>();
	   
	   ArrayList<Color> colorArray;
	   //Scaling factors responsible for proportionality
	   private DrawCanvas canvas;
	   int scaleFactorX=1;
	   int scaleFactorY=1;
	   
	   int sizeX = 1100;
	   int sizeY = 700;
	   int divFactorX = sizeX;
	   int divFactorY = sizeY;
	   
	   
	   public drawKMeans(Vertex[] cities, Vertex[] centr) {
	      canvas = new DrawCanvas();
	      canvas.setPreferredSize(new Dimension(sizeX, sizeY));
	      this.cities = cities;
	      this.centr=centr;
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
	      if(maxX/divFactorX !=0)
	    	  scaleFactorX = maxX/divFactorX;
	      if(maxY/divFactorY!=0)
	    	  scaleFactorY = maxY/divFactorY;
	      
	      System.out.println(scaleFactorX + " " + scaleFactorY);
	      System.out.println(maxX + " " +maxY);
	      //Colors
	      
	      for (int i = 0; i < cities.length/4; i++){
	          colorArray.add(Color.getHSBColor((float) i / (cities.length/4), 1, 1));
	          colorMap.put(i, colorArray.get(i));
	      }
	      
	      Container cp = getContentPane();
	      cp.add(canvas);
	 
	      this.setDefaultCloseOperation(EXIT_ON_CLOSE);   
	      this.pack();              
	      this.setTitle("......");  
	      this.setVisible(true);    
	   }

	   
	   
	   /*
	    * Draw the cities as small points and the centroids as bigger dots
	    */
	   
	   
	   public class DrawCanvas extends JPanel {
	      @Override
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);     
	         setBackground(Color.BLACK);  
	         
//	 		for(Vertex v:cities){
//	 			v.print();
//	 		}
	 		
	         for(Vertex v: cities){
	        	 g.setColor(colorMap.get(v.kClass));
	        	 g.fillOval((int)v.x/scaleFactorX,(int)v.y/scaleFactorY,5,5);
	         }

	         for(Vertex v: centr){
	        	 g.setColor(colorMap.get(v.kClass));
	        	 g.fillOval((int)v.x/scaleFactorX,(int)v.y/scaleFactorY,25,25);
	         }	         
	         
	         g.setColor(Color.WHITE);

	         
	      }
	   }
	 

	}

