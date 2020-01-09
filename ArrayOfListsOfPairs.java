//This structure is an array where each entry is the head of its own
//linked list. The linked lists are made up of "bare nodes" (i.e.
//they are not "wrapped" in a nice linked list class). Each node
//in each linked list contains a key-value pair (rather than an single
//value).



//This class will form the baseline for creating a hash table for a
//map that uses separate chaining (each entry in a map is a key-value
//pair). This class will also form a baseline for creating an adjacency
//list (where each entry is a key-value pair where keys are the
//"adjacent" node and values are the connection between them). This way
//we have a universal way for to access your internal structures when
//grading these two classes.

//You have a lot of freedom in how you design this class, as long as
//the provided code work as described. However, because this is only
//a baseline for the other classes you are writing, it would be bad
//design on your part to add in anything specific to hash tables
//(such as rehashing) or specific to graphs (such as source/destination
//information for edges). Our advice to you is: (1) keep it simple
//and (2) think before you code.

//Read the "do not edit" section carefully so that you know what is
//already available. This should help you form some ideas of what
//types of things are missing.

//You may: Add additional methods or variables of any type (even
//public!), but again, you _must_ use the provided Node class, the
//provided "storage" instance variable, and all provided methods
//_must still work_.

//You may not import anything from java.util (and you may not use anything
//from java.util in your part of the code). We use java.util.ArrayList in
//the provided code, but it is not available to you.

/**
 * Main class containing Array of list of pairs.
 * @author Sifaw Bouylazane.
 * @version 11/9/19
 * @param <K> key 
 * @param <V> value
 * */
public class ArrayOfListsOfPairs<K,V> {
	//This is your internal structure, you must use this
	//you may not change the type, name, privacy, or anything
	//else about this variable. It is initialized in the
	//provided constructor (see the do-not-edit section)
	//and the Node class is also defined there.
	
	
	
