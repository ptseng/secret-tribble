

// key/value pair for record storage
class Data {

    private def int key;
    private def Object value;

    public static void main ( String [] args ) {

    }

    public Data ( k = -1, v = null ) {
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
