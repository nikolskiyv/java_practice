package io.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    private final Map<String, String> headers;
    private String body;
    private HttpStatus status;

    public ResponseBuilder() {
        headers = new HashMap<>();
    }

    public ResponseBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public ResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    /*
        Пример ответа на запрос
    /*
        HTTP/1.1 200
        status: 200
        content-type: application/json
        cache-control: no-cache,no-store,max-age=0,must-revalidate
        {"status": "sucess"}
    */

    private static void writeLine(OutputStream outputStream, String line) {
        /* Функция печатает строку в заданый поток вывода */
        try {
            outputStream.write(line.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(OutputStream output) throws IOException {
        if(this.status == null){
            throw new IllegalArgumentException("Please, provide HttpStatus");
        }
        if(this.body == null){
            throw new IllegalArgumentException("Please, provide body");
        }

        // Строка ответа вида HTTP/1.1 <status_code>
        writeLine(output, String.format("HTTP/1.1 %d\n", this.status.getCode()));

        // Заголовки ответа в формате <ключ>: <значение>
        headers.forEach((key, value) -> writeLine(output, String.format("%s: %s\n", key, value)));

        // Тело запроса
        writeLine(output, "\n");
        writeLine(output, body);
        writeLine(output, "\n\n");
    }

    public static void write404(OutputStream output) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder
                .setStatus(HttpStatus.NOT_FOUND)
                .setBody("<h1>Page not found</h1>")
                .addHeader("cache-control", "no-cache,no-store,max-age=0,must-revalidate")
                .addHeader("content-type", "text/html; charset=UTF-8")
                .write(output);
    }

    public static void writeError(OutputStream output, Exception exception) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder
                .setStatus(HttpStatus.SERVER_ERROR)
                .setBody("<h1>Server error</h1><br /><pre>"+exception.getMessage()+"</pre>")
                .addHeader("cache-control", "no-cache,no-store,max-age=0,must-revalidate")
                .addHeader("content-type", "text/html; charset=UTF-8")
                .write(output);
    }

    public static void writeSuceess(OutputStream output) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder
                .setStatus(HttpStatus.OK)
                .addHeader("cache-control", "no-cache,no-store,max-age=0,must-revalidate")
                .addHeader("content-type", "application/json")
                .setBody("{\"status\": \"sucess\"}")
                .write(output);
    }

    public static void writeFailure(OutputStream output) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder
                .setStatus(HttpStatus.OK)
                .addHeader("cache-control", "no-cache,no-store,max-age=0,must-revalidate")
                .addHeader("content-type", "application/json")
                .setBody("{\"status\": \"failed\"}")
                .write(output);
    }
}
