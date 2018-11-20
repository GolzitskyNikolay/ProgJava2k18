package golzitsky.HomeTasks;

public class Pair<K, V> {
    private K key;
    private V value;
    private boolean wasDeleted;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
        this.wasDeleted = false;
    }

    public boolean isWasDeleted() {
        return wasDeleted;
    }

    public void setWasDeleted(boolean wasDeleted) {
        this.wasDeleted = wasDeleted;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
