package src.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpGet {

    private final int port;

    public HttpGet(int port){
        this.port = port;
        this.start();
    }

    public void start() {
        try(final ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                final Socket client = serverSocket.accept();
                client.setSoTimeout(0);
                new Thread(() -> {
                    try {
                        final InputStream inputStream = client.getInputStream();
                        final OutputStream outputStream = client.getOutputStream();

                        final HttpRequest httpRequest = parseMetadata(inputStream);

                        switch (httpRequest.getMethod()) {
                            case "GET":
                                handleGetRequest(httpRequest, outputStream);
                                break;
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public HttpRequest parseMetadata(InputStream data) throws IOException {
        final List<String> metadataLines = new ArrayList<>();

        final StringBuilder lineBuilder = new StringBuilder();
        int b;
        boolean wasNewLine = false;

        while ((b = data.read()) >= 0) {
            if (b == '\r') {
                int next = data.read();
                if (next == '\n') {
                    if (wasNewLine) {
                        break;
                    }
                    wasNewLine = true;
                    metadataLines.add(lineBuilder.toString());
                    lineBuilder.setLength(0);
                }
            } else {
                lineBuilder.append((char) b);
                wasNewLine = false;
            }
        }

        final String firstLine = metadataLines.get(0);
        final String method = firstLine.split("\\s+")[0];
        final String url = firstLine.split("\\s+")[1];

        final Map<String, String> headers = new HashMap<>();

        for (int i = 1; i < metadataLines.size(); i++) {
            String headerLine = metadataLines.get(i);
            if (headerLine.trim().isEmpty()) {
                break;
            }

            String key = headerLine.split(":\\s")[0];
            String value = headerLine.split(":\\s")[1];

            headers.put(key, value);
        }

        return new HttpRequest(method, url, headers);
    }

    public void handleGetRequest(HttpRequest request, OutputStream outputStream) throws IOException {
        String fileName = request.getUrl();
        final StringBuilder responseMetadata = new StringBuilder();
        responseMetadata.append("HTTP/1.1 200 OK\r\n");

        responseMetadata.append(String.format("Content-Type: %s\r\n", fileName));

        responseMetadata.append("\r\n");

        outputStream.write(responseMetadata.toString().getBytes(StandardCharsets.UTF_8));
    }

    class HttpRequest {
        private final String method;
        private final String url;
        private final Map<String, String> headers;

        HttpRequest(String method, String url, Map<String, String> headers) {
            this.method = method;
            this.url = url;
            this.headers = headers;
        }

        public String getMethod() {
            return method;
        }

        public String getUrl() {
            return url;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }
    }
}
