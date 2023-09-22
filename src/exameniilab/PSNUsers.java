package exameniilab;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class PSNUsers {

    private RandomAccessFile RAF;
    private Entry puntero;
    private int size = 0;
    private HashTable userPositions;

    public PSNUsers() throws IOException {
        try {
            RAF = new RandomAccessFile("psn", "rw");
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
            e.printStackTrace();
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


    /*
         Agrega un nuevo registro al archivo, al agregarlo también se le agrega un elemento a la HashTable con los datos del usuario nuevo. Los datos de un usuario (usar dicho orden como formato) son:
                Username que viene de parámetro y que sé válida que sea ÚNICO.
                Por default tiene el acumulador de puntos por trofeos en 0.
                Por default tiene el contador de trofeos en 0.
                Por default el registro está activado. Si un registro tiene el dato activado en false se CONSIDERA BORRADO
     */

    //Buscar un usuario, si se encuentra, se escribe en disco como no activo y además se borra su registro en la HashTable
    public void deactivateUser(String username) {
        userPositions.remove(username);
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
    public void addTrophieTo(String username, String trophyGame, String trophyName, Trophy trophyType) throws IOException {
        long pos = userPositions.search(username);
        if (pos != -1) {
            // Obtener la posición del usuario en el archivo
            RAF.seek(pos);

            // Leer los datos del usuario
            int userCode = RAF.readInt();
            boolean isActive = RAF.readBoolean();
            String currentUsername = RAF.readUTF();
            int trophyPoints = RAF.readInt();
            int trophyCount = RAF.readInt();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentDate = sdf.format(new Date());
            try (RandomAccessFile archivoTrofeo = new RandomAccessFile("psn.dat", "rw")) {
                archivoTrofeo.seek(archivoTrofeo.length());
                archivoTrofeo.writeInt(userCode);
                archivoTrofeo.writeUTF(trophyType.name()); // Guardar el nombre del enum
                archivoTrofeo.writeUTF(trophyGame);
                archivoTrofeo.writeUTF(trophyName);
                archivoTrofeo.writeUTF(currentDate);
                JOptionPane.showMessageDialog(null, "Se agregó el trofeo para " + username + " correctamente.");

                RAF.seek(pos);
                RAF.writeInt(userCode);
                RAF.writeBoolean(isActive);
                RAF.writeUTF(currentUsername);
                RAF.writeInt(trophyCount + 1);
                RAF.writeInt(trophyPoints + 1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
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
