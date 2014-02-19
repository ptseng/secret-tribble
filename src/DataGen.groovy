import groovy.json.JsonSlurper;

class DataGen {

    public static Object import1913EnglishDictionary()
    {
        return new JsonSlurper().parseText(new File("dictionary.json").text)
    }

    public static void main(String [] args)
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
        assert englishDictionaryHashMap.get("TWERKING") == null    // Twerking did not exist in 1913

        Map englishDictionaryObj = (Map) import1913EnglishDictionary()

        println englishDictionaryObj.get("NEWSY")

        Hashtable sample = new Hashtable();

        englishDictionaryObj.each {
            key, val ->
                sample.put(key, val)
        }

        assert englishDictionaryHashMap.size() == sample.size()

    }

}
