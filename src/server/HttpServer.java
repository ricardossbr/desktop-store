package src.server;

import src.domain.Product;
import src.service.ProductService;
import src.service.impl.ProductServiceImpl;
import src.viewers.ConsoleColors;
import src.viewers.impl.menu.ListProduct;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.HttpRequest;
import java.util.List;

public class HttpServer {



    public HttpServer() throws IOException {

    }

    public void start() {
        try(
            final var serverSocket = new ServerSocket(8080);
            final var clientSocket = serverSocket.accept();
            final var out = new PrintStream(clientSocket.getOutputStream(), true);
            final var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            final String greeting = in.readLine();
            if ("GET / HTTP/1.1".equals(greeting)) {
                final ProductService service = new ProductServiceImpl();
                final List<Product> products = service.getProducts();
                products.forEach(product -> out.println(product.getName()));
            } else {
                out.println("unrecognised greeting");
            }
        } catch (IOException e) {
            ConsoleColors.printError("There was an error while start from the server");
        }

    }
}
