import java.lang.*
import java.util.*
import DataGen

/**
 * Created by sforgie on 2/16/14.
 */
public class HashTable
{

    private LinkedList<Data> [] hashtable

    private int size

    private static int buckets = 5381

    private int collisions

    /**
     * Constructor for a Hashtable as an array of
     * Linked Lists
     */
    public HashTable()
    {
        hashtable  =  new  LinkedList<Data> [(buckets+1)]
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

        hashtable  =  new  LinkedList<Data> [(buckets+1)]
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
    public void Insert(Object key, Object value)
    {
        def d = new Data(key, value)

        int index = Hash(key)

        if(hashtable[index] == null)
        {
            hashtable[index] = new LinkedList<Data>()
        }
        else
        {
            def iter = hashtable[index].iterator()

            while(iter.hasNext())
            {
                //The key already exists in the table
                /**
                 * TODO Return something meaningful when entering duplicate key
                 */
                if(iter.next().matchKey(key))
                    return
            }

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
     * @return  true if key is in the HashTable
     */
    public boolean ContainsKey(Object key)
    {
        def index = Hash(key)

        if(hashtable[index] != null)
        {
            def iter = hashtable[index].iterator()

            while(iter.hasNext())
            {
                if(iter.next().matchKey(key))
                    return true
            }
        }

        return false
    }

    /**
     * Return true if a given value is present in
     * the hashtable and false if not
     *
     * @param value  Object
     * @return  boolean
     */
    public boolean ContainsValue(Object value)
    {
        for(LinkedList<Data> l : hashtable)
        {
            if(l != null && l.head().value.equals(value))
                return true
            def iter = l.iterator()

            while(iter.hasNext())
            {
                if(iter.next().value.equals(value))
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
     * Hashes a given key using Object.hashCode()
     * and mods it against the number of buckets
     *
     * This will be a basic test to compare
     * against with other models of hashing
     * functions
     *
     * @see #hashCode()
     * @param key Object
     *
     * @return int   An index of the hashtable
     */
    public int Hash(Object key)
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

        def dictionary = DataGen.import1913EnglishDictionary() as HashMap<String, String>

        def ht = new HashTable()


        assert ht.Size() == 0

        assert !ht.ContainsKey("A")

        assert !ht.ContainsValue("A")

        assert ht.NumberOfCollisions() == 0

        assert ht.IsEmpty()


        for(Object k : dictionary.keySet())
        {
            ht.Insert(k,dictionary.get(k))
        }

        assert ht.Size() == dictionary.size()





        println("Size: " + ht.Size())
        println("Collisions: " + ht.NumberOfCollisions())

        DataGen.timeit ("Timing test")
        {
            assert ht.ContainsKey("AITCH")
            assert ht.ContainsKey("COMPUTER")
            assert ht.ContainsKey("FARCE")
            assert ht.ContainsKey("ZEBRA")
            //assert ht.get("VAMPIRE") == "Either one of two or more species of South American blood-sucking bats belonging to the genera Desmodus and Diphylla. Thesebats are destitute of molar teeth, but have strong, sharp cuttingincisors with which they make punctured wounds from which they suckthe blood of horses, cattle, and other animals, as well as man,chiefly during sleep. They have a cæcal appendage to the stomach, inwhich the blood with which they gorge themselves is stored."
            //assert ht.get("TWERKING") == null    // Twerking did not exist in 1913}
        }


       assert dictionary.size() != 0
       DataGen.timeit ("Some data to compare against")
       {
            assert dictionary.containsKey("AITCH")
            assert dictionary.containsKey("COMPUTER")
            assert dictionary.containsKey("FARCE")
            assert dictionary.containsKey("ZEBRA")
            //assert dictionary.get("VAMPIRE") == "Either one of two or more species of South American blood-sucking bats belonging to the genera Desmodus and Diphylla. Thesebats are destitute of molar teeth, but have strong, sharp cuttingincisors with which they make punctured wounds from which they suckthe blood of horses, cattle, and other animals, as well as man,chiefly during sleep. They have a cæcal appendage to the stomach, inwhich the blood with which they gorge themselves is stored."
            //assert dictionary.get("TWERKING") == null    // Twerking did not exist in 1913}
       }

    }

}
