import java.lang.*
import java.util.*

// FourTree is a 2-3-4 Tree using integer keys
class FourTree<K,V> {

    private def FourTreeNode<K,V> root;

    public static void main ( String [] args ) {

        FourTree tree1 = new FourTree<String,String>()
        FourTree tree2 = new FourTree<Comparable, String>()
        FourTree tree3 = new FourTree<String,String>()

        def engDict = EnglishDictionary.dictDataSet()
        def engBook = EnglishDictionary.bookStringList()

        tree1.insert( engDict )

        println "Number of elements: ${tree1.nodeCount()}"
        println "Number of nodes: ${tree1.elemCount()}"
        println "Height of tree: ${tree1.height()}"

        assert tree1.find( "PHREATIC" )
        assert tree1.find( "ILLECEBRATION" )
        assert tree1.find( "KNOWN" )
        assert !tree1.find( "BLARGLEFARGLE" )
        assert !tree1.find( "HOOTENANNY" )
        assert !tree1.find( "COGITO ERGO SUM")

        new File( "FourTreeSearchResults.txt" ).withWriter { write ->
            engBook.each { write.writeLine( "$it: " + ( tree1.find( it ) ?: "NOT FOUND" ) )
            }
        }
        println "Results of search ATaleOfTwoCities -> EnglishDictionary written to file."

        def ct1 = 0
        def ct2 = 0
        engBook.each {
            if( tree1.find( it ) ) {
                ++ct1
            }
            else {
                ++ct2
            }
        }

        println "Matches: $ct1"
        println "Misses: $ct2"
        println "Number of searches: " + engBook.size()

        assert engBook.size() == ct1+ct2

        def clos = { engBook.each { tree1.find(it) } }
        EnglishDictionary.timeit( "\nTime to search for all words: ", 1, 3, 3, clos )

    }

    public FourTree ( ) {
        root = null
    }

    /*
    * Split method divides a 3-node and pushes up the middle element.
    *
    * @param rt - FourTreeNode parent of node to split
    * @param chNum - Integer number of rt's child to be split
    */
    def private split( rt, chNum ) {
        def child = rt.getChild( chNum )
        def tempData = child.getData(2)
        def leftNode = new FourTreeNode<K,V>( child.getData(1) )
        def rightNode = new FourTreeNode<K,V>( child.getData(3) )
        leftNode.setChild( 1, child.getChild(1) )
        leftNode.setChild( 2, child.getChild(2) )
        rightNode.setChild( 1, child.getChild(3) )
        rightNode.setChild( 2, child.getChild(4) )

        def elems = rt.countElems()
        def dataKey = tempData.getKey()
        def nodeKey1 = rt.getKey(1)
        def nodeKey2 = rt.getKey(2)
        def nodeKey3 = rt.getKey(3)

        if ( elems == 1 && dataKey.compareTo( nodeKey1 ) < 0 ) {
            rt.setData( 2, rt.getData(1) )
            rt.setChild( 3, rt.getChild(2) )
            rt.setData( 1, tempData )
            rt.setChild( 1, leftNode )
            rt.setChild( 2, rightNode )
        }
        else if( elems == 1 && dataKey.compareTo( nodeKey1 ) >= 0 ) {
            rt.setData( 2, tempData )
            rt.setChild( 2, leftNode )
            rt.setChild( 3, rightNode )
        }
        else if( elems == 2 && dataKey.compareTo( nodeKey1 ) < 0 ) {
            rt.setData( 3, rt.getData(2) )
            rt.setChild( 4, rt.getChild(3) )
            rt.setChild( 3, rt.getChild(2) )
            rt.setData( 2, rt.getData(1) )
            rt.setData( 1, tempData )
            rt.setChild( 1, leftNode )
            rt.setChild( 2, rightNode )
        }
        else if( elems == 2 && dataKey.compareTo( nodeKey2 ) < 0 ) {
            rt.setData( 3, rt.getData(2) )
            rt.setChild( 4, rt.getChild(3) )
            rt.setData( 2, tempData )
            rt.setChild( 2, leftNode )
            rt.setChild( 3, rightNode )
        }
        else {
            rt.setData( 3, tempData )
            rt.setChild( 3, leftNode )
            rt.setChild( 4, rightNode )
        }
    }

