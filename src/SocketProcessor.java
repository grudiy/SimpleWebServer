import java.io.*;
import java.net.Socket;

public class SocketProcessor implements Runnable{
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketProcessor(Socket socket) throws Throwable {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    private void readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while(true) {
            String string = br.readLine();
            if(string == null || string.trim().length() == 0) {
                break;
            }
        }
    }

    private void writeResponse(String string) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: GruandWebServer\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + string.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + string;
        outputStream.write(result.getBytes());
        outputStream.close();
    }


    public void run() {
        try {
            readInputHeaders();
            writeResponse("<html><body><h1>Hello, Client! </h1></body></html>");
        } catch (Throwable t) {
                /*do nothing*/
        } finally {
            try {
                socket.close();
            } catch (Throwable t) {
                    /*do nothing*/
            }
        }
        System.err.println("Client processing finished");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
