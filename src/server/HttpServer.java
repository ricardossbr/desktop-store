package src.server;

import src.domain.Product;
import src.service.ProductService;
import src.service.impl.ProductServiceImpl;
import src.viewers.ConsoleColors;
import src.viewers.impl.menu.ListProduct;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final Socket clientSocket;
    private final PrintStream out;
    private final BufferedReader in;

    public HttpServer() throws IOException {
        this.serverSocket = new ServerSocket(8080);
        clientSocket = serverSocket.accept();
        out = new PrintStream(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void start() {
        try {
            final String greeting = in.readLine();
            final Runnable runnable = () -> {
                if ("GET / HTTP/1.1".equals(greeting)) {
                    final ProductService service = new ProductServiceImpl();
                    final List<Product> products = service.getProducts();
                    products.forEach(product -> out.println(product.getName()));
                } else {
                    out.println("unrecognised greeting");
                }
            };
            new Thread(runnable).start();
        } catch (IOException e) {
            ConsoleColors.printError("There was an error while start from the server");
        }

    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            ConsoleColors.printError("There was an error while stop from the server");
        }

    }
}