	//So this class well be used by hashtable and Network. 
	/**
	 * holds the storage.
	 **/
	private Node<K,V>[] storage;
	/**
	 * keep track of number of pairs in storage.
	 **/
	private int numPairs=0;
	/**
	 * gets the index to enter the storage.
	 * @param key value from pair.
	 * @return integer value.
	 **/
	public int getIndex(K key) {
		return Math.abs(key.hashCode())%storage.length;
	}
	/**
	 * gets the current number of key/value pairs.
	 * @return number of pairs.
	 **/
	public int getNumPairs() {
		return numPairs;
	}
	/**
	 * add the given key/value pair into specific index of storage
	 * true otherwise false.
	 * @param pair key/value pair.
	 * @param id index 
	 * @return boolean value.
	 **/
	public boolean addAt(KeyValuePair<K,V> pair, int id) {//this is for network, helps.
		boolean isAdded=false;
		Node<K,V> nodeP= new Node<K,V>(pair);
		if(!contains(pair.getKey())) {
			if(storage[id] == null) {
				storage[id]=nodeP;
				numPairs++;
				isAdded=true;
			}else {
				Node<K,V> start= storage[id];
				while(start.next!=null) {
					start= start.next;
				}
				start.next=nodeP;
				numPairs++;
				isAdded=true;
			}
		}
		return isAdded;
	}
	/**
	 * add the given key/value pair into the storage and returns
	 * true otherwise false.
	 * @param pair key/value pair.
	 * @return boolean value.
	 **/
	public boolean addPair(KeyValuePair<K,V> pair) {
		//using nodeP.pair.hashCode()
		//increment numPairs for Hashtable's size()
		boolean isAdded=false;
		Node<K,V> nodeP= new Node<K,V>(pair);
		int index= getIndex(pair.getKey());//getting index
		if(!contains(pair.getKey())) {
			if(storage[index]==null){
				storage[index]=nodeP;
				numPairs++;
				isAdded=true;
			}else{//going trough linked list
				Node<K,V> start= storage[index];
				while(start.next!=null) {
					start= start.next;
				}
				start.next=nodeP;
				numPairs++;
				isAdded=true;
			}
		}
		return isAdded;
	}
	/**
	 * checks to see if a pair exists in specific index in storage.
	 * @param key value of pair.
	 * @param index value.
	 * @return boolean value.
	 **/
	public boolean containsAt(int index, K key) {
		boolean contn=false;
		Node<K,V> head= storage[index];
		try {
			if(storage[index].pair.getKey().equals(key)) {
				contn=true;
				return contn;
			}else {
				while(head!=null) {
					if(head.pair.getKey().equals(key)) {
						contn=true;
						return contn;
					}
					head=head.next;
				}
			}
		}catch(RuntimeException e) {}
		return contn;
	}
	/**
	 * checks to see if a pair exists in storage.
	 * @param key value of pair.
	 * @return boolean value.
	 **/
	public boolean contains(K key) {
		boolean contn=false;
		int index= getIndex(key);
		Node<K,V> head= storage[index];
		try {
			if(storage[index].pair.getKey().equals(key)) {
				contn=true;
				return contn;
			}else {
				while(head!=null) {
					if(head.pair.getKey().equals(key)) {
						contn=true;
						return contn;
					}
					head=head.next;
				}
			}
		}catch(RuntimeException e) {}
		return contn;
	}
	/**
	 * removes the pair given the key value and starting index.
	 * returns true otherwise false.
	 * @param key value pair.
	 * @param index specific place
	 * @return boolean value
	 **/
	public boolean removeAt(int index, K key) {
		boolean isRemoved=false;
		Node<K,V> head= storage[index];

		Node<K,V> prv=null;
		try {
			if(containsAt(index, key)) {
				if(storage[index].pair.getKey().equals(key) && 
						storage[index].next==null) {
					storage[index]=null;
					numPairs--;
					return isRemoved=true;
				}else if(storage[index].pair.getKey().equals(key) && 
						storage[index].next!=null) {
					storage[index]=storage[index].next;
					numPairs--;
					return isRemoved=true;
				}else {
					while(head!=null) {
						if(head.next==null) {
							prv.next=null;
						}
						if(head.pair.getKey().equals(key)) {
							prv.next=head.next;
							numPairs--;
							return isRemoved=true;
						}
						prv=head;
						head=head.next;
					}
				}
			}
		}catch(RuntimeException e) {}


		return isRemoved;

	}
	/**
	 * removes the pair given the key value.
	 * returns true otherwise false.
	 * @param key value pair.
	 * @return boolean value
	 **/
	public boolean remove(K key) {
		//removes a KeyValuePair
		boolean isRemoved=false;
		int index= getIndex(key);

		Node<K,V> head= storage[index];
		Node<K,V> prv=null;
		try {
			if(contains(key)) {
				if(storage[index].pair.getKey().equals(key) && 
						storage[index].next==null) {
					storage[index]=null;
					numPairs--;
					return isRemoved=true;
				}else if(storage[index].pair.getKey().equals(key) && 
						storage[index].next!=null) {
					storage[index]=storage[index].next;
					numPairs--;
					return isRemoved=true;
				}else {
					while(head!=null) {
						if(head.next==null) {
							prv.next=null;
							
						}
						if(head.pair.getKey().equals(key)) {
							prv.next=head.next;
							numPairs--;
							return isRemoved=true;
						}
						prv=head;
						head=head.next;
					}
				}
			}
		}catch(RuntimeException e) {}


		return isRemoved;
	}
	/**
	 * changes the value of the given pair.
	 * returns true otherwise false.
	 * @param key value.
	 * @param value of key.
	 * @return boolean value.
	 **/
	public boolean changeVal(K key, V value) {
		boolean changed=false;
		int index= getIndex(key);
		Node<K,V> head= storage[index];
		try {
			if(contains(key)) {
				KeyValuePair<K,V> pair= new KeyValuePair<K,V>(key,value);

				if(storage[index].pair.getKey().equals(key) 
						&& storage[index].next==null) {
					storage[index].pair=pair;
					changed=true;
					return changed;
				}else {
					Node<K,V> prv=null;
					while(head!=null) {
						if(head.pair.getKey().equals(key)) {
							head.pair=pair;
							changed=true;
						}
						prv=head;
						head=head.next;
					}
				}
			}

		}catch(RuntimeException e) {}
		return changed;
	}

