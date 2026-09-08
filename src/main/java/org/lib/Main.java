package org.lib;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.lib.auth.Auth;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        Auth auth = new Auth();
        String username = "Florent";
        String password = "Hash";
        String hash = auth.register(username, password);
        System.out.printf("Cheeky Hash: %s\n", hash);
        Integer token = auth.login(username, password);
        String fetchedUsername = auth.getProfile(token);
        System.out.printf("Did it work: %s\n", fetchedUsername);
        String logout = auth.logout(token);
        System.out.printf("logout %s\n", logout);
        String fetchedUsername1 = auth.getProfile(token);
        System.out.printf("This should fail %s\n", fetchedUsername1);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/login", new MyHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Server is running on port 8000");
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
            throw new IOException(e);
        }

    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response\n";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}