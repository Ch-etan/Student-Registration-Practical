package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Serve the HTML form
        server.createContext("/", ex -> {
            try (InputStream in = App.class.getResourceAsStream("/index.html")) {
                send(ex, in.readAllBytes());
            }
        });

        // Handle form submission
        server.createContext("/register", ex -> {
            String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> f = new HashMap<>();
            for (String pair : body.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    f.put(kv[0], URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
                }
            }

            String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Success</title>"
                + "<style>body{font-family:'Segoe UI',Arial,sans-serif;min-height:100vh;display:flex;"
                + "align-items:center;justify-content:center;margin:0;"
                + "background:linear-gradient(135deg,#4f46e5,#7c3aed 60%,#db2777);}"
                + ".c{background:#fff;padding:36px;border-radius:16px;max-width:420px;width:90%;"
                + "box-shadow:0 20px 50px rgba(0,0,0,.25);}"
                + "h2{color:#16a34a;text-align:center;margin-top:0}p{color:#374151;margin:10px 0}"
                + "a{display:block;text-align:center;margin-top:20px;color:#4f46e5;font-weight:600;"
                + "text-decoration:none}</style></head><body><div class='c'>"
                + "<h2>✅ Registration Successful</h2>"
                + "<p><b>Name:</b> " + esc(f.get("name")) + "</p>"
                + "<p><b>Roll No:</b> " + esc(f.get("roll")) + "</p>"
                + "<p><b>Email:</b> " + esc(f.get("email")) + "</p>"
                + "<p><b>Course:</b> " + esc(f.get("course")) + "</p>"
                + "<a href=\"/\">← Register another student</a></div></body></html>";

            send(ex, html.getBytes(StandardCharsets.UTF_8));
        });

        server.start();
        System.out.println("Server running on http://localhost:8080");
    }

    private static void send(HttpExchange ex, byte[] data) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        ex.sendResponseHeaders(200, data.length);
        ex.getResponseBody().write(data);
        ex.close();
    }

    private static String esc(String s) {
        return s == null ? "" : s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}