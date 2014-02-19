

// Node for 2-3-4 tree in which keys are Data object
class FourTreeNode<K,V> {

    private def Data<K,V> key1, key2, key3;
    private def FourTreeNode<K,V> left, leftmid, rightmid, right;

    public static void main ( String [] args ) {

    }

    public FourTreeNode ( Data<K,V> k ) {
        key2 = k
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
        switch ( k ) {
            case 1: return key1.getKey()
            case 2: return key2.getKey()
            case 3: return key3.getKey()
            default: return null
        }
    }

    def getValue ( k ) {
        switch ( k ) {
            case 1: return key1.getValue()
            case 2: return key2.getValue()
            case 3: return key3.getValue()
            default: return null
        }
    }

    def setKeyValue ( k, key ) {
        switch ( k ) {
            case 1: return key1.setKey( key )
            case 2: return key2.setKey( key )
            case 3: return key3.setKey( key )
            default: return
        }
    }

    def setValue ( k, value ) {
        switch ( k ) {
            case 1: return key1.setValue( value )
            case 2: return key2.setValue( value )
            case 3: return key3.setValue( value )
            default: return
        }
    }

    def setData ( k, d ) {
        switch ( k ) {
            case 1: return key1 = d
            case 2: return key2 = d
            case 3: return key3 = d
            default: return
        }
    }

    def isLeaf () {
        if ( left == null && leftmid == null && rightmid == null && right == null ) {
            return true
        }
        false
    }

}
