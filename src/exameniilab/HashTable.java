package exameniilab;

import javax.swing.JOptionPane;

public class HashTable {
    private Entry puntero;
    private int size=0;
    
    // Agrega un nuevo elemento al final de la lista
    public void add(String username, long pos) {
        Entry nuevop = new Entry(username, pos); //  nuevo puntero
        if (puntero == null) {
            puntero = nuevop; // Si la lista está vacia, el nuevo elemento es el primero
        } else {
            Entry actualp = puntero; // puntero actual
            while (actualp.getNext() != null) {
                actualp = actualp.getNext(); // Recorre la lista hasta el ultimo elemento
            }
            actualp.setNext(nuevop); // Agrega el nuevo elemento al final de la lista
            size++;
            JOptionPane.showMessageDialog(null, "Se agregó " + username + " correctamente.");
        }
    }
    
    //Remueve el elemento de la lista concuerde con el codigo
    public void remove(String username) {
        if (puntero == null) {
            return; // La lista está vacía, no hay nada que remover
        }
        if (puntero.getUsername().equals(username)) {
            puntero = puntero.getNext(); // Si el primer elemento coincide, actualizar la cabeza
            size--;
        } else {
            Entry current = puntero;
            while (current.getNext() != null) {
                if (current.getNext().getUsername().equals(username)) {
                    current.setNext(current.getNext().getNext()); // Saltar el elemento a remover
                    return;
                }
                current = current.getNext();
            }
        }
    }
    
      public long search(String username) {
        Entry current = puntero;
        while (current != null) {
            if (current.getUsername().equals(username)) {
                return current.getPosition(); // Se encontró el elemento, retorna la posición
            }
            current = current.getNext();
        }
        return -1; // El elemento no se encontró en la lista
    }
      
}

