import groovy.json.JsonSlurper;

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

        int found = 0;
        int notfound = 0;

        timeit("Count Number of Words Found in Dictionary",1,100,10)
        {
            found = 0;
            notfound = 0;

            for (String s : tale)
            {
                if (englishDictionaryObj.containsKey(s))
                    found++
                else
                    notfound++
            }

        }

        assert found == 74797
        assert notfound == 48754

        println "Found: " + found
        println "Not Found: " + notfound
    }
}
