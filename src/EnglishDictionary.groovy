import groovy.json.JsonSlurper

public class EnglishDictionary extends DataGen {

    public static Object import1913EnglishDictionary()
    {
        return new JsonSlurper().parseText(new File("dictionary.json").text)
    }

    public static Object importATaleOfTwoCities()
    {
        return (new File("ataleoftwocities.txt").text).replaceAll("[^a-zA-Z ]", "").toUpperCase().split("\\s+");
    }


    public static void main(String [] args)
    {

        timeit("Importing A Tale of Two Cities")
        {
            List<String> tale = importATaleOfTwoCities()
            assert tale.size() == 123551
            assert tale.contains("CITIES")
            assert !tale.contains("BIEBER") //This word was not in tale of two cities
        }

        timeit("Importing 1913 Dictionary into HashMap")
        {
            HashMap<String,String> englishDictionaryHashMap = import1913EnglishDictionary() as HashMap<String, String>

            assert englishDictionaryHashMap != null
            assert englishDictionaryHashMap.size() != 0
            assert englishDictionaryHashMap.size() == 86036
            assert englishDictionaryHashMap.containsKey("AITCH")
            assert englishDictionaryHashMap.containsKey("COMPUTER")
            assert englishDictionaryHashMap.containsKey("FARCE")
            assert englishDictionaryHashMap.containsKey("ZEBRA")
            assert englishDictionaryHashMap.get("VAMPIRE") == "Either one of two or more species of South American blood-sucking bats belonging to the genera Desmodus and Diphylla. Thesebats are destitute of molar teeth, but have strong, sharp cuttingincisors with which they make punctured wounds from which they suckthe blood of horses, cattle, and other animals, as well as man,chiefly during sleep. They have a cæcal appendage to the stomach, inwhich the blood with which they gorge themselves is stored."
            assert englishDictionaryHashMap.get("TWERKING") == null    // Twerking did not exist in 1913}
        }


        List<String> tale = importATaleOfTwoCities()
        Map englishDictionaryObj = (Map) import1913EnglishDictionary()

        SimpleBinaryTree<String, String> bst = new SimpleBinaryTree<String,String>()
        englishDictionaryObj.each {key, val ->
            bst.insert(key.toString(), val.toString())
        }

        assert bst.size() == englishDictionaryObj.size()
        assert bst.lookup("AITCH")
        assert bst.getTraversals("AITCH") == 16
        assert bst.getTraversals("ZEBRA") == 14
        assert bst.get("VAMPIRE") == "Either one of two or more species of South American blood-sucking bats belonging to the genera Desmodus and Diphylla. Thesebats are destitute of molar teeth, but have strong, sharp cuttingincisors with which they make punctured wounds from which they suckthe blood of horses, cattle, and other animals, as well as man,chiefly during sleep. They have a cæcal appendage to the stomach, inwhich the blood with which they gorge themselves is stored."
        assert !bst.get("TWERK")
        assert bst.getTraversals("TWERK") == 20
        assert bst.maxDepth()  == 44
        assert bst.minDepth()  == 4

        int found = 0
        int notfound = 0
        int traversals = 0

        timeit("Count Number of Words Found in Dictionary",1,1,1)
        {
            found = 0
            notfound = 0

            for (String s : tale)
            {
                if (bst.lookup(s))
                    found++
                else
                    notfound++

            }
        }

        assert found == 74797
        assert notfound == 48754

        timeit("Count Number of Traversals",1,1,1)
        {
            traversals = 0

            for (String s : tale)
            {
                traversals += bst.getTraversals(s)
            }

        }

        println()
        println "** Cross-Referencing Every Word in \"A Tale of Two Cities\" against the 1913 Webster Dictionary **"
        println()
        println "Definitions Found: " + found
        println "Not Found: " + notfound

        println()
        println "BST Max Depth: " + bst.maxDepth()
        println "BST Min Depth: " + bst.minDepth()
        println "Total Traversals To Find Definitions: " + traversals
        println "Average Traversals Per Word: " + (traversals/tale.size()).setScale(2,1)
    }
}
