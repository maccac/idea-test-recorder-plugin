package com.maccac.recorder;

import com.jcraft.jsch.Buffer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

public class TestHost {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8086), 0);
        server.createContext("/write", new MyHandler());
        server.start();
    }

    private static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            String body;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(t.getRequestBody()))) {
                body = reader.lines().collect(Collectors.joining("\n"));
            }
            System.out.println(body);

            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
