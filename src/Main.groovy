

class Main {

    def static engDict = EnglishDictionary.dictDataSet()
    def static engBook = EnglishDictionary.bookStringList()
    def static realCityCoordinates = WorldCities.importWorldCitiesAsDataSet()
    def static randomCoordinates = WorldCities.generateRandomCoordinates( 100000 )
    def static randMap = RandomStringGen.generateDataSetFromSet( 8, true, false, true, 100000 )
    def static randList = RandomStringGen.generateStringList( 8, true, false, true, 100000 )

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
        printResults( "Results for ${name1} Tree with ${name2} Queries:", rates, inputSize )

        def pass = 0
        def treeClosure = { terms.each { tree.find(it) } }
        5.times {
            ++pass
            tree.clearTree()
            tree.insert( data )
            DataGen.timeit( "\tTime for pass ${pass}", 1, 5, 3, treeClosure )
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

    def private static printResults( message, rates, inputSize ) {
        println message
        println "\tNumber of searches: ${inputSize}"
        println "\tNumber of hits:     ${rates.get(0)}"
        println "\tNumber of misses:   ${rates.get(1)}"
        println "\tBest case ops:      ${rates.get(2)}"
        println "\tAverage case ops:   ${rates.get(4) as Integer}"
        println "\tWorst case ops:     ${rates.get(3)}"
    }

    def static testHashTable( message ) {

        def ht1 = new HashTable<String,String>( 2 )
        def ht2 = new HashTable<WorldCities.Coordinates,String>( 2 )
        def ht3 = new HashTable<String,String>( 2 )

        println message
        hashTableSuite( ht1, engDict, engBook, "Dictionary", "Book" )
        println()
        hashTableSuite( ht1, engDict, randList, "Dictionary", "Random" )
        println()
        hashTableSuite( ht2, realCityCoordinates, randomCoordinates, "GPS", "Random" )
        println()
        hashTableSuite( ht3, randMap, engBook, "Random", "Book" )
        println()
        hashTableSuite( ht3, randMap, randList, "Random", "Random" )
        println()

    }

    def static hashTableSuite( ht, data, terms, name1, name2 ) {

        //ht.Clear()
        data.each { ht.Insert( it.getKey(), it.getValue() ) }
        def inputSize = terms.size()
        def rates = hashTableHitRate( ht, terms, inputSize )
        printResults( "Results for ${name1} Hash Table with ${name2} Queries:", rates, inputSize )
/*
        def pass = 0
        def hashClosure = { terms.each { ht.ContainsKey(it) } }
        1.times {
            ++pass
            //ht.Clear()
            data.each { ht.Insert( it.getKey(), it.getValue() ) }
            DataGen.timeit( "\tTime for pass ${pass}", 1, 5, 1, hashClosure )
        }*/
    }

    def static hashTableHitRate( ht, input, Integer inputSize ) {

        def ct1 = 0
        def ct2 = 0
        def ops = 0
        def opsCount = new ArrayList<Integer>( inputSize )

        input.each {
            def Integer t = ht.CountTraversalsKey( it )
            if( t >= 0 ) {
                ++ct1
            }
            else {
                ++ct2
            }
            ops = Math.abs( t )
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

    public static void main( String [] args ) {

        //testFourTree( "-------- 2-3-4 TREE TESTING --------" )
        //println()
        testHashTable( "-------- HASH TABLE TESTING --------" )
        println()

    }

}