	/**
	 * gets key/value pair based on key.
	 * @param key value.
	 * @return Node key/value pair value.
	 **/
	public Node<K,V> getPair(K key){
		Node<K,V> pair=null;
		int index= getIndex(key);
		Node<K,V> head=storage[index];
		if(contains(key)) {
			if(head.pair.getKey().equals(key)) {
				pair=head;
			}else {
				while(head!=null) {
					if(head.pair.getKey().equals(key)) {
						pair=head;
						break;
					}
					head=head.next;
				}
			}
		}
		return pair;
	}

	/**
	 * returns the current storage.
	 * @return an array of Node key/value pairs.
	 **/
	public Node<K,V>[] getAr(){
		return storage;
	}
	/**
	 * clears the storage.
	 **/
	public void clear() {
		for(int i=0;i<storage.length;i++) {
			storage[i]=null;
		}
	}
	/**
	 * clears the specified sublist.
	 * @param index value.
	 **/
	public void clearAt(int index) {
		storage[index]=null;
	}
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	/**
	 * gets string rep of inner list in storage.
	 * @return String value.
	 * */
	public String toString() {
		
		return super.toString();
	}
	/**
	 * Testing method.
	 * @param args command input.
	 * */
	public static void main(String[] args) {
		ArrayOfListsOfPairs<Integer,String> list= new ArrayOfListsOfPairs<Integer,String>(4);
	
		KeyValuePair<Integer,String> pair3= new KeyValuePair<Integer,String>(1,"hi");
		KeyValuePair<Integer,String> pair4= new KeyValuePair<Integer,String>(2,"my");
		KeyValuePair<Integer,String> pair5= new KeyValuePair<Integer,String>(3,"name");
		KeyValuePair<Integer,String> pair6= new KeyValuePair<Integer,String>(4,"is");
		KeyValuePair<Integer,String> pair7= new KeyValuePair<Integer,String>(5,"Sifa");
		KeyValuePair<Integer,String> pair8= new KeyValuePair<Integer,String>(14,"how");
		KeyValuePair<Integer,String> pair9= new KeyValuePair<Integer,String>(15,"are");
		KeyValuePair<Integer,String> pair10= new KeyValuePair<Integer,String>(18,"you");
		KeyValuePair<Integer,String> pair11= new KeyValuePair<Integer,String>(16,"yes");
		KeyValuePair<Integer,String> pair12= new KeyValuePair<Integer,String>(19,"Im");
		KeyValuePair<Integer,String> pair13= new KeyValuePair<Integer,String>(20,"good");
		KeyValuePair<Integer,String> pair2= new KeyValuePair<Integer,String>(23,"thank");
		System.out.println("-------addM----------");
		System.out.println(list.addPair(pair3));
		System.out.println(list.contains(pair3.getKey())+" is in lst");
		System.out.println(list.addAt(pair2, 1));
		System.out.println(list.addPair(pair4));
		System.out.println(list.contains(pair3.getKey())+" is in lst");
		System.out.println(list.addPair(pair5));
		System.out.println(list.contains(pair3.getKey())+" is in lst");
		System.out.println(list.addPair(pair6));
		System.out.println(list.contains(pair3.getKey())+" is in lst");
		System.out.println(list.addPair(pair7));
		System.out.println(list.contains(pair3.getKey())+" is in lst");
		System.out.println(list.addPair(pair8));
		System.out.println(list.contains(pair3.getKey())+" is in lst");
		System.out.println(list.addPair(pair9));
		System.out.println(list.contains(pair3.getKey())+" is in lst");
		System.out.println(list.addPair(pair10));
		System.out.println(list.addPair(pair11));
		System.out.println(list.addPair(pair12));
		System.out.println(list.addPair(pair13));
		//		System.out.println(Arrays.toString(list.getAr()));
		for(Node<Integer, String> e:list.getAr()) {
			if(e!=null) {
				System.out.println(e.pair.getKey());
			}
		}
		System.out.println("-------RemoveM-------");
		System.out.println(list.remove(4));
		System.out.println(list.remove(5));
		System.out.println(list.remove(3));
		System.out.println(list.changeVal(14, "myyy"));
		System.out.println(list.changeVal(18, "yay"));
		
		//System.out.println(Arrays.toString(list.getAr()));
		for(Node<Integer, String> e:list.getAr()) {
			if(e!=null) {
				if(e.next!=null) {
					System.out.println(e.pair.getKey()+" has others "+" "+e.pair.getValue());
					System.out.println(e.next.pair.getKey()+" "+e.next.pair.getValue());
					if(e.next.next!=null) {
						System.out.println(e.next.next.pair.getKey()+" "+e.next.next.pair.getValue());
					}
				}else{
					System.out.println(e.pair.getKey()+" has no others");
				}
			}else {
				System.out.println(e);
			}
		}
		System.out.println(list.getNumPairs());
		System.out.println("-------getPairM-------");
		System.out.println(list.getPair(19).pair.getKey());
		System.out.println(list.getPair(4));
		System.out.println("-------clearM-------");
		list.clear();
		//System.out.println(Arrays.toString(list.getAr()));
		
		
		
	}
	
	
	
	
	//--------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE
	//--------------------------------------------------------
	