    /*
    * Builds a Data object with key ==  k and value == v
    * Calls add() to insert that object into the tree.
    * Includes a special case for handling a split if the
    * root itself is a 3-node
    *
    * @param k Object
    * @param v Object
    */
    def insert ( k, v ) {

        Data<K,V> data = new Data<K,V>( k, v )

        if( root == null ) {
            return root = new FourTreeNode( data )
        }

        if ( root.countElems() > 2 ) {
            def leftNode = new FourTreeNode<K,V>( root.getData(1) )
            def rightNode = new FourTreeNode<K,V>( root.getData(3) )
            leftNode.setChild( 1, root.getChild(1) )
            leftNode.setChild( 2, root.getChild(2) )
            rightNode.setChild( 1, root.getChild(3) )
            rightNode.setChild( 2, root.getChild(4) )
            root = new FourTreeNode<K,V>( root.getData(2) )
            root.setChild( 1, leftNode )
            root.setChild( 2, rightNode )
        }

        add( data, root )

    }

    /*
    * Calls add() to insert a Data object into the tree.
    * Includes a special case for handling a split if the
    * root itself is a 3-node
    *
    * @param data Data
    */
    def insert ( Data<K,V> data ) {

        if( root == null ) {
            return root = new FourTreeNode( data )
        }

        if ( root.countElems() > 2 ) {
            def leftNode = new FourTreeNode<K,V>( root.getData(1) )
            def rightNode = new FourTreeNode<K,V>( root.getData(3) )
            leftNode.setChild( 1, root.getChild(1) )
            leftNode.setChild( 2, root.getChild(2) )
            rightNode.setChild( 1, root.getChild(3) )
            rightNode.setChild( 2, root.getChild(4) )
            root = new FourTreeNode<K,V>( root.getData(2) )
            root.setChild( 1, leftNode )
            root.setChild( 2, rightNode )
        }

        add( data, root )

    }

    /*
    * Iterates over the elements of the collection and adds them all to the tree
    * Note that if any elements of the Collection have identical keys only one
    * will be added to the tree. Which one will be added is unpredictable.
    *
    * @param dataSet Collection<Data>
    */
    def insert ( Collection<Data> dataSet ) {
        for ( i in dataSet ) {
            insert( i )
        }
    }

    /*
    * Adds the Data object passed to the tree in the appropriate place based on its key
    *
    * @param data Data
    * @param rt FourTreeNode
    */
    def private add ( data, rt ) {

        if( rt == null ) {
            return rt = new FourTreeNode( data )
        }

        def elems = rt.countElems()

        def dataKey = data.getKey()
        def nodeKey1 = rt.getKey(1)
        def nodeKey2 = rt.getKey(2)
        def nodeKey3 = rt.getKey(3)

        if( rt.isLeaf() ) {
            if( dataKey.compareTo( nodeKey1 ) == 0 ) {
                rt.setData( 1, data )
            }
            else if( elems == 2 && dataKey.compareTo( nodeKey2 ) == 0 ) {
                rt.setData( 2, data )
            }
            else if( elems == 1 && dataKey.compareTo( nodeKey1 ) < 0 ) {
                rt.setData( 2, rt.getData(1) )
                rt.setData( 1, data )
            }
            else if( elems == 1 && dataKey.compareTo( nodeKey1 ) > 0 ) {
                rt.setData( 2, data )
            }
            else if( elems == 2 && dataKey.compareTo( nodeKey1 ) < 0 ) {
                rt.setData( 3, rt.getData(2) )
                rt.setData( 2, rt.getData(1) )
                rt.setData( 1, data )
            }
            else if( elems == 2 && dataKey.compareTo( nodeKey2 ) < 0 ) {
                rt.setData( 3, rt.getData(2) )
                rt.setData( 2, data )
            }
            else {
                rt.setData( 3, data )
            }
        }

        else {
            if( dataKey.compareTo( nodeKey1 ) < 0 ) {
                def nextElems = rt.getChild(1).countElems()
                if( nextElems > 2 ) {
                    split( rt, 1 )
                    if( dataKey.compareTo( rt.getKey(1) ) < 0 ) {
                        add( data, rt.getChild(1) )
                    }
                    else {
                        add( data, rt.getChild(2) )
                    }
                    return
                }
                add( data, rt.getChild(1) )
            }
            else if( (!nodeKey2 && dataKey.compareTo( nodeKey1 ) >= 0) || (nodeKey2 && dataKey.compareTo( nodeKey2 ) < 0) ) {
                def nextElems = rt.getChild(2).countElems()
                if( nextElems > 2 ) {
                    split( rt, 2 )
                    if( dataKey.compareTo( rt.getKey(2) ) < 0 ) {
                        add( data, rt.getChild(2) )
                    }
                    else {
                        add( data, rt.getChild(3) )
                    }
                    return
                }
                add( data, rt.getChild(2) )
            }
            else if( (!nodeKey3 && dataKey.compareTo( nodeKey2 ) >= 0) || (nodeKey3 && dataKey.compareTo( nodeKey3 ) < 0) ) {
                def nextElems = rt.getChild(3).countElems()
                if( nextElems > 2 ) {
                    split( rt, 3 )
                    if( dataKey.compareTo( rt.getKey(3) ) < 0 ) {
                        add( data, rt.getChild(3) )
                    }
                    else {
                        add( data, rt.getChild(4) )
                    }
                    return
                }
                add( data, rt.getChild(3) )
            }
            else {
                def nextElems = rt.getChild(4).countElems()
                if( nextElems > 2 ) {
                    split( rt, 4 )
                }
                add( data, rt.getChild(4) )
            }
        }

    }

