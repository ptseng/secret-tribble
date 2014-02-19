

// key/value pair for record storage
class Data<K,V> {

    private def K key;
    private def V value;

    public static void main ( String [] args ) {

    }

    public Data ( k = null, v = null ) {
        key = k
        value = v
    }

    def setKey ( k ) {
        key = k
    }

    def setValue ( v ) {
        value = v
    }

    def getKey () {
        key
    }

    def getValue () {
        value
    }

    def matchKey ( k ) {
        key == k
    }

}
