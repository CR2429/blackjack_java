import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        ServerSocket serverSocket;
        Socket clientSocket;
        InetAddress inetAddress = InetAddress.getByName("localhost");
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
                serverSocket = new ServerSocket(port);

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
                    //nouvelle parti
                    String new_game = "** Nouvelle Parti **";

                    //brassage des cartes
                    PaquetDeCarte laPioche = new PaquetDeCarte();
                    laPioche.brasser();

                    //donner les deux premier carte au joueur
                    MainBlackjack joueur = new MainBlackjack();
                    joueur.ajouterCarte(laPioche.piger());
                    joueur.ajouterCarte(laPioche.piger());

                    socketOut.println(Arrays.toString(joueur.getMain()));
                    socketOut.println("Valeur de la main : " + joueur.getValeurTotale());

                    //boucle pour savoir si le joueur pige ou pas
                    while (true) {
                        //question
                        socketOut.println("Souhaiter vous piger une nouvelle carte? [n / o] ");

                        //recuperer la reponse
                        socketOut.println("INPUT");
                        String choice = socketIn.readLine();

                        //traiter le choix
                        if (Objects.equals(choice, "o")) { //Reponse positif
                            System.out.println("INFO : Le joueur pige une nouvelle carte");

                            //pioche la carte du joueur
                            joueur.ajouterCarte(laPioche.piger());

                            //Afficher les messages
                            socketOut.println(Arrays.toString(joueur.getMain()));
                            socketOut.println("Valeur de la main : " + joueur.getValeurTotale());
                        } else if (Objects.equals(choice, "n")) { //fin de notre tour
                            System.out.println("\nINFO : Le joueur a fini de piger");
                            break;
                        } else {
                            System.out.println("INFO: Le joueur a fait une erreur dans son choix");
                            System.out.printf("DEBUG: %s", choice);
                            socketOut.println("Reponse non valide");
                        }

                        if (joueur.estPleine() || joueur.estBrulee()) {
                            socketOut.println(Arrays.toString(joueur.getMain()));
                            socketOut.println("Valeur de la main : " + joueur.getValeurTotale());
                            socketOut.println("Vous avez atteint la limite de carte. Au tour du croupier");
                        }
                    }


                    //Le joueur a fini de piocher
                    //Au tour du croupier
                    MainBlackjack croupier = new MainBlackjack();
                    croupier.ajouterCarte(laPioche.piger());
                    croupier.ajouterCarte(laPioche.piger());

                    //boucle pour verifier si le croupier pige toujour ou pas
                    while (true) {
                        if (croupier.getValeurTotale() < 16 && !croupier.estPleine())
                            croupier.ajouterCarte(laPioche.piger());
                        else break;
                    }

                    //afficher valeur du croupier
                    socketOut.println("Main du croupier: " + Arrays.toString(croupier.getMain()));
                    socketOut.println("Valeur de la main : " + croupier.getValeurTotale());

                    //calcul fin de partie
                    if (croupier.estBrulee() && !joueur.estBrulee())
                        socketOut.println("Le croupier a bruler sa main. Le joueur a gagner.");
                    if (!croupier.estBrulee() && joueur.estBrulee())
                        socketOut.println("Le joueur a bruler sa main. Le croupier a gagner.");
                    if (croupier.estBrulee() && joueur.estBrulee()) {
                        if (joueur.getValeurTotale() > croupier.getValeurTotale())
                            socketOut.println("Vous avez tout les deux bruler votre main. Cependant le croupier a une main plus proche de 21. Il gagne donc la partie.");
                        if (joueur.getValeurTotale() < croupier.getValeurTotale())
                            socketOut.println("Vous avez tout les deux bruler votre main. Cependant vous avez une main plus proche de 21. Vous gagnez donc la partie.");
                        if (joueur.getValeurTotale() == croupier.getValeurTotale())
                            socketOut.println("Vous avez tout les deux bruler votre main. Vous avez aussi une main identique. Match nul");
                    }
                    if (!croupier.estBrulee() && !joueur.estBrulee()) {
                        if (joueur.getValeurTotale() > croupier.getValeurTotale())
                            socketOut.println("Le croupier a une main plus proche de 21. Il gagne donc la partie.");
                        if (joueur.getValeurTotale() < croupier.getValeurTotale())
                            socketOut.println("Vous avez une main plus proche de 21. Vous gagnez donc la partie.");
                        if (joueur.getValeurTotale() == croupier.getValeurTotale())
                            socketOut.println("Vous avez aussi une main identique. Match nul");
                    }

                    //compilation du resultat
                    try {
                        Gson gson = new Gson();
                        String json1 = gson.toJson(croupier, MainBlackjack.class);
                        String json2 = gson.toJson(joueur, MainBlackjack.class);
                        String timesStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                        FileWriter writer = new FileWriter(timesStamp+".json");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    //boucle pour savoir si le joueur fait une nouvelle partie ou pas
                    boolean start_new_game = true;
                    while (true) {
                        //question
                        socketOut.println("Souhaiter une nouvelle parti? [n / o] ");

                        //recuperer la reponse
                        socketOut.println("INPUT");
                        String choice = socketIn.readLine();

                        //traiter le choix
                        if (Objects.equals(choice, "o")) { //Reponse positif
                            System.out.println("INFO : nouvvel patie");
                            break;
                        } else if (Objects.equals(choice, "n")) { //fin de notre tour
                            System.out.println("\nINFO : Fin des toutes les partis");
                            start_new_game = false;
                            break;
                        } else {
                            System.out.println("INFO: Le joueur a fait une erreur dans son choix");
                            System.out.printf("DEBUG: %s", choice);
                            socketOut.println("Reponse non valide");
                        }
                    }

                    if (!start_new_game) break;
                }

                socketOut.println("BREAK");

                //fin de session de jeu
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
