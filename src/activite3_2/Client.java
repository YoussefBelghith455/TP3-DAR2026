
package activite3_2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String ipServeur = args.length > 0 ? args[0] : "127.0.0.1";

        try (Socket socket = new Socket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress adresseServeur = InetAddress.getByName(ipServeur);
            InetSocketAddress adresseSocket = new InetSocketAddress(adresseServeur, 1235);
            socket.connect(adresseSocket);

            ObjectOutputStream sortie = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entree = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connecte au serveur calculatrice multithread");

            String messageServeur = (String) entree.readObject();
            System.out.println(messageServeur);

            while (true) {
                System.out.print("Entrez une operation (ex: 25 * 55) ou quitter : ");
                String saisie = scanner.nextLine();

                if (saisie.equalsIgnoreCase("quitter")) {
                    break;
                }

                String[] elements = saisie.trim().split("\\s+");

                if (elements.length != 3) {
                    System.out.println("Format invalide");
                    continue;
                }

                try {
                    double nombre1 = Double.parseDouble(elements[0]);
                    String operateur = elements[1];
                    double nombre2 = Double.parseDouble(elements[2]);

                    Operation operation = new Operation(nombre1, operateur, nombre2);

                    sortie.writeObject(operation);
                    sortie.flush();

                    double resultat = (Double) entree.readObject();
                    System.out.println("Resultat : " + resultat);

                } catch (NumberFormatException e) {
                    System.out.println("Nombres invalides");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
