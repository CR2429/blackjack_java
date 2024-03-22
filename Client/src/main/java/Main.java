import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            //demander une connexion avec un serveur
            String ip = "localhost";
            int port = Integer.parseInt("8080");

            Socket socket = new Socket(ip,port);

            //Recuperer les flux
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //recuperer le message de bienvenue
            String bienvenue = socketIn.readLine();
            System.out.println(bienvenue);

            //Jeu
            while (true) {
                //recuperer le message du serveur
                String message = socketIn.readLine();

                //detect la fermeture du serveur
                if (Objects.equals(message, "BREAK")) {
                    break;
                } else
                if (Objects.equals(message, "INPUT")) {
                    Scanner scanner = new Scanner(System.in);
                    String reponse = scanner.nextLine();
                    socketOut.printf("%s\n", reponse);
                } else {
                    //afficher le message et envoyer l'input
                    System.out.println(message);
                }
            }

            //fermer la connexion avec le serveur
            socket.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}