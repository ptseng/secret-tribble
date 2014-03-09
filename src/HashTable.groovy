import java.lang.*
import java.util.*
import java.security.*

/**
 * Created by sforgie on 2/16/14.
 */
public class HashTable<K,V> extends DataGen
{

    private Object [] hashtable

    /**
     * Number of unique values held in the hashtable
     */
    private int size

    /**
     * Default number of buckets
     */
    private int buckets = 5381

    /**
     * The number of collisions counted as keys are added
     */
    private int collisions

    /**
     * Indicates which hash function to use.
     *
     * 1) java Object hashCode
     * 2) java security cryptographic hash function (Md5)
     *
     * @see #hashCode()
     * @see #CryptoHash(java.lang.Object)
     */
    private int function = 1

    /**
     * MessageDigest object used to create a Md5 hashing object
     */
    private MessageDigest md5

    /**
     * Constructor for a Hashtable as an array of
     * Linked Lists
     */
    public HashTable( f )
    {
        if(f == 1 || f == 2)  function = f

        if(f == 2)
        {
            md5 = MessageDigest.getInstance("MD5")
        }

        hashtable  =  new  LinkedList<Data<K,V>> [(buckets+1)]
        size = 0
        collisions = 0
    }

    /**
     * Overloaded Constructor for Hashtable to define
     * the number of buckets to use
     *
     */
    public HashTable( f, b )
    {
        if(f == 1 || f == 2)  function = f

        if(f == 2)
        {
            md5 = MessageDigest.getInstance("MD5")
        }

        buckets = b


        hashtable  =  new  LinkedList<Data<K,V>> [(buckets+1)]
        size = 0
        collisions = 0
    }

    /**
     * Adds a value of type Object to the Hashtable
     * If a collision occurs the value is added at
     * the head of the Linked List
     *
     * @param key  Object
     * @param value  Object
     */
    public void Insert( key, value)
    {
        def d = new Data<K,V>(key, value)

        int index = GetIndex(key)

        if(hashtable[index] == null)
        {
            hashtable[index] = new LinkedList<Data<K,V>>()
        }
        else
        {
            collisions += 1
        }

        hashtable[index].addFirst(d)
        size += 1
    }

    /**
     *  Return true if a given key is present in the
     *  HashTable and false if not
     *
     * @param key Object
     * @return  boolean found = true not found = false
     */
    public boolean ContainsKey( key )
    {
        def index = GetIndex(key)

        def l = hashtable[index]

        if(l != null)
        {
            def iter = l.iterator()

            while(iter.hasNext())
            {
                if(key.compareTo(iter.next().key) == 0)
                    return true
            }
        }

        return false
    }




    /**
     *  Return the number of traversals it took to find a given key
     *
     * @param key Object
     * @return  int -1 if not in the table and no matching hashes otherwise the traversal number
     *              it took to get there and negative if matched hash value but not in the list
     */
    public int CountTraversalsKey( key )
    {
        def index = GetIndex(key)
        def traversal = 0

        def l = hashtable[index];

        if(l != null)
        {
            def iter = l.iterator()

            while(iter.hasNext())
            {
                ++traversal

                if(key.compareTo(iter.next().key) == 0)
                    return traversal
            }
        }
        return -traversal
    }

    /**
     * Return true if a given value is present in
     * the hashtable and false if not
     *
     * @param value  Object
     * @return  boolean found = true not found = false
     */
    public boolean ContainsValue( value )
    {

        for(LinkedList<Data> l : hashtable)
        {
            if(l != null )
            {
                def iter = l.iterator()

                while(iter.hasNext())
                {
                    if(iter.next().value.equals(value))
                        return true
                }
            }
        }

        return false

    }



    /**
     * Return true if a given value is present in
     * the hashtable and false if not
     *
     * @param value  Object
     * @return  int -1 if not in the table otherwise the traversal number
     *              it took to get there
     */
    public int CountTraversalsValue( value )
    {
        def DNE = 0

        for(LinkedList<Data> l : hashtable)
        {
            def traversal = 0

            if(l != null)
            {
                def iter = l.iterator()

                while(iter.hasNext())
                {
                    ++traversal

                    if(iter.next().value.equals(value))
                        return traversal
                }
                DNE += traversal
            }

        }

        return -DNE

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
     * Hashes a given key using:
     * 1) Default (Object).hashCode()
     * 2) Md5 hashfunction
     * and mods it against the number of buckets
     *
     *
     * @see #hashCode()
     * @see #CryptoHash(java.lang.Object)
     * @param key Object
     *
     * @return int   An index of the hashtable
     */
    public int GetIndex( key )
    {
      switch(function)
      {
          case 1:
              return key.hashCode()%buckets
          case 2:
              return CryptoHash( key )%buckets
      }
    }

    /**
     * Hashes a key using Md5 cryptographic
     * hash function.
     *
     * @param key Object
     * @return  BigInteger
     */
    public int CryptoHash( key )
    {
        md5.update( key.getBytes() )

        return new BigInteger(1, md5.digest())
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


    public void Clear()
    {
        for(LinkedList<Data> l : hashtable )
        {
            if( l != null && !l.empty)
            {
                l.clear()
            }

            l = null
        }

        size = 0
        collisions = 0
    }



    /**
     *  Main method used for a bit of testing
     *
     * @param args
     */
    public static void main(String[] args)
    {
    }

}
