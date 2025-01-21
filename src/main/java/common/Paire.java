package common;

public class Paire<K, V> {
    private K key;
    private V value;

    // Constructeurs
    public Paire() {}

    public Paire(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // Getters et setters
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