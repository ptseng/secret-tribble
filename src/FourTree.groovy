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

        def data = new Data<String,String>("ggg","jee")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("xxx","exs")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("bbb","bee")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("fff","efffff")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("zzz","snore")
        tree1.insert(data)
        dataSet.add(data)
        data = new Data<String,String>("hhh","laugh at me")
        tree1.insert(data)
        dataSet.add(data)

        data = new Data<String,String>("ttt","scold")
        dataSet.add(data)
        data = new Data<String,String>("oooooo","awe")
        dataSet.add(data)
        data = new Data<String,String>("ttt","scold")
        dataSet.add(data)
        data = new Data<String,String>("nnnn","munch food")
        dataSet.add(data)
        tree3.insert(dataSet)

        /*Random randomVal = new Random()
        for( i in 0..100 ) {
            StringBuilder key = new StringBuilder()
            StringBuilder value = new StringBuilder()
            int x = randomVal.nextInt( (i+20 % 10)+1 )
            for( j in 0..x ) {
                key.append( (char) (j % 256 +1) )
            }
            int y = randomVal.nextInt( (i+30 % 10)+1 )
            for( k in 0..y ) {
                value.append( (char) (k % 256 +1) )
            }
            def data = new Data<String,String>( (String)key, (String)value )
            println data
            tree1.insert( data )
            dataSet.add( data )
        }*/

        tree1.printInOrder()
        println()
        tree3.printInOrder()
        println()
        println tree1.find("ggg")
        println()
        println tree3.find("hhh")

    }

    public FourTree ( ) {
        root = null
    }

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

    def insert ( Collection<Data> dataSet ) {
        for ( i in dataSet ) {
            insert( i )
        }
    }

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
            if ( elems == 1 && dataKey < nodeKey1 ) {
                rt.setData( 2, rt.getData(1) )
                rt.setData( 1, data )
            }
            else if( elems == 1 && dataKey >= nodeKey1 ) {
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
    def find ( k ) {

        search( k, root )

    }

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

    def printInOrder() {
        inOrderPrint( root )
    }

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

}
