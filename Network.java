package project4;
//done
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;

import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

//You may use the Java Collections Framework in this part of your
//project, but note that your primary internal structure is an
//ArrayOfListsOfPairs, you may still find some of these useful
import java.util.*;
/**
 * Overall class that implements the network.
 * @author Sifaw Bouylazane
 * @version 11/9/19.
 * */
public class Network implements Graph<Host,Connection>, DirectedGraph<Host,Connection> {
	//We promise the network will never have more than this
	//many nodes... use that knowledge to simplify your code!
	/**
	 * Max number of nodes/network to exists.
	 * */
	private static final int MAX_NETWORK_SIZE = 255;
	
	//Adjacency List: Host 0's outgoing connections will be in list 0
	//and each key-value pair in list 0 is a host-connection pair from
	//host 0, to the host in the pair, using the connection in the pair.
	//You must use this as your internal storage, you may not change
	//the type, name, privacy, or anything else about this variable.
	/**
	 * Inner storage using the ArrayOfList class.
	 * */
	private ArrayOfListsOfPairs<Host,Connection> storage;
	/**
	 * holds all nodes/vertices.
	 * */
	private ArrayList<Host> vertices;
	/**
	 * number of vertices in network.
	 * */
	private int numVe=0;
	//Any other variables you want here! (Must be private.)
	//You may also add private methods if you'd like, but nothing
	//public.
	/**
	 * holds predecessors.
	 * */
	private ArrayList<Host> pred= new ArrayList<Host>();
	/**
	 * holds successors.
	 * */
	private ArrayList<Host> sucessor= new ArrayList<Host>();
	/**
	 * constructor
	 * initializes the max 'nodes'/devices in network.
	 * */
	public Network() {
		storage= new ArrayOfListsOfPairs<Host,Connection>(MAX_NETWORK_SIZE);//HOLD ALL THE CONNECTION PAIRS
		vertices= new ArrayList<Host>(MAX_NETWORK_SIZE);
		for(int i=0;i<255;i++) {//O1
			vertices.add(null);
		}
		//constructor to initialize what you want.
	}
	/**
	 * gets number of vertices.
	 * @return number of vertices.
	 * */
	private int numVer() {
		return numVe;
	}
	/**
	 * Returns a view of all edges(connections i.e values) in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<Connection> getEdges() {
		//so got through the storage i.e the adgcent list and get each pair's value
		ArrayList<Connection> listEdges= new ArrayList<Connection>();
		for(KeyValuePair<Host,Connection> pair: storage.getAllPairs()) {
			listEdges.add(pair.getValue());
		}
		return listEdges;
	}

	/**
	 * Returns a view of all vertices(host i.e keys ) in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<Host> getVertices() {
		//MAX size==Max network; no repeated vertices i.e Key i.e host are unquie
		//since the network treats the vertex as one thing we call the list
		//that holds the vertexs
		ArrayList<Host> host= new ArrayList<Host>();
		for(Host e: vertices) {
			if(e==null) {
				continue;
			}else {
				host.add(e);
			}
		}
		
		return host;
	}

	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(Host vertex) {
		return getVertices().contains(vertex);
	}

	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(Connection edge) {
		return getEdges().contains(edge);
	}

	/**
	 * Returns the number of edges(connections) in this graph.
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		return getEdges().size();
	}

	/**
	 * Returns the number of vertices(hosts) in this graph.
	 * @return the number of vertices in this graph.
	 */
	public int getVertexCount() {
		return getVertices().size();
	}

	/**
	 * Returns a Collection view of the outgoing edges(connection going out from given host i.e vertex) incident to vertex
	 * in this graph.
	 * @param vertex the vertex whose outgoing edges are to be returned.
	 * @return a Collection view of the outgoing edges incident to vertex in this graph.
	 */
	public Collection<Connection> getOutEdges(Host vertex) {
		ArrayList<Connection> outEdg= new ArrayList<Connection>();
		//so you go through storage which holds outgoing edges for each vertex in vertex Array
		//connections are unique due to their ID 
		//go through the storage find vertex index, then go to each node pair and get the value i.e connections

		if(vertices.get(vertex.getId())!=null) {//checking our vertex array making sure it has the desired vertex
			if(storage.getAllPairs(vertex.getId()).size()>=1) {
				for(KeyValuePair<Host, Connection> e: storage.getAllPairs(vertex.getId())) {
					outEdg.add(e.getValue());
					sucessor.add(e.getKey());//adding the host to sucessor array
				} 
			} 
		}
		return outEdg;
	}

