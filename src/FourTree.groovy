import java.lang.*
import java.util.*

// FourTree is a 2-3-4 Tree using integer keys
class FourTree<K,V> {

    private def FourTreeNode<K,V> root;

    public static void main ( String [] args ) {

        FourTree tree1 = new FourTree<String,String>()
        FourTree tree2 = new FourTree<Tuple, String>()
        FourTree tree3 = new FourTree<String,String>()

        def dataSet = new HashSet<Data<String,String>>()

        def data = new Data<String,String>("g","ggg")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("x","xxx")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("b","bbb")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("f","fff")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("z","zzz")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("h","hhh")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("t","ttt")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("s","sss")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("w","www")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("a","aaa")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("m","mmm")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("j","jjj")
        tree1.insert(data)
        dataSet.add(data)

        println "Tree1:"
        tree1.printInOrder()
        println()

        data = new Data<String,String>("o","000")
        dataSet.add(data)
        data = new Data<String,String>("t","REPLACED")
        dataSet.add(data)
        tree1.insert(data)
        data = new Data<String,String>("n","nnn")
        dataSet.add(data)

        tree3.insert(dataSet)

        println "HashSet:"
        dataSet.each{ print it.getValue() + " / " };
        println('\n')

        println "Tree3:"
        tree3.printInOrder()
        println()

        println "Tree1:"
        tree1.printInOrder()
        println()

        println "Search results on Tree1:"
        println tree1.find("g")
        println tree3.find("h")
        println()

        println "# of nodes in Tree1: " + tree1.nodeCount()
        println()

        println "# of elements in Tree1: " + tree1.countElems()
        println()

        println "Height of Tree1: " + tree1.height()
        println()

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
        if ( elems == 1 && dataKey < nodeKey1 ) {
            rt.setData( 2, rt.getData(1) )
            rt.setChild( 3, rt.getChild(2) )
            rt.setData( 1, tempData )
            rt.setChild( 1, leftNode )
            rt.setChild( 2, rightNode )
        }
        else if( elems == 1 && dataKey >= nodeKey1 ) {
            rt.setData( 2, tempData )
            rt.setChild( 2, leftNode )
            rt.setChild( 3, rightNode )
        }
        else if( elems == 2 && dataKey < nodeKey1 ) {
            rt.setData( 3, rt.getData(2) )
            rt.setChild( 4, rt.getChild(3) )
            rt.setChild( 3, rt.getChild(2) )
            rt.setData( 2, rt.getData(1) )
            rt.setData( 1, tempData )
            rt.setChild( 1, leftNode )
            rt.setChild( 2, rightNode )
        }
        else if( elems == 2 && dataKey < nodeKey2 ) {
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
            if( dataKey == nodeKey1 ) {
                rt.setData( 1, data )
            }
            else if( dataKey == nodeKey2 ) {
                rt.setData( 2, data )
            }
            else if ( elems == 1 && dataKey < nodeKey1 ) {
                rt.setData( 2, rt.getData(1) )
                rt.setData( 1, data )
            }
            else if( elems == 1 && dataKey > nodeKey1 ) {
                rt.setData( 2, data )
            }
            else if( elems == 2 && dataKey < nodeKey1 ) {
                rt.setData( 3, rt.getData(2) )
                rt.setData( 2, rt.getData(1) )
                rt.setData( 1, data )
            }
            else if( elems == 2 && dataKey < nodeKey2 ) {
                rt.setData( 3, rt.getData(2) )
                rt.setData( 2, data )
            }
            else {
                rt.setData( 3, data )
            }
        }

        else {
            if( dataKey < nodeKey1 ) {
                def nextElems = rt.getChild(1).countElems()
                if( nextElems > 2 ) {
                    split( rt, 1 )
                }
                add( data, rt.getChild(1) )
            }
            else if( (!nodeKey2 && dataKey >= nodeKey1) || (nodeKey2 && dataKey < nodeKey2) ) {
                def nextElems = rt.getChild(2).countElems()
                if( nextElems > 2 ) {
                    split( rt, 2 )
                }
                add( data, rt.getChild(2) )
            }
            else if( (!nodeKey3 && dataKey >= nodeKey2) || (nodeKey3 && dataKey < nodeKey3) ) {
                def nextElems = rt.getChild(3).countElems()
                if( nextElems > 2 ) {
                    split( rt, 3 )
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
    * @return The Data object with key matching parameter k, or null if no match found
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

        if( k < nodeKey1 ) {
            return search( k, rt.getChild(1) )
        }
        else if( k == nodeKey1 ) {
            return rt.getValue(1)
        }
        else if( k > nodeKey1 && ( !nodeKey2 || k < nodeKey2 ) ) {
            return search( k, rt.getChild(2) )
        }
        else if( k == nodeKey2 ) {
            return rt.getValue(2)
        }
        else if( k > nodeKey2 && ( !nodeKey3 || k < nodeKey3 ) ) {
            return search( k, rt.getChild(3) )
        }
        else if( k == nodeKey3 ) {
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
    def countElems() {

        elemCount( root )

    }

    /*
    * Traverses the tree and counts the number of elements
    *
    * @return - The number of elements in the tree
    */
    def private elemCount( rt ) {
        if( !rt ) {
            return 0
        }
        elemCount( rt.getChild(1) ) +
        elemCount( rt.getChild(2) ) +
        elemCount( rt.getChild(3) ) +
        elemCount( rt.getChild(4) ) +
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
