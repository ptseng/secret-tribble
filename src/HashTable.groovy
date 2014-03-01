import java.lang.*
import java.util.*
import java.security.*

/**
 * Created by sforgie on 2/16/14.
 */
public class HashTable<K,V> extends DataGen
{

    private LinkedList<Data<K,V>> [] hashtable

    /**
     * Number of unique values held in the hashtable
     */
    private int size

    /**
     * Default number of buckets
     */
    private static int buckets = 5381

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
    private static int function = 1

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
            def iter = hashtable[index].iterator()

            while(iter.hasNext())
            {
                //The key already exists in the table
                /**
                 * TODO Return something meaningful when entering duplicate key
                 */
                  if(key.compareTo(iter.next().key) == 0)
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
                //if(iter.next().matchKey(key))
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

        if(l != null) // && l.head().value.equals(value))
        {
            def iter = l.iterator()

            while(iter.hasNext())
            {
                ++traversal

                //if(iter.next().matchKey(key))
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
            if(l != null )  //&& l.head().value.equals(value))
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




    /**
     *  Main method used for a bit of testing
     *
     * @param args
     */
    public static void main(String[] args)
    {

        def dictionary = EnglishDictionary.import1913EnglishDictionary() as HashMap<String, String>


        def ht = new HashTable<String, String>(2)


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

        timeit ("Timing test")
        {
            assert ht.ContainsKey("AITCH")
            assert ht.ContainsKey("COMPUTER")
            assert ht.ContainsKey("FARCE")
            assert ht.ContainsKey("ZEBRA")
            assert !ht.ContainsKey("TWERKING")      // Twerking did not exist in 1913

            //assert ht.get("VAMPIRE") == "Either one of two or more species of South American blood-sucking bats belonging to the genera Desmodus and Diphylla. Thesebats are destitute of molar teeth, but have strong, sharp cuttingincisors with which they make punctured wounds from which they suckthe blood of horses, cattle, and other animals, as well as man,chiefly during sleep. They have a cæcal appendage to the stomach, inwhich the blood with which they gorge themselves is stored."
        }





        println("Traversals required for 'AITCH': " + ht.CountTraversalsKey("AITCH") + "\n")

       assert dictionary.size() != 0
       timeit ("Some data to compare against")
       {
            assert dictionary.containsKey("AITCH")
            assert dictionary.containsKey("COMPUTER")
            assert dictionary.containsKey("FARCE")
            assert dictionary.containsKey("ZEBRA")
            assert !dictionary.containsKey("TWERKING")
            //assert dictionary.get("VAMPIRE") == "Either one of two or more species of South American blood-sucking bats belonging to the genera Desmodus and Diphylla. Thesebats are destitute of molar teeth, but have strong, sharp cuttingincisors with which they make punctured wounds from which they suckthe blood of horses, cattle, and other animals, as well as man,chiefly during sleep. They have a cæcal appendage to the stomach, inwhich the blood with which they gorge themselves is stored."
       }


        timeit("\nTiming value search (hashtable): ", 5, 5, 5)
        {
            assert ht.ContainsValue("Either one of two or more species of South American blood-sucking bats belonging to the genera Desmodus and Diphylla. Thesebats are destitute of molar teeth, but have strong, sharp cuttingincisors with which they make punctured wounds from which they suckthe blood of horses, cattle, and other animals, as well as man,chiefly during sleep. They have a cæcal appendage to the stomach, inwhich the blood with which they gorge themselves is stored.") != -1;
        }


        timeit("\nTiming value search (dictionary): ", 5, 5, 5)
        {
            assert dictionary.containsValue("Either one of two or more species of South American blood-sucking bats belonging to the genera Desmodus and Diphylla. Thesebats are destitute of molar teeth, but have strong, sharp cuttingincisors with which they make punctured wounds from which they suckthe blood of horses, cattle, and other animals, as well as man,chiefly during sleep. They have a cæcal appendage to the stomach, inwhich the blood with which they gorge themselves is stored.")
        }


        /*
            Code below compares searches for words in A Tale of Two Cities
            dictionary (modeled as a HashMap) is assumed to be correct
            someone smarter than me wrote it.
            HashTable is my method that I am testing against to verify correct
            values have been found.
         */


        def taleoftwocities = EnglishDictionary.importATaleOfTwoCities() as ArrayList<String>
        def found = 0
        def missed = 0


        //timeit("\nTime to search for words from A Tale of Two Cities(HashTable): ")
        //{
            taleoftwocities.each
            {

                if(ht.ContainsKey(it))
                {
                    found++
                }
                else missed++
            }

        //}

        println "Matches: $found"
        println "Misses: $missed"


        def foundD = 0
        def missedD = 0

        taleoftwocities.each
        {
            if(dictionary.containsKey(it))
            {
                foundD++
            }
            else missedD++
        }


        println "Matches: $foundD"
        println "Misses: $missedD"

        assert foundD + missedD == taleoftwocities.size();
        assert found == foundD
        assert missed == missedD

        def traversals = ht.CountTraversalsKey("AITCH")
        println "Traversals for 'AITCH': $traversals"

        traversals = ht.CountTraversalsKey("TWERK")
        println "\nTraversals for 'TWERK': $traversals"

        traversals = ht.CountTraversalsValue("Probably not in the dictionary")
        println "\nTraversals for value not in the dictionary: $traversals"


        def min = 0
        def max = 0
        taleoftwocities.each
        {
            def t = ht.CountTraversalsKey(it)

            if(t > max)
            {
                max = t
            }
            else
            {
                if(t < min)
                {
                    min = t
                }
            }
        }

        println "Most traversals when key found: $max"
        println "Most traversals when key not found: $min"



        def test = new HashTable(1,1)

        assert test.CountTraversalsKey("A") == 0
        assert test.CountTraversalsValue("TRUCK") == 0

        test.Insert("A","Phil")
        test.Insert("C","Shawn")
        test.Insert("B","Matt")

        assert test.CountTraversalsKey("A") == 3
        assert test.CountTraversalsKey("C") == 2
        assert test.CountTraversalsKey("B") == 1
        assert test.CountTraversalsKey("PH") == -3


        assert test.CountTraversalsValue("Phil") == 3
        assert test.CountTraversalsValue("Shawn") == 2
        assert test.CountTraversalsValue("Matt") == 1
        assert test.CountTraversalsValue("Truck") == -3





        def cities =  WorldCities.importWorldCities() as HashMap<WorldCities.Coordinates, String>
        def citiesTest = new HashTable<WorldCities.Coordinates, String>(3, 131)

        cities.each{ citiesTest.Insert(it.key, it.value); if(it.value == "Portland") println it.key.hashCode(); }

        assert citiesTest.Size() == cities.size()
        assert citiesTest.Size() == 661
        assert citiesTest.ContainsValue("Portland")
        assert citiesTest.ContainsValue("London")
        assert citiesTest.ContainsValue("Beijing")

        def portland = new WorldCities.Coordinates(lat:45.517F, lon:-122.667F)
        def testcity = new WorldCities.Coordinates(lat: 45.517F, lon:-133.123F)
        def longyearbyennew = new WorldCities.Coordinates(lat: 78.217F, lon:15.55F)

        citiesTest.Insert(testcity, "TestCity")


        timeit("Testing Lookup in Cities Hashtable")
        {
            assert citiesTest.ContainsKey(testcity)
            assert citiesTest.ContainsKey(longyearbyennew)
            assert citiesTest.ContainsKey(portland)

        }


        println "\nTraversals to find testcity: " + citiesTest.CountTraversalsKey(testcity)
        println "\nTraversals to find longyearbyennew: " + citiesTest.CountTraversalsKey(longyearbyennew)
        println "\nTraversals to find portland: " + citiesTest.CountTraversalsKey(portland)

    }

}
