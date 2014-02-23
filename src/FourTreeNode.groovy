

// Node for 2-3-4 tree in which keys are Data object
class FourTreeNode<K,V> {

    private def Data<K,V> key1, key2, key3;
    private def FourTreeNode<K,V> left, leftmid, rightmid, right;

    public static void main ( String [] args ) {

    }

    public FourTreeNode ( Data<K,V> k ) {
        key1 = k
        key2 = null
        key3 = null
        left = null
        leftmid = null
        rightmid = null
        right = null
    }

    public FourTreeNode ( k1 = null, k2 = null, k3 = null, l = null, lm = null, rm = null, r = null ) {
        key1 = k1
        key2 = k2
        key3 = k3
        left = l
        leftmid = lm
        rightmid = rm
        right = r
    }

    def getKey ( k ) {
        if( k == 1 && key1 ) {
            return key1.getKey()
        }
        else if( k == 2 && key2 ) {
            return key2.getKey()
        }
        else if( k == 3 && key3 ) {
            return key3.getKey()
        }
        else {
            return null
        }

        /*switch ( k ) {
            case 1: return key1.getKey()
            case 2: return key2.getKey()
            case 3: return key3.getKey()
            default: return null
        }*/
    }

    def getValue ( k ) {
        if( k == 1 && key1 ) {
            return key1.getValue()
        }
        else if( k == 2 && key2 ) {
            return key2.getValue()
        }
        else if( k == 3 && key3 ) {
            return key3.getValue()
        }
        else {
            return null
        }

        /*switch ( k ) {
            case 1: return key1.getValue()
            case 2: return key2.getValue()
            case 3: return key3.getValue()
            default: return null
        }*/
    }

    def getChild( c ) {
        switch ( c ) {
            case 1: return left
            case 2: return leftmid
            case 3: return rightmid
            case 4: return right
            default: return null
        }
    }

    def setChild( c, child ) {
        switch( c ) {
            case 1: return left = child
            case 2: return leftmid = child
            case 3: return rightmid = child
            case 4: return right = child
            default: return null
        }
    }

    def setData ( k, data ) {
        switch ( k ) {
            case 1: return key1 = data
            case 2: return key2 = data
            case 3: return key3 = data
            default: return
        }
    }

    def getData ( k ) {
        switch ( k ) {
            case 1: return key1
            case 2: return key2
            case 3: return key3
            default: return
        }
    }

    def isLeaf () {
        if ( left == null && leftmid == null && rightmid == null && right == null ) {
            return true
        }
        false
    }

    def countElems() {
        def ct = 0
        if ( key1 != null ) {
            ++ct
        }
        if ( key2 != null ) {
            ++ct
        }
        if ( key3 != null ) {
            ++ct
        }
        ct
    }

}