    /*
    * Calls search() to locate and return a Data object element of the tree by its key
    *
    * @param k Object
    * @return The value in the Data object with key matching parameter k, or null if no match found
    */
    def find ( k ) {

        search( k, root )

    }

    /*
    * Searches the tree for a Data object where Data.key matches parameter k
    * Returns the Data object on match. Returns null if no match found
    *
    * @param k Object
    * @param rt FourTreeNode
    * @return The Data object with key matching parameter k, or null if no match found
    */
    def private search ( k, rt ) {

        if( !rt ) {
            return null
        }

        def nodeKey1 = rt.getKey(1)
        def nodeKey2 = rt.getKey(2)
        def nodeKey3 = rt.getKey(3)

        if( k.compareTo( nodeKey1 ) < 0 ) {
            return search( k, rt.getChild(1) )
        }
        else if( k.compareTo( nodeKey1 ) == 0 ) {
            return rt.getValue(1)
        }
        else if( k.compareTo( nodeKey1 ) > 0 && ( !nodeKey2 || k.compareTo( nodeKey2 ) < 0 ) ) {
            return search( k, rt.getChild(2) )
        }
        else if( k.compareTo( nodeKey2 ) == 0 ) {
            return rt.getValue(2)
        }
        else if( k.compareTo( nodeKey2 ) > 0 && ( !nodeKey3 || k.compareTo( nodeKey3 ) < 0 ) ) {
            return search( k, rt.getChild(3) )
        }
        else if( k.compareTo( nodeKey3 ) == 0 ) {
            return rt.getValue(3)
        }
        else {
            return search( k, rt.getChild(4) )
        }

    }

    /*
    * Calls inOrderPrint()
    */
    def printInOrder() {

        inOrderPrint( root )

    }

    /*
    * Performs inorder traversal of the tree and prints the value field of all Data object elements
    *
    * @param rt FourTreeNode
    */
    def private inOrderPrint( rt ) {
        if( !rt ) {
            return
        }
        inOrderPrint( rt.getChild(1) )
        if( rt.getValue(1) ) {
            println rt.getValue(1)
        }
        inOrderPrint( rt.getChild(2) )
        if( rt.getValue(2) ) {
            println rt.getValue(2)
        }
        inOrderPrint( rt.getChild(3) )
        if( rt.getValue(3) ) {
            println rt.getValue(3)
        }
        inOrderPrint( rt.getChild(4) )
    }

    /*
    * Calls elemCount()
    *
    * @return - The number of elements in the tree
    */
    def elemCount() {

        countElems( root )

    }

    /*
    * Traverses the tree and counts the number of elements
    *
    * @return - The number of elements in the tree
    */
    def private countElems( rt ) {
        if( !rt ) {
            return 0
        }
        countElems( rt.getChild(1) ) +
        countElems( rt.getChild(2) ) +
        countElems( rt.getChild(3) ) +
        countElems( rt.getChild(4) ) +
        rt.countElems()
    }

    /*
    * Calls countNodes and returns the number of nodes in the tree
    *
    * @return The number of nodes in the tree
    */
    def nodeCount() {

        countNodes( root )

    }

    /*
    * Performs node traversal and tallies each one
    *
    * @param rt FourTreeNode - The root of the tree
    * @return The number of nodes in the tree
    */
    def private countNodes( rt ) {
        if( !rt ) {
            return 0
        }
        countNodes( rt.getChild(1) ) +
        countNodes( rt.getChild(2) ) +
        countNodes( rt.getChild(3) ) +
        countNodes( rt.getChild(4) ) + 1
    }

    /*
    * Calls getHeight and returns the height of the tree
    *
    * @return The height of the tree
    */
    def height() {

        getHeight( root )

    }

    /*
    * Performs a traversal and returns the height of the tree
    *
    * @return The number of nodes in the tree
    */
    def private getHeight( rt ) {
        if( !rt ) {
            return 0
        }
        def leftMax = Math.max( getHeight(rt.getChild(1)), getHeight(rt.getChild(2)) )
        def rightMax = Math.max( getHeight(rt.getChild(3)), getHeight(rt.getChild(4)) )
        Math.max( leftMax, rightMax ) + 1
    }

}
