package exameniilab;

import javax.swing.JOptionPane;

public class HashTable {

      private Entry head;
      public int size=0;
      
    public HashTable() {
        this.head = null;
    }

    public void add(String username, long pos) {
        Entry newEntry = new Entry(username, pos);
        if (head == null) {
            head = newEntry;
        } else {
            Entry current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newEntry;
            size++;
        }
    }

    public void remove(String username) {
        if (head == null) {
            return;
        }
        if (head.username.equals(username)) {
            head = head.next;
            return;
        }
        Entry current = head;
        while (current.next != null && !current.next.username.equals(username)) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
        }
        size--;
    }

    public long search(String username) {
        Entry current = head;
        while (current != null) {
            if (current.username.equals(username)) {
                return current.position;
            }
            current = current.next;
        }
        return -1;
    }
}

