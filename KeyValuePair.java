package project4;
//done
//This class represents a key-value pair. It is completed for you,
//but you need to add JavaDocs.

//--------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
//--------------------------------------------------------
/**
 * main class for holding K/V pairs.
 * @author Sifaw Bouylazane.
 * @version 11/9/19.
 * @param <K> key.
 * @param <V> value.
 * */
public class KeyValuePair<K,V> {
	/**
	 * key of type K.
	 * */
	private K key;
	/**
	 * key of type V.
	 * */
	private V value;
	/**
	 * constructor.
	 * Initializes the key and value.
	 * @param key value.
	 * @param value with the key.
	 * */
	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	/**
	 * gets value of Key.
	 * @return key value of type K.
	 * */
	public K getKey() {
		return key;
	}
	/**
	 * gets value.
	 * @return value of type V.
	 * */
	public V getValue() {
		return value;
	}
	/**
	 * checks if the given object type is of 
	 * class instance.
	 * @param o type object
	 * @return boolean value.
	 * */
	public boolean equals(Object o) {
		if(o instanceof KeyValuePair) {
			return key.equals(((KeyValuePair)o).key);
		}
		return false;
	}
	/**
	 * gets hash Code of the key.
	 * @return integer value.
	 * */
	public int hashCode() {//can be used when trying to traverse Storage
		return key.hashCode();
		
	}
	/**
	 * gets string representation of key/value.
	 * @return string value.
	 * */
	public String toString() {
		return "("+key+","+value+")";
	}
}