	/**
	 * Returns a Collection view of the incoming edges(connections coming in for given host) incident to vertex
	 * in this graph.
	 * @param vertex the vertex whose incoming edges are to be returned.
	 * @return  a Collection view of the incoming edges incident to vertex in this graph.
	 */
	public Collection<Connection> getInEdges(Host vertex) {//FIXME
		//here you would through the storage, each vertex and check the key of each pair
		//go through each subvertix's linked list find where key==vertex and add connection dealing with
		//this subvertix to arraylist.
		//[Vertex->v1->v2,vertex->v2,vertex->v3,vertex]
		//a vertex should not have more than one incoming from each vertex.
		ArrayList<Connection> inEdg= new ArrayList<Connection>();

		int i=0;
		int j=0;
		if(vertices.get(vertex.getId()) !=null) {
			while(i<storage.getNumLists()-1 && j<255) {
				Host mainV= vertices.get(j);//starting host to see if host has edge going to vertex i.e it's incoming edge 
				//If there is no vertex then connections wont exists
				//but a vertex can exist with no connections.
				if(mainV== null) {
					j++;//go to next vertex
					i++;//go to next outgoing edges of vertex j;
				}else {
					if(storage.getAllPairs(i).size()>=1) {//checking if there are any connection pairs
						for(KeyValuePair<Host, Connection> e: storage.getAllPairs(i)) {
							if(e.getKey().equals(vertex)) {
								inEdg.add(e.getValue());
								pred.add(mainV);

							}
						}
					}
					j++;
					i++;
				}



			}
		}
		return inEdg;
	}

	/**
	 * Returns a Collection view of the predecessors of vertex 
	 * in this graph. A predecessor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an outgoing edge of 
	 * v and an incoming edge of vertex.
	 * @param vertex the vertex whose predecessors are to be returned
	 * @return  a Collection view of the predecessors of vertex in this graph
	 */
	public Collection<Host> getPredecessors(Host vertex) {
		//look for vertexes that share a connection with host vertex
		ArrayList<Host> predC= new ArrayList<Host>();
		//look at getInEdge() since this returns list of values of  a vertex the share a connection with vertex.
		pred.clear();//clearing it after past action of getInEdges();
		getInEdges(vertex);//since inedges represents connections coming to vertex from V call this method 
		//which also adds the host vertex to predecessor private list.
		predC=pred;
		return predC;
	}

	/**
	 * Returns a Collection view of the successors of vertex 
	 * in this graph.  A successor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an incoming edge of 
	 * v and an outgoing edge of vertex.
	 * @param vertex the vertex whose predecessors are to be returned
	 * @return  a Collection view of the successors of vertex in this graph.
	 */
	public Collection<Host> getSuccessors(Host vertex) {//look at the getOutEdges(vertex);
		ArrayList<Host> suces= new ArrayList<Host>();
		sucessor.clear();
		getOutEdges(vertex);
		suces=sucessor;
		return suces;
	}

	/**
	 * If directedEdge is a directed edge in this graph, returns the source; 
	 * otherwise returns null. 
	 * The source of a directed edge d is defined to be the vertex for which  
	 * d is an outgoing edge.
	 * directedEdge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge the edge to get the source of
	 * @return  the source of directedEdge if it is a directed edge in this graph, or null otherwise
	 */
	public Host getSource(Connection directedEdge) {//FIXME
		//so connections are unquie they have an ID so find the host that has this connection ID.
		//so u go through each vertex that exits for each it's outgoing edges in storage find when
		//value== directedEdge and return that vertex.
		Host source= null;
		for(Host v: vertices) {//going to each vertex
			if(v!=null) {//checking if this index has a vertex
				//checking each vertex's outgoing edges
				for(KeyValuePair<Host,Connection> e: storage.getAllPairs(v.getId())) {
					if(e.getValue()==directedEdge) {//if this pair has connection matching directedEdge
						source=v;
						break;
					}
				}

			}

		}

		return source;
	}

