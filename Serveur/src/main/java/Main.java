import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        ServerSocket serverSocket;
        Socket clientSocket;
        InetAddress inetAddress = InetAddress.getByName("224.214.26.78");
        int port = 8080;

        //demander l'addresse et le port de connexion
    //    Scanner scanner = new Scanner(System.in);
    //    System.out.printf("IP : ");
    //    scanner.nextLine();

    //    inetAddress = InetAddress.getByName(scanner.toString());

    //    System.out.printf("\nPort : ");
    //    scanner.nextLine();

    //    port = Integer.parseInt(scanner.toString());

        //boucle pour que le serveur tourne toujours
        while (true) {
            //demarrer le serveur
            try (DatagramSocket datagramSocket = new DatagramSocket()) {
                //lire le port
                serverSocket = new ServerSocket(8080);

                //connexion
                clientSocket = serverSocket.accept();
                System.out.printf("\nDEBUG: Connexion d'un client: %s\n", clientSocket.getInetAddress().getHostAddress());

                //lire les flux
                PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                //message de bienvenue
                socketOut.println("=* Bienvenue au blackjack *=");

                //jeu
                while (true) {
                    break;
                }

                //fin de session de jeu
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
