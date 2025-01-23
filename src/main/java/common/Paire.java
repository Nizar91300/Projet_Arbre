package common;

public class Paire<T1, T2> {
    private T1 first;  // First element of the pair
    private T2 second; // Second element of the pair

    // Constructor
    public Paire(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public Paire() {
        this.first = null;
        this.second = null;
    }

    // Getter for the first element
    public T1 getKey() {
        return first;
    }

    // Setter for the first element
    public void setKey(T1 first) {
        this.first = first;
    }

    // Getter for the second element
    public T2 getValue() {
        return second;
    }

    // Setter for the second element
    public void setValue(T2 second) {
        this.second = second;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}