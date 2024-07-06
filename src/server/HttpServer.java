package src.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ServerSocket serverSocket;

    public HttpServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() {
        try {
            Socket socket = this.serverSocket.accept();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
