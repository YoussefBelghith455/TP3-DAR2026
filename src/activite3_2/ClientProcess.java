
package activite3_2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientProcess implements Runnable {

    private static int compteurGlobal = 0;

    private final Socket socket;
    private final int numeroClient;

    public ClientProcess(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }

    private static synchronized void incrementerCompteur(int numeroClient) {
        compteurGlobal++;
        System.out.println("Client n°" + numeroClient
                + " - Nombre total d'operations traitees : " + compteurGlobal);
    }

    @Override
    public void run() {
        try (Socket client = socket;
             ObjectOutputStream sortie = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream entree = new ObjectInputStream(client.getInputStream())) {

            sortie.writeObject("Vous etes le client n°" + numeroClient);
            sortie.flush();

            while (true) {
                Object objet = entree.readObject();

                if (!(objet instanceof Operation)) {
                    break;
                }

                Operation operation = (Operation) objet;
                double nombre1 = operation.getNombre1();
                double nombre2 = operation.getNombre2();
                String operateur = operation.getOperateur();

                double resultat;

                switch (operateur) {
                    case "+":
                        resultat = nombre1 + nombre2;
                        break;
                    case "-":
                        resultat = nombre1 - nombre2;
                        break;
                    case "*":
                        resultat = nombre1 * nombre2;
                        break;
                    case "/":
                        resultat = nombre2 != 0 ? nombre1 / nombre2 : Double.NaN;
                        break;
                    default:
                        resultat = Double.NaN;
                }

                sortie.writeObject(resultat);
                sortie.flush();

                incrementerCompteur(numeroClient);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client n°" + numeroClient
                    + " deconnecte : " + e.getMessage());
        }
    }
}
