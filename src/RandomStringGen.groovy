import org.apache.commons.lang.*

class RandomStringGen {

    def static numGen = new Random( System.currentTimeMillis() )

    /*
    * Generates a fixed or random-length string of alphanumeric characters
    *
    * @param length Integer - Length of string
    * @param letters Boolean - True = Include alphabetic characters
    * @param numbers Boolean - True = Include numeric characters
    * @param randomSize Boolean - True = Make string random length with max of first parameter
    */
    def static generateString( length, letters, numbers, randomSize ) {

        def randString = RandomStringUtils.random( length, letters, numbers )

        if ( randomSize ) {
            def upperBound = Math.min( Math.abs(numGen.nextInt()) % length + 1, length - 1 )
            randString = randString.substring ( 0, upperBound )
        }

        randString

    }

    /*
    * Generates an ArrayList of fixed or random-length strings of alphanumeric characters
    *
    * @param length Integer - Length of string
    * @param letters Boolean - True = Include alphabetic characters
    * @param numbers Boolean - True = Include numeric characters
    * @param randomSize Boolean - True = Make string random length with max of first parameter
    * @param size Integer - Size of list to generate
    */
    def static generateStringList( length, letters, numbers, randomSize, size ) {

        def randList = new ArrayList<String>()

        for ( i in 0..size ) {
            def insertString = generateString( length, letters, numbers, randomSize )
            randList.add insertString
        }

        randList

    }

    /*
    * Generates a HashSet of fixed or random-length strings of alphanumeric characters
    * Size of set <= parameter size since sets disallow duplicates
    *
    * @param length Integer - Length of string
    * @param letters Boolean - True = Include alphabetic characters
    * @param numbers Boolean - True = Include numeric characters
    * @param randomSize Boolean - True = Make string random length with max of first parameter
    * @param size Integer - Size of list to generate
    */
    def static generateStringSet( length, letters, numbers, randomSize, size ) {

        def randSet = new HashSet<String>()

        def i = 0
        while ( i < size ) {
            def insertString = generateString( length, letters, numbers, randomSize )
            if ( randSet.add( insertString ) ) {
                ++i
            }
        }

        randSet

    }

    /*
    * Generates a HashMap containing fixed or random-length strings of alphanumeric
    * characters as both key and value entries.
    * Size of map <= parameter size since duplicate keys may be generated and are
    * not allowed in maps
    *
    * @param length Integer - Length of string
    * @param letters Boolean - True = Include alphabetic characters
    * @param numbers Boolean - True = Include numeric characters
    * @param randomSize Boolean - True = Make string random length with max of first parameter
    * @param size Integer - Size of list to generate
    */
    def static generateMap( length, letters, numbers, randomSize, size ) {

        def randMap = new HashMap<String,String>()
        def randList = generateStringList( length, letters, numbers, randomSize, size * 2 )

        for ( i in (0..size).step(2) ) {
            def stringA = randList.get( i )
            def stringB = randList.get( i + 1 )
            randMap.put stringA, stringB
        }

        randMap

    }

    /*
    * Generates a HashSet of Data objects containing fixed or random-length strings of alphanumeric
    * characters as both key and value entries.
    * Number of Data elements <= to parameter size due to possibility of duplicates
    *
    * @param length Integer - Length of string
    * @param letters Boolean - True = Include alphabetic characters
    * @param numbers Boolean - True = Include numeric characters
    * @param randomSize Boolean - True = Make string random length with max of first parameter
    * @param size Integer - Size of list to generate
    */
    def static generateDataSetFromMap( length, letters, numbers, randomSize, size ) {

        def dataSet = new HashSet<Data<String,String>>()
        def randMap = generateMap( length, letters, numbers, randomSize, size )

        randMap.each { key, value -> def data = new Data<String,String>(key, value); dataSet.add( data ) }

        dataSet

    }

    /*
    * Generates an ArrayList of Data objects containing fixed or random-length strings of alphanumeric
    * characters as both key and value entries.
    * Number of Data elements == parameter size - 1
    *
    * @param length Integer - Length of string
    * @param letters Boolean - True = Include alphabetic characters
    * @param numbers Boolean - True = Include numeric characters
    * @param randomSize Boolean - True = Make string random length with max of first parameter
    * @param size Integer - Size of list to generate
    */
    def static generateDataSetFromSet( length, letters, numbers, randomSize, size ) {

        def setSize = size * 2
        def dataSet = new HashSet<Data<String,String>>()
        def randSet = generateStringSet( length, letters, numbers, randomSize, setSize ).toList()

        for ( i in 0..setSize - 2 ) {
            def stringA = randSet.get( i )
            def stringB = randSet.get( i + 1 )
            def data = new Data<String,String>( stringA, stringB )
            dataSet.add data
        }

        dataSet

    }

    public static void main( String [] args ) {

        assert generateStringSet( 20, true, true, true, 100000 ).size() == 100000

        def testDataMap1 = generateDataSetFromMap( 20, true, true, true, 50 )
        def testDataMap2 = generateDataSetFromSet( 20, true, true, true, 50 )

        println "Set 1:"
        testDataMap1.each() {
            println "KEY:   ${it.getKey()}"
            println "VALUE: ${it.getValue()}"
        }

        println "Set 2"
        testDataMap2.each() {
            println "KEY:   ${it.getKey()}"
            println "VALUE: ${it.getValue()}"
        }

        println testDataMap1.size()
        println testDataMap2.size()

    }

}
