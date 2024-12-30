package src.server;

import com.sun.net.httpserver.HttpHandler;
import src.domain.Product;
import src.service.ProductService;
import src.service.impl.ProductServiceImpl;
import src.viewers.ConsoleColors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static src.viewers.ConsoleColors.printError;
import static src.viewers.ConsoleColors.printMessage;

public class HttpServer {

    private final int port;

    public HttpServer(int port ) {
        this.port = port;
    }

    public void start() {
        printError("Server listening on port " + port);
        try(ServerSocket server = new ServerSocket(port)) {
            final ProductService service = new ProductServiceImpl();
            while(true) {
                final List<Product> products = service.getProducts();
                final Socket socket = server.accept();
                final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String reponse;
                while((reponse = in.readLine()) != null) {
                    if(reponse.isEmpty()) {
                        break;
                    }
                }
                OutputStream out = socket.getOutputStream();
                out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                out.write("\r\n".getBytes());
                out.write("{\n".getBytes());
                products.stream().forEach(r -> {
                    try {
                        out.write(r.toJson().getBytes());
                        out.write(",\n".getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                out.write("}".getBytes());
                out.write("\r\n".getBytes());
                out.flush();
                out.close();
                socket.close();
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
