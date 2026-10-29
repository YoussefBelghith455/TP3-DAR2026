
package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        System.out.println("Je suis un client pas encore connecté...");

        try {
            String ipServeur = args.length > 0 ? args[0] : "127.0.0.1";
            InetAddress adresseServeur = InetAddress.getByName(ipServeur);
            InetSocketAddress adresseSocket = new InetSocketAddress(adresseServeur, 1234);

            try (Socket socket = new Socket();
                 Scanner scanner = new Scanner(System.in)) {

                socket.connect(adresseSocket);

                BufferedReader entree = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Connecté au serveur.");

                String messageServeur = entree.readLine();
                System.out.println(messageServeur);

                while (true) {
                    System.out.print("Entrez une opération ou 'quitter' : ");
                    String operation = scanner.nextLine();

                    sortie.println(operation);

                    if (operation.equalsIgnoreCase("quitter")) {
                        break;
                    }

                    String resultat = entree.readLine();

                    if (resultat == null) {
                        System.out.println("Connexion fermée par le serveur.");
                        break;
                    }

                    System.out.println("Résultat : " + resultat);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
