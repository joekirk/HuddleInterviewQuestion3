package collections;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class Dijkstra {

	//returns the weight of an edge given a vertex start and target.  If there is no such edge 0 is returned.
	public static double linkWeight (Vertex start, Vertex target)
	{
		for (Edge e : start.adjacencies)
	 	{
            if (e.target == target){
            	return e.weight;
            }
	 	}
		
		return 0;
	}
	
	
	//given a list a vertices, returns the length of the route.  If no such route exists, positive infinity is returned along with the statement "NO SUCH ROUTE"
	public static double routelength (List<Vertex> route)
	{
		double routelength = 0;
		for (int i = 0; i < route.size()-1; i++){
			 if (linkWeight (route.get(i), route.get(i+1)) > 0){
				routelength = routelength + linkWeight (route.get(i), route.get(i+1));
	         }
			 else {
				 System.out.println("NO SUCH ROUTE");
				 return Double.POSITIVE_INFINITY;
			 }
		}
		return routelength;
	}
	
	//re-initialise all vertices to positive infinity
	public static void initialiseVertices (Vertex[] allvertices)
	{
		for (int i = 0; i < allvertices.length; i++){
			allvertices[i].minDistance = Double.POSITIVE_INFINITY;
		}
	}
	
	//computes the shortest paths from the source to all vertices by implementing the Dijkstra algorithm
	public static void computePaths(Vertex source)
    {	
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }
	
	//returns the paths determined by the Dijkstra algorithm implemented in compute paths
    public static List<Vertex> getShortestPathTo(Vertex source, Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != source.previous; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
    
    //Given a number junctions and a flag to indicate whether it has to be exactly equal to that many junctions,
    //returns the number of routes between 2 vertices and the corresponding routes
    //the function generates partial lists of routes up to the maximum amount of junctions and disregards any which don't meet the final conditions
    public static int numberOfPathsViaJunctions (Vertex source, Vertex target, int maxjunctionsvisited, boolean equalsflag) {
    	
    	
    	List <List<Vertex>> potentialroutes = new ArrayList <List<Vertex>>();
    	List <Vertex> initialroute = new ArrayList <Vertex>();
    	List <List<Vertex>> finalroute = new ArrayList <List<Vertex>>();
    	
    	int numberofpaths = 0;
    	
    	initialroute.add(source);
    	potentialroutes.add(initialroute);
    	
    	for (int i = 0; i < maxjunctionsvisited; i++){
    		int x = potentialroutes.size();
    		for (int j = 0; j < x; j++){
    			
    			List<Vertex> partialroutes = new ArrayList<Vertex> (potentialroutes.get(j));
    			Vertex vertex = partialroutes.get(i);
    			
    			for (Edge e : vertex.adjacencies)
                {	
    				List<Vertex> partial2routes = new ArrayList<Vertex> (potentialroutes.get(j));
    				partial2routes.add(e.target);
    				
    				if (e.target == target && equalsflag && partial2routes.size()  ==  maxjunctionsvisited +1) {
    					finalroute.add(partial2routes);
    					numberofpaths++;	
    				}
    			    else if(e.target == target && equalsflag == false) { 
    			    	finalroute.add(partial2routes);
    					numberofpaths++;
    			    }
    			  
    				potentialroutes.add(partial2routes);
    			
                }
    			
    		
    		}
    		potentialroutes = potentialroutes.subList(x, potentialroutes.size());
    	}		
    		
    	System.out.println("Paths: " + finalroute);
    	System.out.println("Number of routes: " + numberofpaths);
    	return numberofpaths;
    	
    		
    	}
    //given a start and end point, generates the number of paths and their corresponding routes subject to a distance constraint
    public static int numberOfPathsMaxDistance (Vertex source, Vertex target, int maxdistance) {
    	
    	
    	List <List<Vertex>> potentialroutes = new ArrayList <List<Vertex>>();
    	List <Vertex> initialroute = new ArrayList <Vertex>();
    	List <List<Vertex>> finalroute = new ArrayList <List<Vertex>>();
    	
    	int numberofpaths = 0;
    	
    	initialroute.add(source);
    	potentialroutes.add(initialroute);
    	
    	while (!potentialroutes.isEmpty()){
    		int x = potentialroutes.size();
    		for (int j = 0; j < x; j++){
    			
    			List<Vertex> partialroutes = new ArrayList<Vertex> (potentialroutes.get(j));
    			Vertex vertex = partialroutes.get(partialroutes.size()-1);
    			
    			for (Edge e : vertex.adjacencies)
                {	
    				List<Vertex> partial2routes = new ArrayList<Vertex> (potentialroutes.get(j));
    				partial2routes.add(e.target);
    				
    				if (e.target == target && routelength (partial2routes) < maxdistance) {
    					finalroute.add(partial2routes);
    					numberofpaths++;	
    				}
    			    
    			    if (routelength (partial2routes) < maxdistance){
    				potentialroutes.add(partial2routes);
    			    }
    			
                }
    			
    		
    		}
    		potentialroutes = potentialroutes.subList(x, potentialroutes.size());
    	}		
    		
    	System.out.println("Paths: " + finalroute);
    	System.out.println("Number of routes: " + numberofpaths);
    	return numberofpaths;
    	
    		
    	}
    
  

    public static void main(String[] args)
    {
    	
    	
    	//initialise the 5 vertices
    	
    	Vertex v0 = new Vertex("A");
    	Vertex v1 = new Vertex("B");
    	Vertex v2 = new Vertex("C");
    	Vertex v3 = new Vertex("D");
    	Vertex v4 = new Vertex("E");

    	v0.adjacencies = new Edge[]{ new Edge(v1, 5),
    								 new Edge(v3, 5),
    								 new Edge(v4, 7)};
    	v1.adjacencies = new Edge[]{ new Edge(v2, 4)};
    	v2.adjacencies = new Edge[]{ new Edge(v3, 7),
                               		 new Edge(v4, 2)};
    	v3.adjacencies = new Edge[]{ new Edge(v2, 8),
    								 new Edge(v4, 6)};
    	v4.adjacencies = new Edge[]{ new Edge(v1, 3)};
    	
    	Vertex[] vertexarray = {v0,v1,v2,v3,v4};
    
		
    		//Test case 1. Distance for route A-B-C. Expected output 9
    		Vertex[] r1 = {v0,v1,v2};
    		ArrayList<Vertex> test1 = new ArrayList<Vertex>(Arrays.asList(r1));
    		System.out.println("Path: " + test1);
    		System.out.println("Route length: " + routelength(test1));
    		
    		//Test case 2. Distance for route A-D. Expected output 5
    		Vertex[] r2 = {v0,v3};
    		ArrayList<Vertex> test2 = new ArrayList<Vertex>(Arrays.asList(r2));
    		System.out.println("Path: " + test2);
    		System.out.println("Route length: " + routelength(test2));
    		
    		//Test case 3. Distance for route A-D-C. Expected output 13
    		Vertex[] r3 = {v0,v3,v2};
    		ArrayList<Vertex> test3 = new ArrayList<Vertex>(Arrays.asList(r3));
    		System.out.println("Path: " + test3);
    		System.out.println("Route length: " + routelength(test3));
    		
    		//Test case 4. Distance for route A-E-B-C-D. Expected output 21
    		Vertex[] r4 = {v0,v4,v1,v2,v3};
    		ArrayList<Vertex> test4 = new ArrayList<Vertex>(Arrays.asList(r4));
    		System.out.println("Path: " + test4);
    		System.out.println("Route length: " + routelength(test4));
    		
    		//Test case 5. Distance for route A-E-D. Expected output NO SUCH ROUTE
    		Vertex[] r5 = {v0,v4,v3};
    		ArrayList<Vertex> test5 = new ArrayList<Vertex>(Arrays.asList(r5));
    		System.out.println("Path: " + test5);
    		System.out.println("Route length: " + routelength(test5));
    		
    		//Test case 6.  Routes starting at C and ending at C with a maximum of 3
			//junctions.  Expected output 2, C-D-C (2junctions) and C-E-B-C (3 junctions)
			numberOfPathsViaJunctions(v2,v2,3,false);
			
			//Test case 7.The number of routes starting at A and ending at C with exactly 4 junctions.
			//In the sample data below, there are three such routes: A to C (via B,C,D); A
			//to C (via D,C,D); and A to C (via D,E,B). Expected output 3
			numberOfPathsViaJunctions(v0,v2,4, true);
			
			//Test case 8. The length of the shortest route (in terms of distance to travel) from A
			//to C. Expected output 9
			initialiseVertices(vertexarray);
			computePaths(v0);
			System.out.println("Distance to " + v2 + ": " + v2.minDistance);
			List<Vertex> path = getShortestPathTo(v0, v2);
			System.out.println("Path: " + path);
			
			//Test case 9. The length of the shortest route (in terms of distance to travel) from B
			//to B. Expected output 9
			initialiseVertices(vertexarray);
			computePaths(v1);
			System.out.println("Distance to " + v1 + ": " + v1.minDistance);
			List<Vertex> path1 = getShortestPathTo(v1,v1);
			System.out.println("Path: " + path1); 
			//This test case currently returns 0 rather than the desired output of 9.. This is a consequence of how 
			// I have implemented the Dijkstra algorithm above.   While it isn't necessarily incorrect it is not what is desired.
			
			//Test case 10. The number of different routes from C to C with a distance of less than
			//30. In the test data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC,
			//CEBCEBC, CEBCEBCEBC, CDEBCEBC, CEBCDEBC. Expected output 9
			numberOfPathsMaxDistance(v2,v2,30);	
    	}
    
    
}