	//This is what one node in one linked list looks like
	/**
	 * Main class for Node of key/value pairs.
	 * @param <K> key.
	 * @param <V> value.
	 * @author Sifaw Bouylazane.
	 * */
	public static class Node<K,V> {
		//it contains one key-value pair
		/**
		 * Initialized value of key/value.
		 * */
		public KeyValuePair<K,V> pair;
		
		//and one pointer to the next node
		/**
		 * Initialized node of key/value pair.
		 * */
		public Node<K,V> next;
		
		//convenience constructor
		/**
		 * constructor.
		 * @param pair type of key/value pair.
		 * */
		public Node(KeyValuePair<K,V> pair) {
			this.pair = pair;
			
		}
		
		//convenience constructor
		/**
		 * constructor.
		 * @param pair type of key/value pair.
		 * @param next type of node with key/value pair.
		 * */
		public Node(KeyValuePair<K,V> pair, Node<K,V> next) {
			this.pair = pair;
			this.next = next;
		}
	}
	
	//Creates an array with the specified number of lists-of-pairs
	/**
	 * constructor.
	 * Initializes the storage for class.
	 * @param numLists sets size of storage array.
	 * */
	@SuppressWarnings("unchecked")
	public ArrayOfListsOfPairs(int numLists) {
		storage = (Node<K,V>[]) new Node[numLists];
	}
	/**
	 * get method for getting number of lists in storage.
	 * @return integer size of storage.
	 * */
	//Returns the number of lists in this collection
	public int getNumLists() {
		return storage.length;
	}
	
	//Returns all key-value pairs in the specified sublist of this collection
	/**
	 * gets all the key/value pair in specified list in storage.
	 * @param listId index of wanted list.
	 * @return array of key/value pairs.
	 * */
	public java.util.ArrayList<KeyValuePair<K,V>> getAllPairs(int listId) {
		java.util.ArrayList<KeyValuePair<K,V>> lst = new java.util.ArrayList<>();
		
		Node<K,V> current = storage[listId];
		while(current != null) {
			lst.add(current.pair);
			current = current.next;
		}
		
		return lst;
	}
	
	//Returns all key-value pairs in this collection
	/**
	 * gets all the key/value pair in each list in storage.
	 * @return Array list of key/value
	 * */
	public java.util.ArrayList<KeyValuePair<K,V>> getAllPairs() {
		java.util.ArrayList<KeyValuePair<K,V>> lst = new java.util.ArrayList<>();
		
		for(int i = 0; i < storage.length; i++) {
			lst.addAll(getAllPairs(i));
		}
		
		return lst;
	}
}
