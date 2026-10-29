
package serverPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final int numeroClient;

    public ClientHandler(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }

    @Override
    public void run() {
        try (Socket client = socket;
             BufferedReader entree = new BufferedReader(
                     new InputStreamReader(client.getInputStream()));
             PrintWriter sortie = new PrintWriter(
                     client.getOutputStream(), true)) {

            sortie.println("Vous etes le client n°" + numeroClient);

            String operation;

            while ((operation = entree.readLine()) != null) {

                if (operation.equalsIgnoreCase("quitter")) {
                    break;
                }

                System.out.println("Client n°" + numeroClient
                        + " - Operation recue : " + operation);

                String[] elements = operation.trim().split("\\s+");
                String resultat;

                if (elements.length != 3) {
                    resultat = "Erreur : format invalide";
                } else {
                    try {
                        double nombre1 = Double.parseDouble(elements[0]);
                        String operateur = elements[1];
                        double nombre2 = Double.parseDouble(elements[2]);

                        switch (operateur) {
                            case "+":
                                resultat = String.valueOf(nombre1 + nombre2);
                                break;
                            case "-":
                                resultat = String.valueOf(nombre1 - nombre2);
                                break;
                            case "*":
                                resultat = String.valueOf(nombre1 * nombre2);
                                break;
                            case "/":
                                resultat = nombre2 == 0
                                        ? "Erreur : division par zero"
                                        : String.valueOf(nombre1 / nombre2);
                                break;
                            default:
                                resultat = "Erreur : operateur invalide";
                        }

                    } catch (NumberFormatException e) {
                        resultat = "Erreur : nombres invalides";
                    }
                }

                sortie.println(resultat);
                System.out.println("Client n°" + numeroClient
                        + " - Resultat : " + resultat);
            }

            System.out.println("Client n°" + numeroClient
                    + " deconnecte");

        } catch (IOException e) {
            System.out.println("Erreur client n°"
                    + numeroClient + " : " + e.getMessage());
        }
    }
}
