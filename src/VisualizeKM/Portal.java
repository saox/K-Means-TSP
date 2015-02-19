package VisualizeKM;

/*
 * Association of two vertices.
 * This is used when determining the connection of two clusters, hence the name "Portal"
 */

public class Portal {

	public Vertex id1;
	public Vertex id2;
	
	public Portal(Vertex id1, Vertex id2){
		this.id1 = id1;
		this.id2 = id2;
	}
}
