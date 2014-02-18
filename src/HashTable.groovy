import java.lang.*
import java.util.*

/**
 * Created by sforgie on 2/16/14.
 */
public class HashTable
{

    private LinkedList<String> [] hashtable

    private int size

    private static int buckets = 5381

    private int collisions

    /**
     * Constructor for a Hashtable as an array of
     * Linked Lists
     */
    public HashTable()
    {
        hashtable  =  new  LinkedList<String> [(buckets+1)]
        size = 0
        collisions = 0
    }

    /**
     * Overloaded Constructor for Hashtable to define
     * the number of buckets to use
     *
     */
    public HashTable(int b)
    {
        buckets = b

        hashtable  =  new  LinkedList<String> [(buckets+1)]
        size = 0
        collisions = 0
    }

    /**
     * Adds a value of type String to the Hashtable
     * If a collision occurs the value is added at
     * the head of the Linked List
     *
     * @param key  String
     * @param value  String
     */
    public void Insert(String key, String value)
    {
        int k = Hash(key);

        if(hashtable[k] == null)
            hashtable[k] = new LinkedList<String>()
        else
            collisions += 1

        hashtable[k].addFirst(value)
        size += 1
    }

    /**
     *  Return true if a given key is present in the
     *  HashTable and false if not
     *
     * @param key String
     * @return  true if key is in the HashTable
     */
    public boolean ContainsKey(String key)
    {
        int k = Hash(key)

        if(hashtable[k] != null)
            return true

        return false
    }

    /**
     * Return true if a given value is present in
     * the hashtable and false if not
     *
     * @param value  String
     * @return  boolean
     */
    public boolean ContainsValue(String value)
    {
        for(LinkedList<String> l : hashtable)
        {
            if(l != null)
                for(String s : l)
                {
                    if(s.equals(value))
                        return true
                }
        }

        return false

    }

    /**
     * Returns true if size is 0 indicating the
     * table is empty and false if not
     *
     * @return boolean
     */
    public boolean IsEmpty()
    {
        return size == 0
    }

    /**
     * Returns the size of the hashtable,
     * this is the total number of values
     * held in the table
     *
     *
     * @return  int
     */
    public int Size()
    {
        return size
    }




    /**
     * Hashes a given key using String.hashCode()
     * and mods it against the number of buckets
     *
     * This will be a basic test to compare
     * against with other models of hashing
     * functions
     *
     * @see #hashCode()
     * @param key String
     * @return
     */
    public int Hash(String key)
    {
       return key.hashCode()%buckets
    }




    /**
     * Returns the number of collisions that
     * have occurred as values have been added
     * to the hashtable
     *
     * @return int
     */
    public int NumberOfCollisions()
    {
        return collisions
    }




    /**
     *  Main method used for a bit of testing
     *
     * @param args
     */
    public static void main(String[] args)
    {

        def ht = new HashTable()


        assert ht.Size() == 0

        assert ht.ContainsKey("A") == false

        assert ht.ContainsValue("A") == false

        assert ht.NumberOfCollisions() == 0

        assert ht.IsEmpty() == true


        def hasht = new HashTable(333)

        assert hasht.Size() == 0

    }

}
