

class Main {

    def static engDict = EnglishDictionary.dictDataSet()
    def static engBook = EnglishDictionary.bookStringList()
    def static realCityCoordinates = WorldCities.importWorldCitiesAsDataSet()
    def static randomCoordinates = WorldCities.generateRandomCoordinates( 100000 )
    def static randMap = RandomStringGen.generateDataSetFromSet( 8, true, false, true, 100000 )
    def static randList = RandomStringGen.generateStringList( 8, true, false, true, 100000 )
    def static sortedEngDict = new ArrayList(engDict).sort( { it.getKey() } )
    def static sortedRandMap = new ArrayList(randMap).sort( { it.getKey() } )
    def static sortedRealCityCoordinates = new ArrayList(realCityCoordinates).sort( { it.getKey() } )

    // SimpleBinaryTree Testing
    def static testSimpleBinaryTree( message ) {

        def tree1 = new SimpleBinaryTree<String,String>()
        def tree2 = new SimpleBinaryTree<WorldCities.Coordinates,String>()
        def tree3 = new SimpleBinaryTree<String,String>()

        def smallSortedEngDict = new ArrayList(1000)
        for( i in 0..999 ) {
            smallSortedEngDict.add(sortedEngDict.get(i) )
        }
        def smallSortedRandMap = new ArrayList(1000)
        for( i in 0..999 ) {
            smallSortedRandMap.add(sortedRandMap.get(i) )
        }

        println message
        BSTTreeSuite( tree1, engDict, engBook, "Unsorted Dictionary", "Book" )
        println()
        BSTTreeSuite( tree1, smallSortedEngDict, engBook, "Small Sorted Dictionary", "Book" )
        println()
        BSTTreeSuite( tree1, engDict, randList, "Unsorted Dictionary", "Random" )
        println()
        BSTTreeSuite( tree1, smallSortedEngDict, randList, "Small Sorted Dictionary", "Random" )
        println()
        BSTTreeSuite( tree2, realCityCoordinates, randomCoordinates, "Unsorted GPS", "Random" )
        println()
        BSTTreeSuite( tree2, sortedRealCityCoordinates, randomCoordinates, "Sorted GPS", "Random" )
        println()
        BSTTreeSuite( tree3, randMap, engBook, "Unsorted Random", "Book" )
        println()
        BSTTreeSuite( tree1, smallSortedRandMap, engBook, "Small Sorted Random", "Book" )
        println()
        BSTTreeSuite( tree3, randMap, randList, "Unsorted Random", "Random" )
        println()
        BSTTreeSuite( tree1, smallSortedRandMap, randList, "Small Sorted Random", "Random" )
        println()

    }

    def private static BSTTreeSuite( tree, data, terms, name1, name2 ) {

        tree.clearTree()

        data.each{ tree.insert(it.key, it.value)}

        def inputSize = terms.size()
        def rates = BSTtreeHitRate( tree, terms, inputSize )
        treePrintResults( "Results for ${name1} Tree with ${name2} Queries:", rates, inputSize )

        def pass = 0
        def treeClosure = { terms.each { tree.lookup(it) } }
        5.times {
            ++pass
            tree.clearTree()
            data.each{ tree.insert(it.key, it.value)}
            DataGen.timeit( "\tTime for pass ${pass}", 1, 5, 3, treeClosure )
        }

    }

