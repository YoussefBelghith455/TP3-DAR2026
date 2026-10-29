
package activite3_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int numeroClient = 0;

        try (ServerSocket serverSocket = new ServerSocket(1235)) {
            System.out.println("Serveur calculatrice multithread demarre sur le port 1235");

            while (true) {
                Socket socket = serverSocket.accept();
                numeroClient++;

                System.out.println("Client n°" + numeroClient
                        + " connecte : " + socket.getRemoteSocketAddress());

                ClientProcess clientProcess = new ClientProcess(socket, numeroClient);
                Thread thread = new Thread(clientProcess);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
