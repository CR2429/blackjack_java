import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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

        //demarrer le serveur

    }
}
