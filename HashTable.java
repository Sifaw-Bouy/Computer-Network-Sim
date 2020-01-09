
//Hash table with separate chaining. Each key and value gets
//placed together as a single entry in the table. The hash code
//of a key is used to place the pair in the table and to look
//for it again. Note that KeyValuePair is a structure for
//ArrayOfListsOfPairs, this part of the code needs to be able to
//deal with keys and values separately.


import java.util.Collection;

import project4.ArrayOfListsOfPairs.Node;

/**
 * main class that use ArrayOfList as Storage.
 * Implanting a hashTable.
 * @author Sifaw Bouylazane.
 * @version 11/9/19.
 * @param <K> key.
 * @param <V> value.
 * */
public class HashTable<K,V> {
	//This is the minimum number of slots in the hash table
	//Do not change this.
	/**
	 * Minimum size of Hash table.
	 * */
	private static final int MIN_SLOTS = 2;
	
	//You must use this as your internal storage, you may not change
	//the type, name, privacy, or anything else about this variable.
	/**
	 * holds the values of HashTable.
	 * */
	protected ArrayOfListsOfPairs<K,V> storage;
	/**
	 * holds number of pairs.
	 * */
	private int numSl;
	//If the number of slots requested is less than the minimum
	//number of slots, use the minimum instead.
	/**
	 * constructor.
	 * @param numSlots size of storage.
	 * */
	public HashTable(int numSlots) {
		if(numSlots<MIN_SLOTS) {
			storage= new ArrayOfListsOfPairs<K,V>(MIN_SLOTS);
			numSl=MIN_SLOTS;
		}else {
			storage= new ArrayOfListsOfPairs<K,V>(numSlots);
			numSl=numSlots;
		}
		//if numSlots<MIN_Slots intialize storage to MIN_Slots
		//setting the size of storage
		//make a private var that holds numSlots

	}

	//The number of key-value entries in the table.
	//O(1)
	/**
	 * gets the number key/values in hash table.
	 * @return size of table.
	 * */
	public int size() {
		//storage.size();
		return storage.getNumPairs();

	}

	//Returns the number of slots in the table.
	//O(1)
	/**
	 * return the size of hash table.
	 * @return size.
	 * */
	public int getNumSlots() {
		//return slots.

		//use the private numSlots var
		return numSl;
	}

	//Returns the load on the table.
	//O(1)
	/**
	 * gets load of the hashTable.
	 * @return double value.
	 * */
	public double getLoad() {//this is= numitems/storage size(slots)
		//storage.getNumPairs/getNumSlots
		return (double) size()/getNumSlots();
	}

	//If the key is not in the table, add the key-value entry to the table
	//and return true. If unable to add the entry, return false(The key is already exists). Keys and
	//values are _not_ allowed to be null in this table, so return false if
	//either of those are provided instead of trying to add them.

	//If the load goes above 3 /AFTER adding an entry/, this method should
	//rehash to three times the number of slots.

	//Must run in worst case O(n) and average case O(n/m) where n is the
	//number of key-value pairs in the table and m is the number of "slots"
	//in the table.
	/**
	 * adds key/value pair into inner storage casted with 
	 * a node.
	 * @param key what is used for look up.
	 * @param value of the key.
	 * @return boolean value.
	 * */
	public boolean add(K key, V value) {//call the storage.add()
		//make this into a pair using KeyValuePair then call storage.add()
		//since it only takes in node pairs.
		//check key and value are not null
		boolean added=false;
		KeyValuePair<K,V> pair= new KeyValuePair<K,V>(key,value);
		if(key!=null && value!=null) {
			added=storage.addPair(pair);
			if(getLoad()>3) {//seeing if we need to rehash
				rehash(3*getNumSlots());
			}

		}
		return added;
	}

	//Rehashes the table to the given new size (new number of
	//slots). If the new size is less than the minimum number
	//of slots, use the minimum instead.

	//Must run in worst case O(n+m) where n is the number of
	//key-value pairs in the table and m is the number of
	//"slots" in the table.
	/**
	 * sets a new size for the table.
	 * @param newSize size of the table.
	 * */
	public void rehash(int newSize) {
		//creating a new Stroge based on newSize
		ArrayOfListsOfPairs<K,V> newStorage=null;
		if(newSize<=MIN_SLOTS) {
			newStorage= new ArrayOfListsOfPairs<K,V>(MIN_SLOTS);
			numSl=MIN_SLOTS;
		}else {
			newStorage= new ArrayOfListsOfPairs<K,V>(newSize);
			numSl=newSize;
		}
		for(KeyValuePair<K,V> pair: storage.getAllPairs()) {
			//call the add() method
			newStorage.addPair(pair);
		}
		storage=newStorage;
	}

	//If the key requested is in the table, change the associated value
	//to the provided value and return true. Otherwise return false.

