
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws Throwable {
        ServerSocket socket = new ServerSocket(9090);
        while (true) {
            Socket acceptedSocket = socket.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(acceptedSocket)).start();
        }
   }
}
