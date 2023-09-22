package exameniilab;

public class Entry {

    public String username;
    public long position;
    public Entry next;

    public Entry(String username, long position) {
        this.username = username;
        this.position = position;
        this.next = null;
    }

    //Getters de los atributos
    public String getUsername() {
        return username;
    }

    public long getPosition() {
        return position;
    }

    public Entry getNext() {
        return next;
    }

    // Setters de los atributos
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public void setNext(Entry next) {
        this.next = next;
    }

}
