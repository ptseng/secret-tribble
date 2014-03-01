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
    * Generates a HashMap containing fixed or random-length strings of alphanumeric
    * characters as both key and value entries
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
    * Generates an ArrayList of Data objects containing fixed or random-length strings of alphanumeric
    * characters as both key and value entries
    *
    * @param length Integer - Length of string
    * @param letters Boolean - True = Include alphabetic characters
    * @param numbers Boolean - True = Include numeric characters
    * @param randomSize Boolean - True = Make string random length with max of first parameter
    * @param size Integer - Size of list to generate
    */
    def static generateDataSet( length, letters, numbers, randomSize, size ) {

        def dataSet = new HashSet<Data<String,String>>()
        def randMap = generateMap( length, letters, numbers, randomSize, size )

        randMap.each { key, value -> def data = new Data<String,String>(key, value); dataSet.add( data ) }

        dataSet

    }

    public static void main( String [] args ) {

        def testDataMap = generateDataSet( 20, true, true, true, 50 )

        testDataMap.each() {
            println "KEY:   ${it.getKey()}"
            println "VALUE: ${it.getValue()}"
        }

    }

}
