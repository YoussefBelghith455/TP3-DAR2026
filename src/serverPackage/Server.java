
package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        int numeroClient = 0;

        try (ServerSocket serverSocket = new ServerSocket(1234)) {

            System.out.println("Serveur multithread demarre sur le port 1234");

            while (true) {

                Socket socket = serverSocket.accept();
                numeroClient++;

                System.out.println("Client n°" + numeroClient
                        + " connecte : "
                        + socket.getRemoteSocketAddress());

                ClientHandler handler =
                        new ClientHandler(socket, numeroClient);

                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
