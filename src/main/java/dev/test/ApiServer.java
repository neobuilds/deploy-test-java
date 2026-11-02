package dev.test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ApiServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        String p = System.getenv("PORT");
        if (p != null && !p.isBlank()) {
            port = Integer.parseInt(p.trim());
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/health/ready", exchange -> answer(exchange, "{\"status\":\"ready\"}"));
        server.createContext("/", exchange -> answer(exchange, "hello from java\n"));
        server.setExecutor(null);
        server.start();
    }

    private static void answer(HttpExchange exchange, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }
    }
}
