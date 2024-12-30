package src;

import src.server.HttpServer;
import src.viewers.MainMenu;

public class Store {
    public static void main(String[] arg) {
        MainMenu display = new MainMenu();
        HttpServer http = new HttpServer(8080);
        Runnable server = () -> {
            http.start();
        };
        Runnable app = () -> {
            display.handleMenu();
        };
        Thread threadApp = new Thread(app, "Application");
        Thread threadServer = new Thread(server, "Server");
        threadServer.start();
        threadApp.start();
    }
}


