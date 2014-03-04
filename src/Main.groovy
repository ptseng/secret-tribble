

class Main {

    def static engDict = EnglishDictionary.dictDataSet()
    def static engBook = EnglishDictionary.bookStringList()
    def static realCityCoordinates = WorldCities.importWorldCitiesAsDataSet()
    def static randomCoordinates = WorldCities.generateRandomCoordinates( 100000 )
    def static randMap = RandomStringGen.generateDataSetFromSet( 20, true, true, true, 100000 )
    def static randList = RandomStringGen.generateStringList( 20, true, true, true, 100000 )

    def static testFourTree( message ) {

        def tree1 = new FourTree<String,String>()
        def tree2 = new FourTree<WorldCities.Coordinates,String>()
        def tree3 = new FourTree<String,String>()

        println message
        fourTreeSuite( tree1, engDict, engBook, "Dictionary", "Book" )
        println()
        fourTreeSuite( tree1, engDict, randList, "Dictionary", "Random" )
        println()
        fourTreeSuite( tree2, realCityCoordinates, randomCoordinates, "GPS", "Random" )
        println()
        fourTreeSuite( tree3, randMap, engBook, "Random", "Book" )
        println()
        fourTreeSuite( tree3, randMap, randList, "Random", "Random" )
        println()
    }

    def private static fourTreeSuite( tree, data, terms, name1, name2 ) {

        tree.clearTree()
        tree.insert( data )
        def inputSize = terms.size()
        def rates = treeHitRate( tree, terms, inputSize )
        treePrintResults( "Results for ${name1} Tree with ${name2} Queries:", rates, inputSize )

        def pass = 0
        def treeClosure = { terms.each { tree.find(it) } }
        5.times {
            ++pass
            tree.clearTree()
            tree.insert( data )
            DataGen.timeit( "\tTime for pass ${pass}", 1, 5, 1, treeClosure )
        }

    }

    def private static treeHitRate( tree, input, Integer inputSize ) {

        def ct1 = 0
        def ct2 = 0
        def ops = 0
        def opsCount = new ArrayList<Integer>( inputSize )

        input.each {
            if( tree.find( it ) ) {
                ++ct1
            }
            else {
                ++ct2
            }
            ops = tree.getOps()
            opsCount.add( ops )
        }
        opsCount = opsCount.sort{ it.intValue() }
        def max = opsCount.last()
        def min = opsCount.first()
        def sum = 0
        opsCount.each{ sum = sum + it }
        def avg = sum / opsCount.size()

        def counts = new Tuple( ct1, ct2, min, max, avg )

        counts
    }

    def private static treePrintResults( message, rates, inputSize ) {
        println message
        println "\tNumber of searches: ${inputSize}"
        println "\tNumber of hits:     ${rates.get(0)}"
        println "\tNumber of misses:   ${rates.get(1)}"
        println "\tBest case ops:      ${rates.get(2)}"
        println "\tAverage case ops:   ${rates.get(4) as Integer}"
        println "\tWorst case ops:     ${rates.get(3)}"
    }

    public static void main( String [] args ) {

        testFourTree( "-------- 2-3-4 TREE TESTING --------" )
        println()

    }

}
