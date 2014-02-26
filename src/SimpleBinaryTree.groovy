public class SimpleBinaryTree<K extends Comparable<K>, V>
{
    private BSTNode<K,V> root

    public SimpleBinaryTree()   { root = null }

    //SIZE
    public int size() {
        return(sub_size(root))
    }

    private int sub_size(BSTNode<K,V> n) {
        if (n == null) return(0);
        else {
            return(sub_size(n.getLeft()) + 1 + sub_size(n.getRight()))
        }
    }

    //INSERT
    public void insert(K key, V value) {
        root = sub_insert(root, key, value)
    }

    private BSTNode<K,V> sub_insert(BSTNode<K,V> n, K key, V value)
    {
        if (n == null)
        {
            return new BSTNode<K,V>(key, value, null, null)
        }

        if (key.compareTo(n.getKey()) == 0)
        {
            n.setValue(value)
            return n
        }

        if (key.compareTo(n.getKey()) < 0) {
            n.setLeft( sub_insert(n.getLeft(), key, value) )
            return n;
        }
        else {
            n.setRight( sub_insert(n.getRight(), key, value) )
            return n;
        }
    }

    //LOOKUP RETURNING BOOLEAN
    public boolean lookup(K key) {
        return sub_lookup(root, key)
    }

    private boolean sub_lookup(BSTNode<K,V> n, K key) {

        if (n == null) {
            return false
        }

        if (n.getKey().compareTo(key) == 0) {
            return true
        }

        if (key.compareTo(n.getKey()) < 0) {
            return sub_lookup(n.getLeft(), key)
        }
        else {
            return sub_lookup(n.getRight(), key)
        }
    }

    //RETURN MAX DEPTH
    public int maxDepth()
    {
        return sub_maxdepth(root);
    }

    private int sub_maxdepth(BSTNode<K,V> n)
    {
        if ( n == null )
            return 0

        int leftDepth = sub_maxdepth(n.getLeft())
        int rightDepth = sub_maxdepth(n.getRight())

        if (leftDepth > rightDepth)
            return (leftDepth + 1);
        else
            return (rightDepth + 1);
    }

    //RETURN MIN DEPTH
    public int minDepth()
    {
        return sub_mindepth(root, 0);
    }

    private int sub_mindepth(BSTNode<K,V> n, int depth)
    {
        if (n.getLeft() == null && n.getRight() == null)
            return depth;

        int x = (n.getLeft() != null) ? sub_mindepth(n.getLeft(), depth+1) : depth;
        int y = (n.getRight() != null) ? sub_mindepth(n.getRight(), depth+1) : depth;

        return (x < y) ? x : y;
    }

    //COUNT TRAVERSALS
    public int getTraversals(K key)
    {
        return sub_traversals(root, key)
    }

    private int sub_traversals(BSTNode<K,V> n, K key)
    {
        if (n == null) {
            return 0
        }

        if (key.compareTo(n.getKey()) == 0) {
            return 0
        }

        if (key.compareTo(n.getKey()) < 0) {
            return 1 + sub_traversals(n.getLeft(), key)
        }
        else {
            return 1 + sub_traversals(n.getRight(), key)
        }
    }

    //GET
    public V get(K key) {
        return sub_get(root, key)
    }

    private V sub_get(BSTNode<K,V> n, K key) {
        if (n == null) {
            return null
        }

        if (key.compareTo(n.getKey()) == 0) {
            return n.getValue()
        }

        if (key.compareTo(n.getKey()) < 0) {
            return sub_get(n.getLeft(), key)
        }
        else {
            return sub_get(n.getRight(), key)
        }
    }

    //ReturnAllKeys
    public K[] returnKeys()
    {
        return returnKeyInOrder(root)
    }

    private K[] returnKeyInOrder(BSTNode<K,V> n) {
        def keyarray = []

        if (n != null) {
            keyarray.addAll(returnKeyInOrder(n.getRight()))
            keyarray.add(n.key)
            keyarray.addAll(returnKeyInOrder(n.getLeft()))
        }

        return keyarray
    }

    //ReturnAllValues
    public V[] returnValues()
    {
        return returnValuesInOrder(root)
    }

    private V[] returnValuesInOrder(BSTNode<K,V> n) {
        def valuearray = []

        if (n != null) {
            valuearray.addAll(returnValuesInOrder(n.getRight()))
            valuearray.add(n.value)
            valuearray.addAll(returnValuesInOrder(n.getLeft()))
        }

        return valuearray
    }

    //ReturnKVPair
    public Map<K,V> returnKeyValues()
    {
        return returnKeyValuesInOrder(root)
    }

    private Map<K,V> returnKeyValuesInOrder(BSTNode<K,V> n)
    {
        def keyvaluemap = [:]

        if (n != null) {
            keyvaluemap.putAll(returnKeyValuesInOrder(n.getRight()))
            keyvaluemap.put(n.key, n.value)
            keyvaluemap.putAll(returnKeyValuesInOrder(n.getLeft()))
        }

        return keyvaluemap
    }
}

class BSTNode<K, V> {

    private K key
    private V value
    private BSTNode<K, V> left, right

    public BSTNode(K key, V value, BSTNode<K,V> left, BSTNode<K,V> right)
    {
        this.key = key
        this.value = value
        this.left = left
        this.right = right
    }

    public K getKey() { return key }
    public V getValue() { return value }
    public BSTNode<K,V> getLeft() { return left }
    public BSTNode<K,V> getRight() { return right }

    public void setKey(K newK) { key = newK }
    public void setValue(V newV) { value = newV }
    public void setLeft(BSTNode<K,V> newL) { left = newL }
    public void setRight(BSTNode<K,V> newR) { right = newR }
}