    def private static BSTtreeHitRate( tree, input, Integer inputSize ) {

        def ct1 = 0
        def ct2 = 0
        def ops = 0
        def opsCount = new ArrayList<Integer>( inputSize )

        input.each {
            if( tree.lookup( it ) ) {
                ++ct1
            }
            else {
                ++ct2
            }
            ops = tree.getTraversals( it )
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

    // FourTree Testing
    def static testFourTree( message ) {

        def tree1 = new FourTree<String,String>()
        def tree2 = new FourTree<WorldCities.Coordinates,String>()
        def tree3 = new FourTree<String,String>()

        println message
        fourTreeSuite( tree1, engDict, engBook, "Unsorted Dictionary", "Book" )
        println()
        fourTreeSuite( tree1, sortedEngDict, engBook, "Sorted Dictionary", "Book" )
        println()
        fourTreeSuite( tree1, engDict, randList, "Unsorted Dictionary", "Random" )
        println()
        fourTreeSuite( tree1, sortedEngDict, randList, "Sorted Dictionary", "Random" )
        println()
        fourTreeSuite( tree2, realCityCoordinates, randomCoordinates, "Unsorted GPS", "Random" )
        println()
        fourTreeSuite( tree2, sortedRealCityCoordinates, randomCoordinates, "Sorted GPS", "Random" )
        println()
        fourTreeSuite( tree3, randMap, engBook, "Unsorted Random", "Book" )
        println()
        fourTreeSuite( tree3, sortedRandMap, engBook, "Sorted Random", "Book" )
        println()
        fourTreeSuite( tree3, randMap, randList, "Unsorted Random", "Random" )
        println()
        fourTreeSuite( tree3, sortedRandMap, randList, "Sorted Random", "Random" )
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

    def private static treePrintResults( message, rates, inputSize ) {
        println message
        println "\tNumber of searches: ${inputSize}"
        println "\tNumber of hits:     ${rates.get(0)}"
        println "\tNumber of misses:   ${rates.get(1)}"
        println "\tBest case ops:      ${rates.get(2)}"
        println "\tAverage case ops:   ${rates.get(4) as Integer}"
        println "\tWorst case ops:     ${rates.get(3)}"
    }

    // Hash Testing
    def static TestHashTable( message )
    {
        def htdicthashcode = new HashTable<String, String>( 1, engDict.size()*2 )
        def htdictcrypt = new HashTable<String, String>( 2, engDict.size()*2 )
        def htcityhashcode = new HashTable<WorldCities.Coordinates, String>( 1, realCityCoordinates.size()*2 )
        def htcitycrypt = new HashTable<WorldCities.Coordinates, String>( 2, realCityCoordinates.size()*2 )
        def htrandhashcode = new HashTable<String, String>( 1, randMap.size()*2 )
        def htrandcrypt = new HashTable<String, String>( 2, randMap.size()*2 )

        println message

        HashTableTestSuite( htdicthashcode, htdictcrypt, engDict, engBook, "Dictionary", "Book" )
        println()
        HashTableTestSuite( htdicthashcode, htdictcrypt, sortedEngDict, engBook, "Sorted Dictionary", "Book" )
        println()
        HashTableTestSuite( htdicthashcode, htdictcrypt, engDict, randList, "Dictionary", "Random" )
        println()
        HashTableTestSuite( htdicthashcode, htdictcrypt, sortedEngDict, randList, "Sorted Dictionary", "Random" )
        println()
        HashTableTestSuite( htcityhashcode, htcitycrypt, realCityCoordinates, randomCoordinates, "GPS", "Random" )
        println()
        HashTableTestSuite( htcityhashcode, htcitycrypt, sortedRealCityCoordinates, randomCoordinates, "Sorted GPS", "Random" )
        println()
        HashTableTestSuite( htrandhashcode, htrandcrypt, randMap, engBook, "Random", "Book" )
        println()
        HashTableTestSuite( htrandhashcode, htrandcrypt, sortedRandMap, engBook, "Sorted Random", "Book" )
        println()
        HashTableTestSuite( htrandhashcode, htrandcrypt, randMap, randList, "Random", "Random" )
        println()
        HashTableTestSuite( htrandhashcode, htrandcrypt, sortedRandMap, randList, "Sorted Random", "Random" )
        println()

    }

    def private static HashTableTestSuite( HashTable tablehashcode, HashTable tablecrypt, data, terms, name1, name2 )
    {
        data.each { tablehashcode.Insert( it.key, it.value ) }


        def inputSize = terms.size()
        def rates = HashHitRate( tablehashcode, terms, inputSize )
        treePrintResults( "Results for ${name1} HashTable using .hashcode() with ${name2} Queries: ", rates, inputSize )
        println"\tCollisions:    ${ tablehashcode.NumberOfCollisions() }"


        def pass = 0
        def hashClosure = { terms.each { tablehashcode.ContainsKey( it ) } }
        5.times {
            ++pass
            tablehashcode.Clear()
            data.each { tablehashcode.Insert( it.key, it.value ) }
            DataGen.timeit( "\tTime for pass ${pass}", 1, 5, 3, hashClosure )
        }
        tablehashcode.Clear()

        data.each { tablecrypt.Insert( it.key, it.value ) }

        println()
        inputSize = terms.size()
        rates = HashHitRate( tablecrypt, terms, inputSize )
        treePrintResults( "Results for ${name1} HashTable using cryptographic hash with ${name2} Queries: ", rates, inputSize )
        println"\tCollisions:    ${ tablecrypt.NumberOfCollisions() }"

        pass = 0
        hashClosure = { terms.each { tablecrypt.ContainsKey( it ) } }
        5.times {
            ++pass
            tablecrypt.Clear()
            data.each { tablecrypt.Insert( it.key, it.value ) }
            DataGen.timeit( "\tTime for pass ${pass}", 1, 5, 3, hashClosure )
        }

        tablecrypt.Clear()

    }

    def private static HashHitRate( table, input, Integer inputSize )
    {
        def ct1 = 0
        def ct2 = 0
        def ops = 0
        def opsCount = new ArrayList<Integer>( inputSize )

        input.each {

            ops = table.CountTraversalsKey( it )

            if ( ops > 0)
            {
                ++ct1
                opsCount.add( ops )
            }
            else
            {
                if( ops < 0)
                {
                    opsCount.add( Math.abs( ops ) )
                }

                ++ct2
            }
        }

        opsCount = opsCount.sort{ it.intValue() }
        def max = opsCount.last()
        def min = opsCount.first()
        def sum = 0

        opsCount.each { sum += it }
        def avg = sum / opsCount.size()

        def counts = new Tuple ( ct1, ct2, min, max, avg )

        counts
    }

    public static void main( String [] args ) {

        testSimpleBinaryTree("-------- BST  TREE TESTING --------")
        println()
        testFourTree( "-------- 2-3-4 TREE TESTING --------" )
        println()
        TestHashTable("-------- HASH TABLE TESTING --------")
        println()

    }

}
