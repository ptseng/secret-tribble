

class Main {

    def static engDict = EnglishDictionary.dictDataSet()
    def static engBook = EnglishDictionary.bookStringList()
    def static randMap = RandomStringGen.generateDataSet( 40, true, true, true, 100000 )
    def static randList = RandomStringGen.generateStringList( 40, true, true, true, 100000 )

    def static testFourTree( message ) {

        def tree1 = new FourTree<String,String>()
        def tree2 = new FourTree<WorldCities.Coordinates,String>()
        def tree3 = new FourTree<String,String>()

        println message
        fourTreeSuite( tree1, engDict, engBook, "Dictionary", "Book" )
        println()
        fourTreeSuite( tree1, engDict, randList, "Dictionary", "Random" )
        println()
        fourTreeSuite( tree1, randMap, engBook, "Random", "Book" )
        println()
        fourTreeSuite( tree3, randMap, randList, "Random", "Random" )
        println()
    }

    def private static fourTreeSuite( tree, data, terms, name1, name2 ) {

        tree.insert( data )
        def rates = treeHitRate( tree, terms )
        def inputSize = terms.size()
        treePrintResults( "Results for ${name1} Tree with ${name2} Queries:", rates, inputSize )
        def treeClosure = { terms.each { tree.find(it) } }
        DataGen.timeit( "\tTime to search for all words: ", 1, 3, 3, treeClosure )

    }

    def private static treeHitRate( tree, input ) {

        def ct1 = 0
        def ct2 = 0

        input.each {
            if( tree.find( it ) ) {
                ++ct1
            }
            else {
                ++ct2
            }
        }
        def counts = new Tuple( ct1, ct2 )

        counts
    }

    def private static treePrintResults( message, rates, inputSize ) {
        println message
        println "\tNumber of searches: " + inputSize
        println "\tHits:   ${rates.get(0)}"
        println "\tMisses: ${rates.get(1)}"
    }

    public static void main( String [] args ) {

        testFourTree( "-------- 2-3-4 TREE TESTING --------" )
        println()

    }

}