	/**
	 * If directedEdge is a directed edge in this graph, returns the destination; 
	 * otherwise returns null. 
	 * The destination of a directed edge d is defined to be the vertex incident to d for which  
	 * d is an incoming edge.
	 * directedEdge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge the edge to get the destination of
	 * @return  the destination of directedEdge if it is a directed edge in this graph, or null otherwise
	 */
	public Host getDest(Connection directedEdge) {
		//ok so here your looking for a vertex that has incoming edge of directedEdge
		//so go through storage compare directedEdges of each host, find the host then
		//call the getInEdge() with found key and see if directedEdge is in it.
		Host dest=null;

		for(Host v: vertices) {//going to each vertex
			if(v!=null) {//checking if this index has a vertex
				//checking each vertex's outgoing edges
				for(KeyValuePair<Host,Connection> e: storage.getAllPairs(v.getId())) {
					if(e.getValue()==directedEdge) {//if this pair has connection matching directedEdge
						dest=e.getKey();
						break;
					}
				}

			}

		}

		return dest;
	}

	/**
	 * Returns an edge that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if either of the following is true:
	 * <ul>
	 * <li/>v1 is not connected to v2
	 * <li/>either v1 or v2 are not present in this graph(i.e keys are not present in storage)
	 * </ul> 
	 * for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge e if
	 * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.)
	 * @param v1 node one.
	 * @param v2 node two.
	 * @return  an edge that connects v1 to v2, or null if no such edge/vertex exists
	 * @see Hypergraph#findEdgeSet(Object, Object) 
	 */
	public Connection findEdge(Host v1, Host v2) {
		//so look at the outgoing edges of v1 and look at the incoming edges of v2
		//so go through v1's outgoing edges in storage and find v2 and get 
		Connection edge=null;
		//this if-statm see if these vert exist 
		if(vertices.get(v1.getId())!= null && vertices.get(v2.getId())!=null) {

			//checking the corresponding index to v1 in storage
			if(storage.getAllPairs(v1.getId()).size()>=1) {
				for(KeyValuePair<Host, Connection> pair: storage.getAllPairs(v1.getId())) {
					if(pair.getKey().equals(v2)) {
						//checking if the given connection is v1's outgoing edge and v2's incoming edge
						if(v1== getSource(pair.getValue()) && v2== getDest(pair.getValue())) {
							edge=pair.getValue();
							break;
						}
					}
				}
			}

		}
		return edge;
	}

	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2.
	 * Equivalent to addEdge(e, new Pair Host(v1, v2)).
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of
	 * e will be the default for this graph.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object, EdgeType)
	 */
	public boolean addEdge(Connection e, Host v1, Host v2) {
		boolean edgeSet=false;
		//v2 considers this as an incoming edge and v1 considers this as an outgoing edge
		//so v2 and connection e are a KeyValuePair that need to add to v1's outgoing edges
		//this is assuming we already have vertex (v1 or v2) in the array of vertex/ graph
		KeyValuePair<Host, Connection> pair= new KeyValuePair<Host,Connection>(v2,e);

		if(vertices.get(v1.getId())!=null || vertices.get(v2.getId())!=null) {
			Connection c= findEdge(v1,v2);
			if(c==null) {//checking if an edge is already connecting the vertices

				storage.addAt(pair,v1.getId());//this adds the pair to vertex's outgoing edges list in index Id
				edgeSet=true;

			}
		}


		return edgeSet;
	}

	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * 
	 * @param vertex	the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(Host vertex) {//the 'key' 
		//these in the storage are represent as being ones being the visual nodes in graph
		// we add this vertex based on its ID
		boolean isAdded=false;

		try {
			if(vertices.get(vertex.getId()) !=null || vertex==null) {//its in graph(vert array) or its null
				throw new IllegalArgumentException();
			}else {

				vertices.remove(vertex.getId());
				vertices.add(vertex.getId(), vertex);//adding to the index based on vertex.getId()
				numVe++;
				isAdded=true;
			}
		}catch(IllegalArgumentException e) {}

		return isAdded;
	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(Connection edge) {//use storage.remove()
		//to remove a connection between vertices, you go to the source of the connection 
		//then delete the host-connection pair of this vertex

		boolean isRem=false;
		if(containsEdge(edge)) {//checks is edge exits
			Host h= getSource(edge);//get the vertex that has this as an outgoingEdge
			//going through the outedges of h, find edge and remove the pair.
			for(KeyValuePair<Host,Connection> e: storage.getAllPairs(h.getId())) {
				if(e.getValue()==edge) {
					storage.removeAt(h.getId(), e.getKey());
					isRem=true;
					break;
				}
			}
		}


		return isRem;
	}

	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the 
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices.  (Thus, for example, incident hyperedges are not removed, but 
	 * incident edges--which must be connected to a vertex at both endpoints--are removed.) 
	 * 
	 * <p>Fails under the following circumstances:
	 * <ul>
	 * <li/>vertex is not an element of this graph
	 * <li/>vertex is null
	 * </ul>
	 * 
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(Host vertex) {//use storage.remove
		//FIRST delete all outgoing and incoming edges to this vertex ONLY then u delete
		//vertex from vertices array.
		boolean isRem=false;
		if(containsVertex(vertex)) {
			storage.clearAt(vertex.getId());//clears the outgoing edges of this vertex
			int index =0;
			while(index<storage.getNumLists()-1) {
				storage.removeAt(index, vertex);
				index++;
			}
			vertices.remove(vertex.getId());
			vertices.add(vertex.getId(), null);
			numVe--;
			isRem=true;

		}



		return isRem;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	/**
	 * gets string representation of the graph.
	 * @return string value.
	 * */
	public String toString() {
		//you may edit this to make string representations of your
		//graph for testing, making a string representation for
		//ArrayOfListsOfPairs might be helpful here too.
		return super.toString();
	}
	/**
	 * testing method.
	 * @param args command arguments.
	 * */
	public static void main(String[] args) {
		//Some example testing code...
		
		//create a set of 10 nodes and 10 edges to test with
		Host[] hosts = new Host[10];
		Factory<Host> hostFactory = Host.getFactory();
		for(int i = 0; i < hosts.length; i++) {
			hosts[i] = hostFactory.create();
		}
		
		Connection[] connections = new Connection[10];
		Factory<Connection> connFactory = Connection.getFactory();
		for(int i = 0; i < connections.length; i++) {
			connections[i] = connFactory.create();
		}
		
		//constructs a graph
		Network graph = new Network();
		System.out.println("----addVertex-----");
		System.out.println(graph.addVertex(hosts[0]));//v0
		System.out.println(graph.addVertex(hosts[1]));//v1
		System.out.println(graph.addVertex(hosts[2]));//v2
		System.out.println(graph.addVertex(hosts[4]));//v4
		System.out.println(graph.getVertices());
		System.out.println("----addEdge-----");
		System.out.println(graph.addEdge(connections[0],hosts[0],hosts[1]));
		System.out.println(graph.addEdge(connections[4],hosts[0],hosts[0]));
		System.out.println(graph.addEdge(connections[1], hosts[1],hosts[0]));
		System.out.println(graph.addEdge(connections[2], hosts[1],hosts[2]));
		System.out.println(graph.addEdge(connections[3], hosts[2],hosts[0]));
		System.out.println(graph.addEdge(connections[5], hosts[2],hosts[4]));
		System.out.println(graph.getEdges());
		System.out.println("----ContainsV/E and CountV/E-----");
		System.out.println(graph.containsVertex(hosts[0]));
		System.out.println(graph.containsVertex(hosts[1]));
		System.out.println(graph.containsVertex(hosts[2]));
		System.out.println(graph.containsVertex(hosts[4]));
		System.out.println("----");
		System.out.println(graph.containsEdge(connections[0]));
		System.out.println(graph.containsEdge(connections[1]));
		System.out.println(graph.containsEdge(connections[2]));
		System.out.println(graph.containsEdge(connections[3]));
		System.out.println(graph.containsEdge(connections[4]));
		System.out.println(graph.containsEdge(connections[5]));
		System.out.println("----getOutEdges/getInEdges-----");
		System.out.println(graph.getOutEdges(hosts[0]));
		System.out.println(graph.getOutEdges(hosts[1]));
		System.out.println(graph.getOutEdges(hosts[2]));
		System.out.println(graph.getOutEdges(hosts[4]));
		System.out.println("----");
		System.out.println(graph.getInEdges(hosts[0]));
		System.out.println(graph.getInEdges(hosts[1]));
		System.out.println(graph.getInEdges(hosts[2]));
		System.out.println(graph.getInEdges(hosts[4]));
		System.out.println("----getPredessor-----");
		System.out.println(graph.getPredecessors(hosts[0]));
		System.out.println(graph.getPredecessors(hosts[1]));
		System.out.println(graph.getPredecessors(hosts[2]));
		System.out.println(graph.getPredecessors(hosts[4]));
		System.out.println("----getSucessor-----");
		System.out.println(graph.getSuccessors(hosts[0]));
		System.out.println(graph.getSuccessors(hosts[1]));
		System.out.println(graph.getSuccessors(hosts[2]));
		System.out.println(graph.getSuccessors(hosts[4]));
		System.out.println("----getSourceM-----");
		System.out.println(connections[0]);
		System.out.println(graph.getSource(connections[0])+" Im the source");
		System.out.println(connections[1]);
		System.out.println(graph.getSource(connections[1])+" Im the source");
		System.out.println(connections[2]);
		System.out.println(graph.getSource(connections[2])+" Im the source");
		System.out.println(connections[3]);
		System.out.println(graph.getSource(connections[3])+" Im the source");
		System.out.println(connections[4]);
		System.out.println(graph.getSource(connections[4])+" Im the source");
		System.out.println(connections[5]);
		System.out.println(graph.getSource(connections[5])+" Im the source");
		System.out.println("----getDest-----");
		System.out.println(connections[0]);
		System.out.println(graph.getDest(connections[0])+" Im the Dest");
		System.out.println(connections[1]);
		System.out.println(graph.getDest(connections[1])+" Im the Dest");
		System.out.println(connections[2]);
		System.out.println(graph.getDest(connections[2])+" Im the Dest");
		System.out.println(connections[3]);
		System.out.println(graph.getDest(connections[3])+" Im the Dest");
		System.out.println(connections[4]);
		System.out.println(graph.getDest(connections[4])+" Im the Dest");
		System.out.println(connections[5]);
		System.out.println(graph.getDest(connections[5])+" Im the Dest");
		System.out.println("----FindEdge-----");
		System.out.println(graph.findEdge(hosts[0],hosts[1]));
		System.out.println(graph.findEdge(hosts[1],hosts[0]));
		System.out.println(graph.findEdge(hosts[1],hosts[2]));
		System.out.println(graph.findEdge(hosts[2],hosts[0]));
		System.out.println(graph.findEdge(hosts[0],hosts[0]));
		System.out.println(graph.findEdge(hosts[2],hosts[4]));
		System.out.println("----removeE-----");
		System.out.println(graph.removeEdge(connections[0]));
		System.out.println(graph.findEdge(hosts[0],hosts[1]));
		System.out.println(graph.removeEdge(connections[1]));
		System.out.println(graph.findEdge(hosts[1],hosts[0]));
		System.out.println(graph.removeEdge(connections[2]));
		System.out.println(graph.findEdge(hosts[1],hosts[2]));
		System.out.println(graph.removeEdge(connections[3]));
		System.out.println(graph.findEdge(hosts[2],hosts[0]));
		System.out.println(graph.removeEdge(connections[4]));
		System.out.println(graph.findEdge(hosts[0],hosts[0]));
		System.out.println(graph.removeEdge(connections[5]));
		System.out.println(graph.findEdge(hosts[2],hosts[4]));
		System.out.println(graph.addEdge(connections[0],hosts[0],hosts[1]));
		System.out.println(graph.addEdge(connections[1],hosts[1],hosts[0]));
		System.out.println(graph.addEdge(connections[2], hosts[1],hosts[2]));
		System.out.println(graph.addEdge(connections[3], hosts[2],hosts[0]));
		System.out.println(graph.addEdge(connections[4], hosts[0],hosts[0]));
		System.out.println(graph.addEdge(connections[5], hosts[2],hosts[4]));
		System.out.println("----removeV-----");
		System.out.println("---removeH0---");
		System.out.println(graph.removeVertex(hosts[0]));
		System.out.println(graph.findEdge(hosts[0],hosts[1]));
		System.out.println(graph.findEdge(hosts[1],hosts[0]));
		System.out.println(graph.findEdge(hosts[2],hosts[0]));
		System.out.println(graph.findEdge(hosts[0],hosts[0]));
		System.out.println(graph.addVertex(hosts[0]));
		System.out.println(graph.addEdge(connections[0],hosts[0],hosts[1]));
		System.out.println(graph.addEdge(connections[1],hosts[1],hosts[0]));
		System.out.println(graph.addEdge(connections[3], hosts[2],hosts[0]));
		System.out.println(graph.addEdge(connections[4], hosts[0],hosts[0]));
		System.out.println("---removeH1---");
		System.out.println(graph.removeVertex(hosts[1]));
		System.out.println(graph.findEdge(hosts[0],hosts[1]));
		System.out.println(graph.findEdge(hosts[1],hosts[0]));
		System.out.println(graph.findEdge(hosts[1],hosts[2]));
		System.out.println(graph.addVertex(hosts[1]));
		System.out.println(graph.addEdge(connections[0],hosts[0],hosts[1]));
		System.out.println(graph.addEdge(connections[1],hosts[1],hosts[0]));
		System.out.println(graph.addEdge(connections[2], hosts[1],hosts[2]));
		System.out.println("---removeH2---");
		System.out.println(graph.removeVertex(hosts[2]));
		System.out.println(graph.findEdge(hosts[2],hosts[0]));
		System.out.println(graph.findEdge(hosts[1],hosts[2]));
		System.out.println(graph.findEdge(hosts[2],hosts[4]));
		System.out.println(graph.addVertex(hosts[2]));
		System.out.println(graph.addEdge(connections[2], hosts[1],hosts[2]));
		System.out.println(graph.addEdge(connections[3], hosts[2],hosts[0]));
		System.out.println(graph.addEdge(connections[5], hosts[2],hosts[4]));
		
		System.out.println("-----End------");
		System.out.println(graph.getInternalTable().getAr()[0].pair.toString()+"<>");
		System.out.println(graph.getInternalTable().getAr()[1].pair.toString()+"<>");
		System.out.println(graph.getInternalTable().getAr()[2].pair.toString()+"<>");
		//get the internal structure
		ArrayOfListsOfPairs<Host,Connection> intTable = graph.getInternalTable();
		System.out.println(Arrays.toString(intTable.getAr()));
		//get the entries for host0
		ArrayList<KeyValuePair<Host,Connection>> pairs = intTable.getAllPairs(0);
		
		//there should be only one pair.
		KeyValuePair<Host,Connection> pair = pairs.get(1);
		
		//make sure it's an entry connecting to host[1] using connection[0]
		if(pair.getKey().equals(hosts[1]) && pair.getValue().equals(connections[0])) {
			System.out.println("Yay");
		}
	}
	
	//********************************************************************************
	// YOU MAY, BUT DON'T NEED TO, EDIT THINGS IN THIS SECTION, BUT DON'T BREAK IT...
	// THERE ARE MUCH MORE OPTIMAL WAYS TO DO MANY OF THESE METHODS, SO IT MIGHT BE
	// GOOD TO LOOK HERE IF YOUR CODE IS SLOWER THAN IT NEEDS TO BE.
	//********************************************************************************
	
	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * Equivalent to v1.getPredecessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(Host v1, Host v2) {
		return getPredecessors(v2).contains(v1);
	}
	
	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * Equivalent to v1.getSuccessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(Host v1, Host v2) {
		return getSuccessors(v2).contains(v1);
	}

	/**
	 * Returns the endpoints of edge as a Pair<Host/>.
	 * @param edge the edge whose endpoints are to be returned.
	 * @return the endpoints (incident vertices) of edge.
	 */
	public Pair<Host> getEndpoints(Connection edge) {
		//System.out.println(getSource(edge).getId() + "---" + edge + "---" + getDest(edge).getId());
		return new Pair<Host>(getSource(edge), getDest(edge));
	}

	/**
	 * Returns true if vertex and edge.
	 * are incident to each other.
	 * Equivalent to getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * @param vertex the node.
	 * @param edge connection.
	 * @return true if vertex and edge are incident to each other.
	 */
	public boolean isIncident(Host vertex, Connection edge) {
		return getIncidentEdges(vertex).contains(edge);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test.
	 * @param v2 the second vertex to test.
	 * @return true if v1 and v2 share an incident edge.
	 */
	public boolean isNeighbor(Host v1, Host v2) {
		return getNeighbors(v1).contains(v2);
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then 
	 * it will be included in the collection returned.
	 * 
	 * @param vertex the vertex whose neighbors are to be returned
	 * @return  the collection of vertices which are connected to vertex, 
	 * 				or null if vertex is not present
	 */
	public Collection<Host> getNeighbors(Host vertex) {
		if(!containsVertex(vertex)) return null;
		ArrayList<Host> neighbors = new ArrayList<>();
		neighbors.addAll(getSuccessors(vertex));
		neighbors.addAll(getPredecessors(vertex));
		
		Connection c = findEdge(vertex, vertex);
		if(c != null) neighbors.remove(vertex);
		
		return neighbors;
	}
	
	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * 
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return  the collection of edges which are connected to vertex, 
	 * 				or null if vertex is not present
	 */
	public Collection<Connection> getIncidentEdges(Host vertex) {
		if(!containsVertex(vertex)) return null;
		ArrayList<Connection> edges = new ArrayList<>();
		edges.addAll(getOutEdges(vertex));
		edges.addAll(getInEdges(vertex));
		
		Connection c = findEdge(vertex, vertex);
		if(c != null) edges.remove(c);
		
		return edges;
	}
	
	/**
	 * Returns the number of incoming edges incident to vertex.
	 * Equivalent to getInEdges(vertex).size().
	 * @param vertex	the vertex whose indegree is to be calculated
	 * @return  the number of incoming edges incident to vertex
	 */
	public int inDegree(Host vertex) {
		return getInEdges(vertex).size();
	}

	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set).
	 * 
	 * <p>Equivalent to getNeighbors(vertex).size().
	 * @param vertex the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(Host vertex) {
		return getNeighbors(vertex).size();
	}
	
	/**
	 * Returns the number of edges incident to vertex.  
	 * Special cases of interest:
	 * <ul>
	 * <li/> Incident self-loops are counted once.
	 * <li/> If there is only one edge that connects this vertex to
	 * each of its neighbors (and vice versa), then the value returned 
	 * will also be equal to the number of neighbors that this vertex has
	 * (that is, the output of getNeighborCount).
	 * <li/> If the graph is directed, then the value returned will be 
	 * the sum of this vertex's indegree (the number of edges whose 
	 * destination is this vertex) and its outdegree (the number
	 * of edges whose source is this vertex), minus the number of
	 * incident self-loops (to avoid double-counting).
	 * </ul>
	 * <p/>Equivalent to getIncidentEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose degree is to be returned
	 * @return the degree of this node
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(Host vertex) {
		return getIncidentEdges(vertex).size();
	}
	
	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * @param vertex	the vertex whose outdegree is to be calculated
	 * @return  the number of outgoing edges incident to vertex
	 */
	public int outDegree(Host vertex) {
		return getOutEdges(vertex).size();
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return  the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(Host vertex) {
		return getPredecessors(vertex).size();
	}
	
	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * @param vertex the vertex whose successor count is to be returned
	 * @return  the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(Host vertex) {
		return getSuccessors(vertex).size();
	}
	
	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public Host getOpposite(Host vertex, Connection edge) {
		Pair<Host> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}
	
	/**
	 * Returns all edges that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if v1 is not connected to v2.
	 * <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
	 *  
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge d if
	 * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.).
	 * @param v1 vertex.
	 * @param v2 vertex.
	 * @return  a collection containing all edges that connect v1 to v2. null if either vertex is not present.
	 * @see Hypergraph#findEdge
	 */
	public Collection<Connection> findEdgeSet(Host v1, Host v2) {
		Connection edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}
		
		ArrayList<Connection> ret = new ArrayList<>();
		ret.add(edge);
		return ret;
		
	}
	
	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(Host vertex, Connection edge) {
		return getSource(edge).equals(vertex);
	}
	
	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(Host vertex, Connection edge) {
		return getDest(edge).equals(vertex);
	}
	
	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
	 * not be distinct).  Implementations for those graph types may provide alternate methods 
	 * that provide more convenient access to the vertices.
	 * 
	 * @param edge the edge whose incident vertices are to be returned
	 * @return  the collection of vertices which are connected to edge, 
	 * 				or null if edge is not present
	 */
	public Collection<Host> getIncidentVertices(Connection edge) {
		if(!containsEdge(edge)) return null;
		
		ArrayList<Host> vert = new ArrayList<>();
		
		Host source = getSource(edge);
		
		Host dest = getDest(edge);
		
		vert.add(source);
		if(!source.equals(dest)) vert.add(dest);
		
		return vert;
	}
	
	/**
	 * Returns the number of edges of type edgeType in this graph.
	 * @param edgeType the type of edge for which the count is to be returned
	 * @return the number of edges of type edgeType in this graph
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}
	
	/**
	 * Returns the collection of edges in this graph which are of type edgeType.
	 * @param edgeType the type of edges to be returned
	 * @return the collection of edges which are of type edgeType, or
	 * 				null if the graph does not accept edges of this type
	 * @see EdgeType
	 */
	public Collection<Connection> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdges();
		}
		return null;
	}
	
	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2.
	 * Equivalent to addEdge(e, new Pair<Host/>(v1, v2)).
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If edgeType is not legal for this graph, this method will
	 * throw IllegalArgumentException.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @param edgeType the type to be assigned to the edge
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object)
	 */
	public boolean addEdge(Connection e, Host v1, Host v2, EdgeType edgeType) {
		//NOTE: Only directed edges allowed
		
		if(edgeType == EdgeType.UNDIRECTED) {
			throw new IllegalArgumentException();
		}
		
		return addEdge(e, v1, v2);
	}
	
	/**
	 * Adds edge to this graph.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph 
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * </ul>
	 * 
	 * @param edge connection.
	 * @param vertices node.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, 
	 * 				or if a different vertex set in this graph is already connected by edge, 
	 * 				or if vertices are not a legal vertex set for edge 
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(Connection edge, Collection<? extends Host> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		Host[] vs = (Host[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edgeType.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph 
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * <li/>edgeType is not legal for this graph
	 * </ul>
	 * 
	 * @param edge connection.
	 * @param vertices node.
	 * @param edgeType kind of edge.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, 
	 * 				or if a different vertex set in this graph is already connected by edge, 
	 * 				or if vertices are not a legal vertex set for edge 
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(Connection edge, Collection<? extends Host> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		Host[] vs = (Host[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT TO ADD/CORRECT JAVADOCS
	//********************************************************************************
	
	//This will be used to check that you are setting
	//the storage up correctly... it's very important
	//that you (1) are using the ArrayOfListsOfPairs 
	//provided and (2) don't edit this at all
	/**
	 * returns storage of network.
	 * @return Array list of network nodes.
	 * */
	public ArrayOfListsOfPairs<Host,Connection> getInternalTable() {
		return storage;
	}
	
	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * @return graph instance.
	 */
	@SuppressWarnings("unchecked")
	public static Factory<Graph<Host,Connection>> getFactory() { 
		return new Factory<Graph<Host,Connection>> () {
			public Graph<Host,Connection> create() {
				return (Graph<Host,Connection>) new Network();
			}
		};
	}
	
	/**
	 * Returns the edge type of edge in this graph.
	 * @param edge connection.
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(Connection edge) {
		return EdgeType.DIRECTED;
	}
	
	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}
	
	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted). 
	 * 
	 * <p>Equivalent to getIncidentVertices(edge).size().
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(Connection edge) {
		return 2;
	}
}