	//Must run in worst case O(n) and average case O(n/m) where n is the
	//number of key-value pairs in the table and m is the number of "slots"
	//in the table.
	/**
	 * changes a key/value in the table.
	 * @param key value.
	 * @param value of the key.
	 * @return boolean value of whether its add or not.
	 * */
	public boolean replace(K key, V value) {//look at its hash code
		boolean isChanged=false;
		if(contains(key)) {
			isChanged= storage.changeVal(key, value);
		}


		return isChanged;
	}

	//If the key requested is in the table, remove the key-value entry
	//and return true. Otherwise return false.

	//Must run in worst case O(n) and average case O(n/m) where n is the
	//number of key-value pairs in the table and m is the number of "slots"
	//in the table.
	/**
	 * remove a key/value pair in storage based on given Key.
	 * @param key value.
	 * @return boolean value whether pair is removed or not.
	 * */
	public boolean remove(K key) {//look at its hash code
		return storage.remove(key);
	}

	//If the key requested is in the table, return true. Otherwise return false.

	//Must run in worst case O(n) and average case O(n/m) where n is the
	//number of key-value pairs in the table and m is the number of "slots"
	//in the table.
	/**
	 * goes through storage checks if key/value exists.
	 * @param key value.
	 * @return boolean value whether pair exists or not.
	 * */
	public boolean contains(K key) {//look at its hash code
		//U use getPair() this returns the pair associated with key
		return storage.contains(key);
	}

	//If the key requested is in the table, return the associated value.
	//Otherwise return null.

	//Must run in worst case O(n) and average case O(n/m) where n is the
	//number of key-value pairs in the table and m is the number of "slots"
	//in the table.
	/**
	 * gets the value of the given key.
	 * @param key value.
	 * @return value of the key.
	 * */
	public V get(K key) {//look at its hash code
		//U use getPair() this returns the pair associated with key
		V ans=null;
		if(storage.contains(key)) {
			ans=storage.getPair(key).pair.getValue();

		}
		return ans;
	}
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	/**
	 * to string method.
	 * @return string value.
	 * */
	public String toString() {
		//you may edit this to make string representations of your
		//lists for testing
		return super.toString();
	}
	/**
	 * testing method.
	 * @param args command arguments.
	 * */
	public static void main(String[] args) {
		//Some example testing code...
		
		//make a hash table and add something to it
		HashTable<Integer,String> ht = new HashTable<>(2);
		ht.add(2,"Apple");
		
		//get all pairs at location 0
		Collection<KeyValuePair<Integer,String>> pairs = ht.getInternalTable().getAllPairs(0);
		
		//should be one pair there...
		if(pairs.size() == 1) {
			//get the first pair from the list
			KeyValuePair<Integer,String> pair = pairs.iterator().next();
			
			//make sure it's the pair expected
			if(pair.getKey().equals(2) && pair.getValue().equals("Apple")) {
				System.out.println("Yay");
			}
		}
		ht.remove(2);
		if(!ht.contains(2)) {
			System.out.println("Yay1");
		}
	
		System.out.println("-----addM/rehashM-------");
		System.out.println(ht.add(1, "a"));
		System.out.println(ht.add(2, "b"));
		System.out.println(ht.add(3, "c"));
		System.out.println(ht.add(4, "d"));
		System.out.println(ht.add(5, "e"));
		System.out.println(ht.add(6, "f"));
		System.out.println(ht.add(7, "g"));//will be rehashed after adding this
		System.out.println(ht.add(8, "h"));
		System.out.println(ht.getLoad());
		System.out.println("-----getValue------");
		System.out.println(ht.get(1));
		System.out.println(ht.get(2));
		System.out.println(ht.get(3));
		System.out.println(ht.get(4));
		System.out.println(ht.get(5));
		System.out.println(ht.get(6));
		System.out.println(ht.get(7));
		System.out.println(ht.get(8));
		System.out.println("-----contains------");
		System.out.println(ht.contains(1));
		System.out.println(ht.contains(2));
		System.out.println(ht.contains(3));
		System.out.println(ht.contains(4));
		System.out.println(ht.contains(5));
		System.out.println(ht.contains(6));
		System.out.println(ht.contains(7));
		System.out.println(ht.contains(8));
		System.out.println("-----remove------");
		System.out.println(ht.remove(1));
		System.out.println(ht.remove(2));
		System.out.println(ht.remove(3));
		System.out.println(ht.remove(4));
		System.out.println(ht.remove(5));
		System.out.println(ht.remove(6));
		System.out.println(ht.remove(7));
		System.out.println(ht.remove(8));
		System.out.println(ht.size());
		System.out.println("---------");
		
		System.out.println("-------LinkNode-------");
		ht.replace(5, "zz");
		
		
	}
	
	//--------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
	//--------------------------------------------------------
	
	
	//This will be used to check that you are setting
	//the storage up correctly... it's very important
	//that you (1) are using the ArrayOfListsOfPairs 
	//provided and (2) don't edit this at all
	/**
	 * Returns the key/value pairs of the inner storage used by 
	 * Hash Table.
	 * @return pairs of ArrayOfListsOfPairs.
	 * */
	public ArrayOfListsOfPairs<K,V> getInternalTable() {
		return storage;
	}
}
