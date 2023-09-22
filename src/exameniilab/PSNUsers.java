package exameniilab;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class PSNUsers {

    private RandomAccessFile RAF;
    private Entry puntero;
    private int size = 0;
    private HashTable userPositions;

    public PSNUsers() throws IOException {
        try {
            RAF = new RandomAccessFile("psn.txt", "rw");
            this.userPositions = new HashTable();
            reloadHashTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private void reloadHashTable() {
        size = 0;
        try {
            RAF.seek(0);
            userPositions = new HashTable();
            while (RAF.getFilePointer() < RAF.length()) {
                String username = RAF.readUTF();
                int trophyPoints = RAF.readInt();
                int trophiesCount = RAF.readInt();
                boolean registered = RAF.readBoolean();
                System.out.println("[reload] " + username + " regged: " + registered);

                if (!registered) {
                    System.out.println("[reload] " + username + " not regged. SKIPPING");
                    continue;
                }

                userPositions.add(username, size);
                size++;
            }
            System.out.println("[reload] Size: " + userPositions.size);

        } catch (Exception e) {
            System.out.println("error");
        }
}
    
    public void addUser(String username) throws IOException {
        if (userPositions.search(username) != -1){
            System.out.println("YA EXISTE");
            return;
        }
        System.out.println("prueba");
        RAF.seek(RAF.length());
        long pos = RAF.length();
        RAF.writeUTF(username);// NOMBRE
        userPositions.add(username, pos);
        RAF.writeBoolean(true); 
        RAF.writeInt(0); //PUNTOS
        RAF.writeInt(0); //Trofeo
            JOptionPane.showMessageDialog(null, "Se agregó " + username + " correctamente.");
    }


    //Buscar un usuario, si se encuentra, se escribe en disco como no activo y además se borra su registro en la HashTable
    public void deactivateUser(String username) {
        RAF.seek(0);
        while (RAF.getFilePointer() < RAF.length()) {
            long pos = RAF.getFilePointer(); // Guarda la posición actual
            String user = RAF.readUTF();

            if (user.equals(username)) {
                RAF.seek(pos); 
                RAF.readUTF(); 
                RAF.writeBoolean(false); 
                JOptionPane.showMessageDialog(null, "Usuario desactivado");           
                System.out.println("Usuario desactivado");
                return; 
            } else {
                RAF.readBoolean();
                RAF.readInt(); 
                RAF.readInt(); 
            }
        }
    JOptionPane.showMessageDialog(null, "No existe");
    System.out.println("El usuario no existe");
    }
    
    /*
    addTrophieTo(String username, String trophyGame, String trophyName, Trophy type). Busca un usuario con ese username, si se encuentra se le adiciona un trofeo. Los trofeos se guardan en un archivo llamado psn. El formato que se guarda por trofeo es:
            Código del User que se ganó el trofeo.
            String – tipo del trofeo.
            Nombre del juego al que pertenece trofeo.
            Nombre del trofeo.
            Fecha cuando se lo gano. 15%
            También recordar, aumentar el contador de trofeos y acumulador de puntos del usuario.
     */
   
      public void addTrophyTo(String username, String game, String nombretrofeo,  String Description) throws IOException {
        trofeos.seek(trofeos.length());

        Calendar calendar = Calendar.getInstance();
        int año = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        String fecha = dia + "/" + mes + "/" + año;

        String info = "Juego: " + game + " Tipo: " + nombretrofeo + " Fecha:" + fecha + " Descripcion: " + Description;

        if (userPositions.search(username) != -1) {
            trofeos.writeUTF(username);
            trofeos.writeUTF(info);
            JOptionPane.showMessageDialog(null, "Trofeo Agregado Exitosamente!");
        } else {
            JOptionPane.showMessageDialog(null, "Error: user no encontrado");
        }

    }
    
    public void playerInfo(String username) throws IOException {
        long pos = userPositions.search(username);
        if (pos != -1) {
            System.out.println("Usuario: " + username);
            RAF.seek(pos);
            String storedUsername = RAF.readUTF();
            boolean isActive = RAF.readBoolean();

            if (isActive) {
                int trophyCount = RAF.readInt();
                int points = RAF.readInt();

                System.out.println("Conteo de Trofeos: " + trophyCount);
                System.out.println("Puntos: " + points);

                int trophyCountStored = RAF.readInt();
                if (trophyCountStored > 0) {
                    System.out.println("Trofeos:");
                    for (int i = 0; i < trophyCountStored; i++) {
                        String trophyType = RAF.readUTF();
                        String trophyGame = RAF.readUTF();
                        String trophyName = RAF.readUTF();
                        String trophyDate = RAF.readUTF();

                        System.out.println(trophyDate + " - " + trophyType + " - " + trophyGame + " - " + trophyName);
                    }
                } else {
                    System.out.println("No hay trofeos.");
                }
            } else {
                System.out.println("Usuario deactivado.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
}
