package exameniilab;

import java.io.IOException;

public class Main {
    
     public static void main(String[] args) {
        try{
            PSNUsers psnUsers = new PSNUsers();

            psnUsers.addUser("Rebeca");
            psnUsers.addUser("Azalia");

            psnUsers.addTrophieTo("Rebeca", "Juego1", "Trofeo1", Trophy.PLATINO);
            psnUsers.addTrophieTo("Azalia", "Juego2", "Trofeo2", Trophy.ORO);

            System.out.println("Información del usuario 1:");
            psnUsers.playerInfo("Rebeca");

            psnUsers.deactivateUser("Azalia");

            System.out.println("\nInformación del usuario 2 después de desactivación:");
            psnUsers.playerInfo("Azalia");
        }catch(IOException e){
            System.out.println("Error");
        }
     }
}
